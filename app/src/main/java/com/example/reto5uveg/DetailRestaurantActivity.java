package com.example.reto5uveg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.reto5uveg.tab.ViewPagerAdapter;
import com.example.reto5uveg.utils.ButtonAddAction;
import com.google.android.material.tabs.TabLayout;

public class DetailRestaurantActivity extends AppCompatActivity {
    private String restaurantName;
    private int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            restaurantName = extras.getString("restaurant_name");
            restaurantId = extras.getInt("restaurant_id", 0);
        }

        Intent intent = new Intent(this, CrudFoodActivity.class);
        intent.putExtra("restaurant_name", restaurantName);
        intent.putExtra("restaurant_id", restaurantId);
        new ButtonAddAction(findViewById(android.R.id.content), intent);

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
}