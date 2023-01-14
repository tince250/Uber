package com.example.uberapp_tim13.adapters.ride_history;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uberapp_tim13.R;
import com.example.uberapp_tim13.dtos.RideReturnedDTO;
import com.example.uberapp_tim13.model.Ride;
import com.example.uberapp_tim13.tools.Globals;
import com.example.uberapp_tim13.tools.Mockap;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Activity activity;
    List<RideReturnedDTO> items;

    public HistoryAdapter(List<RideReturnedDTO> rides, Activity activity) {
        this.activity = activity;
        this.items = rides;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RideReturnedDTO ride = items.get(i);
        View view_new = view;

        if (view == null) {
            view_new = activity.getLayoutInflater().inflate(R.layout.ride_history_item, null);
        }
        String departureAddress = ride.getLocations().get(0).getDeparture().getAddress();
        String destinationAddress = ride.getLocations().get(0).getDestination().getAddress();
        ((TextView)view_new.findViewById(R.id.routeTV)).setText(departureAddress+ " -> " + destinationAddress);
//        String stops = "";
//        for (String s : ride.getStops()){
//            stops = stops + s +", ";
//        }
//        ((TextView)view_new.findViewById(R.id.stopsTV)).setText("Stops: "+ stops);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String formattedDateTime = ride.getStartTime().format(formatter);

        String time = ride.getStartTime().split("T")[1].split(":")[0] + ":" +ride.getStartTime().split("T")[1].split(":")[1];
        String day = ride.getStartTime().split("T")[0].split("-")[2];
        String month = ride.getStartTime().split("T")[0].split("-")[1];
        String year = ride.getStartTime().split("T")[0].split("-")[0];
        ((TextView)view_new.findViewById(R.id.dateTV)).setText("Date: " + day + "." + month + "." + year + ". " + time);
        ((TextView)view_new.findViewById(R.id.passengersTV)).setText("Passengers: " + ride.getPassengers().size());
        ((TextView)view_new.findViewById(R.id.priceTV)).setText("Price(RSD): " + ride.getTotalCost());

        fitFragmentToRole(view_new, ride);

        return view_new;
    }

    private void fitFragmentToRole(View view_new, RideReturnedDTO ride) {
        switch (Globals.userRole) {
            case "driver":
                view_new.findViewById(R.id.addToFavImg).setVisibility(View.GONE);
                view_new.findViewById(R.id.driverTV).setVisibility(View.GONE);
                break;
            case "passenger":
                ((TextView)view_new.findViewById(R.id.driverTV)).setText("Driver: " + ride.getDriver().getEmail());
                break;
        }
    }
}
