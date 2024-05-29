package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private List<Ride> rideList;

    public RideAdapter(List<Ride> rideList) {
        this.rideList = rideList;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ride, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RideViewHolder holder, int position) {
        Ride ride = rideList.get(position);
        holder.textViewSource.setText(ride.source);
        holder.textViewDestination.setText(ride.destination);
        holder.textViewDate.setText(ride.date);
        holder.textViewTime.setText(ride.time);
        holder.textViewSeats.setText(String.valueOf(ride.seats));
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public static class RideViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSource, textViewDestination, textViewDate, textViewTime, textViewSeats;

        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSource = itemView.findViewById(R.id.textViewSource);
            textViewDestination = itemView.findViewById(R.id.textViewDestination);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewSeats = itemView.findViewById(R.id.textViewSeats);
        }
    }
}

