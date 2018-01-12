package com.baosight.brightfish.ui.checkout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Goods;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */

public class RecentCheckoutAdapter extends RecyclerView.Adapter<RecentCheckoutAdapter.ViewHolder> {
    
    private List<Checkout> mCheckoutList;
    
    public RecentCheckoutAdapter(List<Checkout> CheckoutList){
        mCheckoutList=CheckoutList;
    }
    @Override
    public RecentCheckoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_checkout,parent,false);
        return new RecentCheckoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentCheckoutAdapter.ViewHolder holder, int position) {
        Checkout Checkout=mCheckoutList.get(position);
        Goods goods= DataSupport.find(Goods.class,Checkout.getGoodsId());
        holder.name.setText(goods.getName());
        holder.sku.setText(goods.getSku());
        holder.amount.setText("Qt:"+Checkout.getAmount()+"");
        holder.date.setText(Checkout.getCheckinDate());

    }

    @Override
    public int getItemCount() {
        return mCheckoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,sku,amount,date;
        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name);
            sku=(TextView) itemView.findViewById(R.id.sku);
            amount=(TextView) itemView.findViewById(R.id.amount);
            date=(TextView) itemView.findViewById(R.id.date);
        }
    }
}
