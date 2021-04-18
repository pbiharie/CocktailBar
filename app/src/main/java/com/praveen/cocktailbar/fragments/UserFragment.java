package com.praveen.cocktailbar.fragments;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.praveen.cocktailbar.R;
import com.praveen.cocktailbar.dialogs.AddUserDialog;
import com.praveen.cocktailbar.dialogs.EditDeleteUserDialog;
import com.praveen.cocktailbar.model.User;
import com.praveen.cocktailbar.services.DatabaseHelper;
import com.praveen.cocktailbar.services.UsersListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class UserFragment extends Fragment implements AddUserDialog.AddUserDialogListener{

    ListView usersListView;
    FloatingActionButton addUserButton;
    private DatabaseHelper databaseHelper;
    List<User> usersArrayList = new ArrayList<>();
    UsersListAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersListView = view.findViewById(R.id.users_ListView);
        addUserButton = view.findViewById(R.id.addUser_Button);
        databaseHelper = new DatabaseHelper(getContext());
        populateListView();

        addUserButton.setOnClickListener(c -> {
            AddUserDialog addUserDialog = new AddUserDialog();
            addUserDialog.show(getFragmentManager(), "Add User dialog");
        });

        usersListView.setOnItemClickListener((parent, view1, position, id) -> {
            EditDeleteUserDialog editDeleteUserDialog = new EditDeleteUserDialog();
            Bundle bundle = new Bundle();
            bundle.putParcelable("User" , adapter.getItem(position));
            editDeleteUserDialog.setArguments(bundle);
            editDeleteUserDialog.show(getFragmentManager(), "Edit / Delete User dialog");
        });

        getView().getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> { populateListView(); });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    private void populateListView(){

        usersArrayList = databaseHelper.getAllUsers();
        adapter = new UsersListAdapter(getContext(), usersArrayList);
        usersListView.setAdapter(adapter);
    }

    @Override
    public void applyTexts(String text) {
        // test.setText(text);
    }
}