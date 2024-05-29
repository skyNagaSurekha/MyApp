package com.example.myapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RideHistoryFragment extends Fragment {
    private RecyclerView recyclerViewRides;
    private RideAdapter rideAdapter;
    private List<Ride> rideList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride_history, container, false);

        recyclerViewRides = view.findViewById(R.id.recyclerViewRides);
        recyclerViewRides.setLayoutManager(new LinearLayoutManager(getContext()));

        rideList = readRideDetailsFromFile(getContext());
        rideAdapter = new RideAdapter(rideList);
        recyclerViewRides.setAdapter(rideAdapter);

        return view;
    }

    private List<Ride> readRideDetailsFromFile(Context context) {
        List<Ride> rideList = new ArrayList<>();
        File file = new File(context.getFilesDir(), "ride_details.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String source = line.split(", ")[0].split(": ")[1];
                String destination = reader.readLine().split(", ")[0].split(": ")[1];
                String date = reader.readLine().split(", ")[0].split(": ")[1];
                String time = reader.readLine().split(", ")[0].split(": ")[1];
                int seats = Integer.parseInt(reader.readLine().split(", ")[0].split(": ")[1]);
                rideList.add(new Ride(source, destination, date, time, seats));
                reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error reading ride details", Toast.LENGTH_LONG).show();
        }

        return rideList;
    }
}


