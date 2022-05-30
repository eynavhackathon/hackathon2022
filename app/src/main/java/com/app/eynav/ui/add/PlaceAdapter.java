package com.app.eynav.ui.add;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter <PlaceAdapter.MeetAdapterHolder> {
    List<Place> places;
    Context context;
//    FusedLocationProviderClient clinet;
    static boolean test;
//    StreamDBHelper streamDBHelperamDBHelper;
    static Boolean ifNameCity;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public PlaceAdapter(List<Place> places, Context context) {
        this.places=places;
        this.context=context;
    }

    @NonNull
    @Override
    public PlaceAdapter.MeetAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_add_new_meet_card,parent,false);
        return new PlaceAdapter.MeetAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.MeetAdapterHolder holder, int position) {

            Place place = places.get(position);
//        holder.tvNameTour.setText(meet.getName());
//        holder.tvTourInformation.setText(meet.getType());
            String location = "0";
            holder.place = place;

            if (place.getType().equals("library")){
                String [] sp = place.getNamePlace().split(",");
                holder.tvMeetNamePlace.setText(sp[0]);
            }else {
                holder.tvMeetNamePlace.setText(place.getNamePlace());
            }
            holder.tvPlaceAddress.setText(place.getNameEng());
        holder.btnChoice.setOnClickListener(l ->{
            AddNewMeet.placeChoose = place;

            String placenametext = "You have selected the place: "+holder.tvMeetNamePlace.getText();
            new AlertDialog.Builder(context)
                    .setTitle("Choice Place")
                    .setMessage(placenametext)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_input_add)
                    .show();
        });
//        placeChoose


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
    @Override
    public int getItemCount() {
        return places.size();
//        return 2;

    }


    public class MeetAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvMeetNamePlace;
        TextView tvPlaceAddress;
        AppCompatButton btnChoice;
        Place place;
//TODO btnChoice
        public MeetAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvMeetNamePlace = itemView.findViewById(R.id.tvMeetNamePlace);
            tvPlaceAddress = itemView.findViewById(R.id.tvPlaceAddress);
            btnChoice = itemView.findViewById(R.id.btnChoice);
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
