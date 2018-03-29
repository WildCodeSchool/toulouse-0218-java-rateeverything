package fr.wildcodeschool.rateeverything;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonSignIn = findViewById(R.id.button_signIn);
        final Button buttonCreateAccount = findViewById(R.id.button_create_account);
        final Button buttonValidLogin = findViewById(R.id.button_validLogin);
        final Button buttonValidCreate = findViewById(R.id.button_valid_create);
        final EditText editPseudo = findViewById(R.id.editText_pseudo);
        final EditText editPassword = findViewById(R.id.editText_password);
        final TextView textPseudo = findViewById(R.id.textView_pseudo);
        final TextView textPassword = findViewById(R.id.textView_password);
        final TextView textChangeAvatar = findViewById(R.id.textView_chose_avatar);

        final Button buttonRetour = findViewById(R.id.button_return);

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
            }
        });
        //BOUTON provisoire pour tester le layout
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignIn.setVisibility(View.VISIBLE);
                buttonCreateAccount.setVisibility(View.VISIBLE);
                textPseudo.setVisibility(View.GONE);
                textPassword.setVisibility(View.GONE);
                editPseudo.setVisibility(View.GONE);
                editPassword.setVisibility(View.GONE);
                buttonValidCreate.setVisibility(View.GONE);
                textChangeAvatar.setVisibility(View.GONE);
                buttonValidLogin.setVisibility(View.GONE);
            }
        });
    }
}
