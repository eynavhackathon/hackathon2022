package com.app.eynav.ui.add;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddNewMeet  extends Fragment implements AdapterView.OnItemSelectedListener{
    public static final String EXTRA_MEET = "meet1";
    Spinner spino, languageMeeting;
    CheckBox cbJoinNatives;
    AppCompatButton btnHourMeet, btnDateMeet;
    Button btn_else_place,btnSavePlace;
    TextView tvPlaceChoose;
    static Place placeChooseBefore = null;

    static Place placeChoose = null;
    RecyclerView rvPlaceMeet;
    ArrayList<String> courses = new ArrayList<>();
    LinearLayout llMeetInfo;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String typeMeet = "";
    List<Place> places = new ArrayList<>();
    Runnable runnable;
    PlaceLibrariesSourceJson placeLibrariesSourceJson;
    PlaceSportSourceJson placeSportSourceJson;
    String userType;
    String languageMeet;
    String yearBorn;
    String cityName;
    String cityRegion;
    ArrayList<String> lang_array = new ArrayList<>();
    private FirebaseAuth mAuth;

    public AddNewMeet(String userType) {
        this.userType = userType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_meet, container, false);
    }
    @SuppressLint({"SetTextI18n", "WrongThread"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spino = view.findViewById(R.id.meetings_type_new);
        llMeetInfo = view.findViewById(R.id.llNewMeetInfo);
        btnHourMeet= view.findViewById(R.id.btnHourMeet);
        btnDateMeet= view.findViewById(R.id.btnDateMeet);
         btn_else_place= view.findViewById(R.id.btn_else_place);
         btnSavePlace= view.findViewById(R.id.btnSavePlace);
         rvPlaceMeet= view.findViewById(R.id.rvPlaceMeet);
        tvPlaceChoose = view.findViewById(R.id.tvPlaceChoose);
        languageMeeting = view.findViewById(R.id.languageMeeting);
        cbJoinNatives = view.findViewById(R.id.cbJoinNatives);
        final Handler handler = new Handler();
        final int delay = 100; // 1000 milliseconds == 1 second
        mAuth = FirebaseAuth.getInstance();
        courses.add("support");
        courses.add("Sports - football");
        courses.add("Sports - running");
        courses.add("Sports - Basketball");
        courses.add("Israeli singing");
        courses.add("Quizzes and games");
        courses.add("Israeli holidays and events");
        courses.add("Other");
        spino.setOnItemSelectedListener(this);
        ArrayAdapter ad= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);
        languageMeeting.setOnItemSelectedListener(this);
        lang_array.add("language");
        ArrayAdapter adL= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,lang_array);
        adL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageMeeting.setAdapter(adL);
        btnHourMeet.setText(getTimeMeet());
        btnDateMeet.setText(getDateMeet());
        FirebaseUser userN = mAuth.getCurrentUser();
        String uid = userN.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(uid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                cityName = (dataSnapshot.child("cityName").getValue(String.class)).split("-")[0];
                if (cityName.contains("אביב")){
                    cityName = "אביב";
                }
                cityRegion = dataSnapshot.child("cityRegion").getValue(String.class);
                listRV(typeMeet);
                for (int i = 1; i < 10; i++) {
                    String lang = dataSnapshot.child("lang"+i).getValue(String.class);
                    if (lang != null){
                        lang_array.add(lang);
                    }
                }
                String dateBo = dataSnapshot.child("DateBo").getValue(String.class);

                yearBorn =  (dateBo.split(" "))[2];

            }
        });
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + " " + getMonthFormat(month) + " " + year;
                btnDateMeet.setText(date);
            }
        };
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                btnHourMeet.setText(mFormat.format(calendar.getTime()));
            }
        };
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        btnDateMeet.setOnClickListener(l->{
            datePickerDialog.show();
        });
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, hour, minute,true);
        btnHourMeet.setOnClickListener(l->{
            timePickerDialog.show();
        });
        rvPlaceMeet.setHasFixedSize(true);
        PlaceAdapter placeAdapter = new PlaceAdapter(places,getContext());
        rvPlaceMeet.setAdapter(placeAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager rvLiLinayoutManager = layoutManager;
        rvPlaceMeet.setLayoutManager(rvLiLinayoutManager);
        btn_else_place.setOnClickListener(l ->{
            listRV(typeMeet);
            placeChoose = null;
            tvPlaceChoose.setText("");
        });
        btnSavePlace.setOnClickListener(l ->{


            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> user = new HashMap<>();
            boolean checked = ((CheckBox) cbJoinNatives).isChecked();

            user.put("typeMeet", typeMeet);
            user.put("dateMeet", btnDateMeet.getText());
            user.put("timeMeet", btnHourMeet.getText());
            user.put("placeMeet", "");
            if (userType.equals("new_immigrant")) {
                user.put("countNewImmigrant", 1);
                user.put("countNativeB", 0);
                user.put("countVolunteer", 0);

            }
            if (userType.equals("native_b")) {
                user.put("countNewImmigrant", 0);
                user.put("countNativeB", 1);
                user.put("countVolunteer", 0);
            }
            if (userType.equals("volunteer")) {
                user.put("countNewImmigrant", 0);
                user.put("countNativeB", 0);
                user.put("countVolunteer", 1);
            }
            user.put("languageMeet",languageMeet);
            user.put("joinNatives",checked);
            user.put("yearBorn",yearBorn);
            FirebaseUser userN1 = mAuth.getCurrentUser();
            String uid1 = userN1.getUid();
            user.put("userUID",uid1);
            user.put("cityRegion",cityRegion);

            Map<String, Object> user1 = new HashMap<>();
            user1.put("userUID",uid1);
            if (placeChoose == null){
                user.put("placeNameEng", "Azrieli Center, דרך מנחם בגין, תל אביב-יפו");
                user.put("placeLatitude",34.7943915);
                user.put("placeLongitude", 32.0740814);
                db.collection("meets").document(btnDateMeet.getText() +" "+typeMeet+" "+String.valueOf(34.7943915))
                        .set(user);
                db.collection("meets").document(btnDateMeet.getText() +" "+typeMeet+" "+String.valueOf(placeChoose.getLatitude())).collection("users").document(uid1)
                        .set(user1);
            }else {
                user.put("placeNameEng", placeChoose.getNameEng());
                user.put("placeLatitude", placeChoose.getLatitude());
                user.put("placeLongitude", placeChoose.getLongitude());
                db.collection("meets").document(btnDateMeet.getText() +" "+typeMeet+" "+String.valueOf(placeChoose.getLatitude()))
                        .set(user);
                db.collection("meets").document(btnDateMeet.getText() +" "+typeMeet+" "+String.valueOf(placeChoose.getLatitude())).collection("users").document(uid1)
                        .set(user1);
            }
            placeChoose = null;
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Bundle bundle2 = new Bundle();
            AddNewMeet.placeChoose = null;
            Fragment fragment2 = new AddFragment(userType);
            fragment2.setArguments(bundle2);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment2).addToBackStack(null).commit();
// Add a new document with a generated ID

        });
    }

    private void listRV(String typeMeet) {
        if (typeMeet.contains("support") || typeMeet.length() == 0){
            placeLibrariesSourceJson = new PlaceLibrariesSourceJson(getContext(), rvPlaceMeet,getActivity(), cityName,cityRegion);
            new Thread(new Runnable() {
                public void run() {
                    synchronized (this) {
                        try {
                            places = placeLibrariesSourceJson.doInBackground();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            PlaceAdapter placeAdapter = new PlaceAdapter(places, getContext());

            rvPlaceMeet.setAdapter(placeAdapter);
        }else {
            if (typeMeet.contains("Other")){
                placeSportSourceJson = new PlaceSportSourceJson(getContext(), rvPlaceMeet,getActivity(), cityName,cityRegion);
                new Thread(new Runnable() {
                    public void run() {
                        try  {
                            places = placeSportSourceJson.doInBackground();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                PlaceAdapter placeAdapter = new PlaceAdapter(places, getContext());

                rvPlaceMeet.setAdapter(placeAdapter);
            }else {
                placeSportSourceJson = new PlaceSportSourceJson(getContext(), rvPlaceMeet,getActivity(), cityName,cityRegion);
                new Thread(new Runnable() {
                    public void run() {
                        synchronized (this) {
                            try {
                                places = placeSportSourceJson.doInBackground();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                PlaceAdapter placeAdapter = new PlaceAdapter(places, getContext());

                rvPlaceMeet.setAdapter(placeAdapter);
            }

        }
    }

    private String getTimeMeet() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return mFormat.format(calendar.getTime());
    }
    private String getDateMeet() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return (day + " " + getMonthFormat(month) + " " + year);
    }
    private String getMonthFormat(int month) {
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.e("onItemSelected","onItemSelected");
        TextView llMeetInfoTextType= new TextView(getContext());
        TextView llMeetInfoTextConditions= new TextView(getContext());
        languageMeet = (String) languageMeeting.getSelectedItem();
        typeMeet = (String) spino.getSelectedItem();

        if (cityName != null ){
            final Handler handler = new Handler();
            final int delay = 100; // 1000 milliseconds == 1 second
            mAuth = FirebaseAuth.getInstance();

            listRV(typeMeet);

            handler.postDelayed( runnable = new Runnable() {
                public void run() {
                    if (placeChoose != null) {
                        if ((placeChooseBefore == null) || (!(placeChoose.equals(placeChooseBefore) ))){
                            placeChooseBefore = placeChoose;
                            if (placeChoose.getType().equals("library")) {
                                String[] sp = placeChoose.getNamePlace().split(",");
                                tvPlaceChoose.setText(sp[0]);
                                placeLibrariesSourceJson.cancel(true);

                            } else {
                                tvPlaceChoose.setText(placeChoose.getNamePlace());
                                placeSportSourceJson.cancel(true);
                            }
                            handler.removeCallbacks(runnable);
                            places = new ArrayList<>();
                            PlaceAdapter placeAdapter = new PlaceAdapter(places, getContext());
                            rvPlaceMeet.setAdapter(placeAdapter);
                        }
                        handler.postDelayed(runnable, delay);
                    }
                }
            }, delay);
        }
        switch (typeMeet){
            case "support":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("A meeting to provide answers to questions and problems with a native of the country who will be happy to answer and be of help. The meeting takes place with a number of immigrants who speak your language so that you feel as comfortable as possible\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a volunteer and a minimum number of participants, as soon as there is a volunteer and a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Sports - football":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("A sports meeting in any field you choose: football, basketball, running. The meeting takes place with people your age group.\n It is recommended to combine the meeting with the natives of the country so that you can enjoy a nurturing experience that combines the Israeli atmosphere together with pleasure and fun.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a minimum number of participants, as soon as there is a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Sports - running":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("A sports meeting in any field you choose: football, basketball, running. The meeting takes place with people your age group.\n It is recommended to combine the meeting with the natives of the country so that you can enjoy a nurturing experience that combines the Israeli atmosphere together with pleasure and fun.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a minimum number of participants, as soon as there is a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Sports - Basketball":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("A sports meeting in any field you choose: football, basketball, running. The meeting takes place with people your age group.\n It is recommended to combine the meeting with the natives of the country so that you can enjoy a nurturing experience that combines the Israeli atmosphere together with pleasure and fun.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a minimum number of participants, as soon as there is a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Israeli singing":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("A meeting that aims to make classic and modern songs of Israeli society accessible. Together we will sing the songs of the Land of Israel and understand what is behind them.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a volunteer and a minimum number of participants, as soon as there is a volunteer and a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Quizzes and games":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("A meeting with a volunteer. The session will include games and quizzes in groups and individuals so you can get to know the Israeli culture and society of which you are now a part and all in a fun and enjoyable atmosphere with new people. The meeting is held in basic and easy Hebrew so that everyone can understand and enjoy the fun activities.\n* Meeting with Israelis will contribute to the experience of integration with Israeli society and can be your first step to integration in local society and culture.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a volunteer and a minimum number of participants, as soon as there is a volunteer and a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Israeli holidays and events":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("Schedule a meeting with new people in order to go to Israeli events of all kinds. For example: festivals, concerts, food stalls, street performances and many other diverse events that represent Israeli culture. At these events, you will get to know the Israeli culture and people up close and enjoy the good that the State of Israel has to offer to the general public.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a minimum number of participants, as soon as there is a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
            case "Other":
                llMeetInfo.removeAllViews();
                llMeetInfoTextType.setText("If the indicated meetings do not have a meeting that you are interested in, you can initiate a meeting yourself or join meetings that other people have initiated. The meeting can be anywhere and on any topic you choose. For example: bar, restaurant, cinema, sea and many other places.\n* The meeting can be arranged with Israelis or only with new immigrants.\n");
                llMeetInfo.addView(llMeetInfoTextType);
                llMeetInfoTextConditions.setText("The meeting requires a minimum number of participants, as soon as there is a minimum number required the meeting will take place\n");
                llMeetInfo.addView(llMeetInfoTextConditions);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
