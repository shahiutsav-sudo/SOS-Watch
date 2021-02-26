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

public class Register extends AppCompatActivity {

    EditText jFullName, jEmail, jPassword, jPhoneNumber;
    Button jRegisterButton;
    TextView jGoToLogin;
    FirebaseAuth jAuth;
    ProgressBar jProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        jFullName = findViewById(R.id.fullName);
        jEmail = findViewById(R.id.email2);
        jPassword = findViewById(R.id.password);
        jPhoneNumber = findViewById(R.id.phoneNumber);
        jRegisterButton =findViewById(R.id.registerButton);
        jGoToLogin = findViewById(R.id.goToLogin);

        jAuth = FirebaseAuth.getInstance();
        jProgressBar = findViewById(R.id.progressBar);

        if(jAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        jRegisterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                    String email = jEmail.getText().toString().trim();
                    String password = jPassword.getText().toString().trim();


                    //Error Handling

                    // If email field is empty
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


                    // user registrarion in FireBase

                    jAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                //redirect main activity
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else{

                                Toast.makeText(Register.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            }

        });


        //redirect to login page

        jGoToLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),Login.class));

            }

        });

    }


}