package com.mad.triviaapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mad.triviaapp.R;
import com.mad.triviaapp.databinding.ItemQuestionBinding;
import com.mad.triviaapp.databinding.ItemSummaryBinding;
import com.mad.triviaapp.model.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Question> questions;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemQuestionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.mBinding.itemQuestion.setText(question.getQuestion());
        holder.mBinding.itemResponse.setText(holder.itemView.getContext().getString(R.string.answer) + question.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemQuestionBinding mBinding;

        ViewHolder(ItemQuestionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;
        }
    }
}