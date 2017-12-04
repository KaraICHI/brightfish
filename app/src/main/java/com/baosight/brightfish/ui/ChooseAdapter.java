package com.baosight.brightfish.ui;

import android.content.res.ColorStateList;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.CheckinActivity;
import com.baosight.brightfish.R;
import com.baosight.brightfish.model.ChooseItem;

import java.util.List;



public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder>{

    private List<ChooseItem> mChooseItemList;


    public ChooseAdapter(List<ChooseItem> chooseItemList){
        mChooseItemList=chooseItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.chooseChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.chooseChecked.setBackgroundTintList(ColorStateList.valueOf(R.color.colorDarkGery));
                CheckinActivity.startCheckinActivity(parent.getContext(),viewHolder.chooseName.getText().toString(),viewHolder.chooseSku.getText().toString());

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChooseItem chooseItem=mChooseItemList.get(position);
        holder.chooseName.setText(chooseItem.getName());
        holder.chooseSku.setText(chooseItem.getSku());
    }

    @Override
    public int getItemCount() {
        return mChooseItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView chooseName;
        TextView chooseSku;
        ImageView chooseChecked;

        public ViewHolder(View itemView) {
            super(itemView);
            chooseName=(TextView) itemView.findViewById(R.id.choose_name);
            chooseSku=(TextView) itemView.findViewById(R.id.choose_sku);
            chooseChecked=(ImageView) itemView.findViewById(R.id.choose_checked_icon);

        }
    }

}
