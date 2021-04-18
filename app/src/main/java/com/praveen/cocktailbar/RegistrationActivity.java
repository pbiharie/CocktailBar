package com.praveen.cocktailbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.praveen.cocktailbar.services.DatabaseHelper;
import com.praveen.cocktailbar.model.User;

public class RegistrationActivity extends AppCompatActivity {
    private EditText firstNameEditText, lastNameEditText, userNameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        databaseHelper =  new DatabaseHelper(RegistrationActivity.this);

        setupUIViews();

        registerButton.setOnClickListener(c -> {
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String userName = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if(validate(firstName, lastName, userName, password, confirmPassword)){
                databaseHelper.addUser(new User(firstName, lastName, userName, password));
                gotoMainActivityActivity();
                Toast.makeText(this, "User " + userName + " added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUIViews(){
        firstNameEditText = (EditText) findViewById(R.id.firstNameRegEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameRegEditText);
        userNameEditText = (EditText) findViewById(R.id.usernameRegEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordRegEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordRegEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    private void gotoMainActivityActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean userNameExist(String userName){
        Boolean result = false;
        if(databaseHelper.getUser(userName) != null){
            result = true;
        }
        return result;
    }

    private boolean validate(String firstName, String lastName, String userName, String password, String confirmPassword){
    Boolean result = false;

    if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty() ||
       confirmPassword.isEmpty()){
        Toast.makeText(this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
    } else {
        if(userNameExist(userName)){
            Toast.makeText(this, "This username is already taken. Please try another one!", Toast.LENGTH_SHORT).show();
        } else {
            if(!password.equals(confirmPassword)){
                Toast.makeText(this, "Password fields don't match!", Toast.LENGTH_SHORT).show();
            } else {
                result = true;
            }
        }
    }
    return result;
    }

}