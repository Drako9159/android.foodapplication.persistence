package com.example.reto5uveg.persistence;

import android.provider.BaseColumns;

public class FoodContract {

    private FoodContract() {

    }
    public static class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FOOD_TYPE = "food_type";

        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";

        public static final String SQL_CREATE_FOOD_ENTRY = "" +
                "CREATE TABLE " + FoodEntry.TABLE_NAME + " (" +
                FoodEntry._ID + " INTEGER PRIMARY KEY," +
                FoodEntry.COLUMN_NAME + " TEXT," +
                FoodEntry.COLUMN_PRICE + " TEXT," +
                FoodEntry.COLUMN_DESCRIPTION + " TEXT," +
                FoodEntry.COLUMN_FOOD_TYPE + " TEXT," +
                FoodEntry.COLUMN_RESTAURANT_ID + " INTEGER," +
                "FOREIGN KEY(" + FoodEntry.COLUMN_RESTAURANT_ID + ") REFERENCES " +
                RestaurantEntry.TABLE_NAME + "(" + RestaurantEntry._ID + "))";
    }

    public static class RestaurantEntry implements BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String COLUMN_NAME = "name";
        public static final String SQL_CREATE_RESTAURANT_ENTRY = "" +
                "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
                RestaurantEntry._ID + " INTEGER PRIMARY KEY," +
                RestaurantEntry.COLUMN_NAME + " TEXT)";


    }

}
