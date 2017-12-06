package com.baosight.brightfish.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baosight.brightfish.CheckinActivity;
import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Goods;

import java.util.List;


public class ChooseGoodsdDialogAdapter extends RecyclerView.Adapter<ChooseGoodsdDialogAdapter.ViewHolder> {

    private List<Goods> mChooseItemList;
    private Dialog mDialog;


    public ChooseGoodsdDialogAdapter(List<Goods> chooseItemList,Dialog dialog) {
        mChooseItemList = chooseItemList;
        mDialog=dialog;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_choose, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Goods goods = mChooseItemList.get(position);

        holder.chooseName.setText(goods.getName());
        holder.chooseSku.setText(goods.getSku());

        holder.chooseChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckinActivity.goods=goods;
                CheckinActivity.refesh();
                mDialog.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mChooseItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView chooseName;
        TextView chooseSku;
        View chooseChecked;

        ViewHolder(View itemView) {
            super(itemView);
            chooseName = (TextView) itemView.findViewById(R.id.choose_name);
            chooseSku = (TextView) itemView.findViewById(R.id.choose_sku);
            chooseChecked = itemView;

        }
    }


}
