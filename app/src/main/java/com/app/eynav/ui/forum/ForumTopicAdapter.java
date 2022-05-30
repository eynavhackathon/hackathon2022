package com.app.eynav.ui.forum;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.app.eynav.ui.add.AddNewMeet;
import com.app.eynav.ui.add.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ForumTopicAdapter extends RecyclerView.Adapter <ForumTopicAdapter.ForumTopicAdapterHolder> {
    List<ModeForum> modeForums;
    Context context;
//    FusedLocationProviderClient clinet;
    static boolean test;
    DatabaseReference likesRef;
    DatabaseReference forumsRef;
    String myUid;
    boolean mProcessLike = false;
    static Boolean ifNameCity;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public ForumTopicAdapter(List<ModeForum> modeForums, Context context) {
        this.modeForums=modeForums;
        this.context=context;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        forumsRef = FirebaseDatabase.getInstance().getReference("Forums");

    }

    @NonNull
    @Override
    public ForumTopicAdapter.ForumTopicAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_forum,parent,false);
        return new ForumTopicAdapter.ForumTopicAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumTopicAdapter.ForumTopicAdapterHolder holder, int position) {

            ModeForum modeForum = modeForums.get(position);
            String uid = modeForum.getUid();
        String email = modeForum.getuEmail();
        String name = modeForum.getuName();
        String uDp = modeForum.getuDp();
        String title = modeForum.getpTitle();
        String description = modeForum.getpDescr();
        String image = modeForum.getpImage();
        String timeStamp = modeForum.getpTime();
        String pLike = modeForum.getpLike();

        holder.modeForum = modeForum;

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(timeStamp));
        String time = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();
        holder.uNameTv.setText(name);
        holder.pTimeTv.setText(time);
        holder.pTitleTv.setText(title);
        holder.pDescriptionTv.setText(description);
        holder.pLikesTv.setText(pLike + "Likes");

        if (modeForums.size()-1 == position){
            holder.likeBtn.setVisibility(View.GONE);
            holder.pLikesTv.setVisibility(View.GONE);

        }else {
            holder.commentBtn.setVisibility(View.GONE);

        }
        loadUserInfo(holder);
        setLikes(holder, modeForum.getpId());


        holder.moreBtn.setOnClickListener(l ->{
            Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();

        });
        holder.likeBtn.setOnClickListener(l ->{
            likesRef = FirebaseDatabase.getInstance().getReference("Forums").child(modeForum.getpId()).child("Comments").child(modeForum.getpTime()).child("Likes");

            int pLikes = Integer.parseInt(modeForum.getpLike());
            mProcessLike = true;
            String forumIde = modeForum.getpId();
            likesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (mProcessLike){
                        if (snapshot.child(forumIde).hasChild(myUid)){
                            forumsRef.child(forumIde).child("pLiked").setValue(""+(pLikes-1));
                            likesRef.child(forumIde).child(myUid).removeValue();
                            mProcessLike = false;
                        }else {
                            forumsRef.child(forumIde).child("pLiked").setValue(""+(pLikes+1));
                            likesRef.child(forumIde).child(myUid).setValue("Liked");
                            mProcessLike = false;

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show();

        });
        holder.commentBtn.setOnClickListener(l ->{
//            alert.show();

            Toast.makeText(context, "comment", Toast.LENGTH_SHORT).show();
            ForumTopic.showSendComment = true;
            ForumTopic.rlSendComment.setVisibility(View.VISIBLE);
            ForumTopic.forum = modeForum;
            ForumTopic.pId = modeForum.getpId();
        });

//        holder.tvNameTour.setText(meet.getName());
//        holder.tvTourInformation.setText(meet.getType());
//            String location = "0";
//
//            if (place.getType().equals("library")){
//                String [] sp = place.getNamePlace().split(",");
//                holder.tvMeetNamePlace.setText(sp[0]);
//            }else {
//                holder.tvMeetNamePlace.setText(place.getNamePlace());
//            }
//            holder.tvPlaceAddress.setText(place.getNameEng());
//        holder.btnChoice.setOnClickListener(l ->{
//            AddNewMeet.placeChoose = place;
//
//            String placenametext = "You have selected the place: "+holder.tvMeetNamePlace.getText();
//            new AlertDialog.Builder(context)
//                    .setTitle("Choice Place")
//                    .setMessage(placenametext)
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_input_add)
//                    .show();
//        });
////        placeChoose


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

    private void loadUserInfo(ForumTopicAdapterHolder holder) {
        Query myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String name = ""+ds.child("name").getValue();
                    String myDp = ""+ds.child("image").getValue();
                    try {
                        Picasso.get().load(R.drawable.ic_baseline_person_24).into(holder.uPictureIv);
                    }catch (Exception e){

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setLikes(ForumTopicAdapterHolder holder, String forumKey) {
        likesRef = FirebaseDatabase.getInstance().getReference("Forums").child(holder.modeForum.getpId()).child("Comments").child(holder.modeForum.getpTime()).child("Likes");

        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(forumKey).hasChild(myUid)){
                    holder.likeBtn.setImageResource(R.drawable.ic_action_liked);
                    Log.e("Liked","Liked");
                }else {
                    holder.likeBtn.setImageResource(R.drawable.ic_action_like);
                    Log.e("no","no");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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
    @Override
    public int getItemCount() {
        return modeForums.size();
//        return 2;

    }


    public class ForumTopicAdapterHolder extends RecyclerView.ViewHolder {
        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv, pLikesTv;
        ImageButton moreBtn;
        ImageButton likeBtn, commentBtn;
        ModeForum modeForum;
//TODO btnChoice
        public ForumTopicAdapterHolder(@NonNull View itemView) {
            super(itemView);
//            uPictureIv = itemView.findViewById(R.id.);
            pImageIv = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            pLikesTv = itemView.findViewById(R.id.pLikesTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            commentBtn = itemView.findViewById(R.id.commentBtn);

//            itemView.setOnClickListener((v) -> {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(MeetInfo.EXTRA_MEET, (Parcelable) meet);
//                Fragment fragment = new MeetInfo();
//                fragment.setArguments(bundle);
//                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).addToBackStack(null).commit();
//            });


        }

    }
}
