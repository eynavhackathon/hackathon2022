package com.app.eynav.ui.add;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetAdapter extends RecyclerView.Adapter <MeetAdapter.MeetAdapterHolder> {
    List<Meet> meets;
    Context context;
//    FusedLocationProviderClient clinet;
    static boolean test;
//    StreamDBHelper streamDBHelperamDBHelper;
    static Boolean ifNameCity;
    String userType;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public MeetAdapter(List<Meet> meets, String userType, Context context) {
        this.meets=meets;
        this.context=context;
        this.userType = userType;
    }

    @NonNull
    @Override
    public MeetAdapter.MeetAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_meet_card,parent,false);
        return new MeetAdapter.MeetAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetAdapter.MeetAdapterHolder holder, int position) {
        Meet meet = meets.get(position);
//        holder.tvNameTour.setText(meet.getName());
//        holder.tvTourInformation.setText(meet.getType());


        String location = "0";
        holder.meet = meet;
        holder.tvDate.setText(meet.getDateAndHourMeet());
        holder.tvPlace.setText(meet.getPlaceNameEng());
        holder.meet_type.setText(meet.getTypeMeet());
         db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser userN1 = mAuth.getCurrentUser();
        String uid1 = userN1.getUid();
        db.collection("meets").document(meet.getDateMeetInfo()+" "+meet.getTypeMeet()+" "+meet.getPlaceLatitude()).collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (uid1.equals(document.getId())){
                                    holder.btnRegistration.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
//        TODO לבדוק אם הפגישה נמצאת אצל המשתמש ואם כן אז הלחצן יעלם
//        if (ifNameCity){
//            holder.tvNameCityTour.setText(tour.getCity());
//        }
//        if (test){
//            location = Manifest.permission.ACCESS_FINE_LOCATION;
//            ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
//            clinet = LocationServices.getFusedLocationProviderClient(context);
//        }

//        Location locationTour =new Location("locationTour");
//        locationTour.setLatitude(tour.getY());
//        locationTour.setLongitude(tour.getX());
//        streamDBHelper = new StreamDBHelper(context);
//        if ((ContextCompat.checkSelfPermission(context, location) != PackageManager.PERMISSION_GRANTED) || (!test)){
//            ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
//            String nameCity = tour.getCity();
//            String distanceString;
//            if (nameCity != null) {
//                if (nameCity.equals("תל אביב יפו")){
//                    nameCity = "תל אביב";
//                }
//
//                CitiesDatabase citiesDatabase = new CitiesDatabase(context);
//                List<City> cities = citiesDatabase.getCitiesByName(nameCity);
//                Location locationCity = new Location("locationCity");
//                for (int i = 0; i < cities.size(); i++) {
//                    if (cities.get(i).getX() > 0) {
//                        locationCity.setLatitude(cities.get(i).getY());
//                        locationCity.setLongitude(cities.get(i).getX());
//                    }
//                }
//                distanceString = distance(locationTour,locationCity,tour);
//                holder.tvDistance.setText("מרחק ממרכז העיר " + distanceString + " ק״מ ");
//            }
//        }
//        else {
//            clinet.getLastLocation().addOnSuccessListener(((Activity) context), new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location myLocation) {
//                    if (myLocation != null){
//                        String distanceString = distance(myLocation,locationTour,tour);
//                        holder.tvDistance.setText("מרחק ממקומך הנוכחי " + distanceString + " ק״מ ");
//                        System.out.println(test);
//
//                    }
//
//                }
//            });
//        }
        holder.btnRegistration.setOnClickListener(l ->{
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            FirebaseUser userN = mAuth.getCurrentUser();
            String uid = userN.getUid();
            Map<String, Object> user1 = new HashMap<>();
            user1.put("userUID",uid);
            db.collection("meets").document(meet.getDateMeetInfo()+" "+meet.getTypeMeet()+" "+meet.getPlaceLatitude()).collection("users").document(uid)
                    .set(user1);
            DocumentReference washingtonRef = db.collection("meets").document(meet.getDateMeetInfo()+" "+meet.getTypeMeet()+" "+meet.getPlaceLatitude());
            if (userType.equals("new_immigrant")){
                washingtonRef.update("countNewImmigrant", FieldValue.increment(1));
            }
            if (userType.equals("native_b")){
                washingtonRef.update("countNativeB", FieldValue.increment(1));
            }
            if (userType.equals("volunteer")){
                washingtonRef.update("countVolunteer", FieldValue.increment(1));
            }
//            TODO להוסיף את הפגישה למשתמש
            System.out.println(meet);
            String placenametext = "You sign up for a meeting: "+holder.meet.getTypeMeet();
            new AlertDialog.Builder(context)
                    .setTitle("Register Meet")
                    .setMessage(placenametext)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            holder.btnRegistration.setVisibility(View.GONE);
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_input_add)
                    .show();
        });
    }
//    private String distance(Location locationStart,Location locationEnd , Tour tour){
//        double distance = locationStart.distanceTo(locationEnd) /1000;
//        String distanceString = String.valueOf(distance);
//        int distanceindexOfPoint = distanceString.indexOf(".");
//        if (distanceString.length() >= 4){
//            distanceString = distanceString.substring(0,distanceindexOfPoint+3);
//        }
//        TourMyLocationDistance tourMyLocationDistance = new TourMyLocationDistance(tour,distanceString);
//        streamDBHelper.add(tourMyLocationDistance);
//        return distanceString;
//    }
    public void setTest(boolean test3){
        test = test3;
    }
    @Override
    public int getItemCount() {
        return meets.size();
    }


    public class MeetAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvPlace;
        TextView meet_type;
        AppCompatButton btnRegistration;
        Meet meet;

        public MeetAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvdate);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            meet_type = itemView.findViewById(R.id.meet_type);
            btnRegistration = itemView.findViewById(R.id.btnRegistration);
            itemView.setOnClickListener((v) -> {
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable(MeetInfo.EXTRA_MEET, meet);
                Fragment fragment2 = new MeetInfo();
                fragment2.setArguments(bundle2);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment2).addToBackStack(null).commit();



            });


        }

    }
}
