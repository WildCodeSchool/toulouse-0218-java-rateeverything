package fr.wildcodeschool.rateeverything;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhotoActivity extends Activity {
    ImageView mImagePhoto;
    static final int CAM_REQUEST = 0;
    static final int SELECT_IMAGE = 1;
    private Uri mSelectedImage = null;
    private Bitmap mBitmap = null;
    private StorageReference mStorageRef;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;


    private static final int REQUEST_TAKE_PHOTO = 11;

    //Photo
    private Uri mUrlImage;

    private Uri mPhotoURI;

    private String mCurrentPhotoPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = mCurrentUser.getUid();

        mRef = mDatabase.getReference("Users/" + idUser + "/Photo/");

        Button buttonAdd;
        setContentView(R.layout.activity_add_photo);
        buttonAdd = (Button) findViewById(R.id.button_add_photo);
        mImagePhoto = (ImageView) findViewById(R.id.iv_photo);
        TextView tvTitle = findViewById(R.id.et_title_img);
        TextView tvDescription = findViewById(R.id.et_description_img);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mPhotoURI = FileProvider.getUriForFile(this,
                        "fr.wildcodeschool.rateeverything.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    Glide.with(AddPhotoActivity.this).load(mPhotoURI).into(mImagePhoto);
                }
                break;
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    mSelectedImage = data.getData();
                    mPhotoURI = mSelectedImage;
                    Glide.with(AddPhotoActivity.this).load(mPhotoURI).into(mImagePhoto);
                }
                break;
        }

        Button addImage = findViewById(R.id.button_share_img);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText tvTitle = findViewById(R.id.et_title_img);
                EditText tvDescription = findViewById(R.id.et_description_img);
                final String titleValue = tvTitle.getText().toString();
                final String descriptionValue = tvDescription.getText().toString();

                StorageReference riverRef = mStorageRef.child("Image").child(mPhotoURI.getLastPathSegment());;

                riverRef.putFile(mPhotoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String url = downloadUrl.toString();
                        long date = new Date().getTime();


                        MainPhotoModel mainPhotoModel = new MainPhotoModel(descriptionValue, 1, url, date, titleValue, 1);

                        mRef.push().setValue(mainPhotoModel);
                        Toast.makeText(AddPhotoActivity.this, R.string.Ok, Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Toast.makeText(AddPhotoActivity.this,"Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPhotoActivity.this, R.string.fail, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}









