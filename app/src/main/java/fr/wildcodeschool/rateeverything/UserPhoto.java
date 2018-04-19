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
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPhoto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photo);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_photo);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvTitre = findViewById(R.id.text_titre_photo);
        TextView tvDescription = findViewById(R.id.text_description_photo);
        ImageView ivPhoto = findViewById(R.id.iv_photo);
        RatingBar ratingBar = findViewById(R.id.bar_modif_note);

        database = FirebaseDatabase.getInstance();
        final String profilId = getIntent().getStringExtra("idprofil");
        final String idPhoto = getIntent().getStringExtra("keyphoto");

        myRef = database.getReference("Users/" + profilId + "/Photo/" + idPhoto);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //TODO: paramêtrer en fonction du constructeur

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // -----------MENU BURGER DON'T TOUCH FOR THE MOMENT------------------

        NavigationView navigationViewPhoto = (NavigationView) findViewById(R.id.nav_view_photo);
        navigationViewPhoto.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intentHome = new Intent(UserPhoto.this, MainActivity.class);
            startActivity(intentHome);
        } else if (id == R.id.profil) {
            Intent intentProfil = new Intent(UserPhoto.this, ProfilUserActivity.class);
            startActivity(intentProfil);
        } else if (id == R.id.followers) {
            Intent intentFollowers = new Intent(UserPhoto.this, FollowersActivity.class);
            startActivity(intentFollowers);
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
