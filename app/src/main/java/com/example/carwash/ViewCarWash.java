package com.example.carwash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewCarWash extends AppCompatActivity {

    private String name;
    private String location;
    private int thumbnail;

    public ViewCarWash(){

    }

    public ViewCarWash(String name, String location){
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car_wash);
    }
}
