package com.praveen.cocktailbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.praveen.cocktailbar.services.DatabaseHelper;
import com.praveen.cocktailbar.model.User;
import com.praveen.cocktailbar.services.NotificationService;

public class MainActivity extends AppCompatActivity {
    private TextView registerTextView;
    private EditText userNameEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper =  new DatabaseHelper(MainActivity.this);

        setupUIViews();

        loginButton.setOnClickListener(c ->{
            String userName = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(validate(userName, password)){
                gotoHomeActivity();
                startService(findViewById(android.R.id.content).getRootView());
            }

        });

        registerTextView.setOnClickListener(c -> gotoRegistrationActivity());
    }

    private void gotoRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void gotoHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    private boolean validate(String userName, String password){
        Boolean result = false;

        if(userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
        } else {
            if(isLoginValid(userName, password)){
                result = true;
            } else {
                Toast.makeText(this, "Incorrect credentials. Please enter correct login credentials!", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    private boolean isLoginValid(String userName, String password){
        Boolean result = false;
        User user = databaseHelper.getUser(userName);
        if (user != null) {
            if(user.getUserName().equalsIgnoreCase(userName)){
                if(user.getPassword().equals(password)){
                    result = true;
                }
            }
        }
        return result;
    }

    private void setupUIViews(){
        userNameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerTextView = (TextView) findViewById(R.id.registerTextView);
    }

    public void startService(View v) {
        String input = "Uh oh something is shaking in my cocktail shaker cup. \nIT'S " +
                "OUR ONLINE BUY OPTION!!!";
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra("inputExtra", input);
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, NotificationService.class);
        stopService(serviceIntent);
    }

}