package com.mad.triviaapp.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.mad.triviaapp.R;
import com.mad.triviaapp.databinding.ItemMultipleChoiceOptionBinding;
import com.mad.triviaapp.databinding.ItemSingleChoiceOptionBinding;
import com.mad.triviaapp.model.Option;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MultipleChoiceQuestionAdapter extends RecyclerView.Adapter<MultipleChoiceQuestionAdapter.ViewHolder> {

    private List<Option> mOptions;
    private onMultipleChoiceAdapterManager mListener;

    SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();
    List<String> mSelectedOptionList = new ArrayList<>();

    public MultipleChoiceQuestionAdapter(List<Option> options,
                                         onMultipleChoiceAdapterManager listener) {
        this.mOptions = options;
        this.mListener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMultipleChoiceOptionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Option option = mOptions.get(position);

        holder.mBinding.itemOptionCheckbox.setChecked(checkBoxStateArray.get(position, false));
        holder.mBinding.itemOptionCheckbox.setText(option.getOption_text());
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public interface onMultipleChoiceAdapterManager {
        void onSelected(List<String> options);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMultipleChoiceOptionBinding mBinding;

        ViewHolder(ItemMultipleChoiceOptionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;

            mBinding.itemOptionCheckbox.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (!checkBoxStateArray.get(position, false)) {
                    mSelectedOptionList.add(mBinding.itemOptionCheckbox.getText().toString());
                    mBinding.itemOptionCheckbox.setChecked(true);
                    checkBoxStateArray.put(position, true);
                } else {
                    mSelectedOptionList.remove(mBinding.itemOptionCheckbox.getText().toString());
                    checkBoxStateArray.put(position, false);
                }
                mListener.onSelected(mSelectedOptionList);
            });
        }
    }
}