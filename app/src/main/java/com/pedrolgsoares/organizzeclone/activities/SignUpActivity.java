package com.pedrolgsoares.organizzeclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.pedrolgsoares.organizzeclone.R;
import com.pedrolgsoares.organizzeclone.config.ConfiguracaoFirebase;
import com.pedrolgsoares.organizzeclone.model.Usuario;

public class SignUpActivity extends AppCompatActivity {
    private EditText name, email, password;
    private Button buttonSignUp;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // captura os id's dos itens
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        password = findViewById(R.id.textPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // busca o que foi digitado nas variáveis
                String getName = name.getText().toString();
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();
                validateText(getName,getEmail,getPassword);


            }
        });
    }
    public void validateText(String name,String email,String password){
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Alguns campos estão vazios, por favor preencher !",Toast.LENGTH_SHORT).show();
        }else {
            usuario = new Usuario();
            usuario.setNome(name);
            usuario.setEmail(email);
            usuario.setSenha(password);
            signUpUser();
        }

    }
    public void signUpUser(){
        firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(SignUpActivity.this,"Sucesso ao cadastrar usuário !",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    String exception = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        exception = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exception = "Por favor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        exception = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar o usuário !" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(SignUpActivity.this,exception,Toast.LENGTH_SHORT).show();

                }
            }
        });
    };


}