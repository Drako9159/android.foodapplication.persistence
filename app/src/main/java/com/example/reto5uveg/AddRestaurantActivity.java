package com.example.reto5uveg;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto5uveg.persistence.FoodContract;
import com.example.reto5uveg.persistence.FoodDBHelper;

public class AddRestaurantActivity extends AppCompatActivity {

    private Bundle extras;
    private EditText etName;
    private Button btnCreate, btnUpdate, btnDelete;

    private int restaurantSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setVisibility(View.GONE);
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etName = (EditText) findViewById(R.id.etName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnUpdate.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);

        extras = getIntent().getExtras();
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (extras.getString("name") != null) {
                btnUpdate.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.GONE);

                etName.setText(extras.getString("name"));
                restaurantSelectedId = extras.getInt("_id", 0);
            }

        }

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRestaurant();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRestaurant();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRestaurant();
            }
        });

    }

    public void createRestaurant() {
        FoodDBHelper foodDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getWritableDatabase();

        String name = etName.getText().toString();

        if (!name.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);

            long newRowId = sqLiteDatabase.insert(
                    FoodContract.RestaurantEntry.TABLE_NAME, null, contentValues
            );
            if (newRowId != -1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Restaurante registrado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRestaurant() {
        FoodDBHelper foodDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getWritableDatabase();

        String name = etName.getText().toString();
        if (!name.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            long count = sqLiteDatabase.update(
                    FoodContract.RestaurantEntry.TABLE_NAME,
                    contentValues, "_id=" + restaurantSelectedId,
                    null
            );
            if (count != 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Restaurante actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
            }
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        }


    }

    public void deleteRestaurant() {
        FoodDBHelper foodDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getWritableDatabase();

        int count = sqLiteDatabase.delete(
                FoodContract.RestaurantEntry.TABLE_NAME,
                "_id=" + restaurantSelectedId,
                null
        );
        if (count != 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Restaurante eliminado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error, intenta otra vez", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabase.close();
    }
}