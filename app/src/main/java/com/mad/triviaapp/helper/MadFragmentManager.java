package com.mad.triviaapp.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mad.triviaapp.R;

import java.util.HashMap;

public class MadFragmentManager {

    private Context context;

    private FragmentManager fm;

    public MadFragmentManager(Context context) {
        this.context = context;
        fm = ((AppCompatActivity) context).getSupportFragmentManager();
    }

    public MadFragmentManager(Fragment fragment) {
        fm = fragment.getFragmentManager();
    }

    public FragmentManager getFragmentManager() {
        return fm;
    }

    public FragmentTransaction getTransaction() {
        return fm.beginTransaction();
    }

    private void handleFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment, boolean popLastFragment) {

        FragmentTransaction ft = fm.beginTransaction();

        if (popLastFragment) {
            fm.popBackStack();
        }

        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(containerViewId, fragment, fragment.getTag())
                .addToBackStack(fragment.getTag())
                .commit();
    }

    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment) {

        handleFragment(containerViewId, fragment, true);
    }

    public void removeAllFragments() {
        fm = ((AppCompatActivity) context).getSupportFragmentManager();

        while (fm.getBackStackEntryCount() != 0) {
            fm.popBackStackImmediate();
        }
    }

    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment, View sharedView) {

        fm = ((AppCompatActivity) context).getSupportFragmentManager();

        if (fm.getBackStackEntryCount() == 0) {
            addFragment(containerViewId, fragment);
        }

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(containerViewId, fragment, fragment.getTag())
                .addToBackStack(fragment.getTag())
                .addSharedElement(sharedView, sharedView.getTransitionName())
                .commit();
    }

    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment, HashMap<String, View> sharedViews) {

        fm = ((AppCompatActivity) context).getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        ft = ft.replace(containerViewId, fragment, fragment.getTag())
                .addToBackStack(fragment.getTag());

        for (String name : sharedViews.keySet()) {
            View view = sharedViews.get(name);
            if (view != null) {
                Log.d("*** TRANS %s", name);
                ft = ft.addSharedElement(view, name);
            }
        }
        ft.commit();
    }

    public void pushFragment(@IdRes int containerViewId,
                             @NonNull Fragment fragment) {
        handleFragment(containerViewId, fragment, false);
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment) {
        fm.beginTransaction()
                .add(containerViewId, fragment, fragment.getTag())
                .disallowAddToBackStack()
                .commit();
    }
}
