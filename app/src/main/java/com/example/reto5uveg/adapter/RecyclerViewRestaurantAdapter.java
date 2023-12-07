package com.example.reto5uveg.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto5uveg.DetailRestaurantActivity;
import com.example.reto5uveg.R;
import com.example.reto5uveg.entity.Restaurant;

import java.util.ArrayList;

public class RecyclerViewRestaurantAdapter extends RecyclerView.Adapter<RecyclerViewRestaurantAdapter.ViewHolder> {
    private ArrayList<Restaurant> restaurantArrayList;
    private OnLongItemCustomListener onLongItemCustomListener;
    private Context context;

    public RecyclerViewRestaurantAdapter(Context context, ArrayList<Restaurant> restaurantArrayList) {
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewRestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_restaurant_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecyclerViewRestaurantAdapter.ViewHolder(view);
    }


    public interface OnLongItemCustomListener {
        void itemLongClicked(View v, int position);
    }


    public void setOnLongItemCustomListener(OnLongItemCustomListener onLongItemCustomListener) {
        this.onLongItemCustomListener = onLongItemCustomListener;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewRestaurantAdapter.ViewHolder holder, int position) {
        holder.setValues(new Restaurant(restaurantArrayList.get(position).get_id(), restaurantArrayList.get(position).getName()));
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
        return restaurantArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {
        TextView tvName;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutRestaurantItem);
            tvName = (TextView) itemView.findViewById(R.id.tvRestaurantName);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnCreateContextMenuListener(this);
        }

        public void setValues(Restaurant restaurant) {
            tvName.setText(restaurant.getName());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }

        @Override
        public void onClick(View v) {
            Restaurant restaurant = restaurantArrayList.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailRestaurantActivity.class);
            intent.putExtra("restaurant_name", restaurant.getName());
            intent.putExtra("restaurant_id", restaurant.get_id());
            context.startActivity(intent);
        }
    }


}
