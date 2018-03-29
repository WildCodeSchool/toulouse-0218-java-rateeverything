package fr.wildcodeschool.rateeverything;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by wilder on 27/03/18.
 */

public class MainPhotoAdapter extends ArrayAdapter<MainPhotoModel> {

    public MainPhotoAdapter(Context context, ArrayList<MainPhotoModel> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainPhotoModel photoModel = getItem(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photos, parent, false);
        }
        ImageView photo = convertView.findViewById(R.id.image_photo);
        Drawable drawablephoto = ContextCompat.getDrawable(getContext(), photoModel.getPhoto());
        photo.setImageDrawable(drawablephoto);
        TextView textUsername = (TextView) convertView.findViewById(R.id.text_user_name_pub);
        textUsername.setText(photoModel.getUsername());
        TextView textDatePub = (TextView) convertView.findViewById(R.id.text_date_pub);
        textDatePub.setText(""+photoModel.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateValue = sdf.format(photoModel.getDate());
        textDatePub.setText(dateValue);
        TextView note = (TextView) convertView.findViewById(R.id.text_note);
        note.setText(""+photoModel.getNote());
        return convertView;
    }


}
