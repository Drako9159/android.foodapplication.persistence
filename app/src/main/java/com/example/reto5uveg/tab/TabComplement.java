package com.example.reto5uveg.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reto5uveg.R;
import com.example.reto5uveg.adapter.RecyclerViewFoodAdapter;
import com.example.reto5uveg.persistence.GetDataList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabComplement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabComplement extends Fragment {
    private String restaurantName;
    private int restaurantId;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabComplement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabComplement.
     */
    // TODO: Rename and change types and number of parameters
    public static TabComplement newInstance(String param1, String param2) {
        TabComplement fragment = new TabComplement();
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
        View view = inflater.inflate(R.layout.fragment_tab_complement, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewComplement);
        restaurantId = getArguments().getInt("restaurant_id", 0);
        restaurantName = getArguments().getString("restaurant_name");
        generateListComplements();
        return view;
    }

    public void generateListComplements() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerViewFoodAdapter adapterComplement = new RecyclerViewFoodAdapter(getContext(), GetDataList.getDataComplement(getContext(), restaurantId), restaurantName);
        recyclerView.setAdapter(adapterComplement);
        adapterComplement.setOnLongItemCustomListener((v, position) -> {
            v.showContextMenu();
        });
        registerForContextMenu(recyclerView);
    }
}