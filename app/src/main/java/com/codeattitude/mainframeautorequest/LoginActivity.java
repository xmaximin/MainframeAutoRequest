package com.codeattitude.mainframeautorequest;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    // current ressources
    EditText username;
    EditText password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set current resources
        setResources();
        Typeface tf = getTypeFaceFont("fonts/MuseoSans-100.otf");
        username.setTypeface(tf);
        password.setTypeface(tf);
        login.setTypeface(tf);


    }







    // private methods
    private void setResources(){
        username = (EditText)findViewById(R.id.editTextUsername);
        password = (EditText)findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.login);
    }

    private Typeface getTypeFaceFont(String fontfile){
        Typeface tf = Typeface.createFromAsset(getAssets(),
               fontfile);
        return tf;
    }



}
