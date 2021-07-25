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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SecondQuestionFragment extends BaseFragment {

    FragmentQuestionBinding mBinding;
    private static String FRAGMENT_QUESTION = "FRAGMENT_QUESTION";
    private static String FRAGMENT_HISTORY = "FRAGMENT_HISTORY";
    private List<Questionnaire> mQuestionnaireList;
    private String mResponse;
    private History mHistory;
    private Boolean isResponseCollected = false;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_question;
    }

    @Override
    protected String getTitle() {
        return "Question-2";
    }

    //entry point to fragment with necessary arguments
    public static SecondQuestionFragment newInstance(List<Questionnaire> questionnaireList, History history) {
        SecondQuestionFragment fragment = new SecondQuestionFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_QUESTION, questionnaireList.toString());
        args.putString(FRAGMENT_HISTORY, history.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type listType = new TypeToken<ArrayList<Questionnaire>>() {
        }.getType();
        mQuestionnaireList = new Gson().fromJson(getArguments().getString(FRAGMENT_QUESTION), listType);
        mHistory = new Gson().fromJson(getArguments().getString(FRAGMENT_HISTORY), History.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentQuestionBinding.inflate(inflater, container, false);

        showDetails(mQuestionnaireList.get(1));
        loadRecyclerView();

        mBinding.buttonContinue.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mResponse)) {
                Toast.makeText(getContext(), "Select one option and Try again", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isResponseCollected) {
                saveResponse(mQuestionnaireList.get(1), mResponse);
            }
            loadAndReplaceFragment(SummaryFragment.newInstance(mHistory));
        });

        return mBinding.getRoot();
    }

    private void showDetails(Questionnaire question) {

        mBinding.question.setText(question.getQuestion().getQuestion_text());

        if (question.getQuestion().getQuestion_type().equals(Constants.QType.SINGLE)) {
            loadSingleChoiceOptions(question);
        } else {
            loadMultipleChoiceOptions(question);
        }
    }

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

    private void loadSingleChoiceOptions(Questionnaire question) {
        SingleChoiceQuestionAdapter adapter = new SingleChoiceQuestionAdapter(getContext(),
                question.getOptions(), optionId -> mResponse = optionId);
        mBinding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        updateAdapterView(adapter, mBinding.recyclerView);
    }

    private void saveResponse(Questionnaire question, String answer) {
        List<Question> questions = new ArrayList<>(mHistory.getQuestions());
        questions.add(new Question(question.getQuestion().getQuestion_text(), answer));
        mHistory.setQuestions(questions);
        isResponseCollected = true;
    }

    private void loadRecyclerView() {
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private <T extends RecyclerView.ViewHolder> void updateAdapterView(
            RecyclerView.Adapter<T> Adapter, RecyclerView recyclerView) {
        if (Adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

}
