package com.app.eynav.ui.forum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.app.eynav.databinding.FragmentForumBinding;
import com.app.eynav.ui.add.AddNewMeet;
import com.app.eynav.ui.add.MeetAdapter;
import com.app.eynav.ui.add.MeetInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment {

    private FragmentForumBinding binding;
    RecyclerView rvForum;
    FloatingActionButton fbAddForum;
    ArrayList<ModeForum> modeForums = new ArrayList<>();
    String userType = "";
    List<ModeForum> modeForumList = new ArrayList<>();
    ForumAdapter forumAdapter;
    public ForumFragment(String userType) {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        rvForum = view.findViewById(R.id.rvForum);
        fbAddForum = view.findViewById(R.id.fbAddForum);
        ForumAdapter meetsAdapter = new ForumAdapter(modeForums,userType,getContext());
        rvForum.setAdapter(meetsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager rvLiLinayoutManager = layoutManager;
        rvForum.setLayoutManager(rvLiLinayoutManager);
        loadPosts();

        fbAddForum.setOnClickListener(l ->{
            Bundle bundle1 = new Bundle();
            Fragment fragment1 = new AddForumTopic();
            fragment1.setArguments(bundle1);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment1).addToBackStack(null).commit();
        });

        return view;
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
                    String like = (String) ds.child("pLiked").getValue();

                    ModeForum modeForum = new ModeForum(pId,pTitle,pDescriptionv,"",pTime,uid, uEmail,"", uName,like);
                    modeForumList.add(modeForum);
                    forumAdapter = new ForumAdapter(modeForumList,"", getActivity());
                    rvForum.setAdapter(forumAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
