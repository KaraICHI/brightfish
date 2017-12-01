package com.baosight.brightfish.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.model.ConditionItem;
import com.baosight.brightfish.R;

import java.util.List;


public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.ViewHolder> {

    private List<ConditionItem> mConditionItemList;

    public int itemLayout;

    public ConditionAdapter(List<ConditionItem> conditionItemList) {
        mConditionItemList = conditionItemList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView conditionName, conditionContent;
        ImageView conditionCancel;

        ViewHolder(View itemView) {
            super(itemView);
            conditionName = (TextView) itemView.findViewById(R.id.condition_name);
            conditionContent = (TextView) itemView.findViewById(R.id.condition_content);
            conditionCancel = (ImageView) itemView.findViewById(R.id.search_close_btn);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.conditionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionItemList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConditionItem conditionItem = mConditionItemList.get(position);
        holder.conditionName.setText(conditionItem.getConditionName());
        holder.conditionContent.setText(conditionItem.getConditionContent());

    }

    @Override
    public int getItemCount() {
        return mConditionItemList.size();
    }

    public void setItemLayout(int itemLayout){
        this.itemLayout=itemLayout;
    }
}
