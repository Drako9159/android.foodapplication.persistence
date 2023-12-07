package com.example.reto5uveg.tab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.reto5uveg.entity.FoodType;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Bundle bundle = new Bundle();
    int behavior;

    public ViewPagerAdapter(FragmentManager fm, int behavior, int restaurantId, String restaurantName) {
        super(fm, behavior);
        this.behavior = behavior;
        bundle.putInt("restaurant_id", restaurantId);
        bundle.putString("restaurant_name", restaurantName);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabFood tabFood = new TabFood();
                tabFood.setArguments(bundle);
                return tabFood;
            case 1:
                TabDrink tabDrink = new TabDrink();
                tabDrink.setArguments(bundle);
                return tabDrink;
            case 2:
                TabComplement tabComplement = new TabComplement();
                tabComplement.setArguments(bundle);
                return tabComplement;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }


}
