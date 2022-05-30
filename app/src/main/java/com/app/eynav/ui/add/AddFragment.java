package com.app.eynav.ui.add;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.app.eynav.databinding.FragmentAddBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy",Locale.getDefault());
    SimpleDateFormat dateFormatday = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
    RecyclerView rvMeet;
    MeetAdapter meetsAdapter;
    FloatingActionButton fdAddMeet;
    Button mon_date;
    String userType;
    private FirebaseAuth mAuth;
    String yearBorn;
    String cityRegion;
    ArrayList<String> lang_array = new ArrayList<>();
    CompactCalendarView compactCalendarView;
    public AddFragment(String userType) {
        this.userType = userType;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel dashboardViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvMeet = root.findViewById(R.id.rvMeet);
        fdAddMeet = root.findViewById(R.id.fdAddMeet);
        if (!userType.equals("new_immigrant")){
            fdAddMeet.setVisibility(View.GONE);
        }
        fdAddMeet.setTranslationY(300);
        float v = 0;
        fdAddMeet.setAlpha(v);

        fdAddMeet.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        fdAddMeet.setOnClickListener(l ->{
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(MeetInfo.EXTRA_MEET, (Parcelable) meet);
//            Fragment fragment = new AddNewMeet();
////            fragment.setArguments(bundle);
////            AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
//
//            getContext().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).addToBackStack(null).commit();
//            FragmentManager fm = getSupportFragmentManager();
//            fm.beginTransaction().replace(R.id.frameLayout,fragment2).commit();




//            AppCompatActivity activity = (AppCompatActivity)getContext();
//            Bundle bundle2 = new Bundle();
//            Fragment fragment = new AddNewMeet();
//            fragment.setArguments(bundle2);
//            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).addToBackStack(null).commit();
//            activity.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).addToBackStack(null).commit();

//            FragmentManager fragmentManager = getChildFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.addToBackStack(null).replace(R.id.nav_host_fragment_activity_main, fragment);
//            transaction.commit();


//            AddNewMeet youTubeFragment = AddNewMeet.newInstance(youTubeFragment1.url, timeVideo);
            AppCompatActivity activity = (AppCompatActivity) root.getContext();
            Bundle bundle2 = new Bundle();
            AddNewMeet.placeChoose = null;
            Fragment fragment2 = new AddNewMeet(userType);
            fragment2.setArguments(bundle2);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment2).addToBackStack(null).commit();
//            AddNewMeet fragment1 = AddNewMeet;
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.frLayout, fragment, "TAG");
//            transaction.commit();

        });
        rvMeet.setHasFixedSize(true);
          compactCalendarView = (CompactCalendarView) root.findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
         mon_date = root.findViewById(R.id.mon_date);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);


        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.

        // events has size 2 with the 2 events inserted previously

        // define a listener to receive callbacks when certain events happen.

//        final TextView textView = binding.textAdd;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

