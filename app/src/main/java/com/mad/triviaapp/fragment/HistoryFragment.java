package com.mad.triviaapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.triviaapp.R;
import com.mad.triviaapp.adapter.HistoryAdapter;
import com.mad.triviaapp.adapter.SummaryAdapter;
import com.mad.triviaapp.databinding.FragmentHistoryBinding;
import com.mad.triviaapp.fragment.base.BaseFragment;
import com.mad.triviaapp.model.History;
import com.mad.triviaapp.viewmodel.HistoryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HistoryFragment extends BaseFragment {

    FragmentHistoryBinding mBinding;
    HistoryViewModel mViewModel;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_history;
    }

    @Override
    protected String getTitle() {
        return "Game History";
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHistoryBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        // fetch game history from db
        mViewModel.getHistoryList().observe(getViewLifecycleOwner(), histories -> {
            showDetails(histories);
        });

        loadRecyclerView();

        return mBinding.getRoot();
    }

    // show game history.
    private void showDetails(List<History> histories) {
        HistoryAdapter adapter = new HistoryAdapter(histories);
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

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_history).setVisible(false);
    }
}
