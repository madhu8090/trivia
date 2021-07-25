package com.mad.triviaapp.activity.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mad.triviaapp.R;

public abstract class BaseActivity extends XActivity {

    protected abstract Fragment getHomeFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = getHomeFragment();
        mFragmentManager.addFragment(R.id.content_frame, fragment);
    }

}
