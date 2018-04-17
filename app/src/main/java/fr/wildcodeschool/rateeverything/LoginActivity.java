package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;

    //Widget
    private Button buttonSignIn, buttonCreateAccount, buttonValidLogin, buttonValidCreate;
    private EditText editPseudo, editPassword, editMail;
    private TextView textPseudo, textMail, textPassword, textChangeAvatar;
    private ProgressBar progressBarLoading;

    //Intent
    private Intent goToMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);

        initWidgets();

        if (isSharedPreference()) {

            startActivity(goToMainActivity);
            finish();
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(goToMainActivity);
                    finish();
                }
            }
        };

        //Show Widget Create Account
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) {

                initWidgetRegister();
            }
        });

        //Register
        buttonValidCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                createAccount();
            }
        });

        //Show Widget Sign In
        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) {

                initWidgetSignIn();
            }
        });

        //Sign In
        buttonValidLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signIn();
            }
        });

    }
    /*
    ---------------------------------CreateAnAccount--------------------------------------
     */
    public void createAccount() {

        String mail = editMail.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) {

            Toast.makeText(LoginActivity.this, R.string.bothValues, Toast.LENGTH_SHORT).show();
        }
        else if(pass.length() < 6) {

            Toast.makeText(LoginActivity.this, R.string.passwordNeed, Toast.LENGTH_SHORT).show();
        }
        else {

            progressBarLoading.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, R.string.authentificatinFailed, Toast.LENGTH_SHORT).show();
                    }
                    else {

                        String mail = editMail.getText().toString().trim();
                        String pseudo = editPseudo.getText().toString().trim();
                        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = currentUser.getUid();
                        myRef.child(userID).child("Profil").child("Email").setValue(mail);
                        myRef.child(userID).child("Profil").child("Name").setValue(pseudo);
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

        String mail = editMail.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) {

            Toast.makeText(LoginActivity.this, R.string.bothValues, Toast.LENGTH_SHORT).show();
        } else {

            progressBarLoading.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String mail = editMail.getText().toString().trim();
                        SaveSharedPreference.setUserName(LoginActivity.this, mail);
                        LoginActivity.this.startActivity(goToMainActivity);

                    } else {

                        Toast.makeText(LoginActivity.this, R.string.incorrectUserPassword, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*
    -----------------------------------Firebase-------------------------------------------------
     */

    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    /*
    ------------------------SharedPreference-----------------------------------
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
    -------------------WidgetsMethod--------------------------------------
     */

    private void initWidgets() {

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonCreateAccount = findViewById(R.id.button_create_account);
        buttonValidLogin = findViewById(R.id.button_valid_login);
        buttonValidCreate = findViewById(R.id.button_valid_create);
        editPseudo = findViewById(R.id.edit_text_pseudo);
        editMail = findViewById(R.id.edit_text_mail);
        editPassword = findViewById(R.id.edit_text_password);
        textPseudo = findViewById(R.id.text_view_pseudo);
        textMail = findViewById(R.id.text_view_mail);
        textPassword = findViewById(R.id.text_view_password);
        textChangeAvatar = findViewById(R.id.text_view_chose_avatar);
        progressBarLoading = findViewById(R.id.progress_bar_load);
    }

    private void initWidgetSignIn() {

        buttonSignIn.setVisibility(View.GONE);
        buttonCreateAccount.setVisibility(View.GONE);
        textPseudo.setVisibility(View.VISIBLE);
        textMail.setVisibility(View.VISIBLE);
        textPassword.setVisibility(View.VISIBLE);
        editPseudo.setVisibility(View.VISIBLE);
        editMail.setVisibility(View.VISIBLE);
        editPassword.setVisibility(View.VISIBLE);
        buttonValidLogin.setVisibility(View.VISIBLE);
    }

    private void initWidgetRegister() {

        buttonSignIn.setVisibility(View.GONE);
        buttonCreateAccount.setVisibility(View.GONE);
        textPseudo.setVisibility(View.VISIBLE);
        textMail.setVisibility(View.VISIBLE);
        textPassword.setVisibility(View.VISIBLE);
        editPseudo.setVisibility(View.VISIBLE);
        editMail.setVisibility(View.VISIBLE);
        editPassword.setVisibility(View.VISIBLE);
        buttonValidCreate.setVisibility(View.VISIBLE);
        textChangeAvatar.setVisibility(View.VISIBLE);
    }

}

