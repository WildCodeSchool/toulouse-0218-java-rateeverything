package fr.wildcodeschool.rateeverything;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddPhotoActivity extends Activity {
    Button buttonAdd;
    ImageView imagePhoto;
    static final int CAM_REQUEST = 0;
    static final int SELECT_IMAGE = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        buttonAdd =(Button) findViewById(R.id.button_add_photo);
        imagePhoto =(ImageView) findViewById(R.id.iv_photo);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAM_REQUEST);
            }
        });
        Button gallery = findViewById(R.id.button_add_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, SELECT_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CAM_REQUEST:
                if(resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imagePhoto.setImageBitmap(bitmap);
                }
                break;
            case SELECT_IMAGE:
                if(resultCode== RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imagePhoto.setImageURI(selectedImage);
                }
                break;
        }
    }
}



