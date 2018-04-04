package fr.wildcodeschool.rateeverything;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ProfilUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);


        ImageView photoUser = (ImageView)findViewById(R.id.profil_photo_user);
        photoUser.setImageResource(R.drawable.photobenbronde);

        ImageView logoFollower = (ImageView)findViewById(R.id.logo_follower);
        logoFollower.setImageResource(R.drawable.suricate);

        ImageView logoPicture = (ImageView)findViewById(R.id.logo_picture);
        logoPicture.setImageResource(R.drawable.camera);
    }

}
