package com.mad.triviaapp.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mad.triviaapp.R;
import com.mad.triviaapp.activity.main.MainActivity;
import com.mad.triviaapp.viewmodel.HistoryViewModel;
import com.mad.triviaapp.viewmodel.QuestionViewModel;

public class SplashActivity extends AppCompatActivity {

    QuestionViewModel mViewModel;
    private boolean isOptionsAvailable;
    private boolean isQuestionsAvailable;
    private boolean isPostDelayCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(QuestionViewModel.class);

        // collect questions
        mViewModel.getQuestions().observe(this, questions -> {
            isQuestionsAvailable = true;
            moveToMainActivity();
        });

        // collect options for questions
        mViewModel.getOptions().observe(this, options -> {
            isOptionsAvailable = true;
            moveToMainActivity();
        });

        // post delay 3ms
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            isPostDelayCompleted = true;
            moveToMainActivity();
        }, 3000);
    }

    // move to next activity after splash animation
    private void moveToMainActivity() {
        if (isQuestionsAvailable && isOptionsAvailable && isPostDelayCompleted) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
