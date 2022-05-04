package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.lab3.R;
import com.example.lab3.adapter.ViewPagerAdapter;

import java.util.Objects;

public class DeveloperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewPager2 pager = findViewById(R.id.developer_view_pager);
        FragmentStateAdapter adapter = new ViewPagerAdapter(this);
        pager.setAdapter(adapter);
    }
}