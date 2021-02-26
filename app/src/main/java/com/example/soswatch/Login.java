package com.example.soswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Login extends AppCompatActivity {

    EditText jEmail, jPassword;
    Button jLoginButton;
    TextView jGoToRegister;
    FirebaseAuth jAuth;
    ProgressBar jProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        jEmail = findViewById(R.id.email1);
        jPassword = findViewById(R.id.password);

        jLoginButton =findViewById(R.id.loginButton);
        jGoToRegister = findViewById(R.id.goToRegister);

        jAuth = FirebaseAuth.getInstance();
        jProgressBar = findViewById(R.id.progressBar2);


        // Log in button onClick

        jLoginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                String email = jEmail.getText().toString().trim();
                String password = jPassword.getText().toString().trim();


                //Error Handling

                if (TextUtils.isEmpty(email)){
                    jEmail.setError("Email is required!");
                    return;
                }


                if (TextUtils.isEmpty(password)){
                    jPassword.setError("Password is required!");
                    return;
                }

                if (password.length() < 6){
                    jPassword.setError("Password must be more than 6 characters long!");
                    return;
                }

                jProgressBar.setVisibility(View.VISIBLE);


                // authenticate the user

                jAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Login.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

        });

        //redirect to register page

        jGoToRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),Register.class));

            }

        });

    }
}