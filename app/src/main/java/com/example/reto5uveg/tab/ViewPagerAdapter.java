package com.example.reto5uveg.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int behavior;

    public ViewPagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.behavior = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabFood();
            case 1:
                return new TabDrink();
            case 2:
                return new TabComplement();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }
}
