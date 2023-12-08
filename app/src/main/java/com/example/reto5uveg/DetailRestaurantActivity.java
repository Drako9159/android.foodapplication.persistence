package com.example.reto5uveg;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.reto5uveg.entity.Food;
import com.example.reto5uveg.tab.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DetailRestaurantActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private int recyclerViewItemSelected;
    private ArrayList<Food> foodArrayList;
    private String restaurantName;
    private int restaurantId;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        extras = getIntent().getExtras();
        if (extras != null) {
            restaurantName = extras.getString("restaurant_name");
            restaurantId = extras.getInt("restaurant_id", 0);
        }


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


    public void generateTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), restaurantId, restaurantName);
        viewPager.setAdapter(viewPagerAdapter);
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
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
    }*/

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_bar, menu);
        return true;
    }

*/


/*
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editItem) {
            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }*/

    /*
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);

    }*/
}