package com.app.eynav.ui.forum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ForumTopic extends Fragment {
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<ModeForum> modeForumList = new ArrayList<>();
    ForumTopicAdapter forumTopicAdapter;
    public static final String EXTRA_FORUM = "forum1";
    ModeForum modeForum;
    static RelativeLayout rlSendComment;
    EditText commentEd;
    ImageButton sendBtn;
    ProgressDialog pd;
    static boolean showSendComment = false;
    static String pId = "";
    static ModeForum forum;
//     AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

    public ForumTopic(ModeForum modeForum) {
        System.out.println(modeForum);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_topic, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.postRecyclerview);
        commentEd = view.findViewById(R.id.commentEd);
        sendBtn = view.findViewById(R.id.sendBtn);
        rlSendComment = view.findViewById(R.id.rlSendComment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        Bundle bundle = this.getArguments();


//        final EditText edittext = new EditText(getContext());
//        alert.setMessage("Enter Your Message");
//        alert.setTitle("Enter Your Title");
//
//        alert.setView(edittext);
//
//        alert.setPositiveButton("send comment", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String YouEditTextValue1 = edittext.getText().toString();
//            }
//        });
//
//        alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // what ever you want to do with No option.
//            }
//        });
//
//        alert.show();
        if (bundle != null) {
            modeForum = bundle.getParcelable(EXTRA_FORUM);
            System.out.println(modeForum);
        }
        modeForumList = new ArrayList<>();
        loadPosts();

        sendBtn.setOnClickListener(l ->{
            forumComment();
        });


        return view;
    }

    private void forumComment() {
        pd = new ProgressDialog(getContext());
        pd.setMessage("Adding comment...");

        String comment = commentEd.getText().toString().trim();
        if (TextUtils.isEmpty(comment)){
            Toast.makeText(getContext(), "comment is empty", Toast.LENGTH_SHORT).show();
        }
        if (forum!= null){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Forums").child(pId).child("Comments");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("cId",forum.getpTime());
            hashMap.put("comment",comment);
            hashMap.put("timeStamp",String.valueOf(System.currentTimeMillis()));
            hashMap.put("uid",forum.getUid());
            hashMap.put("uEmail",forum.getuEmail());
            hashMap.put("uDp",forum.getuDp());
            hashMap.put("uName",forum.getuName());
            hashMap.put("pLiked",String.valueOf(0));


            long time= System.currentTimeMillis();

            ref.child(String.valueOf(time)).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    pd.dismiss();
                    Toast.makeText(getContext(), "comment Added...", Toast.LENGTH_SHORT).show();
                    commentEd.setText("");

                }
            });
        }


    }

    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Forums");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modeForumList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String pDescriptionv = (String) ds.child("pDescription").getValue();
                    String pId = (String) ds.child("pId").getValue();
                    String pTime = (String) ds.child("pTime").getValue();
                    String pTitle = (String) ds.child("pTitle").getValue();
                    String uEmail = (String) ds.child("uEmail").getValue();
                    String uid = (String) ds.child("uid").getValue();
                    String uName = (String) ds.child("uName").getValue();
                    String pLiked = (String) ds.child("pLiked").getValue();


                    ModeForum modeForum1 = new ModeForum(pId,pTitle,pDescriptionv,"",pTime,uid, uEmail,"", uName,pLiked);
                    if (modeForum1.getpTitle().equals(modeForum.getpTitle()) && modeForum1.getpDescr().equals(modeForum.getpDescr())){
                        modeForumList.add(modeForum1);
                        Log.e("jjjjjjj","jjjjjjjj");
                        System.out.println(modeForum1);
                    }


                }
                loadComments(modeForumList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments(List<ModeForum> modeForumList) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Forums").child(modeForumList.get(0).getpId()).child("Comments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    System.out.println("jjjjkkmmmckdnkkdjd");
                    System.out.println(ds.getValue());
                    String pDescriptionv = (String) ds.child("comment").getValue();
                    String pId = (String) ds.child("cId").getValue();
                    String pTime = (String) ds.child("timeStamp").getValue();
                    String uEmail = (String) ds.child("uEmail").getValue();
                    String uid = (String) ds.child("uid").getValue();
                    String uName = (String) ds.child("uName").getValue();
                    String uDp = (String) ds.child("uDp").getValue();
                    String pLiked = (String) ds.child("pLiked").getValue();


                    ModeForum modeForum = new ModeForum(pId,"",pDescriptionv,"",pTime,uid, uEmail,"", uName,pLiked);
                    System.out.println("+++++++++++++");
                    System.out.println(modeForum);
//                    ModeForum modeForum = ds.getValue(ModeForum.class);
                    modeForumList.add(modeForum);

                }
                Collections.sort(modeForumList);

                forumTopicAdapter = new ForumTopicAdapter(modeForumList, getActivity());
                recyclerView.setAdapter(forumTopicAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
