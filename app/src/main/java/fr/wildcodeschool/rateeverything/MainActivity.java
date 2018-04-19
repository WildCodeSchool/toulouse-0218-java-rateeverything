package fr.wildcodeschool.rateeverything;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<MainPhotoModel> photoList = new ArrayList<>();

        try {
            photoList.add(new MainPhotoModel(R.drawable.index, "Coralie", sdf.parse("21/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.moon, "Thomas", sdf.parse("20/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.cat, "Benjamin", sdf.parse("19/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.whelk, "Thomas", sdf.parse("18/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.cat, "Benjamin", sdf.parse("17/02/2017"), 4));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        MainPhotoAdapter adapter = new MainPhotoAdapter(this, photoList);
        ListView photoListView = (ListView) findViewById(R.id.listview_photo_main);
        photoListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPhotoActivity.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            Intent intentHome = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intentHome);
        } else if (id == R.id.profil) {
            Intent intentProfil = new Intent(MainActivity.this, ProfilUserActivity.class);
            startActivity(intentProfil);
        } else if (id == R.id.followers) {
            Intent intentFollowers = new Intent(MainActivity.this, FollowersActivity.class);
            startActivity(intentFollowers);
        } else if (id == R.id.disconnect){
            FirebaseAuth.getInstance().signOut();
            SaveSharedPreference.setUserName(MainActivity.this, "");
            Intent goToLoginActivity = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(goToLoginActivity);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
