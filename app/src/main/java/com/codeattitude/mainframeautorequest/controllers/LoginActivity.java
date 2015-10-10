package com.codeattitude.mainframeautorequest.controllers;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codeattitude.mainframeautorequest.R;

public class LoginActivity extends AppCompatActivity {

    // current resources
    EditText editTestUsername;
    EditText editTextPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set current resources
        setResources();
        setColorAndFont();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send the authenticate request
                String username = editTestUsername.getText().toString();
                String password = editTextPassword.getText().toString();


                // if success :
                // store token on preference android (crypted if possible)
                // then go to RequestsActivity

                //if error :
                // display error message red with Toast
            }
        });

    }


    // private methods
    private void setResources() {
        editTestUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.login);
    }

    private void setColorAndFont() {
        Typeface tf = getTypeFaceFont("fonts/Museo Sans/MuseoSans-300.otf");
        editTestUsername.setTypeface(tf, Typeface.BOLD);
        editTextPassword.setTypeface(tf, Typeface.BOLD);
        btnLogin.setTypeface(tf, Typeface.BOLD);

    }

    private Typeface getTypeFaceFont(String fontfile) {
        Typeface tf = Typeface.createFromAsset(getAssets(),
                fontfile);
        return tf;
    }


}
