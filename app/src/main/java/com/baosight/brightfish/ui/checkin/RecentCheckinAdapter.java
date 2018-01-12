package com.baosight.brightfish.ui.checkin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Goods;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */

public class RecentCheckinAdapter extends RecyclerView.Adapter<RecentCheckinAdapter.ViewHolder> {

    private List<Checkin> mCheckinList;

    public RecentCheckinAdapter(List<Checkin> checkinList){
        mCheckinList=checkinList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_checkin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Checkin checkin=mCheckinList.get(position);
        Goods goods= DataSupport.find(Goods.class,checkin.getGoodsId());
        holder.name.setText(goods.getName());
        holder.sku.setText(goods.getSku());
        holder.amount.setText("Qt:"+checkin.getAmount());
        holder.date.setText(checkin.getCheckinDate());

    }

    @Override
    public int getItemCount() {
        return mCheckinList.size();
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
