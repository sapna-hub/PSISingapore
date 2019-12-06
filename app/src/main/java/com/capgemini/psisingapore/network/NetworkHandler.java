package com.capgemini.psisingapore.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.capgemini.psisingapore.util.AppConstants;
import com.capgemini.psisingapore.model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.capgemini.psisingapore.util.AppConstants.REGION_EAST;
import static com.capgemini.psisingapore.util.AppConstants.REGION_NATIONAL;
import static com.capgemini.psisingapore.util.AppConstants.REGION_NORTH;
import static com.capgemini.psisingapore.util.AppConstants.REGION_SOUTH;
import static com.capgemini.psisingapore.util.AppConstants.REGION_WEST;

/**
 * This class has the all the network related methods as well as parsing of the response data.
 */
public class NetworkHandler {
    private static Context context;
    private static final String TAG = "NetworkHandler";
    private static NetworkHandler instance = null;
    public RequestQueue requestQueue;

    private Location east;
    private Location west;
    private Location south;
    private Location north;

    private List<Location> locations = new ArrayList<>();

    public NetworkHandler(Context con) {
        context = con;
        try {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized NetworkHandler getInstance(Context context) {
        if (null == instance)
            instance = new NetworkHandler(context);
        return instance;
    }

    public void getPSI(final ResponseListener responseListener) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, AppConstants.FETCH_PSI_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJson(response);
                        if (responseListener != null) {
                            responseListener.getResult(locations);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (responseListener != null) {
                    responseListener.onErrorResponse(error.getMessage());
                }
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        requestQueue.add(req);
    }

    /**
     * Parse region_metadata json object from response
     * @param response Json response from the server response
     */
    private void parseJson(JSONObject response) {
        Log.d(TAG, "parseJson");
        try {
            JSONArray jsonArray = response.getJSONArray("region_metadata");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String keyName = jsonObject.getString("name");
                Log.d(TAG, "parseJson keyName : "+keyName);
                switch (keyName) {
                    case REGION_EAST:
                        east = new Location(REGION_EAST,
                                parseLatitude(jsonObject), parseLongitude(jsonObject));
                        locations = parsePsi(response, east);
                        break;

                    case REGION_WEST:
                        west = new Location(REGION_WEST,
                                parseLatitude(jsonObject), parseLongitude(jsonObject));
                        locations = parsePsi(response, west);
                        break;

                    case REGION_NORTH:
                        north = new Location(REGION_NORTH,
                                parseLatitude(jsonObject), parseLongitude(jsonObject));
                        locations = parsePsi(response, north);
                        break;

                    case REGION_SOUTH:
                        south = new Location(REGION_SOUTH,
                                parseLatitude(jsonObject), parseLongitude(jsonObject));
                        locations = parsePsi(response, south);
                        break;

                    case REGION_NATIONAL:
                        locations.add(new Location(REGION_NATIONAL,
                                parseLatitude(jsonObject), parseLongitude(jsonObject)));
                        break;

                    default:
                        //do nothing
                        break;
                }
            }

        } catch (JSONException e) {
            Log.d(TAG, "parseJson exception "+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Parse Json object to fetch PSI values for all the regions
     * @param response Json response from the webservice
     * @param region Location object
     * @return List of locations with the updated PSI values
     */
    private List<Location> parsePsi(JSONObject response, Location region) {
        Log.d(TAG, "parsePsi location : "+region);
        double latitude = 0.0;
        try {
            JSONArray jsonArray = response.getJSONArray("items");
            String status = response.getJSONObject("api_info").getString("status");
            JSONObject readings = jsonArray.getJSONObject(0).getJSONObject("readings");
            switch (region.getName()) {
                case REGION_EAST:
                    int e_no2_one_hour_max = readings.getJSONObject("no2_one_hour_max").getInt("east");
                    int e_o3_eight_hour_max = readings.getJSONObject("o3_eight_hour_max").getInt("east");
                    int e_psi_twenty_four_hourly = readings.getJSONObject("psi_twenty_four_hourly").getInt("east");
                    int e_so2_twenty_four_hourly = readings.getJSONObject("so2_twenty_four_hourly").getInt("east");

                    east.setNo2OnehourMax("no2_one_hour_max", e_no2_one_hour_max);
                    east.setO3EightHourMax("o3_eight_hour_max", e_o3_eight_hour_max);
                    east.setPsiTwentyFourHourly("psi_twenty_four_hourly", e_psi_twenty_four_hourly);
                    east.setSo2TwentyFourHourly("so2_twenty_four_hourly", e_so2_twenty_four_hourly);
                    east.setAppInfo(status);
                    locations.add(east);

                    break;
                case REGION_WEST:
                    int w_no2_one_hour_max = readings.getJSONObject("no2_one_hour_max").getInt("west");
                    int w_o3_eight_hour_max = readings.getJSONObject("o3_eight_hour_max").getInt("west");
                    int w_psi_twenty_four_hourly = readings.getJSONObject("psi_twenty_four_hourly").getInt("west");
                    int w_so2_twenty_four_hourly = readings.getJSONObject("so2_twenty_four_hourly").getInt("west");

                    west.setNo2OnehourMax("no2_one_hour_max", w_no2_one_hour_max);
                    west.setO3EightHourMax("o3_eight_hour_max", w_o3_eight_hour_max);
                    west.setPsiTwentyFourHourly("psi_twenty_four_hourly", w_psi_twenty_four_hourly);
                    west.setSo2TwentyFourHourly("so2_twenty_four_hourly", w_so2_twenty_four_hourly);
                    west.setAppInfo(status);
                    locations.add(west);
                    break;
                case REGION_NORTH:
                    int n_no2_one_hour_max = readings.getJSONObject("no2_one_hour_max").getInt("north");
                    int n_o3_eight_hour_max = readings.getJSONObject("o3_eight_hour_max").getInt("north");
                    int n_psi_twenty_four_hourly = readings.getJSONObject("psi_twenty_four_hourly").getInt("north");
                    int n_so2_twenty_four_hourly = readings.getJSONObject("so2_twenty_four_hourly").getInt("north");

                    north.setNo2OnehourMax("no2_one_hour_max", n_no2_one_hour_max);
                    north.setO3EightHourMax("o3_eight_hour_max", n_o3_eight_hour_max);
                    north.setPsiTwentyFourHourly("psi_twenty_four_hourly", n_psi_twenty_four_hourly);
                    north.setSo2TwentyFourHourly("so2_twenty_four_hourly", n_so2_twenty_four_hourly);
                    north.setAppInfo(status);
                    locations.add(north);
                    break;
                case REGION_SOUTH:

                    int s_no2_one_hour_max = readings.getJSONObject("no2_one_hour_max").getInt("south");
                    int s_o3_eight_hour_max = readings.getJSONObject("o3_eight_hour_max").getInt("south");
                    int s_psi_twenty_four_hourly = readings.getJSONObject("psi_twenty_four_hourly").getInt("south");
                    int s_so2_twenty_four_hourly = readings.getJSONObject("so2_twenty_four_hourly").getInt("south");

                    south.setNo2OnehourMax("no2_one_hour_max", s_no2_one_hour_max);
                    south.setO3EightHourMax("o3_eight_hour_max", s_o3_eight_hour_max);
                    south.setPsiTwentyFourHourly("psi_twenty_four_hourly", s_psi_twenty_four_hourly);
                    south.setSo2TwentyFourHourly("so2_twenty_four_hourly", s_so2_twenty_four_hourly);
                    south.setAppInfo(status);
                    locations.add(south);
                    break;
                default:
                    //do nothing
                    break;
            }
        } catch (JSONException e) {
            Log.d(TAG, "parsePsi exception : "+e.getMessage());
            e.printStackTrace();
        }
        return locations;
    }

    /**
     * Parse Latitude from the json response
     * @param jsonObject Json response from the webservice
     * @return latitude
     */
    private double parseLatitude(JSONObject jsonObject) {
        double latitude = 0.0;
        try {
            latitude = jsonObject.getJSONObject("label_location").getDouble("latitude");

        } catch (JSONException e) {
            Log.d(TAG, "parseLatitude exception : "+e.getMessage());
            e.printStackTrace();
        }
        return latitude;
    }

    /**
     * Parse Longitude from the json response
     * @param jsonObject Json response from the webservice
     * @return logitude
     */
    private double parseLongitude(JSONObject jsonObject) {
        double longitude = 0.0;
        try {
            longitude = jsonObject.getJSONObject("label_location").getDouble("longitude");

        } catch (JSONException e) {
            Log.d(TAG, "parseLongitude exception : "+e.getMessage());
            e.printStackTrace();
        }
        return longitude;
    }

}
