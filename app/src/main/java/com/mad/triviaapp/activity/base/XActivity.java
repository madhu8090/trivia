package com.mad.triviaapp.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.mad.triviaapp.helper.MadFragmentManager;
import com.mad.triviaapp.helper.ProgressBarHandler;

import io.reactivex.disposables.Disposable;

public abstract class XActivity extends AppCompatActivity {

    protected MadFragmentManager mFragmentManager;
    protected ProgressBarHandler mProgressBar;
    protected Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressBar = new ProgressBarHandler(this);
        mFragmentManager = new MadFragmentManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard();
    }

    void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        if (mProgressBar != null) {
            mProgressBar.hide();
        }
    }
}
