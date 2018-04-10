package fr.wildcodeschool.rateeverything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button buttonSignIn = findViewById(R.id.button_sign_in);
        final Button buttonCreateAccount = findViewById(R.id.button_create_account);
        final Button buttonValidLogin = findViewById(R.id.button_valid_login);
        final Button buttonValidCreate = findViewById(R.id.button_valid_create);

        final EditText editPseudo = findViewById(R.id.edit_text_pseudo);
        final EditText editPassword = findViewById(R.id.edit_text_password);

        final TextView textPseudo = findViewById(R.id.text_view_pseudo);
        final TextView textPassword = findViewById(R.id.text_view_password);
        final TextView textChangeAvatar = findViewById(R.id.text_view_chose_avatar);

        final Intent goToMainActivity = new Intent(LoginActivity.this,MainActivity.class);

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

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignIn.setVisibility(View.GONE);
                buttonCreateAccount.setVisibility(View.GONE);
                textPseudo.setVisibility(View.VISIBLE);
                textPassword.setVisibility(View.VISIBLE);
                editPseudo.setVisibility(View.VISIBLE);
                editPassword.setVisibility(View.VISIBLE);
                buttonValidLogin.setVisibility(View.VISIBLE);

                buttonValidLogin.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        String pseudoValue = textPseudo.getText().toString();

                        if (textPseudo.length() == 0 || textPassword.length() == 0){

                            Toast.makeText(LoginActivity.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();

                        } else {
                            SaveSharedPreference.setUserName(LoginActivity.this,pseudoValue);
                            LoginActivity.this.startActivity(goToMainActivity);
                        }

                    }
                });
            }
        });
    }
}
