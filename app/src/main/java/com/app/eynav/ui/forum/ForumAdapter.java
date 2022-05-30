package com.app.eynav.ui.forum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.app.eynav.ui.add.Meet;
import com.app.eynav.ui.add.MeetInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumAdapter extends RecyclerView.Adapter <ForumAdapter.ForumAdapterHolder> {
    List<ModeForum> modeForumList;
    Context context;
//    FusedLocationProviderClient clinet;
    static boolean test;
//    StreamDBHelper streamDBHelperamDBHelper;
    static Boolean ifNameCity;
    String userType;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public ForumAdapter(List<ModeForum> modeForumList, String userType, Context context) {
        this.modeForumList=modeForumList;
        this.context=context;
        this.userType = userType;
    }

    @NonNull
    @Override
    public ForumAdapter.ForumAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_forum_card,parent,false);
        return new ForumAdapter.ForumAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumAdapter.ForumAdapterHolder holder, int position) {
        ModeForum modeForum = modeForumList.get(position);
//        holder.tvNameTour.setText(meet.getName());
//        holder.tvTourInformation.setText(meet.getType());


        String location = "0";
        holder.modeForum = modeForum;
//        holder.tvDate.setText(meet.getDateAndHourMeet());
//        holder.tvPlace.setText(meet.getPlaceNameEng());
//        holder.meet_type.setText(meet.getTypeMeet());
        holder.tvTopicForum.setText(modeForum.getpTitle());
//        String format = DateFormat.getInstance().format(modeForum.getpTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(Long.parseLong(modeForum.getpTime()));
        System.out.println(sdf.format(resultdate));
        sdf.format(resultdate);
        holder.tvdateForum.setText(sdf.format(resultdate));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

//        FirebaseUser userN1 = mAuth.getCurrentUser();
//        String uid1 = userN1.getUid();
//        db.collection("meets").document(meet.getDateMeetInfo()+" "+meet.getTypeMeet()+" "+meet.getPlaceLatitude()).collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (uid1.equals(document.getId())){
//                                    holder.btnRegistration.setVisibility(View.GONE);
//                                }
//                            }
//                        }
//                    }
//                });
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
        return modeForumList.size();
    }


    public class ForumAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvTopicForum;
        TextView tvdateForum;

        ModeForum modeForum;

        public ForumAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvTopicForum = itemView.findViewById(R.id.tvTopicForum);
            tvdateForum = itemView.findViewById(R.id.tvdateForum);

            itemView.setOnClickListener((v) -> {
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable(ForumTopic.EXTRA_FORUM, modeForum);
                Fragment fragment2 = new ForumTopic(modeForum);
                fragment2.setArguments(bundle2);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment2).addToBackStack(null).commit();



            });


        }

    }
}
