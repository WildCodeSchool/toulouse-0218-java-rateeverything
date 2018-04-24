package fr.wildcodeschool.rateeverything;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final MainPhotoModel photoModel = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photos, parent, false);
        }
        final ImageView photo = convertView.findViewById(R.id.image_photo);
        if (photoModel.getPhoto() != null) {
            Glide.with(getContext()).load(photoModel.getPhoto()).into(photo);
        }
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyphoto = photoModel.getKeyphoto();
                String iduserphoto = photoModel.getIduser();
                Intent intentphoto = new Intent(parent.getContext(), UserPhoto.class);
                intentphoto.putExtra("keyphoto", keyphoto);
                intentphoto.putExtra("idprofil", iduserphoto);
                parent.getContext().startActivity(intentphoto);
            }
        });

        TextView textUsername = (TextView) convertView.findViewById(R.id.text_user_name_pub);
        textUsername.setText(photoModel.getTitle());

        TextView textDatePub = (TextView) convertView.findViewById(R.id.text_date_pub);
        textDatePub.setText("" + photoModel.getTimestamp());

        // TODO : faire le systeme de notation dans le popup
        TextView note = (TextView) convertView.findViewById(R.id.text_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder popup = new AlertDialog.Builder(parent.getContext());
                popup.setTitle("Note la photo");
                popup.setMessage("Quel note veux tu mettre");
                popup.setPositiveButton("Oui tkt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(parent.getContext(), "Ok c'est supprimé morray", Toast.LENGTH_LONG).show();
                    }
                });
                popup.setNegativeButton("Finalement non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(parent.getContext(), "C'est un bon choix", Toast.LENGTH_LONG).show();
                    }
                });
                popup.show();
            }
        });

        int valeurnote = photoModel.getTotalnote() / photoModel.getNbnote();
        note.setText("" + valeurnote);

        return convertView;
    }
}
