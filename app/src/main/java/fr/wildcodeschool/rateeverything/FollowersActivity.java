package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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



    ListView listViewFollowers;
    ArrayList<FollowersModel> followers;
    FollowersAdapter adapter;
    FollowersModel myModel;

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

        listViewFollowers = findViewById(R.id.listview_followers);
        followers = new ArrayList<>();
        adapter = new FollowersAdapter(this, followers);

        listViewFollowers.setAdapter(adapter);
        refFollowers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                followers.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    myModel = ds.child("Profil").getValue(FollowersModel.class);
                    followers.add(myModel);
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

