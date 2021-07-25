package com.mad.triviaapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mad.triviaapp.R;
import com.mad.triviaapp.adapter.MultipleChoiceQuestionAdapter;
import com.mad.triviaapp.adapter.SingleChoiceQuestionAdapter;
import com.mad.triviaapp.databinding.FragmentQuestionBinding;
import com.mad.triviaapp.fragment.base.BaseFragment;
import com.mad.triviaapp.model.Constants;
import com.mad.triviaapp.model.History;
import com.mad.triviaapp.model.Question;
import com.mad.triviaapp.model.Questionnaire;
import com.mad.triviaapp.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FirstQuestionFragment extends BaseFragment {

    FragmentQuestionBinding mBinding;
    private static String FRAGMENT_USERNAME = "FRAGMENT_USERNAME";
    private static String FRAGMENT_QUESTION = "FRAGMENT_QUESTION";
    private List<Questionnaire> mQuestionnaireList;
    private String mUserName;
    private String mResponse;
    private History mHistory;
    private Boolean isResponseCollected = false;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_question;
    }

    @Override
    protected String getTitle() {
        return "Question-1";
    }

    //entrypoint for fragment
    public static FirstQuestionFragment newInstance(List<Questionnaire> questionnaireList, String userName) {
        FirstQuestionFragment fragment = new FirstQuestionFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_QUESTION, questionnaireList.toString());
        args.putString(FRAGMENT_USERNAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    // build the questionnaire object and username colleceted from previous screen
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type listType = new TypeToken<ArrayList<Questionnaire>>() {
        }.getType();
        mQuestionnaireList = new Gson().fromJson(getArguments().getString(FRAGMENT_QUESTION), listType);
        mUserName = getArguments().getString(FRAGMENT_USERNAME);
        Log.d("USername ", mUserName);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentQuestionBinding.inflate(inflater, container, false);

        showDetails(mQuestionnaireList.get(0));
        mHistory = new History();
        mHistory.setUserName(mUserName);
        loadRecyclerView();

        /***
         * check if the response is submitted by the user.
         * if yes move to next fragment
         * else prompt message to user.
         */
        mBinding.buttonContinue.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mResponse)) {
                Toast.makeText(getContext(), "Select one option and Try again", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isResponseCollected) {
                saveResponse(mQuestionnaireList.get(0), mResponse);
            }
            loadAndReplaceFragment(SecondQuestionFragment.newInstance(mQuestionnaireList, mHistory));
        });
        return mBinding.getRoot();
    }

    // show question details
    private void showDetails(Questionnaire question) {
        mBinding.question.setText(question.getQuestion().getQuestion_text());

        if (question.getQuestion().getQuestion_type().equals(Constants.QType.SINGLE)) {
            loadSingleChoiceOptions(question);
        } else {
            loadMultipleChoiceOptions(question);
        }
    }

    // if the question is multiple choice question load this method
    private void loadMultipleChoiceOptions(Questionnaire question) {
        MultipleChoiceQuestionAdapter adapter = new MultipleChoiceQuestionAdapter(question.getOptions(),
                optionIds -> {
                    if (optionIds.size() > 0) {
                        mResponse = TextUtils.join(",", optionIds);
                    }
                });
        mBinding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        updateAdapterView(adapter, mBinding.recyclerView);
    }

    // if the question is single choice question load this method
    private void loadSingleChoiceOptions(Questionnaire question) {
        SingleChoiceQuestionAdapter adapter = new SingleChoiceQuestionAdapter(getContext(),
                question.getOptions(), optionId -> {
            mResponse = optionId;
        });
        mBinding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        updateAdapterView(adapter, mBinding.recyclerView);
    }

    // store the user response in history object
    private void saveResponse(Questionnaire question, String answer) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(question.getQuestion().getQuestion_text(), answer));
        mHistory.setQuestions(questions);
        isResponseCollected = true;
    }

    // load recycler view
    private void loadRecyclerView() {
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    // update adapter if any changes take place
    private <T extends RecyclerView.ViewHolder> void updateAdapterView(
            RecyclerView.Adapter<T> Adapter, RecyclerView recyclerView) {
        if (Adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

}
