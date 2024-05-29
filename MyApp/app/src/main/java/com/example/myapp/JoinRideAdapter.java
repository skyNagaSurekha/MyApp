package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JoinRideAdapter extends RecyclerView.Adapter<JoinRideAdapter.RideViewHolder> {

    private List<Ride> rideList;

    public JoinRideAdapter(List<Ride> rideList) {
        this.rideList = rideList;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_join_ride, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        Ride ride = rideList.get(position);
        holder.textViewSource.setText(ride.getSource());
        holder.textViewDestination.setText(ride.getDestination());
        holder.textViewDate.setText(ride.getDate());
        holder.textViewTime.setText(ride.getTime());
        holder.textViewSeats.setText(String.valueOf(ride.getSeats()));

        holder.buttonJoin.setOnClickListener(v -> {
            int currentSeats = ride.getSeats();
            if (currentSeats > 0) {
                Toast.makeText(holder.itemView.getContext(), "Ride is booked!", Toast.LENGTH_LONG).show();
                currentSeats--; // Decrement seat count by 1
                ride.setSeats(currentSeats); // Update the ride object
                holder.textViewSeats.setText(String.valueOf(currentSeats)); // Update the TextView

                if (currentSeats == 0) {
                    // Remove the ride from the list if seats become 0
                    rideList.remove(ride);
                    notifyDataSetChanged(); // Notify adapter of the removal
                }
            } else {
                Toast.makeText(holder.itemView.getContext(), "No available seats!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public static class RideViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSource, textViewDestination, textViewDate, textViewTime, textViewSeats;
        Button buttonJoin;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSource = itemView.findViewById(R.id.textViewSource);
            textViewDestination = itemView.findViewById(R.id.textViewDestination);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewSeats = itemView.findViewById(R.id.textViewSeats);
            buttonJoin = itemView.findViewById(R.id.buttonJoin);
        }
    }
}
