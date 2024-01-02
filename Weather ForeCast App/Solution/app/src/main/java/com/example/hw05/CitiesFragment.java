package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.hw05.databinding.FragmentCitiesBinding;


public class CitiesFragment extends Fragment {
    public CitiesFragment() {
        // Required empty public constructor
    }
//part 1c
    FragmentCitiesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayAdapter<DataService.City> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Cities");
        // here there's part 1d.1 go to dataservices and ceck if to string is present which should match the rubric
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, DataService.cities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //part 1d.2 int position
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mListener.onCitySelected(adapter.getItem(position));
            }
        });
    }
//end of 1c

    //part 1e.1
    CitiesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CitiesListener) {
            mListener = (CitiesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CitiesListener");
        }
    }


    interface CitiesListener {
        void onCitySelected(DataService.City city);
    }

//end of part 1e.1
}