package com.mad.triviaapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mad.triviaapp.R;
import com.mad.triviaapp.adapter.SummaryAdapter;
import com.mad.triviaapp.databinding.FragmentSummaryBinding;
import com.mad.triviaapp.fragment.base.BaseFragment;
import com.mad.triviaapp.model.History;
import com.mad.triviaapp.viewmodel.HistoryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class SummaryFragment extends BaseFragment {

    FragmentSummaryBinding mBinding;
    HistoryViewModel mViewModel;
    private static String FRAGMENT_HISTORY = "FRAGMENT_HISTORY";
    private History mHistory;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_summary;
    }

    @Override
    protected String getTitle() {
        return "Summary";
    }

    public static SummaryFragment newInstance(History history) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_HISTORY, history.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHistory = new Gson().fromJson(getArguments().getString(FRAGMENT_HISTORY), History.class);
        Log.d("Summary ", mHistory.toString());
    }


    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentSummaryBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        loadRecyclerView();
        showDetails(mHistory);

        /***
         * when user clicks on finish. store the user response in db
         * display success message.
         * load first page
         */
        mBinding.buttonFinish.setOnClickListener(v -> {
            mHistory.setCreationDate(new Date().getTime());
            mViewModel.insert(mHistory);
            Toast.makeText(getContext(), "Game Details Saved. Starting new game again", Toast.LENGTH_SHORT).show();
            reloadFirstPage();
        });

        // go to history page
        mBinding.buttonHistory.setOnClickListener(v -> loadFragment(HistoryFragment.newInstance()));

        return mBinding.getRoot();
    }


    //clear backstack and load first page
    private void reloadFirstPage() {
        for (int i = 0; i < mFragmentManager.getFragmentManager().getBackStackEntryCount(); ++i) {
            getChildFragmentManager().popBackStack();
        }
        loadAndReplaceFragment(UserInfoFragment.newInstance());
    }

    // show user response
    private void showDetails(History history) {
        mBinding.tvName.setText(getString(R.string.hello) + " " + history.getUserName());
        SummaryAdapter adapter = new SummaryAdapter(history.getQuestions());
        mBinding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        updateAdapterView(adapter, mBinding.recyclerView);
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
