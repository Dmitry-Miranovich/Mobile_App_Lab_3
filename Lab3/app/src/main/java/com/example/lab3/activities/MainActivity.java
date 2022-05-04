package com.example.lab3.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lab3.HomeFragment;
import com.example.lab3.PlayerFragment;
import com.example.lab3.ProfileFragment;
import com.example.lab3.R;
import com.example.lab3.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:{
                    replaceFragment(new HomeFragment());
                    break;
                }
                case R.id.profile:{
                    replaceFragment(new ProfileFragment());
                    break;
                }
                case R.id.player:{
                    replaceFragment(new PlayerFragment());
                    break;
                }
            }

            return true;
        }
        );
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment){
       FragmentManager manager = getSupportFragmentManager();
       FragmentTransaction transaction = manager.beginTransaction();
       transaction.replace(R.id.frame_layout, fragment);
       transaction.commit();
   }
}