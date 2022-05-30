package com.app.eynav.ui.add;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//public class PlaceSportSourceJson {

    public class PlaceSportSourceJson extends AsyncTask<Void, Void, List<Place>> {
    Context mContext;
    RecyclerView rvPlaceMeet;
    List<Place> places = new ArrayList<>();
    String citySearch;
    String cityRegion;
    FragmentActivity activity;
    static Boolean isCancelled = false;

    public PlaceSportSourceJson(Context context, RecyclerView rvPlaceMeet, FragmentActivity activity, String citySearch, String cityRegion) {
        mContext = context;
        this.rvPlaceMeet = rvPlaceMeet;
        this.citySearch = citySearch;
        this.activity = activity;
        this.cityRegion = cityRegion;
    }

//    protected List<Place> doInBackground() {
    @Override
    protected List<Place> doInBackground(Void... voids) {
        try {
            Log.e("PlaceSportSourceJson", "PlaceSportSourceJson");
            String url = "https://firebasestorage.googleapis.com/v0/b/hackathon2022-b2c47.appspot.com/o/sport.json?alt=media&token=3f097730-a6e0-4190-910b-7ec94e9bfe68";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            try (Response response = call.execute()) {
                if (response.body() == null) System.out.println("null");
                String json = response.body().string();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (isCancelled()) break;
//                    if (!isCancelled) {
//
//                    }

                    JSONObject cityObject = jsonArray.getJSONObject(i);
                    String name = cityObject.getString("שם המתקן");
                    String nameEng;
                    double latitude;
                    double longitude;

                    String type = cityObject.getString("סוג מתקן");
                    String city = cityObject.getString("רשות מקומית");
                    if (city.contains(citySearch) || city.contains(cityRegion)) {
                        String phone = cityObject.optString("טלפון איש קשר");
                        String timeOpen = cityObject.optString("פנוי לפעילות");
                        Geocoder geocoder = new Geocoder(mContext, Locale.US);
                        try {
                            List<Address> addresses = geocoder.getFromLocationName(city + " " + name, 1);
                            int count = 0;
                            while (addresses.size() == 0 && count < 4) {
                                addresses = geocoder.getFromLocationName(city + " " + name, 1);
                                count++;
                            }
                            if (addresses.size() > 0) {
                                Address addr = addresses.get(0);
                                nameEng = addr.getAddressLine(0);
                                latitude = addr.getLatitude();
                                longitude = addr.getLongitude();
                                Place place = new Place(name, nameEng, latitude, longitude, type, city, phone, timeOpen);
                                places.add(place);
//                                PlaceAdapter placeAdapter = new PlaceAdapter(places, mContext);
//                                rvPlaceMeet.setAdapter(placeAdapter);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        PlaceAdapter placeAdapter = new PlaceAdapter(places, mContext);

                                        rvPlaceMeet.setAdapter(placeAdapter);
                                    }
                                });

                                LatLng latLng = new LatLng(addr.getLatitude(), addr.getLongitude());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        if (places.size() > 0){
                            return places;
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return places;
    }
}



