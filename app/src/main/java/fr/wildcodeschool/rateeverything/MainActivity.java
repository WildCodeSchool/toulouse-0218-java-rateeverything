package fr.wildcodeschool.rateeverything;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<MainPhotoModel> photoList = new ArrayList<>();
        try {

            photoList.add(new MainPhotoModel(R.drawable.index,"Coralie",sdf.parse("21/02/2017" ), 4));
            photoList.add(new MainPhotoModel(R.drawable.moon,"Thomas", sdf.parse("20/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.cat,"Benjamin",sdf.parse("19/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.whelk,"Thomas",sdf.parse("18/02/2017"), 4));
            photoList.add(new MainPhotoModel(R.drawable.cat,"Benjamin",sdf.parse("17/02/2017"), 4));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        MainPhotoAdapter adapter = new MainPhotoAdapter(this, photoList);
        ListView photoListView = (ListView)findViewById(R.id.listView_photo_main);
        photoListView.setAdapter(adapter);

    }
}

