package com.example.reto5uveg.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto5uveg.CrudFoodActivity;
import com.example.reto5uveg.DetailFoodActivity;
import com.example.reto5uveg.R;
import com.example.reto5uveg.entity.Food;

import java.util.ArrayList;

public class RecyclerViewFoodAdapter extends RecyclerView.Adapter<RecyclerViewFoodAdapter.ViewHolder> {
    private ArrayList<Food> foodArrayList;
    private OnLongItemCustomListener onLongItemCustomListener;
    private Context context;

    private String restaurantName;

    public RecyclerViewFoodAdapter(Context context, ArrayList<Food> foodArrayList, String restaurantName) {
        this.context = context;
        this.restaurantName = restaurantName;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_food_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewFoodAdapter.ViewHolder(view);
    }

    public interface OnLongItemCustomListener {
        void itemLongClicked(View v, int position);
    }

    public void setOnLongItemCustomListener(OnLongItemCustomListener onLongItemCustomListener) {
        this.onLongItemCustomListener = onLongItemCustomListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewFoodAdapter.ViewHolder holder, int position) {
        holder.setValues(foodArrayList.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongItemCustomListener != null) {
                    onLongItemCustomListener.itemLongClicked(v, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView tvName, tvPrice;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutFoodItem);
            tvName = (TextView) itemView.findViewById(R.id.tvFoodName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvFoodPrice);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnCreateContextMenuListener(this);

        }

        public void setValues(Food food) {
            tvPrice.setText(food.getPrice() + "");
            tvName.setText(food.getName());
        }


        @Override
        public void onClick(View v) {
            Food food = foodArrayList.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailFoodActivity.class);
            intent.putExtra("food_name", food.getName());
            intent.putExtra("food_price", "Precio: " + food.getPrice().toString() + "$");
            intent.putExtra("food_description", food.getDescription());
            context.startActivity(intent);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem editItem = menu.add(Menu.NONE, R.id.editItem, 1, "EDITAR");
            editItem.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(context, CrudFoodActivity.class);
                Food food = foodArrayList.get(getAdapterPosition());
                intent.putExtra("name", food.getName());
                intent.putExtra("description", food.getDescription());
                intent.putExtra("price", food.getPrice());
                intent.putExtra("food_type", food.getFoodType().toString());
                intent.putExtra("_id", food.get_id());
                intent.putExtra("restaurant_id", food.getRestaurant_id());
                intent.putExtra("restaurant_name", restaurantName);
                v.getContext().startActivity(intent);
                return true;
            });
        }
    }

}
