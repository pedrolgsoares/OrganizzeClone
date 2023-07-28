package com.pedrolgsoares.organizzeclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.config.ConfiguracaoFirebase;
import com.pedrolgsoares.organizzeclone.model.Usuario;

public class SignInActivity extends AppCompatActivity {
    private EditText name,password;
    private Button buttonSignin;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;
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
                // busca o que foi digitado nas variáveis
                String getName = name.getText().toString();
                String getPassword = password.getText().toString();
                validateText(getName,getPassword);
                //Toast.makeText(SignInActivity.this,"FAZENDO LOGIN",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void validateText(String name,String password){
        if (name.isEmpty()  || password.isEmpty()){
            Toast.makeText(SignInActivity.this,"Alguns campos estão vazios, por favor preencher !",Toast.LENGTH_SHORT).show();
        }else {
          usuario = new Usuario();
          usuario.setEmail(name);
          usuario.setSenha(password);
          signInUser();
        }

    }
    public void signInUser(){
        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
        firebaseAuth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful() ){
                    abrirTelaPrincipal();
                }else {

                    String exception = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        exception = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        exception = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(SignInActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
        finish();
    }
}