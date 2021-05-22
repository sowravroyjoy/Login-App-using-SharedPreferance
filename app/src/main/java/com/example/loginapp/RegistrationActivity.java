package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText eRegName;
    private EditText eRegPassword;
    private Button eRegBtn;
    private boolean isValid = false;
    public InputData inputData;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        eRegName = findViewById(R.id.etRegName);
        eRegPassword = findViewById(R.id.etRegPassword);
        eRegBtn = findViewById(R.id.btnRegister);

        inputData = new InputData();

        sharedPreferences = getApplicationContext().getSharedPreferences("UserInfoDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> preferencesMap = sharedPreferences.getAll();

            if(preferencesMap.size() != 0){
                inputData.loadInputs(preferencesMap);
            }
        }

        eRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RegName = eRegName.getText().toString();
                String RegPassword = eRegPassword.getText().toString();

                if(validate(RegName,RegPassword)){

                    if(inputData.checkUsername(RegName)){
                        Toast.makeText(RegistrationActivity.this, "Username already taken!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        inputData.addinput(RegName,RegPassword);

                        /* store the user info */
                        sharedPreferencesEditor.putString( RegName, RegPassword);

                        sharedPreferencesEditor.putString("LastSavedUsername",RegName);
                        sharedPreferencesEditor.putString("LastSavedPassword",RegPassword);

                        /* apply the storing function */
                        sharedPreferencesEditor.apply();

                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Registration Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate(String username, String password){
        if(username.isEmpty() || password.length() < 8){
            Toast.makeText(RegistrationActivity.this, "enter variable correctly & password should be 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}