package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesListener, WeatherFragment.WeatherListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//part 1b
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .commit();
//end 1b
    }
    //part 1e.2 start
    @Override
    public void onCitySelected(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                //part 2a.1 for new instance
                .replace(R.id.rootView, WeatherFragment.newInstance(city) )
                .addToBackStack(null)
                .commit();
    }
    //end part 1e.2 and 2a.1

    //start of 3b.3.2
    @Override
    public void goToForecastFragment(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ForecastFragment.newInstance(city) )
                .addToBackStack(null)
                .commit();
    }
    //end of 3b.3.2
}