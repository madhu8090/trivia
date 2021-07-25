package com.mad.triviaapp.helper;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProgressBarHandler {

    private ProgressBar mProgressBar;

    public ProgressBarHandler(Activity activity) {
        Context mContext = activity.getApplicationContext();
        ViewGroup layout = (ViewGroup) (activity.findViewById(android.R.id.content).getRootView());
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyle);
        mProgressBar.setIndeterminate(true);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(mContext);

        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);

        layout.addView(rl, params);

        hide();
    }

    public ProgressBarHandler(Context context) {

        ViewGroup rootView = (ViewGroup) ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content).getRootView();


        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyle);
        mProgressBar.setIndeterminate(true);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(context);

        rl.setGravity(Gravity.CENTER);
        rl.addView(mProgressBar);

        rootView.addView(rl, params);

        hide();
    }


    public void show(TextView mEmptyListView) {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyListView.setVisibility(View.VISIBLE);
        mEmptyListView.setText("Loading...");
    }

    public void hide(TextView mEmptyListView, int size, String message) {

        mProgressBar.setVisibility(View.INVISIBLE);
        switch (size) {
            case 0:
                mEmptyListView.setVisibility(View.VISIBLE);
                mEmptyListView.setText("No " + message + " found");
                break;
            case -1:
                mEmptyListView.setVisibility(View.VISIBLE);
                mEmptyListView.setText("Error loading " + message + " or no " + message + " found");
                break;
            default:
                mEmptyListView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
        }
    }

    public void show() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mProgressBar.setVisibility(View.GONE);
    }
}