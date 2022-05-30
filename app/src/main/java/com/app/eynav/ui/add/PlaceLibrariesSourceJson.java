package com.app.eynav.ui.add;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

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

public class PlaceLibrariesSourceJson extends AsyncTask<Void, Void, List<Place>> {
    Context mContext;
    RecyclerView rvPlaceMeet;
    List<Place> places = new ArrayList<>();
    String citySearch;
    FragmentActivity activity;
    String cityRegion;
    public PlaceLibrariesSourceJson(Context context, RecyclerView rvPlaceMeet, FragmentActivity activity, String citySearch, String cityRegion){
        mContext = context;
        this.rvPlaceMeet = rvPlaceMeet;
        this.citySearch = citySearch;
        this.activity = activity;
        this.cityRegion = cityRegion;
    }

    @Override
    protected List<Place> doInBackground(Void... voids) {
//        String link = "https://firebasestorage.googleapis.com/v0/b/myproject-a56a3.appspot.com/o/WeaderApi.json?alt=media&token=0775cef9-f276-46d2-bc9c-3e2011df9ef8";

        try {
            String url = "https://firebasestorage.googleapis.com/v0/b/hackathon2022-b2c47.appspot.com/o/libraries.json?alt=media&token=8422aae7-4b11-41f1-b528-b7dc6ac56fd5";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            try (Response response = call.execute()) {
                if (response.body() == null) System.out.println("null");
                String json = response.body().string();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (isCancelled()) break;
                    JSONObject cityObject = jsonArray.getJSONObject(i);
                    String name = cityObject.getString("כתובת הספרייה");
                    String city = cityObject.getString("שם היישוב");
                    if (city.contains(citySearch) || city.contains(cityRegion)){
                        String nameEng;
                        double latitude;
                        double longitude;
                        String phone = cityObject.getString("טלפון");
                        Geocoder geocoder = new Geocoder(mContext, Locale.US);
                        try {
                            List<Address> addresses = geocoder.getFromLocationName(name, 1);
                            int count = 0;
                            while (addresses.size() == 0 && count < 4) {
                                addresses = geocoder.getFromLocationName(name, 1);
                                count++;
                            }
                            if (addresses.size() > 0) {
                                Address addr = addresses.get(0);
                                nameEng = addr.getAddressLine(0);
                                latitude = addr.getLatitude();
                                longitude = addr.getLongitude();
                                Place place = new Place(name, nameEng, latitude, longitude, "library", city, phone, "");
                                System.out.println(place);
                                places.add(place);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        PlaceAdapter placeAdapter = new PlaceAdapter(places, mContext);

                                        rvPlaceMeet.setAdapter(placeAdapter);
                                    }
                                });
//                                placeAdapter.notifyDataSetChanged();
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