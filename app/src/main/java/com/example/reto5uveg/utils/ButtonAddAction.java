package com.example.reto5uveg.utils;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.reto5uveg.R;

public class ButtonAddAction {
    public ButtonAddAction(View parentView, Intent intent) {
        ImageView ivAdd = parentView.findViewById(R.id.ivAdd);
        if (ivAdd != null) {
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vInside) {
                    vInside.getContext().startActivity(intent);
                }
            });
        }
    }
}
