package edu.illinois.handsup;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    LinearLayout androidDropDownMenuIconItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidDropDownMenuIconItem = (LinearLayout) findViewById(R.id.horizontal_dropdown_icon_menu_items);
        DataStore.getInstance();
    }

    public void horizontalDropDownIconMenu(View view) {
        if (androidDropDownMenuIconItem.getVisibility() == View.VISIBLE) {
            androidDropDownMenuIconItem.setVisibility(View.INVISIBLE);
        } else {
            androidDropDownMenuIconItem.setVisibility(View.VISIBLE);
        }
    }

    public void menuItemClick(View view) {

        int id = view.getId();
        if (id == R.id.select_student) {
            Intent intent = new Intent(this, RandomSelectActivity.class);
            startActivity(intent);
        } else if (id == R.id.history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.create) {
            DataStore.getInstance().buildRandomGroup();
            Intent intent = new Intent(this, GroupActivity.class);
            startActivity(intent);
        }

    }

}