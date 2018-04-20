package fr.wildcodeschool.rateeverything;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    //CONSTANT
    static final int SELECT_IMAGE = 0;
    private static final int REQUEST_TAKE_PHOTO = 11;
    private static final String ID_PROFIL = "idprofil";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;

    //Widget
    private Button mButtonSignIn, mButtonCreateAccount, mButtonValidLogin, mButtonValidCreate;
    private EditText mEditPseudo, mEditPassword, mEditMail;
    private TextView mTextPseudo, mTextMail, mTextPassword, mTextChangeAvatar;
    private ProgressBar mProgressBarLoading;
    private ImageView mImageAvatar;

    private String mCurrentPhotoPath;


    //Intent
    private Intent mGoToMainActivity;

    //Photo
    private Uri mUrlImage;
    private Bitmap mBitmap;

    private Uri mPhotoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mGoToMainActivity = new Intent(LoginActivity.this, MainActivity.class);

        initWidgets();

        if (isSharedPreference()) {

            startActivity(mGoToMainActivity);
            finish();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(mGoToMainActivity);
                    finish();
                }
            }
        };

        //Show Widget Create Account
        mButtonCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) {

                initWidgetRegister();
            }
        });

        //Register
        mButtonValidCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                createAccount();
            }
        });

        //Show Widget Sign In
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) {

                initWidgetSignIn();
            }
        });

        //Sign In
        mButtonValidLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signIn();
            }
        });

        //Choose Avatar
        mTextChangeAvatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showPickImageDialog();
            }
        });

    }
    /*
    ---------------------------------CreateAnAccount--------------------------------------
     */
    public void createAccount() {

        String mail = mEditMail.getText().toString().trim();
        String pass = mEditPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) {

            Toast.makeText(LoginActivity.this, R.string.bothValues, Toast.LENGTH_SHORT).show();
        }
        else if(pass.length() < 6) {

            Toast.makeText(LoginActivity.this, R.string.passwordNeed, Toast.LENGTH_SHORT).show();
        }
        else {

            mProgressBarLoading.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, R.string.authentificatinFailed, Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String mail = mEditMail.getText().toString().trim();
                        String pseudo = mEditPseudo.getText().toString().trim();
                        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = mCurrentUser.getUid();
                        mRef.child(userID).child("Profil").child("id").setValue(userID);
                        mRef.child(userID).child("Profil").child("mail").setValue(mail);
                        mRef.child(userID).child("Profil").child("username").setValue(pseudo);
                        mRef.child(userID).child("Profil").child("nbfollowers").setValue(0);
                        mRef.child(userID).child("Profil").child("nbphoto").setValue(0);
                        mRef.child(userID).child("Profil").child("photobackground").setValue("1");

                        if (mUrlImage == null && mBitmap == null) {
                            mRef.child(userID).child("Profil").child("photouser").setValue("1");
                        }
                        // TODO : enregistrer dans firebaseStorage

                        SaveSharedPreference.setUserName(LoginActivity.this, mail);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                }
            });
        }
    }

    /*
    -------------------------------SignIn---------------------------------------------
     */

    public void signIn() {

        String mail = mEditMail.getText().toString().trim();
        String pass = mEditPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) {

            Toast.makeText(LoginActivity.this, R.string.bothValues, Toast.LENGTH_SHORT).show();
        } else {

            mProgressBarLoading.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = mCurrentUser.getUid();

                        mGoToMainActivity.putExtra(ID_PROFIL, userID);

                        String mail = mEditMail.getText().toString().trim();
                        SaveSharedPreference.setUserName(LoginActivity.this, mail);

                        LoginActivity.this.startActivity(mGoToMainActivity);

                    } else {

                        Toast.makeText(LoginActivity.this, R.string.incorrectUserPassword, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    /*
    -----------------------------------AvatarMethod-------------------------------------------------
     */

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

    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(LoginActivity.this);
        builderSingle.setTitle("Select One Option");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                LoginActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Gallery");
        arrayAdapter.add("Camera");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, SELECT_IMAGE);
                                break;

                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }

                    }
                });
        builderSingle.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    mUrlImage = selectedImage;
                    mImageAvatar.setImageURI(selectedImage);
                }
                break;
            case REQUEST_TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    Glide.with(LoginActivity.this).load(mPhotoURI).into(mImageAvatar);
                }
                break;
        }
    }

    /*
    -----------------------------------Firebase-----------------------------------------------------
     */

    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    /*
    ------------------------------SharedPreference--------------------------------------------------
     */
    private boolean isSharedPreference() {

        if(SaveSharedPreference.getUserName(LoginActivity.this).length() != 0)
        {
            // call Login Activity
            return true;
        }
        return false;
    }

    /*
    --------------------------------WidgetsMethod---------------------------------------------------
     */

    private void initWidgets() {

        mButtonSignIn = findViewById(R.id.button_sign_in);
        mButtonCreateAccount = findViewById(R.id.button_create_account);
        mButtonValidLogin = findViewById(R.id.button_valid_login);
        mButtonValidCreate = findViewById(R.id.button_valid_create);
        mEditPseudo = findViewById(R.id.edit_text_pseudo);
        mEditMail = findViewById(R.id.edit_text_mail);
        mEditPassword = findViewById(R.id.edit_text_password);
        mTextPseudo = findViewById(R.id.text_view_pseudo);
        mTextMail = findViewById(R.id.text_view_mail);
        mTextPassword = findViewById(R.id.text_view_password);
        mTextChangeAvatar = findViewById(R.id.text_view_chose_avatar);
        mProgressBarLoading = findViewById(R.id.progress_bar_load);
        mImageAvatar = findViewById(R.id.image_view_logo);
    }

    private void initWidgetSignIn() {

        mButtonSignIn.setVisibility(View.GONE);
        mButtonCreateAccount.setVisibility(View.GONE);
        mTextPseudo.setVisibility(View.VISIBLE);
        mTextMail.setVisibility(View.VISIBLE);
        mTextPassword.setVisibility(View.VISIBLE);
        mEditPseudo.setVisibility(View.VISIBLE);
        mEditMail.setVisibility(View.VISIBLE);
        mEditPassword.setVisibility(View.VISIBLE);
        mButtonValidLogin.setVisibility(View.VISIBLE);
    }

    private void initWidgetRegister() {

        mButtonSignIn.setVisibility(View.GONE);
        mButtonCreateAccount.setVisibility(View.GONE);
        mTextPseudo.setVisibility(View.VISIBLE);
        mTextMail.setVisibility(View.VISIBLE);
        mTextPassword.setVisibility(View.VISIBLE);
        mEditPseudo.setVisibility(View.VISIBLE);
        mEditMail.setVisibility(View.VISIBLE);
        mEditPassword.setVisibility(View.VISIBLE);
        mButtonValidCreate.setVisibility(View.VISIBLE);
        mTextChangeAvatar.setVisibility(View.VISIBLE);
    }

}

