package fr.wildcodeschool.rateeverything;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

        if (followers != null) {

            if (followers.getPhotouser() != null) {
                ImageView photo = (ImageView) convertView.findViewById(R.id.imageview_photo_followers);
                Glide.with(parent.getContext()).load(followers.getPhotouser().toString()).into(photo);
            }

            if (followers.getUsername().toString() != null) {
                TextView textName = (TextView) convertView.findViewById(R.id.textview_user_name_followers);
                textName.setText(followers.getUsername().toString());
            }

            if (followers.getNbphoto() != 0) {
                TextView textNbPhoto = (TextView) convertView.findViewById(R.id.textview_nb_photo_followers);
                textNbPhoto.setText(followers.getNbphoto() + "");
            }

            if (followers.getNbfollowers() != 0) {
                TextView textNbFollowers = (TextView) convertView.findViewById(R.id.textview_nb_followers);
                textNbFollowers.setText(followers.getNbfollowers() + "");
            }
        }

        // TODO : problème, plante quand on affiche les follow et que un compte est créé, à rectifier

        return convertView;
    }



}