//        CalendarView calendarView = root.findViewById(R.id.calendarView);
//        if (calendarView != null) {
//            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//                @Override
//                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                    // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
//                    String msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year;
//                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser userN = mAuth.getCurrentUser();
        String uid = userN.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.child(uid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                cityRegion = dataSnapshot.child("cityRegion").getValue(String.class);
                for (int i = 1; i < 10; i++) {
                    String lang = dataSnapshot.child("lang"+i).getValue(String.class);
                    if (lang != null){
                        lang_array.add(lang);
                    }
                }
                String dateBo = dataSnapshot.child("DateBo").getValue(String.class);
                yearBorn =  (dateBo.split(" "))[2];
                System.out.println(yearBorn);
                setMeets();

            }
        });



        return root;
    }

    private void setMeets() {
        List<Meet> meets = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        db.collection("meets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String dateMeet = (String) document.getData().get("dateMeet");
                                String typeMeet = (String) document.getData().get("typeMeet");
                                String timeMeet = (String) document.getData().get("timeMeet");
                                String placeMeet = (String) document.getData().get("placeMeet");
                                Long countNewImmigrant = (Long) document.getData().get("countNewImmigrant");
                                Long countNativeB = (Long) document.getData().get("countNativeB");
                                Long countVolunteer = (Long) document.getData().get("countVolunteer");
                                String placeNameEng = (String) document.getData().get("placeNameEng");
                                Double placeLatitude = (Double) document.getData().get("placeLatitude");
                                Double placeLongitude = (Double) document.getData().get("placeLongitude");
                                String cityRegionMeet = (String) document.getData().get("cityRegion");
                                String yearBornMeet = (String) document.getData().get("yearBorn");
                                String languageMeet = (String) document.getData().get("languageMeet");
                                Boolean joinNatives = (Boolean) document.getData().get("joinNatives");
                                int countNewImmigrant1 = Math.toIntExact(countNewImmigrant);
                                int countNativeB1 =  Math.toIntExact(countNativeB);
                                int countVolunteer1 = Math.toIntExact(countVolunteer);
                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
//                                int month = calendar.get(Calendar.MONTH);
//                                month = month + 1;
                                String[] k = dateMeet.split(" ");
                                String [] time = timeMeet.split(":");
                                int month = 1;
                                switch (k[1]) {
                                    case "JAN":
                                        month = 1;
                                        break;
                                    case "FEB":
                                        month = 2;
                                        break;
                                    case "MAR":
                                        month = 3;
                                        break;
                                    case "APR":
                                        month = 4;
                                        break;
                                    case "MAY":
                                        month = 5;
                                        break;
                                    case "JUN":
                                        month = 6;
                                        break;
                                    case "JUL":
                                        month = 7;
                                        break;
                                    case "AUG":
                                        month = 8;
                                        break;
                                    case "SEP":
                                        month = 9;
                                        break;
                                    case "OCT":
                                        month = 10;
                                        break;
                                    case "NOV":
                                        month = 11;
                                        break;
                                    case "DEC":
                                        month = 12;
                                        break;
                                    default:
                                        month = 1;
                                        break;
                                }
                                month = month - 1;
                                calendar.set(Calendar.YEAR, Integer.parseInt(k[2]));
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(k[0]));
                                calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
                                calendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
                                Date dateM = calendar.getTime();
                                Event ev1 = new Event(Color.GREEN, (calendar.getTimeInMillis()), typeMeet);
                                compactCalendarView.addEvent(ev1);
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, 0);
                                c.set(Calendar.MINUTE, 0);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                Date today = c.getTime();
                                System.out.println(today);
                                boolean testLang = false;
                                for (int i = 0; i < lang_array.size(); i++) {
                                    if (languageMeet.equals(lang_array.get(i))){
                                        testLang = true;
                                    }
                                }
                                Calendar calendar1 = Calendar.getInstance();
                                int year1 = calendar1.get(Calendar.YEAR);
                                Log.e("yearBorn",yearBorn);
                                int age = year1 - Integer.valueOf(yearBorn);
                                int ageMeet = year1 - Integer.valueOf(yearBornMeet);
                                int numGroupMeet = getNumGroup(ageMeet);
                                int numGroup = getNumGroup(age);

                                if ((calendar.getTime().after(today)) && (testLang) && (numGroupMeet == numGroup) && (cityRegion.equals(cityRegionMeet))){
                                    if (userType != null){
                                        if (!(userType.equals("native_b"))){
                                            Meet meet = new Meet(typeMeet,dateMeet,timeMeet,placeMeet,countNewImmigrant1,countNativeB1,countVolunteer1,dateM,placeNameEng,placeLatitude,placeLongitude,cityRegion,yearBorn,languageMeet);
                                            System.out.println(meet);
                                            meets.add(meet);
                                        }else {
                                            if  (joinNatives != null){
                                                if (joinNatives){
                                                    Meet meet = new Meet(typeMeet,dateMeet,timeMeet,placeMeet,countNewImmigrant1,countNativeB1,countVolunteer1,dateM,placeNameEng,placeLatitude,placeLongitude,cityRegion,yearBorn,languageMeet);
                                                    System.out.println(meet);
                                                    meets.add(meet);
                                                }

                                            }
                                        }
                                    }



                                }

                            }
                            Collections.sort(meets);

                            MeetAdapter meetsAdapter = new MeetAdapter(meets,userType,getContext());
                            rvMeet.setAdapter(meetsAdapter);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            RecyclerView.LayoutManager rvLiLinayoutManager = layoutManager;
                            rvMeet.setLayoutManager(rvLiLinayoutManager);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                            @Override
                            public void onDayClick(Date dateClicked) {
                                System.out.println(dateClicked);
                                rvMeet.getLayoutManager().scrollToPosition(0);
                                for (int i = 0; i < meets.size(); i++) {
                                    System.out.println(meets.get(i));
                                    if (DateUtils.isSameDay(meets.get(i).getDateM(),dateClicked)){
                                        System.out.println(i);
//                                        rvMeet.getLayoutManager().scrollToPosition(0);
//                                        RecyclerView.SmoothScroller smoothScroller = new
//                                                LinearSmoothScroller(getContext()) {
//                                                    @Override
//                                                    protected int getVerticalSnapPreference() {
//                                                        return LinearSmoothScroller.SNAP_TO_START;
//                                                    }
//                                                };
//                                        smoothScroller.setTargetPosition(i);
//                                        rvMeet.getLayoutManager().startSmoothScroll(smoothScroller);
                                        ((LinearLayoutManager)rvMeet.getLayoutManager()).scrollToPositionWithOffset(i,0);

//                                        rvMeet.getLayoutManager().smoothScrollToPosition(i);
                                        break;
                                    }

                                }
                                List<Event> events = compactCalendarView.getEvents(dateClicked);
//                                System.out.println(events);
                                List<String> dates = new ArrayList<>();
                                String date = (String) mon_date.getText();

                                if (date.length() > 2){
                                    try {
//                                    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
//                                        rvMeet.getLayoutManager().scrollToPosition(2);

                                        Date date1= dateFormat.parse(date);
                                        System.out.println(date1);
                                        for (Event e : compactCalendarView.getEventsForMonth(date1)) {
                                            dates.add(dateFormatday.format(e.getTimeInMillis()));
                                            if (dateFormatday.format(e.getTimeInMillis()).toString().contains("18-JUN-2022")){
                                                Log.d(TAG, "Day was clicked: " + dateFormat.format(e.getTimeInMillis()) + " with events " + e.getData());
                                            }
                                        }
                                        System.out.println(dates);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }else {
//                                    rvMeet.getLayoutManager().scrollToPosition(0);

                                    for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
                                        dates.add(dateFormatday.format(e.getTimeInMillis()));
                                        if (dateFormatday.format(e.getTimeInMillis()).toString().contains("18-JUN-2022")){
                                            Log.d(TAG, "Day was clicked: " + dateFormat.format(e.getTimeInMillis()) + " with events " + e.getData());
                                        }
                                    }
                                    System.out.println(dates);

                                }
//                                Date date1 =  dateFormat.format(date);

//                                Date date1= dateFormat.parse(date);



//                if (dateClicked.toString().compareTo("Fri Oct 21 09:00:00 AST 2022") == 0){
//                    Toast.makeText(getContext(), "eventjjdlldlddd", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getContext(), "noevent", Toast.LENGTH_SHORT).show();
//
//                }
                            }

                            @Override
                            public void onMonthScroll(Date firstDayOfNewMonth) {
                                mon_date.setText(dateFormat.format(firstDayOfNewMonth));
                                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                            }
                        });
                    }


                });
    }

    private int getNumGroup(int age) {
        int numGroup = 1;
        if (age < 16){
            numGroup = 1;
        }
        if ((age >= 16) && (age <= 20)){
            numGroup = 2;
        }
        if ((age >= 21) && (age <= 35)){
            numGroup = 3;
        }
        if ((age >= 36) && (age <= 50)){
            numGroup = 4;
        }
        if ((age >= 51) && (age <= 70)){
            numGroup = 5;
        }
        if (age > 70){
            numGroup = 6;
        }
        return numGroup;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}