package com.capgemini.psisingapore.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.capgemini.psisingapore.R;
import com.capgemini.psisingapore.model.Location;
import com.capgemini.psisingapore.util.AppConstants;
import com.capgemini.psisingapore.viewmodel.MainViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Activity to load Google Maps and load the corresponding data.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private List<Location> locations = new ArrayList<>();
    private Location east;
    private Location west;
    private Location north;
    private Location south;
    private Context context;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = this;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        fetchAllLocations();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This method will observer the change in the data from API call
     */
    public void fetchAllLocations() {
        mainViewModel.getAllLocation().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                if(null != MapActivity.this.locations) {
                    MapActivity.this.locations.clear();
                }
                createMarker(locations);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "Marker " + marker.getTag());
        switch (marker.getTag().toString()) {
            case AppConstants.REGION_EAST:
                showDialog(east);
                break;

            case AppConstants.REGION_WEST:
                showDialog(west);
                break;

            case AppConstants.REGION_SOUTH:
                showDialog(south);
                break;

            case AppConstants.REGION_NORTH:
                showDialog(north);
                break;

            default:
                // do nothing
                break;
        }
        return false;
    }

    /**
     * Method to create marker based on the Location latitude and longitude.
     * @param locations
     */
    private void createMarker(List<Location> locations){
        MapActivity.this.locations = locations;
        LatLng eastSingapore;
        LatLng westSingapore;
        LatLng northSingapore;
        LatLng southSingapore;
        LatLng nationalSingapore;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Location location : locations) {

            switch (location.getName().toLowerCase()) {
                case AppConstants.REGION_EAST:
                    east = location;
                    eastSingapore = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(eastSingapore).title(AppConstants
                            .REGION_EAST.toUpperCase())).setTag(AppConstants.REGION_EAST);
                    builder.include(eastSingapore);
                    break;

                case AppConstants.REGION_WEST:
                    west = location;
                    westSingapore = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(westSingapore).title(AppConstants
                            .REGION_WEST.toUpperCase())).setTag(AppConstants.REGION_WEST);
                    builder.include(westSingapore);
                    break;

                case AppConstants.REGION_NORTH:
                    north = location;
                    northSingapore = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(northSingapore).title(AppConstants
                            .REGION_NORTH.toUpperCase())).setTag(AppConstants.REGION_NORTH);
                    builder.include(northSingapore);
                    break;

                case AppConstants.REGION_SOUTH:
                    south = location;
                    southSingapore = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(southSingapore).title(AppConstants
                            .REGION_SOUTH.toUpperCase())).setTag(AppConstants.REGION_SOUTH);
                    builder.include(southSingapore);
                    break;

                case AppConstants.REGION_NATIONAL:
                    nationalSingapore = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(nationalSingapore));
                    break;

                default:
                    //do nothing
                    break;
            }
        }
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10);
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        createMarker(locations);
    }

    /**
     * A Dialog appears on click of the marker to show the list of PSI data
     *
     * @param location
     */
    private void showDialog(Location location) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View psiDialogView = inflater.inflate(R.layout.dialog, null);
        final AlertDialog psiDialog = new AlertDialog.Builder(context, R.style.CustomAlertDialog).create();
        psiDialog.setView(psiDialogView);

        TextView text = psiDialogView.findViewById(R.id.title);
        text.setText(getResources().getString(R.string.title_psi) + location.getAppInfo() + "!");
        RecyclerView psiListview = psiDialogView.findViewById(R.id.psilistview);

        ArrayList<Map<String, Integer>> names = new ArrayList<>();

        names.add(location.getNo2OneHourMax());
        names.add(location.getO3EightHourMax());
        names.add(location.getPsiTwentyFourHourly());
        names.add(location.getSo2TwentyFourHourly());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        psiListview.setLayoutManager(layoutManager);

        PSIViewAdapter adapter = new PSIViewAdapter(inflater, names);
        psiListview.setAdapter(adapter);

        Button dialogButton = psiDialogView.findViewById(R.id.buttonOk);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psiDialog.dismiss();
            }
        });

        psiDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
