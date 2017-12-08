package com.baosight.brightfish.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Checklist;

import java.util.List;


public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    private List<Checklist> mChecklists;

    public ChecklistAdapter(List<Checklist> checklists){
        mChecklists=checklists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Checklist checklist=mChecklists.get(position);
        holder.sku.setText(checklist.getGoods().getSku());
        holder.name.setText(checklist.getGoods().getName());
        holder.amount.setText(String.valueOf(checklist.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mChecklists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView sku,name;
        Button amount;
        public ViewHolder(View itemView) {
            super(itemView);
            sku=(TextView) itemView.findViewById(R.id.goods_sku);
            name=(TextView) itemView.findViewById(R.id.goods_name);
            amount=(Button) itemView.findViewById(R.id.checklist_amount);
        }
    }
}
