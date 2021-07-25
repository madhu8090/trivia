package com.mad.triviaapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.mad.triviaapp.R;
import com.mad.triviaapp.databinding.FragmentUserInfoBinding;
import com.mad.triviaapp.fragment.base.BaseFragment;
import com.mad.triviaapp.model.Option;
import com.mad.triviaapp.model.Questionnaire;
import com.mad.triviaapp.model.Questions;
import com.mad.triviaapp.model.User;
import com.mad.triviaapp.viewmodel.QuestionViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserInfoFragment extends BaseFragment {

    // viewbinding for the fragment layout
    FragmentUserInfoBinding binding;
    // view model for fetching questions from database
    QuestionViewModel mQuestionViewModel;

    private List<Questions> mQuestions;
    private List<Option> mOptions;

    // entry to fragment.
    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false);
        mQuestionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        binding.etUserName.getText().clear();
        binding.buttonContinue.setOnClickListener(v -> {
            if (validate()) {
                storeUserName();
            }
        });

        return binding.getRoot();
    }

    // store username in user object. and fetch questions and options from db.
    private void storeUserName() {
        User user = new User(binding.etUserName.getText().toString());

        mQuestionViewModel.getQuestions().observe(getViewLifecycleOwner(), questions -> {
            mQuestions = questions;
            buildQuestionnaire(user);
        });

        mQuestionViewModel.getOptions().observe(getViewLifecycleOwner(), options -> {
            mOptions = options;
            buildQuestionnaire(user);
        });

    }

    // build questionnaire before moving to next fragment
    private void buildQuestionnaire(User user) {
        if (mQuestions != null && mOptions != null) {
            List<Questionnaire> questionnaires = new ArrayList<>();
            for (Questions question : mQuestions) {
                Questionnaire questionnaire = getQuestionnaire(question);
                questionnaires.add(questionnaire);
            }
            loadAndReplaceFragment(FirstQuestionFragment.newInstance(questionnaires, user.getName()));
        }
    }

    // map options to respection question and return the questionnaire
    private Questionnaire getQuestionnaire(Questions question) {
        List<Option> options = getOptionsForQuestion(question.getId());
        return new Questionnaire(question, options);
    }

    // get the options for given question id.
    private List<Option> getOptionsForQuestion(int id) {
        List<Option> options = new ArrayList<>();
        if (mOptions != null) {
            for (Option option : mOptions) {
                if (option.getQuestion_id() == id) {
                    options.add(option);
                }
            }
        }
        return options;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected String getTitle() {
        return "User Info";
    }

    // validate if the input fields are empty or not.
    @Override
    protected boolean validate() {
        boolean valid = true;
        String userName = binding.etUserName.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            binding.etUserName.setError("Enter your name here");
            valid = false;
        }

        return valid;
    }
}
