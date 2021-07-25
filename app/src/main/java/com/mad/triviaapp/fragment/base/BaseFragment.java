package com.mad.triviaapp.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mad.triviaapp.R;
import com.mad.triviaapp.fragment.HistoryFragment;

public abstract class BaseFragment extends XFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        setHasOptionsMenu(true);
        parseFragmentArgs(getArguments());
        return view;
    }


    protected void onFragmentCreated(Bundle arguments) {
    }

    protected void parseFragmentArgs(Bundle arguments) {
    }

    protected void loadFragment(Fragment fragment) {
        mFragmentManager.pushFragment(R.id.content_frame, fragment);
    }

    protected void loadAndReplaceFragment(Fragment fragment) {
        mFragmentManager.replaceFragment(R.id.content_frame, fragment);
    }

    protected abstract int getFragmentLayout();

    protected abstract String getTitle();

    protected boolean validate() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                loadFragment(HistoryFragment.newInstance());
        }
        return true;
    }

}
