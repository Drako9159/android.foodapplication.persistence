package com.example.reto5uveg.tab;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto5uveg.AddFoodActivity;
import com.example.reto5uveg.R;
import com.example.reto5uveg.adapter.RecyclerViewFoodAdapter;
import com.example.reto5uveg.entity.Food;
import com.example.reto5uveg.entity.FoodType;
import com.example.reto5uveg.persistence.FoodContract;
import com.example.reto5uveg.persistence.FoodDBHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFood#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFood extends Fragment {

    private RecyclerView recyclerView;
    private int restaurantId;
    private String restaurantName;

    private RecyclerViewFoodAdapter adapter;

    private int recyclerViewItemSelected;
    private ArrayList<Food> foodArrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabFood() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFood.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFood newInstance(String param1, String param2) {
        TabFood fragment = new TabFood();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_food, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFood);
        restaurantId = getArguments().getInt("restaurant_id", 0);
        restaurantName = getArguments().getString("restaurant_name");
        buildRecyclerView();
        return view;
    }


    public void buildRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        foodArrayList = new ArrayList<>();

        FoodDBHelper foodDBHelper = new FoodDBHelper(getContext());
        SQLiteDatabase sqLiteDatabase = foodDBHelper.getReadableDatabase();
        FoodType foodType = FoodType.FOOD;
        String sql = "SELECT * FROM " + FoodContract.FoodEntry.TABLE_NAME +
                " WHERE restaurant_id=" + restaurantId +
                " AND food_type='" + foodType.toString() + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));

            int restaurant_id = cursor.getInt(cursor.getColumnIndex("restaurant_id"));
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            foodArrayList.add(new Food(_id, name, price, description, foodType, restaurant_id));
        }
        sqLiteDatabase.close();
        adapter = new RecyclerViewFoodAdapter(getContext(), foodArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnLongItemCustomListener((View v, int position) -> {
            recyclerViewItemSelected = position;
            v.showContextMenu();
        });
        registerForContextMenu(recyclerView);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editItem) {
            Intent intent = new Intent(getContext(), AddFoodActivity.class);
            Food food = foodArrayList.get(recyclerViewItemSelected);
            intent.putExtra("name", food.getName());
            intent.putExtra("description", food.getDescription());
            intent.putExtra("price", food.getPrice());
            intent.putExtra("foot_type", food.getFoodType());
            intent.putExtra("_id", food.get_id());
            intent.putExtra("restaurant_id", food.getRestaurant_id());
            intent.putExtra("restaurant_name", restaurantName);
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}