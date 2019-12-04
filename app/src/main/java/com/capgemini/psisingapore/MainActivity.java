package com.capgemini.psisingapore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.capgemini.psisingapore.data.Location;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity{

    private static final String TAG = "MainActivity";
    private List<Location> locations = new ArrayList<>();
    private MainViewModel mainViewModel;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.activity_welcome);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        fetchAllLocations();
        progress = new ProgressDialog(this);
        progress.setTitle(getResources().getString(R.string.dialog_loading));
        progress.setMessage(getResources().getString(R.string.dialog_wait));
        progress.setCancelable(false);
        progress.show();
    }

    /**
     * Method to fetch and observer the change in data from the server.
     */
    public void fetchAllLocations() {
        mainViewModel.getAllLocation().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                MainActivity.this.locations.clear();
                MainActivity.this.locations = locations;
                progress.dismiss();
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(AppConstants.LOCATION_KEY, (ArrayList) MainActivity.this.locations);
                startActivity(i);
                Log.d(TAG, "fetchAllLocations onChanged ");
            }
        });
    }
}
