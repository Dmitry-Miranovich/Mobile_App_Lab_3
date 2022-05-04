package com.example.lab3.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab3.R;

public class PageFragment extends Fragment {
    private int pageNumber;

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.developer_page_fragment, container, false);
        TextView pageDescription = result.findViewById(R.id.developer_info_text);
        TextView pageLabel = result.findViewById(R.id.developer_info_label);
        String description = "";
        String header = "";
        switch(pageNumber){
            case 0:{
                header = "О разработчике";
                description = "Студент 951006, \nМиранович Дмитрий Владимирович, \nБГУИР ";
                break;
            }
            case 1: {
                header = "О приложении";
                description = "Советы для настроения";
                break;
            }
        }
        pageLabel.setText(header);
        pageDescription.setText(description);
        return result;
    }
}
