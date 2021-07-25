package com.mad.triviaapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mad.triviaapp.R;
import com.mad.triviaapp.databinding.ItemSingleChoiceOptionBinding;
import com.mad.triviaapp.model.Option;
import com.mad.triviaapp.utils.NumberUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SingleChoiceQuestionAdapter extends RecyclerView.Adapter<SingleChoiceQuestionAdapter.ViewHolder> {

    private List<Option> mOptions;
    private Context mContext;
    private RadioGroup lastCheckedOptionGroup = null;
    private onSingleChoiceAdapterManager mListener;

    public SingleChoiceQuestionAdapter(Context context, List<Option> options, onSingleChoiceAdapterManager listener) {
        this.mOptions = options;
        this.mContext = context;
        this.mListener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSingleChoiceOptionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Option option = mOptions.get(position);

        RadioButton rb = new RadioButton(this.mContext);
        rb.setId(NumberUtil.parseInt(String.valueOf(option.getId())));
        rb.setText(option.getOption_text());

        holder.mBinding.itemOptionGrp.addView(rb);
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public interface onSingleChoiceAdapterManager {
        void onSelected(String optionName);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSingleChoiceOptionBinding mBinding;

        RadioButton mRadioButtonOption;

        ViewHolder(ItemSingleChoiceOptionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;

            mBinding.itemOptionGrp.setOnCheckedChangeListener((RadioGroup radioGroup, int i) -> {
                Log.d("TAG ", "Option selected");

                if (lastCheckedOptionGroup != null
                        && lastCheckedOptionGroup.getCheckedRadioButtonId()
                        != radioGroup.getCheckedRadioButtonId()
                        && lastCheckedOptionGroup.getCheckedRadioButtonId() != -1) {
                    lastCheckedOptionGroup.clearCheck();
                    mRadioButtonOption = itemView.findViewById(radioGroup.getCheckedRadioButtonId());
                    mListener.onSelected(mRadioButtonOption.getText().toString());
                }
                lastCheckedOptionGroup = radioGroup;
            });
        }
    }
}