package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity<isValid> extends AppCompatActivity {

    private EditText eName;
    private EditText ePassword;
    private Button btnLogin;
    private TextView tvAttempts;
    private TextView tvReg;
    private CheckBox eCheck;

    boolean isValid = false;
    private int counter = 5;

    public InputData inputData;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eName = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvAttempts = findViewById(R.id.tvAttempts);
        tvReg = findViewById(R.id.tvRegister);
        eCheck = findViewById(R.id.cbRemember);

        inputData = new InputData();


        sharedPreferences = getApplicationContext().getSharedPreferences("UserInfoDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> preferencesMap = sharedPreferences.getAll();

            if(preferencesMap.size() != 0){
                inputData.loadInputs(preferencesMap);
            }

            String savedUsername = sharedPreferences.getString("LastSavedUsername","");
            String savedPassword = sharedPreferences.getString("LastSavedPassword","");

            if(sharedPreferences.getBoolean("cbRememberMe", false)){
                eName.setText(savedUsername);
                ePassword.setText(savedPassword);
                eCheck.setChecked(true);
            }
        }

        eCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferencesEditor.putBoolean("cbRememberMe", eCheck.isChecked());

                sharedPreferencesEditor.apply();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = eName.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if( inputName.isEmpty() || inputPassword.isEmpty() ){
                    Toast.makeText(MainActivity.this,"Please enter name or password correctly!", Toast.LENGTH_SHORT).show();
                }
                else{
                    isValid = validate(inputName, inputPassword);
                    if(!isValid){
                        counter--;
                        Toast.makeText(MainActivity.this,"incorrect name or password!", Toast.LENGTH_SHORT).show();
                        tvAttempts.setText("number of attempts remaining: " + counter);
                        if(counter==0) {
                            btnLogin.setEnabled(false);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Login Successful!", Toast.LENGTH_SHORT).show();

                        sharedPreferencesEditor.putString("LastSavedUsername", inputName);
                        sharedPreferencesEditor.putString("LastSavedPassword" , inputPassword);
                        sharedPreferencesEditor.apply();
                        // add the code to go to homepage
                        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
    private boolean validate(String name, String password) {
        return inputData.checkInput(name, password);
    }
}