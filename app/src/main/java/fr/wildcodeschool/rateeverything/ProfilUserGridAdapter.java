package fr.wildcodeschool.rateeverything;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by wilder on 04/04/18.
 */

public class ProfilUserGridAdapter extends ArrayAdapter<ProfilUserGridModel> {
    public ProfilUserGridAdapter (Context context, ArrayList<ProfilUserGridModel> userGrid) {
        super(context, 0, userGrid);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ProfilUserGridModel userGrid = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_user, parent, false);
        }

        ImageView imgUser = (ImageView) convertView.findViewById(R.id.img_user);
        Glide.with(getContext())
                .load(userGrid.getImgUser())
                .into(imgUser);

        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar_user);


        ratingBar.setRating(userGrid.getRatingPhotoUser());

        return convertView;
    }
}
