package com.example.lab3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.lab3.adapter.ViewPagerAdapter;
import com.example.lab3.adapter.ZodiacPagerAdapter;
import com.example.lab3.db.users.Profile;
import com.example.lab3.parser.Parser;
import com.example.lab3.volley.CustomRequestQueue;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static Profile cur_profile;
    public static ArrayList<String> descriptions = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CustomRequestQueue queue;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    String response_mes = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    String mes = "";
    String url = "";
    private Semaphore sem = new Semaphore(1, true);
    private ArrayList<String> links = new ArrayList<>();
    private ViewPager2 pager;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = view.findViewById(R.id.zodiac_view_pager);
        FragmentStateAdapter adapter = new ZodiacPagerAdapter(getActivity());
        pager.setAdapter(adapter);
        queue = new CustomRequestQueue(getContext());
        queue.start();
        switch (pager.getCurrentItem()){

        }
        TextView name = view.findViewById(R.id.main_name);
        String temp = name.getText().toString();
        name.setText(temp.concat(cur_profile.getName()));
        Thread listener = new Thread(() -> {
            try {
                sem.acquire();
                System.out.println("Start listening, sem acquired");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(true){
                if(queue != null && queue.getResponseExist()){
                    if(queue.getError()){
                        System.out.println("Error is queue, retry");
                        queue.closeRequest();
                        queue.setUrl(url);
                        queue.addQueue();
                        queue.setResponseExist(false);
                    }else{
                        mes = queue.getMessage();
                        queue.setError(false);
                        queue.closeRequest();
                        System.out.println("Queue catch successfully!");
                        queue.setResponseExist(false);
                        sem.release();
                    }
                }
            }
        });
        Thread messages_thread = new Thread(()->{
            if(queue!= null){
                for(String link : links){
                    queue.setUrl(link);
                    System.out.println("Messages_request is set");
                    queue.addQueue();
                    try {
                        sem.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Message request is acquired");
                    System.out.println(queue.getMessage());
                    Parser parser = new Parser(queue.getMessage());
                    String desc = parser.getZodiacDescription();
                    descriptions.add(desc);
                    System.out.println(desc);
                    queue.closeRequest();
                }
            }
        });
        Thread thread_links = new Thread(() -> {
            if(queue!= null){
                url = "https://horoscopes.rambler.ru/znaki-zodiaka/";
                queue.setUrl(url);
                queue.addQueue();
                System.out.println("Request is set");
                try {
                    sem.acquire();
                    System.out.println("Request acquired");
                    Parser parser = new Parser(queue.getMessage());
                    links = parser.getLinks();
                    messages_thread.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                throw new NullPointerException();
            }
        });
        ImageButton button1 = view.findViewById(R.id.zodiac_image_one);
        ImageButton button2 = view.findViewById(R.id.zodiac_image_two);
        ImageButton button3 = view.findViewById(R.id.zodiac_image_three);
        listener.start();
        thread_links.start();
        /*
        ImageButton button = view.findViewById(R.id.imageButton);
        ImageButton button2 = view.findViewById(R.id.imageButton2);
        ImageButton button3 = view.findViewById(R.id.imageButton3);

        button.setOnClickListener(view1 -> {
            String url = "https://horoscopes.rambler.ru/znaki-zodiaka/";
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    queue.setUrl(url);
                    queue.addQueue();
                }
            });
            thread.start();
        });
        button2.setOnClickListener(view12 -> {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(queue.getResponseExist()){
                        Parser parser = new Parser(queue.getMessage());
                        ArrayList<String> list = parser.getLinks();
                        queue.setUrl(list.get(0));
                        queue.addQueue();
                    }
                }
            });
            thread.start();
        });
        button3.setOnClickListener(view13 -> {
           if(queue.getResponseExist()){
               Parser parser = new Parser(queue.getMessage());
               String desc = parser.getZodiacDescription();
               System.out.println(desc);
           }
        });
        */
    }
}