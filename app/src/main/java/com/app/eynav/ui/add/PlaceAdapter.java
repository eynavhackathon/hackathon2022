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
    static boolean test;
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
    }
    @Override
    public int getItemCount() {
        return places.size();
    }


    public class MeetAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvMeetNamePlace;
        TextView tvPlaceAddress;
        AppCompatButton btnChoice;
        Place place;
        public MeetAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvMeetNamePlace = itemView.findViewById(R.id.tvMeetNamePlace);
            tvPlaceAddress = itemView.findViewById(R.id.tvPlaceAddress);
            btnChoice = itemView.findViewById(R.id.btnChoice);

        }

    }
}
