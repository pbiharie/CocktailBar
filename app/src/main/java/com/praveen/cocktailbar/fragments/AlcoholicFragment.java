package com.praveen.cocktailbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praveen.cocktailbar.R;
import com.praveen.cocktailbar.dialogs.CocktailDialog;
import com.praveen.cocktailbar.model.Cocktail;
import com.praveen.cocktailbar.services.CocktailListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlcoholicFragment extends Fragment implements CocktailDialog.CocktailDialogListener{

    private static final String API_URL = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic";
    ArrayList<Cocktail> cocktailArrayList = new ArrayList<>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alcoholic, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.alcoholic_ListView);
        getAlcoholicCocktails();

        listView.setOnItemClickListener((parent, view1, position, id) -> {
                CocktailDialog cocktailDialog = new CocktailDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("cocktail_ID",  cocktailArrayList.get(position).getId());
                cocktailDialog.setArguments(bundle);
                cocktailDialog.show(getFragmentManager(), "Cocktail dialog");
        });
    }

    private void getAlcoholicCocktails() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray drinksArray = obj.getJSONArray("drinks");

                        for (int i = 0; i < drinksArray.length(); i++) {
                            JSONObject drinkObject = drinksArray.getJSONObject(i);
                            Cocktail cocktail = new Cocktail(drinkObject.getInt("idDrink"), drinkObject.getString("strDrink"),
                                    drinkObject.getString("strDrinkThumb"));
                            cocktailArrayList.add(cocktail);
                        }

                        CocktailListAdapter adapter = new CocktailListAdapter(getContext(), cocktailArrayList);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void applyTexts(String text) {
        // test.setText(text);
    }
}
