package com.app.eynav.ui.add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.eynav.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MeetInfo extends Fragment implements OnMapReadyCallback {
    public static final String EXTRA_MEET = "meet1";
    TextView tvDateMeetInfo, tvTypeMeetInfo, tvPlaceMeetInfo;
    FloatingActionButton fdLinkChat;
    MapView mapViewMeetInfo;
    GoogleMap mMap;
    LinearLayout llMeetInfo;
    Meet meet = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_meet, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        llMeetInfo = view.findViewById(R.id.llMeetInfo);
        tvDateMeetInfo = view.findViewById(R.id.tvDateMeetInfo);
        tvTypeMeetInfo = view.findViewById(R.id.tvTypeMeetInfo);
        tvPlaceMeetInfo = view.findViewById(R.id.tvPlaceMeetInfo);
        fdLinkChat = view.findViewById(R.id.fdLinkChat);
        mapViewMeetInfo = view.findViewById(R.id.mapViewMeetInfo);
        if (mapViewMeetInfo != null){
            mapViewMeetInfo.onCreate(null);
            mapViewMeetInfo.onResume();
            mapViewMeetInfo.getMapAsync(this);
        }
        fdLinkChat.setTranslationY(300);
        float v = 0;
        fdLinkChat.setAlpha(v);
        fdLinkChat.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//        TODO fdLinkChat
//        TODO mapViewMeetInfo
        if (bundle != null) {
            meet = bundle.getParcelable(EXTRA_MEET);

        }
        tvTypeMeetInfo.setText(meet.getTypeMeet());
        tvDateMeetInfo.setText(meet.getDateAndHourMeet());
        tvPlaceMeetInfo.setText(meet.getPlaceNameEng());
        TextView llMeetInfoTextType = new TextView(getContext());
        TextView llMeetInfoTextConditions = new TextView(getContext());
        String text;

        switch (meet.getTypeMeet()) {
            case "support":
                llMeetInfoTextType.setText("A meeting to provide answers to questions and problems with a native of the country who will be happy to answer and be of help. The meeting takes place with a number of immigrants who speak your language so that you feel as comfortable as possible\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) && (meet.getCountVolunteer() >= 1)) {
                    text = "The conditions required for a meeting are met";
                } else {
                    if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) < 5) && (meet.getCountVolunteer() == 0)) {
                        text = "Missing a volunteer and " + (5 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " participants to meet the conditions required for the meeting to take place.\n";
                    } else {
                        if ((meet.getCountVolunteer() == 0)) {
                            text = "Missing a volunteer to meet the conditions required for the meeting to take place.\n";

                        } else {
                            text = "Missing " + (5 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " participants to meet the conditions required for the meeting to take place.\n";
                        }
                    }
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Sports - football":
                llMeetInfoTextType.setText("A sports meeting in any field you choose: football, basketball, running. The meeting takes place with people your age group.\n It is recommended to combine the meeting with the natives of the country so that you can enjoy a nurturing experience that combines the Israeli atmosphere together with pleasure and fun.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 6) {
                    text = "The conditions required for a meeting are met";
                } else {
                    text = (6 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " more participants are missing to reach the minimum number of participants per meeting";
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Sports - running":
                llMeetInfoTextType.setText("A sports meeting in any field you choose: football, basketball, running. The meeting takes place with people your age group.\n It is recommended to combine the meeting with the natives of the country so that you can enjoy a nurturing experience that combines the Israeli atmosphere together with pleasure and fun.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) {
                    text = "The conditions required for a meeting are met";
                } else {
                    text = (5 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " more participants are missing to reach the minimum number of participants per meeting";
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Sports - Basketball":
                llMeetInfoTextType.setText("A sports meeting in any field you choose: football, basketball, running. The meeting takes place with people your age group.\n It is recommended to combine the meeting with the natives of the country so that you can enjoy a nurturing experience that combines the Israeli atmosphere together with pleasure and fun.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 6) {
                    text = "The conditions required for a meeting are met";
                } else {
                    text = (6 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " more participants are missing to reach the minimum number of participants per meeting";
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Israeli singing":
                llMeetInfoTextType.setText("A meeting that aims to make classic and modern songs of Israeli society accessible. Together we will sing the songs of the Land of Israel and understand what is behind them.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 8) && (meet.getCountVolunteer() >= 1)) {
                    text = "The conditions required for a meeting are met";
                } else {
                    if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) < 8) && (meet.getCountVolunteer() == 0)) {
                        text = "Missing a volunteer and " + (8 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " participants to meet the conditions required for the meeting to take place.\n";
                    } else {
                        if ((meet.getCountVolunteer() == 0)) {
                            text = "Missing a volunteer to meet the conditions required for the meeting to take place.\n";

                        } else {
                            text = "Missing " + (8 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " participants to meet the conditions required for the meeting to take place.\n";
                        }
                    }
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Quizzes and games":
                llMeetInfoTextType.setText("A meeting with a volunteer. The session will include games and quizzes in groups and individuals so you can get to know the Israeli culture and society of which you are now a part and all in a fun and enjoyable atmosphere with new people. The meeting is held in basic and easy Hebrew so that everyone can understand and enjoy the fun activities.\n* Meeting with Israelis will contribute to the experience of integration with Israeli society and can be your first step to integration in local society and culture.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 8) && (meet.getCountVolunteer() >= 1)) {
                    text = "The conditions required for a meeting are met";
                } else {
                    if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) < 8) && (meet.getCountVolunteer() == 0)) {
                        text = "Missing a volunteer and " + (8 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " participants to meet the conditions required for the meeting to take place.\n";
                    } else {
                        if ((meet.getCountVolunteer() == 0)) {
                            text = "Missing a volunteer to meet the conditions required for the meeting to take place.\n";

                        } else {
                            text = "Missing " + (8 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " participants to meet the conditions required for the meeting to take place.\n";
                        }
                    }
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Israeli holidays and events":
                llMeetInfoTextType.setText("Schedule a meeting with new people in order to go to Israeli events of all kinds. For example: festivals, concerts, food stalls, street performances and many other diverse events that represent Israeli culture. At these events, you will get to know the Israeli culture and people up close and enjoy the good that the State of Israel has to offer to the general public.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) {
                    text = "The conditions required for a meeting are met";
                } else {
                    text = (5 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " more participants are missing to reach the minimum number of participants per meeting";
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Other":
                llMeetInfoTextType.setText("If the indicated meetings do not have a meeting that you are interested in, you can initiate a meeting yourself or join meetings that other people have initiated. The meeting can be anywhere and on any topic you choose. For example: bar, restaurant, cinema, sea and many other places.\n* The meeting can be arranged with Israelis or only with new immigrants.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) {
                    text = "The conditions required for a meeting are met";
                } else {
                    text = (5 - (meet.getCountNewImmigrant() + meet.getCountNativeB())) + " more participants are missing to reach the minimum number of participants per meeting";
                }
                llMeetInfoTextConditions.setText(text);
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
        }

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (meet.getPlaceLatitude() == null){
            meet.setPlaceLongitude(34.7943915);
            meet.setPlaceLatitude(32.0740814);
        }
        System.out.println(meet.getPlaceLatitude());
            LatLng meetLocation = new LatLng(meet.getPlaceLatitude(),meet.getPlaceLongitude());
        mMap.addMarker(new MarkerOptions().position(meetLocation).title(meet.getPlaceNameEng()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meetLocation,15));


        showLocation();
    }

        private void showLocation() {
            final int RC_LOCASION = 0;

            String location = Manifest.permission.ACCESS_FINE_LOCATION;

            if (ContextCompat.checkSelfPermission(getContext(), location) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{location},RC_LOCASION);
                return;
            }
            mMap.setMyLocationEnabled(true);

        }
}
