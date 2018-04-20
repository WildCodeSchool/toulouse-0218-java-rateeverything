package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;



    ListView mListViewFollowers;
    ArrayList<FollowersModel> mFollowers;
    FollowersAdapter mAdapter;
    FollowersModel mModel;git

    FirebaseDatabase mDatabase;
    FirebaseUser mUser;
    DatabaseReference myRef, refFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_follow);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String iDUser = mUser.getUid();
        myRef = mDatabase.getReference("Users/" + mUser.getUid());
        refFollowers = mDatabase.getReference("Users/");

        mListViewFollowers = findViewById(R.id.listview_followers);
        mFollowers = new ArrayList<>();
        mAdapter = new FollowersAdapter(this, mFollowers);

        mListViewFollowers.setAdapter(mAdapter);
        refFollowers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFollowers.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    mModel = ds.child("Profil").getValue(FollowersModel.class);
                    mFollowers.add(mModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });











        // -------------------------MENU BURGER DON'T TOUCH--------------------------------


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_follow);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intentHome = new Intent(FollowersActivity.this, MainActivity.class);
            startActivity(intentHome);
        } else if (id == R.id.profil) {
            Intent intentProfil = new Intent(FollowersActivity.this, ProfilUserActivity.class);
            startActivity(intentProfil);
        } else if (id == R.id.followers) {
            Intent intentFollowers = new Intent(FollowersActivity.this, FollowersActivity.class);
            startActivity(intentFollowers);
        } else if (id == R.id.disconnect) {
            FirebaseAuth.getInstance().signOut();
            SaveSharedPreference.setUserName(FollowersActivity.this, "");
            Intent goToLoginActivity = new Intent(FollowersActivity.this,LoginActivity.class);
            startActivity(goToLoginActivity);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_follow);
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

