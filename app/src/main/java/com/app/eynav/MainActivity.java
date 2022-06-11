package com.app.eynav;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.eynav.databinding.ActivityMainBinding;
import com.app.eynav.ui.add.AddFragment;
import com.app.eynav.ui.chat.ChatFragment;
import com.app.eynav.ui.forum.ForumFragment;
import com.app.eynav.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ActivityMainBinding binding;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navigation = findViewById(R.id.nav_view);

        navigation.setOnNavigationItemSelectedListener(this);
        userType = getIntent().getStringExtra("userType");
        FragmentManager fm;

        fm = getFragmentManager();

        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, new HomeFragment(userType)).addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                System.out.println("navigation_home");
                fm = getFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new HomeFragment(userType)).addToBackStack(null)
                        .commit();
                return true;
            case R.id.navigation_add:
                System.out.println("navigation_add");
                fm = getFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new AddFragment(userType)).addToBackStack(null)
                        .commit();
                return true;
            case R.id.navigation_chat:
                System.out.println("navigation_chat");
                fm = getFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new ChatFragment(userType)).addToBackStack(null)
                        .commit();
                return true;
            case R.id.navigation_forum:
                System.out.println("navigation_forum");
                fm = getFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
                    fm.popBackStack();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new ForumFragment(userType)).addToBackStack(null)
                        .commit();
                return true;
        }
        return false;
    }
}
