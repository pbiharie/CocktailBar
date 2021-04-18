package com.praveen.cocktailbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.praveen.cocktailbar.R;

public class HomeFragment extends Fragment {

    ImageView alcoholicCocktail, nonAlcoholicCocktail;
    NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationView = getActivity().findViewById(R.id.nav_view);
        alcoholicCocktail = view.findViewById(R.id. alcoholic_imageView);
        nonAlcoholicCocktail = view.findViewById(R.id. nonAlcoholic_imageView);

        alcoholicCocktail.setOnClickListener(v -> {
            if(savedInstanceState == null) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlcoholicFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_alcoholic);
            }
        });

        nonAlcoholicCocktail.setOnClickListener(v -> {
            if(savedInstanceState == null) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NonAlcoholicFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_non_alcoholic);
            }
        });
    }

}
