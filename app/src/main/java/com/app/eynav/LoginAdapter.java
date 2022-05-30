package com.app.eynav;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    String userType;
    public LoginAdapter(@NonNull FragmentManager fm, Context context, int totalTabs, String userType) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.userType = userType;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment(this.userType);
                return loginTabFragment;
            case 1:
                SignupTabFragment signupTabFragment = new SignupTabFragment(this.userType);
                return signupTabFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
