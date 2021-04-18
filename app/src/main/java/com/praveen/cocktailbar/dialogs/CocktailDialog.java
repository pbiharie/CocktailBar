package com.praveen.cocktailbar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.praveen.cocktailbar.model.Cocktail;
import com.praveen.cocktailbar.services.CocktailListAdapter;
import com.praveen.cocktailbar.services.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class CocktailDialog extends AppCompatDialogFragment {
    private TextView cocktailName_TextView, cocktailCategory_TextView, cocktailDrinkType_TextView, cocktailGlass_TextView,
            cocktailIngredients_TextView, cocktailInstruction_TextView;
    private ImageView cocktailThumb_ImageView;
    View view;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private static final String API_URL = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = getArguments();
        int cocktailId = bundle.getInt("cocktail_ID");

        setupUIViews();

        getAlcoholicCocktail(cocktailId);

        builder.setView(view);
        return builder.create();
    }


    public interface CocktailDialogListener{
        void applyTexts(String text);
    }

    private void setupUIViews(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.cocktail_dialog_layout,null);
        cocktailName_TextView = view.findViewById(R.id.dialogCocktailName_TextView);
        cocktailCategory_TextView = view.findViewById(R.id.dialogCocktailCategory_TextView);
        cocktailThumb_ImageView = view.findViewById(R.id.cocktkailThumbnail_ImageView);
        cocktailDrinkType_TextView = view.findViewById(R.id.dialogCocktailDrinkType_TextView);
        cocktailGlass_TextView = view.findViewById(R.id.dialogCocktailGlass_TextView);
        cocktailIngredients_TextView = view.findViewById(R.id.dialogCocktailIngredients_TextView);
        cocktailInstruction_TextView = view.findViewById(R.id.dialogCocktailInstruction_TextView);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
    }

    private void getAlcoholicCocktail(int cocktailId) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, (API_URL + String.valueOf(cocktailId)),
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray drinksArray = obj.getJSONArray("drinks");
                        Hashtable<String, String> ingredientAndMeasurement = new Hashtable<>();
                        String measurement = null;

                        JSONObject drinkObject = drinksArray.getJSONObject(0);

                        for(int x = 1; x < 16; x++){
                            if(drinkObject.getString("strIngredient" + x).contains("null")){
                                break;
                            }
                            if(!drinkObject.getString("strMeasure"+x).contains("null")){
                                measurement = drinkObject.getString("strMeasure"+x);
                            }
                            ingredientAndMeasurement.put(drinkObject.getString("strIngredient"+ x),
                                    measurement == null ? "" :
                                            measurement.contains("\n")? measurement.replace("\n", "") :
                                                    measurement);

                        }

                        Cocktail cocktail = new Cocktail(drinkObject.getInt("idDrink"), drinkObject.getString("strDrink"),
                            drinkObject.getString("strDrinkThumb"), drinkObject.getString("strCategory"),
                            drinkObject.getString("strAlcoholic"), drinkObject.getString("strGlass"),
                            drinkObject.getString("strInstructions"), ingredientAndMeasurement);

                        cocktailName_TextView.setText(cocktail.getCocktailName());
                        options = new DisplayImageOptions.Builder()
                                .showStubImage(R.drawable.ic_launcher_foreground)
                                .showImageForEmptyUri(R.drawable.ic_launcher_background)
                                .cacheInMemory()
                                .cacheOnDisc()
                                .build();
                        imageLoader.displayImage(cocktail.getDrinkThumbnailURL(), cocktailThumb_ImageView, options, null);
                        cocktailCategory_TextView.setText("Category: " + cocktail.getCategory());
                        cocktailDrinkType_TextView.setText("Drink Type: " + cocktail.getDrinkType());
                        cocktailGlass_TextView.setText("Glass: " + cocktail.getGlass());

                        StringBuilder ingredientsAndMeasurement = new StringBuilder();
                        for(Map.Entry m : cocktail.getIngredientAndMeasurement().entrySet()){
                            ingredientsAndMeasurement.append(("- " + m.getValue() + " " + m.getKey() + "\n"));
                        }
                        cocktailIngredients_TextView.setText(ingredientsAndMeasurement != null ? ingredientsAndMeasurement.toString() : null);

                        cocktailInstruction_TextView.setText("Instruction: " + cocktail.getInstruction());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
