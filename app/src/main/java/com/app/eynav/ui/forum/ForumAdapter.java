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
    static boolean test;
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
        String location = "0";
        holder.modeForum = modeForum;
        holder.tvTopicForum.setText(modeForum.getpTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(Long.parseLong(modeForum.getpTime()));
        System.out.println(sdf.format(resultdate));
        sdf.format(resultdate);
        holder.tvdateForum.setText(sdf.format(resultdate));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
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
