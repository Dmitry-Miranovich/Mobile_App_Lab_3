package com.example.lab3.pages;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab3.HomeFragment;
import com.example.lab3.R;

import java.util.Objects;

public class ZodiacFragment extends Fragment {
    private int pageNumber;

    public static ZodiacFragment newInstance(int page) {
        ZodiacFragment fragment = new ZodiacFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public ZodiacFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.zodiac_fragment, container, false);
        TextView name1 = result.findViewById(R.id.zodiac_name_one);
        TextView name2 = result.findViewById(R.id.zodiac_name_two);
        TextView name3 = result.findViewById(R.id.zodiac_name_three);
        ImageButton button1 = result.findViewById(R.id.zodiac_image_one);
        ImageButton button2 = result.findViewById(R.id.zodiac_image_two);
        ImageButton button3 = result.findViewById(R.id.zodiac_image_three);
        switch(pageNumber){
            case 0:{
                name1.setText("Овен");
                name2.setText("Телец");
                name3.setText("Близнецы");
                break;
            }
            case 1:{
                name1.setText("Рак");
                name2.setText("Лев");
                name3.setText("Дева");
                break;
            }
            case 2:{
                name1.setText("Весы");
                name2.setText("Скорпион");
                name3.setText("Стрелец");
                break;
            }
            case 3:{
                name1.setText("Козерог");
                name2.setText("Водолей");
                name3.setText("Рыбы");
                break;
            }
        }
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
