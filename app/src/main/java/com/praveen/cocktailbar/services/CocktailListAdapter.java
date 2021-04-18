package com.praveen.cocktailbar.services;

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

import java.util.ArrayList;

public class CocktailListAdapter extends ArrayAdapter<Cocktail>{

    Context context;
    ArrayList<Cocktail> cocktailList;
    DisplayImageOptions options;
    ImageLoader imageLoader;

    public CocktailListAdapter (Context c, ArrayList<Cocktail> cocktailList){
        super(c, R.layout.row, R.id.cocktailName_TextView, cocktailList);
        this. context = c;
        this.cocktailList = cocktailList;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        ImageView cocktailThumbnail = row.findViewById(R.id.cocktailThumbnail_ImageView);
        TextView cocktailName = row.findViewById(R.id.cocktailName_TextView);

        cocktailName.setText(cocktailList.get(position).getCocktailName());

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher_foreground)
                .showImageForEmptyUri(R.drawable.ic_launcher_background)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        imageLoader.displayImage(cocktailList.get(position).getDrinkThumbnailURL(), cocktailThumbnail, options, null);

        return row;
    }
}
