package com.praveen.cocktailbar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.praveen.cocktailbar.R;
import com.praveen.cocktailbar.model.User;
import com.praveen.cocktailbar.services.DatabaseHelper;

public class EditDeleteUserDialog extends AppCompatDialogFragment {
    private EditText firstNameEditDailogEditText, lastNameEditDailogEditText, userNameEditDailogEditText, passwordEditDialogEditText,
            confirmPasswordEditDialogEditText;
    Button editUserDialogButton, deleteDialogButton;
    private DatabaseHelper databaseHelper;
    User user;
    View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        databaseHelper =  new DatabaseHelper(getContext());

        Bundle bundle = getArguments();
        user = bundle.getParcelable("User");

        setupUIViews();
        setText(user);

        editUserDialogButton.setOnClickListener(c -> {
            int id = user.getId();
            String firstName = firstNameEditDailogEditText.getText().toString();
            String lastName = lastNameEditDailogEditText.getText().toString();
            String userName = userNameEditDailogEditText.getText().toString();
            String password = passwordEditDialogEditText.getText().toString();
            String confirmPassword = confirmPasswordEditDialogEditText.getText().toString();

            if(validate(firstName, lastName, userName, password, confirmPassword)){
                new UpdateUserTask().execute(new User(id, firstName, lastName, userName, password));
                Toast.makeText(getContext(), "User Updated", Toast.LENGTH_SHORT).show();
                this.dismiss();
            }
        });

        deleteDialogButton.setOnClickListener(c -> {
            databaseHelper.deleteUser(user);
            this.dismiss();
        });


        builder.setView(view);
        return builder.create();

    }

    private void setText(User user){
        firstNameEditDailogEditText.setText(user.getFirstName());
        lastNameEditDailogEditText.setText(user.getLastName());
        userNameEditDailogEditText.setText(user.getUserName());
        passwordEditDialogEditText.setText(user.getPassword());
        confirmPasswordEditDialogEditText.setText(user.getPassword());
    }

    private boolean userNameValidToEdit(String userName){
        Boolean result = true;
        if(userName.equalsIgnoreCase(user.getUserName())){
           result = true;
        } else {
            if(databaseHelper.getUser(userName) != null){
                result = false;
            }
        }
        return result;
    }

    private boolean validate(String firstName, String lastName, String userName, String password, String confirmPassword){
        Boolean result = false;

        if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty()){
            Toast.makeText(getContext(), "Please enter all the details!", Toast.LENGTH_SHORT).show();
        } else {
            if(!userNameValidToEdit(userName)){
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
        view = inflater.inflate(R.layout.edit_delete_user_dialog_layout,null);
        firstNameEditDailogEditText = view.findViewById(R.id.firstNameEditDailogEditText);
        lastNameEditDailogEditText = view.findViewById(R.id.lastNameEditDailogEditText);
        userNameEditDailogEditText = view.findViewById(R.id.userNameEditDailogEditText);
        passwordEditDialogEditText = view.findViewById(R.id.passwordEditDialogEditText);
        confirmPasswordEditDialogEditText = view.findViewById(R.id.confirmPasswordEditDialogEditText);
        editUserDialogButton = view.findViewById(R.id.editUserDialogButton);
        deleteDialogButton = view.findViewById(R.id.deleteDialogButton);
    }



    private class UpdateUserTask extends AsyncTask<User, Void, Boolean> {

        private ContentValues contentValues;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            contentValues = new ContentValues();
            contentValues.put("Firstname", firstNameEditDailogEditText.getText().toString());
            contentValues.put("Lastname", lastNameEditDailogEditText.getText().toString());
            contentValues.put("Username", userNameEditDailogEditText.getText().toString());
            contentValues.put("Password", passwordEditDialogEditText.getText().toString());
        }


        @Override
        protected Boolean doInBackground(User... users) {
            DatabaseHelper databaseHelper =  new DatabaseHelper(getContext());

            try {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.update("user", contentValues, "ID = ?", new String[]{String.valueOf(users[0].getId())});
                db.close();
                return  true;
            }catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
