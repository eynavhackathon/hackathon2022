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

        });
        holder.commentBtn.setOnClickListener(l ->{
            ForumTopic.showSendComment = true;
            ForumTopic.rlSendComment.setVisibility(View.VISIBLE);
            ForumTopic.forum = modeForum;
            ForumTopic.pId = modeForum.getpId();
        });
  
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
                }else {
                    holder.likeBtn.setImageResource(R.drawable.ic_action_like);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modeForums.size();

    }


    public class ForumTopicAdapterHolder extends RecyclerView.ViewHolder {
        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv, pLikesTv;
        ImageButton moreBtn;
        ImageButton likeBtn, commentBtn;
        ModeForum modeForum;
        public ForumTopicAdapterHolder(@NonNull View itemView) {
            super(itemView);
            pImageIv = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            pLikesTv = itemView.findViewById(R.id.pLikesTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            commentBtn = itemView.findViewById(R.id.commentBtn);

        }

    }
}
