package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class ProfilUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_user);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ImageView photoUser = (ImageView)findViewById(R.id.profil_photo_user);
        photoUser.setImageResource(R.drawable.photobenbronde);

        ImageView logoFollower = (ImageView)findViewById(R.id.logo_follower);
        logoFollower.setImageResource(R.drawable.suricate);

        ImageView logoPicture = (ImageView)findViewById(R.id.logo_picture);
        logoPicture.setImageResource(R.drawable.camera);

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
            SaveSharedPreference.setUserName(ProfilUserActivity.this,"");
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
