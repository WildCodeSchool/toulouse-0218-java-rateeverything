package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

import static java.lang.System.load;

public class ProfilUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference myProfil;

    ImageView photo;
    TextView nbFollowers, nbPhoto, userName;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        photo = findViewById(R.id.profil_photo_user);
        nbFollowers = findViewById(R.id.item_followers);
        nbPhoto = findViewById(R.id.item_pictures);
        userName = findViewById(R.id.edit_name_user);

        final GridView gridView = findViewById(R.id.grid_view_user);
        ArrayList<ProfilUserGridModel> userGrid = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String iDUser = user.getUid();
        myProfil = database.getReference("Users/" + iDUser + "/Profil/");

        myProfil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("UserName").getValue() != null) {
                    String stringName = (String) dataSnapshot.child("UserName").getValue();
                    userName.setText(stringName);
                }
                if (dataSnapshot.child("NbFollowers").getValue() != null) {
                    String stringNbFollowers = dataSnapshot.child("NbFollowers").getValue().toString();
                    nbFollowers.setText(stringNbFollowers);
                }
                if (dataSnapshot.child("NbPhotos").getValue() != null) {
                    String stringNbPhotos = dataSnapshot.child("NbPhotos").getValue().toString();
                    nbPhoto.setText(stringNbPhotos);
                }
                if (dataSnapshot.child("PhotoUser").getValue() != null) {
                    String stringUrl = (String) dataSnapshot.child("PhotoUser").getValue();
                    Glide.with(ProfilUserActivity.this)
                            .load(stringUrl)
                            .into(photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ProfilUserGridAdapter adapter = new ProfilUserGridAdapter(this, userGrid);
        gridView.setAdapter(adapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_user);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_user);
        navigationView.setNavigationItemSelectedListener(this);
    }

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
            startActivity(intentProfil);
        } else if (id == R.id.followers) {
            Intent intentFollowers = new Intent(ProfilUserActivity.this, FollowersActivity.class);
            startActivity(intentFollowers);
        } else if (id == R.id.disconnect) {
            FirebaseAuth.getInstance().signOut();
            SaveSharedPreference.setUserName(ProfilUserActivity.this, "");
            Intent goToLoginActivity = new Intent(ProfilUserActivity.this,LoginActivity.class);
            startActivity(goToLoginActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user);
        drawer.closeDrawer(GravityCompat.START);
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
