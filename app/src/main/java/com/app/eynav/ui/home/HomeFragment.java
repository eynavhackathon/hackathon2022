package com.app.eynav.ui.home;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.eynav.R;
import com.app.eynav.databinding.FragmentHomeBinding;
import com.app.eynav.ui.add.Meet;
import com.app.eynav.ui.add.MeetAdapter;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //storage
    StorageReference storageReference;
    //path where image of user and cover will be stored
    String storagePath = "Users_Profile_Cover_Imgs/";

    //view from xml
    ImageView avatarIv, coverIv;
    TextView nameTv, emailTv, hobbiesTv;
    FloatingActionButton fab;
    int count = 0;

    //progress dialog
    ProgressDialog pd;
    private FirebaseAuth mAuth;

    //permission constance
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    //array of permission to be requested
    String cameraPermission[];
    String storagePermission[];
    RecyclerView rvMeet;
    //uri of picked image
    Uri image_uri;
    String userType;

    //for chacking profile or cover photo
    String profileOrCoverPhoto;
    public HomeFragment(String userType) {
        this.userType = userType;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


//init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference(); // firebase storage reference

        //init arrays of permissions
        cameraPermission = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //init views
        avatarIv = view.findViewById(R.id.avatarIv);
        coverIv = view.findViewById(R.id.coverIv);
        nameTv = view.findViewById(R.id.nameTv);
        emailTv = view.findViewById(R.id.emailTv);
        hobbiesTv = view.findViewById(R.id.hobbiesTv);
        fab = view.findViewById(R.id.fab);
        rvMeet = view.findViewById(R.id.rvMeetSelect);
        //init progress dialog
        pd = new ProgressDialog(getActivity());
        setMeets();
//        MeetChooseAdapter meetsAdapter = new MeetChooseAdapter(meets,userType,getContext());
//        rvMeet.setAdapter(meetsAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        RecyclerView.LayoutManager rvLiLinayoutManager = layoutManager;
//        rvMeet.setLayoutManager(rvLiLinayoutManager);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapShot) {

                //check until required data get
                for(DataSnapshot ds: dataSnapShot.getChildren()){
                    //get data
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String hobbies = "" + ds.child("hobbies").getValue();
                    String image = "" + ds.child("image").getValue();
                    String cover = "" + ds.child("cover").getValue();

                    //set data
                    nameTv.setText(name);
                    emailTv.setText(email);
                    hobbiesTv.setText(hobbies);
                    try{
                        //if image is received then set
                        Picasso.get().load(image).into(avatarIv);
                    }
                    catch(Exception e){
                        //if there is any exception while getting image then set default
//                        Picasso.get().load(R.drawable.ic_baseline_person_24).into(avatarIv);
                    }
                    try{
                        //if image is received then set
                        Picasso.get().load(cover).into(coverIv);
                    }
                    catch(Exception e){
                        //if there is any exception while getting image then set default
//                        Picasso.get().load(R.drawable.ic_default_imp_white).into(coverIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //fab button click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });





        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean checkStoragePermission(){
        //check if storage permission is enabled or not
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    } //check - good

    private void requestStoragePermission(){
        //request runtime storage permission
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    } //check - good


    private boolean checkCameraPermission(){
        //check if camera permission is enabled or not
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    } //check - good

    private void requestCameraPermission(){
        //request runtime storage permission
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    } //check - good


    private void showEditProfileDialog() {

        //option to show in dialog
        String options[] = {"Edit Profile Picture", "Edit Cover Photo", "Edit Name", "Edit Phone"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle("Choose Action");
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item click
                if(which == 0){
                    //Edit Profile clicked
                    pd.setMessage("Updating Profile Picture");
                    profileOrCoverPhoto = "image";
                    showImagePicDialog();
                }
                else if (which == 1){
                    //Edit Cover clicked
                    pd.setMessage("Updating Cover Photo");
                    profileOrCoverPhoto = "cover";
                    showImagePicDialog();
                }
                else if (which == 2){
                    //Edit Name clicked
                    pd.setMessage("Updating Name");
                    showNamePhoneUpdateDialog("name");
                }
                else if (which == 3){
                    //Edit Phone clicked
                    pd.setMessage("Updating hobbies");
                    showNamePhoneUpdateDialog("hobbies");

                }
            }
        });
        //create and show dialog
        builder.create().show();

    } //check - good

    private void showNamePhoneUpdateDialog(String key) {
        //key = name: user update name
        //key = phone: user update phone number

        //custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update " + key);
        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);
        //add edit text
        EditText editText = new EditText(getActivity());
        editText.setHint("Enter " + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //add button in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from edit text
                String value = editText.getText().toString().trim();
                //validate if user has entered something or not
                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //updated, dismiss progress
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Updated...", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //failed, dismiss progress, get and show error massage
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                else{
                    Toast.makeText(getActivity(), "Please enter " + key, Toast.LENGTH_SHORT).show();
                }

            }
        });
        //add button in dialog to cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //create and show dialog
        builder.create().show();
    } //check - good

    private void showImagePicDialog() {
        //show dialog containing options camera and gallery to pick the image
        String options[] = {"Camera", "Gallery"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle("Pick Image From");
        //set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item click
                if(which == 0){
                    //Camera clicked
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        pickFromCamera();
                    }
                }
                else if (which == 1){
                    //gallery clicked
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        pickFromGallery();
                    }
                }
            }
        });
        //create and show dialog
        builder.create().show();
    } //check - good

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //This method called when user press allow or deny from permission request dialog
        //here will handle permission cases
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                //picking from camera
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        //permission enabled
                        pickFromCamera();
                    } else {
                        //permission denied
                        Toast.makeText(getActivity(), "Please enable camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                //picking from gallery
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        //permission enabled
                        pickFromGallery();
                    } else {
                        //permission denied
                        Toast.makeText(getActivity(), "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
    //check - good

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //this method will be called after picking image from camera or gallery
        if (resultCode == RESULT_OK){

            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //image is picked from gallery, get uri from image
                image_uri = data.getData();

                uploadProfileCoverPhoto(image_uri);

            }
            if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //image is picked from camera, get uri from image
                uploadProfileCoverPhoto(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    } //check - good

    private void uploadProfileCoverPhoto(Uri uri) {
        //show progress
        pd.show();

        //path and name of image to be stored in firebase storage
        String filePathAndName = storagePath + "" + profileOrCoverPhoto + "_" + user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //image is uploaded to storage, now get it's url and store in user database
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        //check if image is uploaded or not
                        if(uriTask.isSuccessful()){
                            //image uploaded
                            //add/update url in user database
                            HashMap<String, Object> results = new HashMap<>();
                            results.put(profileOrCoverPhoto, downloadUri.toString());

                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //url in database of user is added successfully
                                            //dismiss progress bar
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), "Image Updated..." , Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //error adding url in database of user
                                            //dismiss progress bar
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), "Error Updating Image..." , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else{
                            //error
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //there are some error, get and show error message, dismiss progress dialog
                        pd.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    } //check - good

    private void pickFromCamera() {
        //intent of picking image from device camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp pick");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //put image uri
        image_uri  = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    } //check - good

    private void pickFromGallery() {
        //pick from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    } //check - good

    private void setMeets() {
        List<Meet> meets = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser userN = mAuth.getCurrentUser();
        String uid = userN.getUid();
        db.collection("meets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String dateMeet = (String) document.getData().get("dateMeet");
                                String typeMeet = (String) document.getData().get("typeMeet");
                                String timeMeet = (String) document.getData().get("timeMeet");
                                String placeMeet = (String) document.getData().get("placeMeet");
                                Long countNewImmigrant = (Long) document.getData().get("countNewImmigrant");
                                Long countNativeB = (Long) document.getData().get("countNativeB");
                                Long countVolunteer = (Long) document.getData().get("countVolunteer");
                                String placeNameEng = (String) document.getData().get("placeNameEng");
                                Double placeLatitude = (Double) document.getData().get("placeLatitude");
                                Double placeLongitude = (Double) document.getData().get("placeLongitude");
                                String cityRegionMeet = (String) document.getData().get("cityRegion");
                                String yearBornMeet = (String) document.getData().get("yearBorn");
                                String languageMeet = (String) document.getData().get("languageMeet");
                                Boolean joinNatives = (Boolean) document.getData().get("joinNatives");
                                int countNewImmigrant1 = Math.toIntExact(countNewImmigrant);
                                System.out.println("+++++++ countNewImmigrant1 "+countNewImmigrant1);
                                int countNativeB1 = Math.toIntExact(countNativeB);
                                int countVolunteer1 = Math.toIntExact(countVolunteer);
                                System.out.println("+++++++ countVolunteer1 "+countVolunteer1);

                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
//                                int month = calendar.get(Calendar.MONTH);
//                                month = month + 1;
                                String[] k = dateMeet.split(" ");
                                String[] time = timeMeet.split(":");
                                int month = 1;
                                switch (k[1]) {
                                    case "JAN":
                                        month = 1;
                                        break;
                                    case "FEB":
                                        month = 2;
                                        break;
                                    case "MAR":
                                        month = 3;
                                        break;
                                    case "APR":
                                        month = 4;
                                        break;
                                    case "MAY":
                                        month = 5;
                                        break;
                                    case "JUN":
                                        month = 6;
                                        break;
                                    case "JUL":
                                        month = 7;
                                        break;
                                    case "AUG":
                                        month = 8;
                                        break;
                                    case "SEP":
                                        month = 9;
                                        break;
                                    case "OCT":
                                        month = 10;
                                        break;
                                    case "NOV":
                                        month = 11;
                                        break;
                                    case "DEC":
                                        month = 12;
                                        break;
                                    default:
                                        month = 1;
                                        break;
                                }
                                month = month - 1;
                                calendar.set(Calendar.YEAR, Integer.parseInt(k[2]));
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(k[0]));
                                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                                calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
                                Date dateM = calendar.getTime();
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, 0);
                                c.set(Calendar.MINUTE, 0);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                Date today = c.getTime();
                                if ((calendar.getTime().after(today))) {

                                    db.collection("meets").document(document.getId()).collection("users")
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                    if (task1.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                            Log.d(TAG, document1.getId() + " => " + document1.getData());
                                                            System.out.println("____________ " + uid);
                                                            if (uid.equals(document1.getId())) {
                                                                Meet meet = new Meet(typeMeet, dateMeet, timeMeet, placeMeet, countNewImmigrant1, countNativeB1, countVolunteer1, dateM, placeNameEng, placeLatitude, placeLongitude, "", "", languageMeet);
                                                                System.out.println(meet);
                                                                meets.add(meet);

                                                            }
                                                        }
                                                        if (task.getResult().size() == count) {
                                                            Log.e("meets", "meets");
                                                            Collections.sort(meets);
                                                            System.out.println(meets);
                                                            for (int i = 0; i < meets.size(); i++) {
                                                                System.out.println(meets.get(i));
                                                            }
                                                            MeetChooseAdapter meetsAdapter = new MeetChooseAdapter(meets, userType, getContext());
                                                            rvMeet.setAdapter(meetsAdapter);

                                                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                                            RecyclerView.LayoutManager rvLiLinayoutManager = layoutManager;
                                                            rvMeet.setLayoutManager(rvLiLinayoutManager);

                                                        }
                                                    }
                                                }

                                            });

                                }
                            }
                             } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }


                    }


                });
    }
}