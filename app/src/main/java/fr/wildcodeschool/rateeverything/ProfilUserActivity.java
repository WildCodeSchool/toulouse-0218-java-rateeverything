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
import java.util.ArrayList;

public class ProfilUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);


        final GridView gridView = findViewById(R.id.grid_view_user);
        ArrayList<ProfilUserGridModel> userGrid = new ArrayList<>();

        userGrid.add(new ProfilUserGridModel(R.drawable.coco,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.lebosse,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.toto,4));
        userGrid.add(new ProfilUserGridModel(R.drawable.tofperrine,5));
        userGrid.add(new ProfilUserGridModel(R.drawable.pad_ps,4));
        userGrid.add(new ProfilUserGridModel(R.drawable.bottes,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.lampe,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.hamac_pieds,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.licorne_chat,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.taille_chat,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.licornes,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.pascaltof,3));
        userGrid.add(new ProfilUserGridModel(R.drawable.tofperrine,3));


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
