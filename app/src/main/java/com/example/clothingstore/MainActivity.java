package com.example.clothingstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView dataList;
    List<String> titles;
    List<String> prices;
    List<Integer> images;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = findViewById(R.id.dataList);

        titles = new ArrayList<>();
        prices = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Pink Dress");
        titles.add("Red Dress");
        titles.add("Black Dress");
        titles.add("$100");
        titles.add("$200");
        titles.add("Second Item");
        titles.add("Third Item");
        titles.add("Fourth Item");

        prices.add("$198");
        prices.add("$198");
        prices.add("$198");
        prices.add("$198");
        prices.add("$198");
        prices.add("$198");

        images.add(R.drawable.pink);
        images.add(R.drawable.black);
        images.add(R.drawable.red);
        images.add(R.drawable.pink);
        images.add(R.drawable.black);
        images.add(R.drawable.red);

        adapter = new Adapter(this,titles,prices,images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

    }

    public void view(View view){
        Intent intent = new Intent(this, SideNavBar.class);

        startActivity(intent);
    }
}
