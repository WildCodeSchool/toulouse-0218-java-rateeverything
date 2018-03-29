package fr.wildcodeschool.rateeverything;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        final ListView listFollowers = findViewById(R.id.listview_followers);
        ArrayList<FollowersModel> followers = new ArrayList<>();

        followers.add(new FollowersModel(R.drawable.lebosse, 23, 12, "Benjamin W"));
        followers.add(new FollowersModel(R.drawable.toto, 2, 150, "Thomas"));
        followers.add(new FollowersModel(R.drawable.coco, 200, 1500, "Coralie"));
        followers.add(new FollowersModel(R.drawable.benou, 50, 2, "Benjamin B"));
        followers.add(new FollowersModel(R.drawable.tofperrine, 150, 4, "Perrine"));
        followers.add(new FollowersModel(R.drawable.pascaltof, 20, 50, "Pascal"));

        FollowersAdapter adapter = new FollowersAdapter(this, followers);
        listFollowers.setAdapter(adapter);

    }
}

