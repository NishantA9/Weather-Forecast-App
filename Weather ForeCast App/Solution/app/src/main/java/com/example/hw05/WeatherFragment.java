package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw05.databinding.FragmentCitiesBinding;
import com.example.hw05.databinding.FragmentWeatherBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherFragment extends Fragment {

    //part 2a.1
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;

    public WeatherFragment() {
        // Required empty public constructor
    }

     public static WeatherFragment newInstance(DataService.City city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    FragmentWeatherBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
        binding.textViewCityName.setText(mCity.toString());
        getWeather();

        //part 3b.2
        binding.buttonCheckForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToForecastFragment(mCity);
            }
        });
    }
//end of part 3b.2

    //end part 2a.1

    //part 2b.1
    private final OkHttpClient client = new OkHttpClient();
     //middle part of 2b.3
    Weather mWeather;
    //middle part of 2b.3 end
    void getWeather(){
        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addQueryParameter("appid", "PUT_YOUR_KEY_HERE")
                .addQueryParameter("units", "imperial")
                .addQueryParameter("lon", String.valueOf(mCity.getLon()))
                .addQueryParameter("lat", String.valueOf(mCity.getLat()))
                .build();
//end of 2b.1
        //part 2b.2
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    //write this line "JSONObject jsonObject = new JSONObject(body);" and then it will go in try catch
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        //end of 2b.2

                        //start of 2b.3
                        mWeather = new Weather(jsonObject);
                        //final 2b.3
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewTemp.setText(mWeather.getTemp() + " F");
                                binding.textViewTempMax.setText(mWeather.getTemp_max() + " F");
                                binding.textViewTempMin.setText(mWeather.getTemp_min() + " F");
                                binding.textViewDesc.setText(mWeather.getDescription());
                                binding.textViewWindSpeed.setText(mWeather.getSpeed() + " miles/hr");
                                binding.textViewWindDegree.setText(mWeather.getDeg() + " degrees");
                                binding.textViewHumidity.setText(mWeather.getHumidity() +" %");
                                binding.textViewCloudiness.setText(mWeather.getCloudiness() + " %");
                                //new library to show the image, import the library
                                Picasso.get().load(mWeather.getIconUrl()).into(binding.imageViewWeatherIcon);

                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                }
            }
        });
    }
    //end of final 2b.3

    //start of 3b.3.1
    WeatherListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof WeatherListener){
            mListener = (WeatherListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement WeatherListener");
        }
    }

    interface WeatherListener{
        void goToForecastFragment(DataService.City city);
    }

//end of 3b.3.2
}