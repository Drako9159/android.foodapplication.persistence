package com.example.reto5uveg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.reto5uveg.entity.Food;
import com.example.reto5uveg.entity.FoodType;
import com.example.reto5uveg.persistence.FoodContract;
import com.example.reto5uveg.persistence.FoodDBHelper;
import com.example.reto5uveg.tab.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class DetailRestaurantActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Food> foodArrayList;
    private String restaurantName;
    private int restaurantId;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        extras = getIntent().getExtras();
        restaurantName = extras.getString("restaurant_name");
        restaurantId = extras.getInt("restaurant_id");


        ImageView ivAdd = (ImageView) findViewById(R.id.ivAdd);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddFoodActivity.class);
                intent.putExtra("restaurant_name", restaurantName);
                intent.putExtra("restaurant_id", restaurantId);
                startActivity(intent);
            }
        });


        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(restaurantName);
        tvTitle.setTextAppearance(R.style.Body);


        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener((b) -> onBackPressed());

        generateTabs();

    }

    public void buildRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        foodArrayList = new ArrayList<>();
        FoodDBHelper foodDBHelper = new FoodDBHelper(this);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + FoodContract.FoodEntry.TABLE_NAME + " WHERE restaurant_id=" + restaurantId;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            FoodType foodType = FoodType.FOOD;
            if(cursor.getString(cursor.getColumnIndex("food_type")) == "FOOD") foodType = FoodType.FOOD;
            if(cursor.getString(cursor.getColumnIndex("food_type")) == "DRINK") foodType = FoodType.DRINK;
            if(cursor.getString(cursor.getColumnIndex("food_type")) == "COMPLEMENT") foodType = FoodType.COMPLEMENT;

            int restaurant_id = cursor.getInt(cursor.getColumnIndex("restaurant_id"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            foodArrayList.add(new Food(_id, name, price, description, foodType, restaurant_id));
        }
    }



    public void generateTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
/*
        String footType = getIntent().getStringExtra("food_type");
        if (Objects.equals(footType, "food_frame")) {
            viewPager.setCurrentItem(0);
        }
        if (Objects.equals(footType, "drink_frame")) {
            viewPager.setCurrentItem(1);
        }
        if (Objects.equals(footType, "complement_frame")) {
            viewPager.setCurrentItem(2);
        }*/

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == (0 | 1 | 2)) {
                    viewPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}