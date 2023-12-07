package com.example.reto5uveg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto5uveg.adapter.RecyclerViewRestaurantAdapter;
import com.example.reto5uveg.entity.Restaurant;
import com.example.reto5uveg.persistence.FoodContract;
import com.example.reto5uveg.persistence.FoodDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Restaurant> restaurantArrayList;
    private RecyclerViewRestaurantAdapter adapter;
    private int recyclerViewItemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buildRecyclerView();


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddRestaurantActivity.class);
                startActivity(intent);
            }
        });


    }

    public void buildRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        restaurantArrayList = new ArrayList<>();

        FoodDBHelper foodDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + FoodContract.RestaurantEntry.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(FoodContract.RestaurantEntry.COLUMN_NAME));
            int _id = cursor.getInt(cursor.getColumnIndex(FoodContract.RestaurantEntry._ID));
            restaurantArrayList.add(new Restaurant(_id, name));
        }
        sqLiteDatabase.close();

        adapter = new RecyclerViewRestaurantAdapter(this, restaurantArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnLongItemCustomListener((View v, int position) -> {
            recyclerViewItemSelected = position;
            v.showContextMenu();
        });
        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
        if (item.getItemId() == R.id.editItem) {
            Intent intent = new Intent(this, AddRestaurantActivity.class);
            Restaurant restaurant = restaurantArrayList.get(recyclerViewItemSelected);
            // not get id bug
            Toast.makeText(this, recyclerViewItemSelected+"e", Toast.LENGTH_SHORT).show();
            intent.putExtra("name", restaurant.getName());
            intent.putExtra("_id", restaurant.get_id());
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}