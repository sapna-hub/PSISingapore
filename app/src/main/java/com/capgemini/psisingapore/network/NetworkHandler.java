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
import com.capgemini.psisingapore.R;
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
            Log.d(TAG, ""+e.getMessage());
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
        try {
            JSONArray jsonArray = response.getJSONArray("items");
            String status = response.getJSONObject("api_info").getString("status");
            JSONObject readings = jsonArray.getJSONObject(0).getJSONObject("readings");
            switch (region.getName()) {
                case REGION_EAST:
                    int eNo2OneHourMax = readings.getJSONObject("no2_one_hour_max").getInt(REGION_EAST);
                    int eO3EightHourMax = readings.getJSONObject("o3_eight_hour_max").getInt(REGION_EAST);
                    int ePsiTwentyFourHourly = readings.getJSONObject("psi_twenty_four_hourly").getInt(REGION_EAST);
                    int eSo2TwentyFourHourly = readings.getJSONObject("so2_twenty_four_hourly").getInt(REGION_EAST);

                    east.setNo2OnehourMax(context.getResources().getString(R.string.no2_one_hour_max), eNo2OneHourMax);
                    east.setO3EightHourMax(context.getResources().getString(R.string.o3_eight_hour_max), eO3EightHourMax);
                    east.setPsiTwentyFourHourly(context.getResources().getString(R.string.psi_twenty_four_hourly), ePsiTwentyFourHourly);
                    east.setSo2TwentyFourHourly(context.getResources().getString(R.string.so2_twenty_four_hourly), eSo2TwentyFourHourly);
                    east.setAppInfo(status);
                    locations.add(east);

                    break;

                case REGION_WEST:
                    int wNo2OneHourMax = readings.getJSONObject("no2_one_hour_max").getInt(REGION_WEST);
                    int wO3EightHourMax = readings.getJSONObject("o3_eight_hour_max").getInt(REGION_WEST);
                    int wPsiTwentyFourHourly = readings.getJSONObject("psi_twenty_four_hourly").getInt(REGION_WEST);
                    int wSo2TwentyFourHourly = readings.getJSONObject("so2_twenty_four_hourly").getInt(REGION_WEST);

                    west.setNo2OnehourMax(context.getResources().getString(R.string.no2_one_hour_max), wNo2OneHourMax);
                    west.setO3EightHourMax(context.getResources().getString(R.string.o3_eight_hour_max), wO3EightHourMax);
                    west.setPsiTwentyFourHourly(context.getResources().getString(R.string.psi_twenty_four_hourly), wPsiTwentyFourHourly);
                    west.setSo2TwentyFourHourly(context.getResources().getString(R.string.so2_twenty_four_hourly), wSo2TwentyFourHourly);
                    west.setAppInfo(status);
                    locations.add(west);

                    break;

                case REGION_NORTH:
                    int nNo2OneHourMax = readings.getJSONObject("no2_one_hour_max").getInt(REGION_NORTH);
                    int nO3EightHourMax = readings.getJSONObject("o3_eight_hour_max").getInt(REGION_NORTH);
                    int nPsiTwentyFourHourly = readings.getJSONObject("psi_twenty_four_hourly").getInt(REGION_NORTH);
                    int nSo2TwentyFourHourly = readings.getJSONObject("so2_twenty_four_hourly").getInt(REGION_NORTH);

                    north.setNo2OnehourMax(context.getResources().getString(R.string.no2_one_hour_max), nNo2OneHourMax);
                    north.setO3EightHourMax(context.getResources().getString(R.string.o3_eight_hour_max), nO3EightHourMax);
                    north.setPsiTwentyFourHourly(context.getResources().getString(R.string.psi_twenty_four_hourly), nPsiTwentyFourHourly);
                    north.setSo2TwentyFourHourly(context.getResources().getString(R.string.so2_twenty_four_hourly), nSo2TwentyFourHourly);
                    north.setAppInfo(status);
                    locations.add(north);

                    break;

                case REGION_SOUTH:

                    int sNo2OneHourMax = readings.getJSONObject("no2_one_hour_max").getInt(REGION_SOUTH);
                    int sO3EightHourMax = readings.getJSONObject("o3_eight_hour_max").getInt(REGION_SOUTH);
                    int sPsiTwentyFourHourly = readings.getJSONObject("psi_twenty_four_hourly").getInt(REGION_SOUTH);
                    int sSo2TwentyFourHourly = readings.getJSONObject("so2_twenty_four_hourly").getInt(REGION_SOUTH);

                    south.setNo2OnehourMax(context.getResources().getString(R.string.no2_one_hour_max), sNo2OneHourMax);
                    south.setO3EightHourMax(context.getResources().getString(R.string.o3_eight_hour_max), sO3EightHourMax);
                    south.setPsiTwentyFourHourly(context.getResources().getString(R.string.psi_twenty_four_hourly), sPsiTwentyFourHourly);
                    south.setSo2TwentyFourHourly(context.getResources().getString(R.string.so2_twenty_four_hourly), sSo2TwentyFourHourly);
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
