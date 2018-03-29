package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonProfilUser = findViewById(R.id.button_profil_user);
        buttonProfilUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfilUserActivity.class));
            }
        });

        Button buttonPhotoUser = findViewById(R.id.button_user_photo);
        buttonPhotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserPhoto.class));
            }
        });

    }
}
