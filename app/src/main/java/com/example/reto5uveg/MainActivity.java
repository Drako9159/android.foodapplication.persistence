package com.example.reto5uveg;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto5uveg.adapter.RecyclerViewRestaurantAdapter;
import com.example.reto5uveg.entity.Restaurant;
import com.example.reto5uveg.persistence.GetDataList;
import com.example.reto5uveg.utils.ButtonAddAction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Restaurant> restaurantArrayList;
    private int recyclerViewItemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buildRecyclerView();
        Intent intent = new Intent(this, CrudRestaurantActivity.class);
        new ButtonAddAction(findViewById(android.R.id.content), intent);
    }


    public void buildRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        restaurantArrayList = GetDataList.getDataRestaurant(this);
        RecyclerViewRestaurantAdapter adapter = new RecyclerViewRestaurantAdapter(this, restaurantArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnLongItemCustomListener((View v, int position) -> {
            recyclerViewItemSelected = position;
            v.showContextMenu();
        });
        registerForContextMenu(recyclerView);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editItem) {
            Intent intent = new Intent(this, CrudRestaurantActivity.class);
            Restaurant restaurant = restaurantArrayList.get(recyclerViewItemSelected);
            intent.putExtra("name", restaurant.getName());
            intent.putExtra("_id", restaurant.get_id());
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}