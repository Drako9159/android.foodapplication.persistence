package com.example.reto5uveg;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto5uveg.persistence.FoodContract;
import com.example.reto5uveg.persistence.FoodDBHelper;
import com.example.reto5uveg.utils.CustomSpinnerAdapter;

import java.util.Objects;

public class AddFoodActivity extends AppCompatActivity {
    private Bundle extras;
    private int restaurantId;
    private String restaurantName;

    private String foodType;
    private EditText etFoodName, etFoodPrice, etFoodDescription;

    private Button btnCreate, btnUpdate, btnDelete;

    private int foodSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        etFoodName = (EditText) findViewById(R.id.etName);
        etFoodDescription = (EditText) findViewById(R.id.etDescription);
        etFoodPrice = (EditText) findViewById(R.id.etPrice);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnUpdate.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

        extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getString("name") != null) {
                btnUpdate.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.GONE);
            }
            etFoodName.setText(extras.getString("name"));
            etFoodPrice.setText("" + extras.getDouble("price", 0.0));
            etFoodDescription.setText(extras.getString("description"));

            foodSelectedId = extras.getInt("_id", 0);
            restaurantId = extras.getInt("restaurant_id", 0);
            restaurantName = extras.getString("restaurant_name");
            foodType = extras.getString("food_type");
        }


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setVisibility(View.GONE);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(restaurantName);
        tvTitle.setTextAppearance(R.style.Body);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener((b) -> onBackPressed());

        Spinner spinnerType = findViewById(R.id.spinnerType);
        String[] tipos = getResources().getStringArray(R.array.tipos);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, tipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(0);

        if (Objects.equals(foodType, "FOOD")) spinnerType.setSelection(0);
        if (Objects.equals(foodType, "DRINK")) spinnerType.setSelection(1);
        if (Objects.equals(foodType, "COMPLEMENT")) spinnerType.setSelection(2);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedType = tipos[position];

                if (Objects.equals(selectedType, "COMIDA")) foodType = "FOOD";
                if (Objects.equals(selectedType, "BEBIDA")) foodType = "DRINK";
                if (Objects.equals(selectedType, "COMPLEMENTO")) foodType = "COMPLEMENT";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Se llama cuando no hay ninguna selección
            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFood();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFood();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFood();
            }
        });
    }


    public void createFood() {
        FoodDBHelper storeDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        String name = etFoodName.getText().toString();
        double price = Double.parseDouble(etFoodPrice.getText().toString());
        String description = etFoodDescription.getText().toString();


        if (!name.isEmpty() && !description.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("price", price);
            contentValues.put("description", description);
            contentValues.put("food_type", foodType);
            contentValues.put("restaurant_id", restaurantId);
            long newRowId = sqLiteDatabase.insert(
                    FoodContract.FoodEntry.TABLE_NAME,
                    null,
                    contentValues
            );
            if (newRowId != -1) {
                Intent intent = new Intent(this, DetailRestaurantActivity.class);
                intent.putExtra("restaurant_id", restaurantId);
                intent.putExtra("restaurant_name", restaurantName);
                startActivity(intent);
                Toast.makeText(this, "Producto registrado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFood() {
        FoodDBHelper storeDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        String name = etFoodName.getText().toString();
        double price = Double.parseDouble(etFoodPrice.getText().toString());
        String description = etFoodDescription.getText().toString();

        if (!name.isEmpty() && !description.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("price", price);
            contentValues.put("description", description);
            contentValues.put("food_type", foodType);


            long count = sqLiteDatabase.update(
                    FoodContract.FoodEntry.TABLE_NAME,
                    contentValues, "_id=" + foodSelectedId, null
            );
            if (count != 0) {
                Intent intent = new Intent(this, DetailRestaurantActivity.class);
                intent.putExtra("restaurant_id", restaurantId);
                intent.putExtra("restaurant_name", restaurantName);
                startActivity(intent);
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFood() {
        FoodDBHelper storeDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = storeDBHelper.getWritableDatabase();

        int count = sqLiteDatabase.delete(
                FoodContract.FoodEntry.TABLE_NAME,
                "_id=" + foodSelectedId, null
        );
        if (count != 0) {
            Intent intent = new Intent(this, DetailRestaurantActivity.class);
            intent.putExtra("restaurant_id", restaurantId);
            intent.putExtra("restaurant_name", restaurantName);
            startActivity(intent);
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();

        }
        sqLiteDatabase.close();

    }
}