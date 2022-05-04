package com.example.lab3.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CustomRequestQueue {
    private Context context;
    private String url = "https://catalog.onliner.by/mobile";
    private String message;
    private Boolean isResponseExist = false;
    private Boolean isError = false;
    private RequestQueue queue;
    private final String TAG = "My Tag";

    public CustomRequestQueue(Context context){
        this.context = context;
    }
    public void start(){
        if(queue == null){
            queue = Volley.newRequestQueue(context);
            System.out.println("Server created");
        }
    }
    private StringRequest createRequest(String url){
        return new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                message = response;
                isResponseExist = true;
                isError = false;
                System.out.println("Request with url:" + url + " in Thread " + Thread.currentThread().getName());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                message = "error";
                System.out.println("Request error:\n" + error.getMessage() + "\n in Thread " + Thread.currentThread().getName());
                isError = true;
                isResponseExist = true;
            }
        });
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public void closeRequest(){
        if(queue!=null){
            queue.cancelAll(TAG);
        }
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void addQueue(){
        if(queue != null){
            isResponseExist = false;
            StringRequest request = createRequest(url);
            request.setTag(TAG);
            setError(false);
            queue.add(request);
        }
    }

    public Boolean getResponseExist() {
        return isResponseExist;
    }

    public void setResponseExist(Boolean responseExist) {
        isResponseExist = responseExist;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

}