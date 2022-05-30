package com.app.eynav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {
    String userType;
    TextView tvUserType,tvWelcomeUserType;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        userType = getIntent().getStringExtra("userType");

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount(),userType);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        if (userType.equals("new_immigrant")){
            tvUserType.setText(R.string.new_immigrant);
            tvWelcomeUserType.setText(R.string.welcome_new_immigrant);
        }
        if (userType.equals("native_b")){
            tvUserType.setText(R.string.native_b);
            tvWelcomeUserType.setText(R.string.welcome_native_b);
        }
        if (userType.equals("volunteer")){
            tvUserType.setText(R.string.volunteer);
            tvWelcomeUserType.setText(R.string.welcome_volunteer);
        }
    }
    private void findView() {
        tvUserType = findViewById(R.id.tvUserType);
        tvWelcomeUserType = findViewById(R.id.tvWelcomeUserType);
        tabLayout= findViewById(R.id.tab_layout);
        viewPager= findViewById(R.id.view_pager);
        float v = 0;
        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
    }
}