package com.app.eynav.ui.forum;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.eynav.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class AddForumTopic  extends Fragment {
    FirebaseAuth firebaseAuth;
    Button pUploadBtn;
    EditText pDescriptionEt, pTitleEt;
    String name, email, uid, dp;
    DatabaseReference userDbRef;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_forum_topic, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        pd = new ProgressDialog(getContext());
        pUploadBtn = view.findViewById(R.id.pUploadBtn);
        pDescriptionEt = view.findViewById(R.id.pDescriptionEt);
        pTitleEt = view.findViewById(R.id.pTitleEt);
        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    name = ""+ds.child("name").getValue();
                    email = ""+ds.child("email").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pUploadBtn.setOnClickListener(l ->{
            String title = pTitleEt.getText().toString().trim();
            String description = pDescriptionEt.getText().toString().trim();
            if (TextUtils.isEmpty(title)){
                Toast.makeText(getContext(), "Enter title...", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(description)){
                Toast.makeText(getContext(), "Enter description...", Toast.LENGTH_SHORT).show();
            }
            uploadData(title,description);
        });


        return view;
    }

    private void uploadData(String title, String description) {
        pd.setMessage("Publishing forum...");
        pd.show();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePateAndName = "Forums/" + "forum_"+timeStamp;
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("uid",uid);
        hashMap.put("uName",name);
        hashMap.put("uEmail",email);
        hashMap.put("pId",timeStamp);
        hashMap.put("pTitle",title);
        hashMap.put("pDescription",description);
        hashMap.put("pTime",timeStamp);
        hashMap.put("pLiked","0");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Forums");
        ref.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getContext(), "forum published", Toast.LENGTH_SHORT).show();
                pTitleEt.setText("");
                pDescriptionEt.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            email = user.getEmail();
            uid = user.getUid();
        }else {

        }
    }
}
