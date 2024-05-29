package com.example.myapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateRideFragment extends Fragment {

    private EditText editTextSource, editTextDestination, editTextDate, editTextTime, editTextSeats;
    private Button buttonCreateRide;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);

        editTextSource = view.findViewById(R.id.editTextSource);
        editTextDestination = view.findViewById(R.id.editTextDestination);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextTime = view.findViewById(R.id.editTextTime);
        editTextSeats = view.findViewById(R.id.editTextSeats);
        buttonCreateRide = view.findViewById(R.id.buttonCreateRide);

        buttonCreateRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRide();
            }
        });
        //clearRideDetailsFile(getContext());

        return view;
    }

    private void createRide() {
        String source = editTextSource.getText().toString().trim();
        String destination = editTextDestination.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String seatsText = editTextSeats.getText().toString().trim();

        if (source.isEmpty() || destination.isEmpty() || date.isEmpty() || time.isEmpty() || seatsText.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int seats;
        try {
            seats = Integer.parseInt(seatsText);
            if (seats <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Seats must be a positive integer", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

        Date parsedDate, parsedTime;
        try {
            parsedDate = dateFormat.parse(date);
            parsedTime = timeFormat.parse(time);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Invalid date or time format", Toast.LENGTH_SHORT).show();
            return;
        }

        String rideDetails = "Source: " + source + "\n" +
                "Destination: " + destination + "\n" +
                "Date: " + date + "\n" +
                "Time: " + time + "\n" +
                "Available Seats: " + seats + "\n";

       saveRideDetailsToFile(getContext(), rideDetails);

        editTextSource.setText("");
        editTextDestination.setText("");
        editTextDate.setText("");
        editTextTime.setText("");
        editTextSeats.setText("");

        Toast.makeText(getContext(), "Ride created successfully", Toast.LENGTH_SHORT).show();
    }

    private void saveRideDetailsToFile(Context context, String rideDetails) {
        File file = new File(context.getFilesDir(), "ride_details.txt");

        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write(rideDetails.getBytes());
            outputStream.write("\n".getBytes());
          //  Toast.makeText(context, "Ride details saved to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving ride details", Toast.LENGTH_LONG).show();
        }
    }

    private void clearRideDetailsFile(Context context) {
        File file = new File(context.getFilesDir(), "ride_details.txt");

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(new byte[0]);
            Toast.makeText(context, "Ride details file cleared", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error clearing ride details file", Toast.LENGTH_LONG).show();
        }
    }
}
