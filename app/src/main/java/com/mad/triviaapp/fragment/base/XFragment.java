package com.mad.triviaapp.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;


import com.mad.triviaapp.helper.MadFragmentManager;
import com.mad.triviaapp.helper.ProgressBarHandler;

import io.reactivex.disposables.Disposable;

public abstract class XFragment extends Fragment {

    protected MadFragmentManager mFragmentManager;
    protected ProgressBarHandler mProgressBar;
    protected Disposable mDisposable;

    protected abstract String getTitle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressBar = new ProgressBarHandler(getActivity());
        mFragmentManager = new MadFragmentManager(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        setTitle();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
        hideKeyboard();
    }

    private void setTitle() {
        String title = getTitle();
        if (title != null) {
            (getActivity()).setTitle(title);
        }
    }

    public void setTitle(String title) {
        (getActivity()).setTitle(title);
    }

    protected void finish() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    protected void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        if (mProgressBar != null) {
            mProgressBar.hide();
        }
    }
}
