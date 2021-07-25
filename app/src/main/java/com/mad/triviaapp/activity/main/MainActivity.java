package com.mad.triviaapp.activity.main;

import androidx.fragment.app.Fragment;

import com.mad.triviaapp.activity.base.BaseActivity;
import com.mad.triviaapp.fragment.UserInfoFragment;

public class MainActivity extends BaseActivity {
    // load user info fragment one activity created.
    @Override
    protected Fragment getHomeFragment() {
        return UserInfoFragment.newInstance();
    }
}
