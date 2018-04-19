package fr.wildcodeschool.rateeverything;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DateTimePatternGenerator;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static com.google.firebase.storage.FirebaseStorage.*;

public class AddPhotoActivity extends Activity {
    ImageView mImagePhoto;
    static final int CAM_REQUEST = 0;
    static final int SELECT_IMAGE = 1;
    private Uri selectedImage = null;
    private Bitmap bitmap = null;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String idUser = currentUser.getUid();

        mRef = database.getReference("Users/" + idUser + "/Photo/");


        Button buttonAdd;
        setContentView(R.layout.activity_add_photo);
        buttonAdd = (Button) findViewById(R.id.button_add_photo);
        mImagePhoto = (ImageView) findViewById(R.id.iv_photo);
        TextView tvTitle = findViewById(R.id.et_title_img);
        TextView tvDescription = findViewById(R.id.et_description_img);


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
                    bitmap = (Bitmap) data.getExtras().get("data");
                    mImagePhoto.setImageBitmap(bitmap);
                }
                break;
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    selectedImage = data.getData();
                    mImagePhoto.setImageURI(selectedImage);
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

                StorageReference riverRef = mStorageRef.child("Image");

                riverRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String url = downloadUrl.toString();
                        long date = new Date().getTime();

                        MainPhotoModel mainPhotoModel = new MainPhotoModel(descriptionValue, date,titleValue, 1, url, 1);
                        mRef.push().setValue(mainPhotoModel);
                        Toast.makeText(AddPhotoActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPhotoActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }


/*
    public void shareImageButton(View view){



                    .child(selectedImage.getLastPathSegment());
            filePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUri = taskSnapshot.getUploadSessionUri();
                    final DatabaseReference newPost = databaseReference.push();
                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newPost.child("description").setValue(descriptionValue);

                            newPost.child("photo").setValue(downloadUri.toString());

                            newPost.child("title").setValue(titleValue);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        if(CAM_REQUEST == 0){;
            bitmap = mImagePhoto.getDrawingCache();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataB = baos.toByteArray();

            StorageReference filePath = storageReference.child("Image").child(dataB+"");
            UploadTask uploadTask = filePath.putBytes(dataB);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUriB = taskSnapshot.getUploadSessionUri();
                    final DatabaseReference newPost = databaseReference.push();
                    mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newPost.child("description").setValue(descriptionValue);

                            newPost.child("photo").setValue(downloadUriB.toString());

                            newPost.child("title").setValue(titleValue);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
      */


    }









