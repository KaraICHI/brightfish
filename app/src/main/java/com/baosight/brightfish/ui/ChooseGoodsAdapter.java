package com.baosight.brightfish.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Supplier;

import java.util.List;


public class ChooseGoodsAdapter extends RecyclerView.Adapter<ChooseGoodsAdapter.ViewHolder> {

    private List<Goods> mChooseItemList;
    private Activity mActivity;

    public ChooseGoodsAdapter(List<Goods> chooseItemList, Activity activity) {
        mChooseItemList = chooseItemList;
        mActivity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Goods goods = mChooseItemList.get(position);

        holder.chooseName.setText(goods.getName());
        holder.chooseSku.setText(goods.getSku());

        holder.chooseChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.chooseChecked.setBackgroundTintList(ColorStateList.valueOf(R.color.colorDarkGery));
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", goods);


                intent.putExtra("bundle", bundle);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();

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
        ImageView chooseChecked;

        public ViewHolder(View itemView) {
            super(itemView);
            chooseName = (TextView) itemView.findViewById(R.id.choose_name);
            chooseSku = (TextView) itemView.findViewById(R.id.choose_sku);
            chooseChecked = (ImageView) itemView.findViewById(R.id.choose_checked_icon);

        }
    }


}
