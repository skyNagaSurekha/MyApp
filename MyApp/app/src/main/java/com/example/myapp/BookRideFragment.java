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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookRideFragment extends Fragment {
        private EditText editTextSearchSource;
        private EditText editTextSearchDestination;
        private EditText editTextSearchDate;
        private EditText editTextSearchTime;
        private Button buttonSearch;
        private RecyclerView recyclerViewSearchResults;
        private JoinRideAdapter joinRideAdapter;
        private List<Ride> allRidesList;
        private List<Ride> searchResultsList;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_book_ride, container, false);

            editTextSearchSource = view.findViewById(R.id.editTextSearchSource);
            editTextSearchDestination = view.findViewById(R.id.editTextSearchDestination);
            editTextSearchDate = view.findViewById(R.id.editTextSearchDate);
            editTextSearchTime = view.findViewById(R.id.editTextSearchTime);
            buttonSearch = view.findViewById(R.id.buttonSearch);
            recyclerViewSearchResults = view.findViewById(R.id.recyclerViewSearchResults);
            recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));

            allRidesList = readRideDetailsFromFile(getContext());
            searchResultsList = new ArrayList<>(allRidesList);
            joinRideAdapter = new JoinRideAdapter(searchResultsList);
            recyclerViewSearchResults.setAdapter(joinRideAdapter);

            buttonSearch.setOnClickListener(v -> performSearch());

            return view;
        }

        private List<Ride> readRideDetailsFromFile(Context context) {
            List<Ride> rideList = new ArrayList<>();
            File file = new File(context.getFilesDir(), "ride_details.txt");

            if (!file.exists()) {
                return rideList;
            }

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

        private void performSearch() {
            String searchSource = editTextSearchSource.getText().toString().trim();
            String searchDestination = editTextSearchDestination.getText().toString().trim();
            String searchDate = editTextSearchDate.getText().toString().trim();
            String searchTime = editTextSearchTime.getText().toString().trim();

            searchResultsList.clear();

            for (Ride ride : allRidesList) {
                if ((searchSource.isEmpty() || ride.getSource().equalsIgnoreCase(searchSource)) &&
                        (searchDestination.isEmpty() || ride.getDestination().equalsIgnoreCase(searchDestination)) &&
                        (searchDate.isEmpty() || ride.getDate().equalsIgnoreCase(searchDate)) &&
                        (searchTime.isEmpty() || ride.getTime().equalsIgnoreCase(searchTime))) {
                    searchResultsList.add(ride);
                }
            }

            joinRideAdapter.notifyDataSetChanged();
        }
    }
