package com.praveen.cocktailbar.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.praveen.cocktailbar.R;
import com.praveen.cocktailbar.model.Cocktail;
import com.praveen.cocktailbar.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends ArrayAdapter<User>{

    Context context;
    List<User> usersArrayList;

    public UsersListAdapter(Context c, List<User> usersArrayList){
        super(c, R.layout.row, android.R.id.text1, usersArrayList);
        this. context = c;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.user_row, parent, false);
        TextView userName_TextView = row.findViewById(R.id.userName_TextView);

        userName_TextView.setText(usersArrayList.get(position).getUserName());

        return row;
    }
}
