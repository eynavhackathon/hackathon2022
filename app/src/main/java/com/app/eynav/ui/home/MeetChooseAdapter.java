package com.app.eynav.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.app.eynav.ui.add.Meet;
import com.app.eynav.ui.add.MeetInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MeetChooseAdapter extends RecyclerView.Adapter <MeetChooseAdapter.MeetAdapterHolder> {
    List<Meet> meets;
    Context context;
    static boolean test;
    static Boolean ifNameCity;
    String userType;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public MeetChooseAdapter(List<Meet> meets, String userType, Context context) {
        this.meets=meets;
        this.context=context;
        this.userType = userType;
    }

    @NonNull
    @Override
    public MeetAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_home_meet_select_card,parent,false);
        return new MeetAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetAdapterHolder holder, int position) {
        Meet meet = meets.get(position);
        String location = "0";
        holder.meet = meet;
        holder.tvDate.setText(meet.getDateAndHourMeet());
        holder.tvPlace.setText(meet.getPlaceNameEng());
        holder.meet_type.setText(meet.getTypeMeet());
         db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser userN1 = mAuth.getCurrentUser();
        String uid1 = userN1.getUid();
            switch (meet.getTypeMeet()) {
                case "support":
                    if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) && (meet.getCountVolunteer() >= 1)) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Sports - football":

                    if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 6) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Sports - running":

                    if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Sports - Basketball":

                    if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 6) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Israeli singing":

                    if (((meet.getCountNewImmigrant() + meet.getCountVolunteer()) >= 8) && (meet.getCountNativeB() >= 1)) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Quizzes and games":

                    if (((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 8) && (meet.getCountVolunteer() >= 1)) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Israeli holidays and events":

                    if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }

                    break;
                case "Other":

                    if ((meet.getCountNewImmigrant() + meet.getCountNativeB()) >= 5) {
                        holder.ivLock.setVisibility(View.GONE);
                        holder.divider.setBackgroundResource(R.color.white);
                    } else {
                        holder.ivLock.setVisibility(View.VISIBLE);
                        holder.divider.setBackgroundResource(R.color.gray);
                    }
                    break;
            }

    }

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
        View divider;
        ImageView ivLock;
        Meet meet;

        public MeetAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvdate);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            meet_type = itemView.findViewById(R.id.meet_type);
            divider =  itemView.findViewById(R.id.divider);
            ivLock = itemView.findViewById(R.id.ivLock);
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
