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
    static boolean test;
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
