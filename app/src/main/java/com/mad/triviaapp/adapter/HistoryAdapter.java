package com.mad.triviaapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.triviaapp.R;
import com.mad.triviaapp.databinding.ItemHistoryBinding;
import com.mad.triviaapp.model.History;
import com.mad.triviaapp.utils.DatetimeUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> histories;

    public HistoryAdapter(List<History> histories) {
        this.histories = histories;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = histories.get(position);
        holder.mBinding.itemDatetime.setText("Game-" + history.getId() + " " + DatetimeUtil.formatDateTime(history.getCreationDate()));
        holder.mBinding.itemUsername.setText(holder.itemView.getContext().getString(R.string.name) + " " + history.getUserName());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        holder.mBinding.recyclerChild.setLayoutManager(layoutManager);
        holder.mBinding.recyclerChild.setHasFixedSize(true);

        // nested adapter call.
        // load another adapter to display user response in recycler view.
        QuestionAdapter adapter = new QuestionAdapter(history.getQuestions());
        holder.mBinding.recyclerChild.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHistoryBinding mBinding;

        ViewHolder(ItemHistoryBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;
        }
    }
}