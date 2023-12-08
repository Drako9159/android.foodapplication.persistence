package com.example.reto5uveg.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.reto5uveg.entity.Food;
import com.example.reto5uveg.entity.FoodType;
import com.example.reto5uveg.entity.Restaurant;

import java.util.ArrayList;

public class GetDataList {

    public static ArrayList<Food> getDataFood(Context context, int restaurantId) {
        ArrayList<Food> arrayList = new ArrayList<>();
        FoodDBHelper foodDBHelper = new FoodDBHelper(context);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + FoodContract.FoodEntry.TABLE_NAME +
                " WHERE restaurant_id=" + restaurantId +
                " AND food_type='" + FoodType.FOOD.toString() + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int restaurant_id = cursor.getInt(cursor.getColumnIndex("restaurant_id"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            arrayList.add(new Food(_id, name, price, description, FoodType.FOOD, restaurant_id));
        }
        sqLiteDatabase.close();
        return arrayList;
    }

    public static ArrayList<Food> getDataDrink(Context context, int restaurantId) {
        ArrayList<Food> arrayList = new ArrayList<>();
        FoodDBHelper foodDBHelper = new FoodDBHelper(context);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + FoodContract.FoodEntry.TABLE_NAME +
                " WHERE restaurant_id=" + restaurantId +
                " AND food_type='" + FoodType.DRINK.toString() + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int restaurant_id = cursor.getInt(cursor.getColumnIndex("restaurant_id"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            arrayList.add(new Food(_id, name, price, description, FoodType.DRINK, restaurant_id));
        }
        sqLiteDatabase.close();
        return arrayList;
    }


    public static ArrayList<Food> getDataComplement(Context context, int restaurantId) {
        ArrayList<Food> arrayList = new ArrayList<>();
        FoodDBHelper foodDBHelper = new FoodDBHelper(context);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + FoodContract.FoodEntry.TABLE_NAME +
                " WHERE restaurant_id=" + restaurantId +
                " AND food_type='" + FoodType.COMPLEMENT.toString() + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int restaurant_id = cursor.getInt(cursor.getColumnIndex("restaurant_id"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            arrayList.add(new Food(_id, name, price, description, FoodType.COMPLEMENT, restaurant_id));
        }
        sqLiteDatabase.close();
        return arrayList;
    }

    public static ArrayList<Restaurant> getDataRestaurant(Context context) {
        ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
        FoodDBHelper foodDBHelper = new FoodDBHelper(context);
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + FoodContract.RestaurantEntry.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(FoodContract.RestaurantEntry.COLUMN_NAME));
            int _id = cursor.getInt(cursor.getColumnIndex(FoodContract.RestaurantEntry._ID));
            restaurantArrayList.add(new Restaurant(_id, name));
        }
        sqLiteDatabase.close();
        return restaurantArrayList;
    }


}
