package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfilUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    private DatabaseReference mProfil, mRefUserPhoto, mProfilUser;

    private ImageView mPhoto;
    private TextView mNbFollowers, mNbPhoto, mUserName;
    private String mUserID;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    GridView mUserPhoto;
    ArrayList<MainPhotoModel> mUserProfil;
    ProfilUserGridAdapter mUserAdapter;
    MainPhotoModel mModelPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        mPhoto = findViewById(R.id.profil_photo_user);
        mNbFollowers = findViewById(R.id.item_followers);
        mNbPhoto = findViewById(R.id.item_pictures);
        mUserName = findViewById(R.id.edit_name_user);
        Button boutonModifier = findViewById(R.id.button_modify_profil);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserID = mUser.getUid();
        mProfilUser = mDatabase.getReference("Users/" + mUserID + "/Profil/");

        final String profilId = getIntent().getStringExtra("idprofil");
        if (mUserID.equals(profilId)) {

            mProfil = mDatabase.getReference("Users/" + mUserID + "/Profil/");

            mProfil.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child("username").getValue() != null) {
                        String stringName = (String) dataSnapshot.child("username").getValue();
                        mUserName.setText(stringName);
                    }
                    if (dataSnapshot.child("nbfollowers").getValue() != null) {
                        String stringNbFollowers = dataSnapshot.child("nbfollowers").getValue().toString();
                        mNbFollowers.setText(stringNbFollowers);
                    }
                    if (dataSnapshot.child("nbphoto").getValue() != null) {
                        String stringNbPhotos = dataSnapshot.child("nbphoto").getValue().toString();
                        mNbPhoto.setText(stringNbPhotos);
                    }
                    if (dataSnapshot.child("photouser").getValue() != null) {
                        String stringUrl = (String) dataSnapshot.child("photouser").getValue();
                        Glide.with(ProfilUserActivity.this)
                                .load(stringUrl)
                                .into(mPhoto);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_user);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mRefUserPhoto = FirebaseDatabase.getInstance().getReference().child("Users/"+ mUserID +"/Photo/");
            mUserPhoto = findViewById(R.id.grid_view_user);
            mUserProfil = new ArrayList<>();
            mUserAdapter = new ProfilUserGridAdapter(this, mUserProfil);

            mRefUserPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUserProfil.clear();
                    for(DataSnapshot dsUser: dataSnapshot.getChildren()){
                        mModelPhoto = dsUser.getValue(MainPhotoModel.class);
                        mUserProfil.add(mModelPhoto);
                        mUserPhoto.setAdapter(mUserAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mUserPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainPhotoModel laPhoto = (MainPhotoModel) mUserPhoto.getItemAtPosition(position);
                    Intent goToUserPhoto = new Intent(ProfilUserActivity.this, UserPhoto.class);
                    goToUserPhoto.putExtra("keyphoto", laPhoto.getKeyphoto().toString());
                    goToUserPhoto.putExtra("idprofil", mUserID);
                    startActivity(goToUserPhoto);

                }
            });

        }
        else {
                mProfil = mDatabase.getReference("Users/" + profilId + "/Profil/");
                boutonModifier.setVisibility(View.INVISIBLE);
                final Button boutonAddFollowers = findViewById(R.id.button_add_followers);
                boutonAddFollowers.setVisibility(View.VISIBLE);


                // Fonction Follow
                final DatabaseReference refFollowers = mDatabase.getReference("Users/" + mUserID + "/Followers/" + profilId);
                refFollowers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            Boolean verifFollowers = (Boolean) dataSnapshot.getValue();
                            if (verifFollowers) {
                                boutonAddFollowers.setText(R.string.ne_plus_suivre);
                                boutonAddFollowers.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        refFollowers.setValue(false);
                                        boutonAddFollowers.setText(R.string.suivre_cette_personne);
                                    }
                                });
                            }
                            else {
                                boutonAddFollowers.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        refFollowers.setValue(true);
                                        boutonAddFollowers.setText(R.string.ne_plus_suivre);
                                    }
                                });
                            }
                        }
                        else {
                            boutonAddFollowers.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    refFollowers.setValue(true);
                                    boutonAddFollowers.setText(R.string.ne_plus_suivre);
                                }
                            });
                        }
                        mUserPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                MainPhotoModel laPhoto = (MainPhotoModel) mUserPhoto.getItemAtPosition(position);
                                Intent goToUserPhoto = new Intent(ProfilUserActivity.this, UserPhoto.class);
                                goToUserPhoto.putExtra("keyphoto", laPhoto.getKeyphoto().toString());
                                goToUserPhoto.putExtra("idprofil", profilId);
                                startActivity(goToUserPhoto);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                mProfil.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child("username").getValue() != null) {
                            String stringName = (String) dataSnapshot.child("username").getValue();
                            mUserName.setText(stringName);
                        }
                        if (dataSnapshot.child("nbfollowers").getValue() != null) {
                            String stringNbFollowers = dataSnapshot.child("nbfollowers").getValue().toString();
                            mNbFollowers.setText(stringNbFollowers);
                        }
                        if (dataSnapshot.child("nbphoto").getValue() != null) {
                            String stringNbPhotos = dataSnapshot.child("nbphoto").getValue().toString();
                            mNbPhoto.setText(stringNbPhotos);
                        }
                        if (dataSnapshot.child("photouser").getValue() != null) {
                            String stringUrl = (String) dataSnapshot.child("photouser").getValue();
                            Glide.with(ProfilUserActivity.this)
                                    .load(stringUrl)
                                    .into(mPhoto);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_user);
                mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
                mDrawerLayout.addDrawerListener(mToggle);
                mToggle.syncState();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mRefUserPhoto = FirebaseDatabase.getInstance().getReference().child("Users/" + profilId + "/Photo/");
            mUserPhoto = findViewById(R.id.grid_view_user);
            mUserProfil = new ArrayList<>();
            mUserAdapter = new ProfilUserGridAdapter(this, mUserProfil);

            mRefUserPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUserProfil.clear();
                    for(DataSnapshot dsUser: dataSnapshot.getChildren()){
                        mModelPhoto = dsUser.getValue(MainPhotoModel.class);
                        mUserProfil.add(mModelPhoto);
                        mUserPhoto.setAdapter(mUserAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            }

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_user);
            navigationView.setNavigationItemSelectedListener(this);

        // TODO remettre le menu burger

    }

    // -----------------------MENU BURGER DON'T TOUCH PLEASE !!!--------------------------

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intentHome = new Intent(ProfilUserActivity.this, MainActivity.class);
            startActivity(intentHome);
        } else if (id == R.id.profil) {
            Intent intentProfil = new Intent(ProfilUserActivity.this, ProfilUserActivity.class);
            intentProfil.putExtra("idprofil", mUserID);
            startActivity(intentProfil);
        } else if (id == R.id.followers) {
            Intent intentFollowers = new Intent(ProfilUserActivity.this, FollowersActivity.class);
            startActivity(intentFollowers);
        } else if (id == R.id.disconnect) {
            FirebaseAuth.getInstance().signOut();
            SaveSharedPreference.setUserName(ProfilUserActivity.this, "");
            Intent goToLoginActivity = new Intent(ProfilUserActivity.this,LoginActivity.class);
            startActivity(goToLoginActivity);
            finish();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
