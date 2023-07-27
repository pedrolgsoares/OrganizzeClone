package com.pedrolgsoares.organizzeclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pedrolgsoares.organizzeclone.R;

public class SignInActivity extends AppCompatActivity {
    private EditText name,password;
    private Button buttonSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name = findViewById(R.id.nameText);
        password = findViewById(R.id.passwordText);
        buttonSignin = findViewById(R.id.buttonSignin);

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this,"FAZENDO LOGIN",Toast.LENGTH_SHORT).show();
            }
        });
    }
}