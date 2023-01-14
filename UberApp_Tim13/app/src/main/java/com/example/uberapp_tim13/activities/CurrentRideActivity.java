package com.example.uberapp_tim13.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uberapp_tim13.R;
import com.example.uberapp_tim13.dialogs.DriverDetailsDialog;
import com.example.uberapp_tim13.dialogs.PanicReasonDialog;
import com.example.uberapp_tim13.dialogs.PassengerDetailsDialog;
import com.example.uberapp_tim13.dtos.PanicRideDTO;
import com.example.uberapp_tim13.dtos.RideReturnedDTO;
import com.example.uberapp_tim13.dtos.UserInRideDTO;
import com.example.uberapp_tim13.fragments.MapFragment;
import com.example.uberapp_tim13.model.User;
import com.example.uberapp_tim13.rest.RestUtils;
import com.example.uberapp_tim13.tools.Globals;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRideActivity extends AppCompatActivity {

    public static RideReturnedDTO ride;
    private Activity context;

    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ride = (RideReturnedDTO) getIntent().getExtras().get("ride");

        getSupportFragmentManager().beginTransaction().replace(R.id.map_container_current, new MapFragment(ride)).commit();

        context=this;


        setTitle("Current ride");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_current_ride);

        timer = findViewById(R.id.timePassedTV);

        fitActivityToRole();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (Globals.userRole.equals("driver")) {
                    startActivity(new Intent(this, DriverMainActivity.class));
                    return true;
                } else {
                    startActivity(new Intent(this, PassengerMainActivity.class));
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fitActivityToRole() {
        LinearLayout startFinishBtns = findViewById(R.id.start_finish_buttons);
        Button inconsistentBtn = findViewById(R.id.inconsistentBtn);
        View driverDetails = findViewById(R.id.driverDetailsCardCV);
        View passDetails = findViewById(R.id.passDetailsCardLL);
        switch (Globals.userRole) {
            case "driver":
                driverDetails.setVisibility(View.GONE);
                inconsistentBtn.setVisibility(View.GONE);

                startFinishBtns.setVisibility(View.VISIBLE);
                addListenersToStartFinishBtns();

                passDetails.setVisibility(View.VISIBLE);
                passDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new PassengerDetailsDialog(context, ride).show();
                    }
                });
                break;
            case "passenger":
                passDetails.setVisibility(View.GONE);
                startFinishBtns.setVisibility(View.GONE);

                driverDetails.setVisibility(View.VISIBLE);
                driverDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DriverDetailsDialog(context, ride).show();
                    }
                });
                inconsistentBtn.setVisibility(View.VISIBLE);
                break;
        }

        findViewById(R.id.panicBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PanicReasonDialog(context, ride).show();
            }
        });
    }

    private void addListenersToStartFinishBtns() {
        Button start = findViewById(R.id.startBtn);
        Button finish = findViewById(R.id.finishBtn);

        start.setEnabled(true);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(false);
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                finish.setEnabled(true);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.stop();
            }
        });
        finish.setEnabled(false);
    }
}
