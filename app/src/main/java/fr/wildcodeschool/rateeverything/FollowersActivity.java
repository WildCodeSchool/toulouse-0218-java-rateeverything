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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_follow);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView listFollowers = findViewById(R.id.listview_followers);
        ArrayList<FollowersModel> followers = new ArrayList<>();

        followers.add(new FollowersModel(R.drawable.lebosse_min, 23, 12, "Benjamin W"));
        followers.add(new FollowersModel(R.drawable.toto_min, 2, 150, "Thomas"));
        followers.add(new FollowersModel(R.drawable.coco_min, 200, 1500, "Coralie"));
        followers.add(new FollowersModel(R.drawable.benou_min, 50, 2, "Benjamin B"));
        followers.add(new FollowersModel(R.drawable.tofperrine_min, 150, 4, "Perrine"));
        followers.add(new FollowersModel(R.drawable.pascaltof_min, 20, 50, "Pascal"));

        FollowersAdapter adapter = new FollowersAdapter(this, followers);
        listFollowers.setAdapter(adapter);

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

