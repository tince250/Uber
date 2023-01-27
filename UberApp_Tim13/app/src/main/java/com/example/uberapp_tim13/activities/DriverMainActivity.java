package com.example.uberapp_tim13.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.uberapp_tim13.R;

import com.example.uberapp_tim13.dtos.RideInInviteDTO;
import com.example.uberapp_tim13.dtos.RideInviteDTO;
import com.example.uberapp_tim13.dtos.RideReturnedDTO;

import com.example.uberapp_tim13.fragments.AccountFragment;
import com.example.uberapp_tim13.fragments.RideHistoryFragment;
import com.example.uberapp_tim13.fragments.DriverHomeFragment;
import com.example.uberapp_tim13.fragments.InboxFragment;
import com.example.uberapp_tim13.services.RideService;
import com.example.uberapp_tim13.tools.FragmentTransition;
import com.example.uberapp_tim13.tools.Globals;
import com.example.uberapp_tim13.tools.StompManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DriverMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        setTitle(R.string.home_nav);

        connectToRideOffersSocket();

        FragmentTransition.to(DriverHomeFragment.newInstance(), this, true, R.id.driverFL);


        setBottomNavigationBar();
    }

    private void connectToRideOffersSocket() {
        StompManager manager = new StompManager();
        manager.connect();
        Log.d("OFFER", "/topic/driver/ride-offers/" + Globals.userId);
        StompManager.stompClient.topic("/topic/driver/ride-offers/" + Globals.userId).subscribe(topicMessage -> {
            RideReturnedDTO ride = Globals.gson.fromJson(topicMessage.getPayload(), RideReturnedDTO.class);
            RideService.returnedRide = ride;

            Log.d("OFFER", "" + ride);
            Intent i = new Intent(DriverMainActivity.this, AcceptanceRideActivity.class);
            i.putExtra("invite", new RideInviteDTO(null, new RideInInviteDTO(ride)));
            i.putExtra("type", "driver-offer");
            startActivity(i);
        });
    }

    public void onClickCurrentRide(View v){
        //TODO: IMPLEMENT
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.driver_upper_menu, menu);

        MenuItem switchItem = menu.findItem(R.id.activeToggle);
        switchItem.setActionView(R.layout.toggle_button_bar);
        final Switch switchBtn = (Switch) menu.findItem(R.id.activeToggle).getActionView().findViewById(R.id.activeSwitch);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "checked", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "not checked", Toast.LENGTH_LONG).show();
                }
            }
        });


        return true;
    }

    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            //redirection to other activities/fragments
            switch (item.getItemId()) {
                case R.id.nav_inbox:
                    setTitle("Inbox");
                    FragmentTransition.to(InboxFragment.newInstance(), this, true, R.id.driverFL);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.nav_home:
                    setTitle("Home");
                    FragmentTransition.to(DriverHomeFragment.newInstance(), this, true, R.id.driverFL);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.nav_profile:
                    setTitle("Account");
                    FragmentTransition.to(AccountFragment.newInstance(), this, true, R.id.driverFL);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.nav_history:
                    setTitle("History");
                    FragmentTransition.to(RideHistoryFragment.newInstance(), this, true, R.id.driverFL);
                    overridePendingTransition(0, 0);
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}