package com.example.reto5uveg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setVisibility(View.GONE);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener((b) -> onBackPressed());

        String foodName = getIntent().getStringExtra("food_name");
        String foodPrice = getIntent().getStringExtra("food_price");
        String foodDescription = getIntent().getStringExtra("food_description");
        TextView tvFoodName = (TextView) findViewById(R.id.tvFoodName);
        TextView tvFoodPrice = (TextView) findViewById(R.id.tvFoodPrice);
        TextView tvFoodDescription = (TextView) findViewById(R.id.tvFoodDescription);
        tvFoodName.setText(foodName);
        tvFoodPrice.setText(foodPrice);
        tvFoodDescription.setText(foodDescription);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(foodName);
        tvTitle.setTextAppearance(R.style.Body);

        assert foodName != null;
        assignableIv(foodName.toLowerCase());
    }
    public void assignableIv(String foodName) {
        ImageView ivFood = (ImageView) findViewById(R.id.ivFood);
        if (foodName.contains("pastel")) {
            ivFood.setImageResource(R.drawable.ic_pastel);
        }
        if (foodName.contains("hamburguesa") | foodName.contains("tortas")) {
            ivFood.setImageResource(R.drawable.ic_hamburguer);
        }
        if (foodName.contains("pizza")) {
            ivFood.setImageResource(R.drawable.ic_pizza);
        }
        if (foodName.contains("café")) {
            ivFood.setImageResource(R.drawable.ic_coffe);
        }
        if (foodName.contains("helado")) {
            ivFood.setImageResource(R.drawable.ic_ice_cream);
        }
        if (foodName.contains("nuggets")) {
            ivFood.setImageResource(R.drawable.ic_nuggets);
        }
        if (foodName.contains("cerveza")) {
            ivFood.setImageResource(R.drawable.ic_beer);
        }
        if (foodName.contains("tacos") | foodName.contains("carnitas")) {
            ivFood.setImageResource(R.drawable.ic_tacos);
        }
        if (foodName.contains("huevos")) {
            ivFood.setImageResource(R.drawable.ic_eggs);
        }
        if (foodName.contains("fruta")) {
            ivFood.setImageResource(R.drawable.ic_fruit);
        }
        if (foodName.contains("refrescos")) {
            ivFood.setImageResource(R.drawable.ic_soda);
        }

    }

}