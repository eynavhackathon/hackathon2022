package com.app.eynav;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Calendar;
import java.util.Collections;

public class SignupTabFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    EditText email,pass,full_name,origin_country,year_immigration,hobbies;
    TextView lang;
    Button btn_signup;
    AppCompatButton btnDateBo;
    DatePickerDialog datePickerDialog;
    City chooseCity;
    Spinner spino;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    //    Spinner   sYear,sMo,sDay;
    float v = 0;
    String[]  langArray  = { "Afar","Abkhaz","Avestan","Afrikaans","Akan","Amharic","Aragonese","Arabic","Assamese"
            ,"Avaric","Aymara","Azerbaijani","South Azerbaijani","Bashkir","Belarusian","Bulgarian","Bihari"
            ,"Bislama","Bambara","Bengali","Tibetan","Breton","Bosnian","Catalan","Chechen","Chamorro","Corsican"
            ,"Cree","Czech","Chuvash","Welsh","Danish","German","Maldivian","Dzongkha","Ewe","Greek","English"
            ,"Esperanto","Spanish","Estonian","Basque","Persian","Fula","Finnish","Fijian","Faroese","French"
            ,"Western Frisian","Irish","Gaelic","Galician","Guara","Gujarati","Manx","Hausa","Hebrew","Hindi"
            ,"Hiri Motu","Croatian","Haitian","Hungarian","Armenian","Herero","Interlingua","Indonesian","Interlingue"
            ,"Igbo","Nuosu","Inupiaq","Ido","Icelandic","Italian","Inuktitut","Japanese","Javanese","Georgian"
            ,"Kongo","Kikuyu","Kwanyama","Kazakh","Kalaallisu","Khmer","Kannada","Korean","Kanuri","Kashmiri"
            ,"Kurdish","Komi","Cornish","Kyrgyz","Latin","Luxembourgish","Ganda","Limburgish","Lingala","Lao"
            ,"Lithuanian","Luba-Katanga", "Latvian","Malagasy","Marshallese","Macedonian","Malayalam","Mongolian"
            ,"Marathi","Malay","Maltese", "Burmese","Nauru","Norwegian","North Ndebele","Nepali","Ndonga","Dutch"
            ,"Norwegian Nynorsk","Norwegian","South Ndebele","Navajo","Chichewa", "Occitan","Ojibwe", "Oromo"
            ,"Oriya","Ossetian","Panjabi","Polish","Pashto","Portuguese","Quechua","Romansh","Kirundi","Romanian","Russian","Kinyarwanda","Sanskrit","Sardinian","Sindhi"
            ,"Northern Sami","Sango","Sinhala","Slovak","Slovene","Samoan","Shona","Somali","Albanian","Serbian"
            ,"Swati","Southern Sotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tigrinya"
            ,"Turkmen","Tagalog","Tswana","Tonga","Turkish","Tsonga","Tatar","Twi","Tahitian","Uyghur","Ukrainian","Urdu"
            ,"Uzbek","Venda","Vietnamese","Volap","Walloon","Wolof","Xhosa","Yiddish","Yoruba","Zhuang","Chinese","Zulu"
    };
    boolean [] selectedLang;
    ArrayList<Integer> langList = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    ArrayList<City> cities = new ArrayList<>();

    String userType;
    public SignupTabFragment(String userType){
        this.userType = userType;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tap_fragment, container, false);
        email= root.findViewById(R.id.email);
        pass= root.findViewById(R.id.pass);
        courses.add("city");
        full_name= root.findViewById(R.id.full_name);
        origin_country= root.findViewById(R.id.origin_country);
        year_immigration= root.findViewById(R.id.year_immigration);
        hobbies = root.findViewById(R.id.hobbies);
        if (userType.equals("new_immigrant")){
            origin_country.setVisibility(View.VISIBLE);
            year_immigration.setVisibility(View.VISIBLE);
        }
        if (userType.equals("native_b") || userType.equals("volunteer")){
            origin_country.setVisibility(View.GONE);
            year_immigration.setVisibility(View.GONE);
        }

        btn_signup= root.findViewById(R.id.btn_signup);
        lang = root.findViewById(R.id.lang);
        email.setTranslationX(800);
        email.setAlpha(v);
        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.setTranslationX(800);
        pass.setAlpha(v);
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn_signup.setTranslationX(800);
        btn_signup.setAlpha(v);
        btn_signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        btnDateBo = root.findViewById(R.id.btnDateBo);
         spino = root.findViewById(R.id.city);
        spino.setOnItemSelectedListener(this);
        ArrayAdapter ad= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Registering user...");
        btn_signup.setOnClickListener(l ->{
            String email1 = email.getText().toString().trim();
            String password = pass.getText().toString().trim();
            if (userType.equals("new_immigrant")){
                System.out.println(origin_country.getText());
                System.out.println(year_immigration.getText());
            }
            ArrayList<String>langChoose = new ArrayList<>();
            for (int i = 0; i < langList.size(); i++) {
                langChoose.add(langArray[langList.get(i)]);
                System.out.println(langArray[langList.get(i)]);
            }
            System.out.println(btnDateBo.getText());
            if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                //set error amd focus to email edit text
                email.setError("Invalid Email");
                email.setFocusable(true);
            }
            else if (password.length()<6){
                //set error amd focus to password edit text
                pass.setError("Password length at least 6 characters");
                pass.setFocusable(true);
            }
            else{
                if (userType.equals("new_immigrant")){
                    System.out.println(origin_country.getText());
                    System.out.println(year_immigration.getText());
                    registerUser(email1, password,chooseCity,full_name.getText().toString(),langChoose,btnDateBo.getText().toString(),userType,origin_country.getText().toString(),year_immigration.getText().toString(),hobbies.getText().toString());

                }else {
                    registerUser(email1, password,chooseCity,full_name.getText().toString(),langChoose,btnDateBo.getText().toString(),userType,"","",hobbies.getText().toString());

                }
            }

        });

        btnDateBo.setText(getDateBo());
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + " " + getMonthFormat(month) + " " + year;
                btnDateBo.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        btnDateBo.setOnClickListener(l->{
            datePickerDialog.show();
        });


        selectedLang = new boolean[langArray.length];
        lang.setOnClickListener(l->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select Lang");
            builder.setCancelable(false);
            builder.setMultiChoiceItems(langArray, selectedLang, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    if (b){
                        langList.add(i);
                        Collections.sort(langList);
                    }else {
                        langList.removeIf( name -> name.equals(i));

                    }
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < langList.size(); j++) {
                        stringBuilder.append(langArray[langList.get(j)]);
                        if (j != langList.size()-1){
                            stringBuilder.append(", ");
                        }
                    }
                    lang.setText(stringBuilder.toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for (int j = 0; j < selectedLang.length; j++) {
                        selectedLang[j] = false;
                        langList.clear();
                        lang.setText("");
                    }
                }
            });
            builder.show();
        });



        new Thread(new Runnable() {
            public void run() {
                try{
                    String url = "https://firebasestorage.googleapis.com/v0/b/hackathon2022-b2c47.appspot.com/o/cities.json?alt=media&token=8920722e-30f9-405c-ba02-1d1ab7e02e2f";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Call call = client.newCall(request);
                    try (Response response = call.execute()) {
                        if (response.body() == null) System.out.println("null");
                        String json = response.body().string();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject cityObject = jsonArray.getJSONObject(i);
                            int id = cityObject.getInt("סמל_ישוב");
                            String name = cityObject.getString("שם_ישוב");
                            String nameEng = cityObject.getString("שם_ישוב_לועזי");
                            int regionInt = cityObject.getInt("סמל_לשכת_מנא");
                            String region = cityObject.getString("לשכה");
                            if (nameEng.length() > 2){
                                courses.add(nameEng);
                                City cityJson = new City(id,name,nameEng,regionInt,region);
                                cities.add(cityJson);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

        return root;
    }

    private void registerUser(String email, String password, City chooseCity, String full_name, ArrayList<String> langChoose, String btnDateBo, String userType, String origin_country, String year_immigration, String hobbies) {
            //email and password is valid, show progress dialog and start registering user
        System.out.println(getActivity());
        progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            System.out.println("jnkjsjbkhdsd");
                            if (task.isSuccessful()) {
                                // Sign in success, dismiss dialog and tart register activity
                                progressDialog.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();
                                //get user email and id from auth
                                String email = user.getEmail();
                                String uid = user.getUid();
                                //when user is registered store user info in firebase realtime database too using hashmap
                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("email", email);
                                hashMap.put("uid", uid);
                                hashMap.put("name", full_name);//will add later
                                hashMap.put("DateBo", btnDateBo);
                                hashMap.put("userType", userType);
                                for (int i = 0; i < langChoose.size(); i++) {
                                    hashMap.put("lang"+String.valueOf(i+1), langChoose.get(i));
                                }
                                hashMap.put("cityRegion", chooseCity.getRegion());
                                hashMap.put("cityName", chooseCity.getName());
                                hashMap.put("cityNameEng", chooseCity.getNameEng());
                                hashMap.put("origin_country", origin_country);
                                hashMap.put("hobbies", hobbies);
                                hashMap.put("year_immigration", year_immigration);
                                hashMap.put("phone", "");//will add later
                                hashMap.put("image", "");//will add later
                                hashMap.put("cover", "");//will add later
                                //firebase database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //path to store user data named "users"
                                DatabaseReference reference = database.getReference("Users");
                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);

                                Toast.makeText(getContext(), "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                                Intent intent = new Intent(getContext(),MainActivity.class);
                                intent.putExtra("userType",userType);
                                startActivity(intent);
//                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //error, dismiss progress dialog and get and show the error massage
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    private String getDateBo() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return (day + " " + getMonthFormat(month) + " " + year);
    }

    private String getMonthFormat(int month) {
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0){
            System.out.println(cities.get(i-1).getRegion());
            chooseCity = cities.get(i-1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
