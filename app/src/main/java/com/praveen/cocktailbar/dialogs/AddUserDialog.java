package com.praveen.cocktailbar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.praveen.cocktailbar.R;
import com.praveen.cocktailbar.RegistrationActivity;
import com.praveen.cocktailbar.fragments.UserFragment;
import com.praveen.cocktailbar.model.Cocktail;
import com.praveen.cocktailbar.model.User;
import com.praveen.cocktailbar.services.DatabaseHelper;
import com.praveen.cocktailbar.services.UsersListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class AddUserDialog extends AppCompatDialogFragment {
    private EditText firstNameDailogEditText, lastNameDailogEditText, userNameDailogEditText, passwordDialogEditText,
            confirmPasswordDialogEditText;
    Button addUserDialogButton, cancelDialogButton;
    private DatabaseHelper databaseHelper;
    View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        databaseHelper =  new DatabaseHelper(getContext());


        setupUIViews();

        addUserDialogButton.setOnClickListener(c -> {

            String firstName = firstNameDailogEditText.getText().toString();
            String lastName = lastNameDailogEditText.getText().toString();
            String userName = userNameDailogEditText.getText().toString();
            String password = passwordDialogEditText.getText().toString();
            String confirmPassword = confirmPasswordDialogEditText.getText().toString();

            if(validate(firstName, lastName, userName, password, confirmPassword)){
                databaseHelper.addUser(new User(firstName, lastName, userName, password));
                Toast.makeText(getContext(), "User " + userName + " added", Toast.LENGTH_SHORT).show();
                this.dismiss();
            }
        });

        cancelDialogButton.setOnClickListener(c -> {
            this.dismiss();
        });


        builder.setView(view);
        return builder.create();

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
            Toast.makeText(getContext(), "Please enter all the details!", Toast.LENGTH_SHORT).show();
        } else {
            if(userNameExist(userName)){
                Toast.makeText(getContext(), "This username is already taken. Please try another one!", Toast.LENGTH_SHORT).show();
            } else {
                if(!password.equals(confirmPassword)){
                    Toast.makeText(getContext(), "Password fields don't match!", Toast.LENGTH_SHORT).show();
                } else {
                    result = true;
                }
            }
        }
        return result;
    }


    public interface AddUserDialogListener{
        void applyTexts(String text);
    }

    private void setupUIViews(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_user_dialog_layout,null);
        firstNameDailogEditText = view.findViewById(R.id.firstNameDailogEditText);
        lastNameDailogEditText = view.findViewById(R.id.lastNameDailogEditText);
        userNameDailogEditText = view.findViewById(R.id.userNameDailogEditText);
        passwordDialogEditText = view.findViewById(R.id.passwordDialogEditText);
        confirmPasswordDialogEditText = view.findViewById(R.id.confirmPasswordDialogEditText);
        addUserDialogButton = view.findViewById(R.id.addUserDialogButton);
        cancelDialogButton = view.findViewById(R.id.cancelDialogButton);
    }


}
