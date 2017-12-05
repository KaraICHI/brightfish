package com.baosight.brightfish.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.CheckinActivity;
import com.baosight.brightfish.ChooseGoodsActivity;
import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.model.ChooseItem;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.model.Supplier;

import java.util.List;


public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

    private List<Object> mChooseItemList;
    private Activity mActivity;


    public ChooseAdapter(List<Object> chooseItemList, Activity activity) {
        mChooseItemList = chooseItemList;
        mActivity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Object obj = mChooseItemList.get(position);
        if (obj instanceof Goods) {
            Goods goods = (Goods) obj;
            holder.chooseName.setText(goods.getName());
            holder.chooseSku.setText(goods.getSku());
        }else if(obj instanceof Supplier){
            Supplier supplier=(Supplier) obj;
            holder.chooseName.setText(supplier.getName());
            holder.chooseSku.setText(supplier.getSku());
        }else if(obj instanceof Buyer){
            Buyer buyer=(Buyer) obj;
            holder.chooseName.setText(buyer.getName());
            holder.chooseSku.setText(buyer.getSku());
        }
       holder.chooseChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.chooseChecked.setBackgroundTintList(ColorStateList.valueOf(R.color.colorDarkGery));
                setResult(obj);

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

    private void setResult(Object obj){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        if (obj instanceof Goods) {
            Goods goods = (Goods) obj;
            bundle.putSerializable("goods",goods);

        }else if(obj instanceof Supplier){
            Supplier supplier=(Supplier) obj;
            bundle.putSerializable("supplier",supplier);
        }else if(obj instanceof Buyer){
            Buyer buyer=(Buyer) obj;
            bundle.putSerializable("buyer",buyer);
        }
        intent.putExtra("bundle",bundle);
        mActivity.setResult(Activity.RESULT_OK,intent);
        mActivity.finish();
    }

}
