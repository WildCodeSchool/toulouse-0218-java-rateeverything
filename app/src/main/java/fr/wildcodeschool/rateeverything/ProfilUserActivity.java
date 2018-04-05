package fr.wildcodeschool.rateeverything;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;

public class ProfilUserActivity extends AppCompatActivity {

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

        ImageView photoUser = (ImageView)findViewById(R.id.profil_photo_user);
        photoUser.setImageResource(R.drawable.photobenbronde);

        ImageView logoFollower = (ImageView)findViewById(R.id.logo_follower);
        logoFollower.setImageResource(R.drawable.suricate);

        ImageView logoPicture = (ImageView)findViewById(R.id.logo_picture);
        logoPicture.setImageResource(R.drawable.camera);

    }


}
