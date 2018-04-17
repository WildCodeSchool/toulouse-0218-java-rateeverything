package fr.wildcodeschool.rateeverything;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by wilder on 29/03/18.
 */

public class FollowersAdapter extends ArrayAdapter<FollowersModel> {
    public FollowersAdapter (Context context, ArrayList<FollowersModel> followers) {
        super(context, 0, followers);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        final FollowersModel followers = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_followers, parent, false);
        }

        TextView textName = (TextView) convertView.findViewById(R.id.textview_user_name);
        TextView textNbPhoto = (TextView) convertView.findViewById(R.id.textview_nb_photo);
        TextView textNbFollowers = (TextView) convertView.findViewById(R.id.textview_nb_followers);
        ImageView imgPhotoUser = (ImageView) convertView.findViewById(R.id.imageview_photo);




        textName.setText(followers.getUserName());
        String stringNbPhoto = String.valueOf(followers.getNbPhoto());
        textNbPhoto.setText(stringNbPhoto);
        String stringNbFollowers = String.valueOf(followers.getNbFollowers());
        textNbFollowers.setText(stringNbFollowers);
        imgPhotoUser.setImageResource(followers.getUserPhoto());

        ImageView addFollowers = (ImageView) convertView.findViewById(R.id.imageview_ajout_followers);

        addFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();
                DatabaseReference myRef = database.getReference("Users");
                myRef.child(userID).child("Followers").setValue(followers.getUserName());
            }
        });

        return convertView;

    }



}
