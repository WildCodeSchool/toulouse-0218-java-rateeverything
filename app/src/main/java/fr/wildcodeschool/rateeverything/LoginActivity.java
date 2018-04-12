package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText editPseudo;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(goToMainActivity);
                }
            }
        };

            final Button buttonSignIn = findViewById(R.id.button_sign_in);
            final Button buttonCreateAccount = findViewById(R.id.button_create_account);
            final Button buttonValidLogin = findViewById(R.id.button_valid_login);
            final Button buttonValidCreate = findViewById(R.id.button_valid_create);
            final EditText editPseudo = findViewById(R.id.edit_text_pseudo);
            final EditText editPassword = findViewById(R.id.edit_text_password);
            final TextView textPseudo = findViewById(R.id.text_view_pseudo);
            final TextView textPassword = findViewById(R.id.text_view_password);
            final TextView textChangeAvatar = findViewById(R.id.text_view_chose_avatar);


        if(SaveSharedPreference.getUserName(LoginActivity.this).length() == 0)
        {
            // call Login Activity
        }
        else

            {
                // Stay at the current activity.
                LoginActivity.this.startActivity(goToMainActivity);
                finish();
            }

        buttonCreateAccount.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                buttonSignIn.setVisibility(View.GONE);
                buttonCreateAccount.setVisibility(View.GONE);
                textPseudo.setVisibility(View.VISIBLE);
                textPassword.setVisibility(View.VISIBLE);
                editPseudo.setVisibility(View.VISIBLE);
                editPassword.setVisibility(View.VISIBLE);
                buttonValidCreate.setVisibility(View.VISIBLE);
                textChangeAvatar.setVisibility(View.VISIBLE);
            }
            });

                    buttonSignIn.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                buttonSignIn.setVisibility(View.GONE);
                buttonCreateAccount.setVisibility(View.GONE);
                textPseudo.setVisibility(View.VISIBLE);
                textPassword.setVisibility(View.VISIBLE);
                editPseudo.setVisibility(View.VISIBLE);
                editPassword.setVisibility(View.VISIBLE);
                buttonValidLogin.setVisibility(View.VISIBLE);

                buttonValidLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String email = editPseudo.getText().toString().trim();
                        String pass = editPassword.getText().toString().trim();
                        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

                            Toast.makeText(LoginActivity.this, R.string.bothValues, Toast.LENGTH_SHORT).show();
                        } else {

                            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        SaveSharedPreference.setUserName(LoginActivity.this, email);
                                        LoginActivity.this.startActivity(goToMainActivity);

                                    } else {
                                        Toast.makeText(LoginActivity.this, R.string.incorrectUserPassword, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });
            }
            });
        }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }
}

