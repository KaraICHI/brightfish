package com.baosight.brightfish.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.AccountActivity;
import com.baosight.brightfish.AnalyzeActivity;
import com.baosight.brightfish.NewBuyerActivity;
import com.baosight.brightfish.CheckinActivity;
import com.baosight.brightfish.ChecklistActivity;
import com.baosight.brightfish.CheckoutActivity;
import com.baosight.brightfish.NewGoodsActivity;
import com.baosight.brightfish.model.HomeItem;
import com.baosight.brightfish.MainActivity;
import com.baosight.brightfish.R;
import com.baosight.brightfish.SearchActivity;
import com.baosight.brightfish.SettingActivity;
import com.baosight.brightfish.NewSupplierActivity;

import java.util.List;

/**
 * Created by saitama on 2017/11/20.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context mContext;

    private List<HomeItem> mHomeItem;

    public HomeAdapter(List<HomeItem> homeItems,Context context){
        mHomeItem=homeItems;
        mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        holder.homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                HomeItem homeItem=mHomeItem.get(position);
                startItemActivity(homeItem,parent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeItem homeItem=mHomeItem.get(position);
        holder.itemName.setText(homeItem.getItem_name());
        holder.itemIcon.setImageResource(homeItem.getIcon());
        holder.itemIcon.setColorFilter(mContext.getResources().getColor(homeItem.getColor()));

    }

    @Override
    public int getItemCount() {
        return mHomeItem.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View homeView;
        ImageView itemIcon;
        TextView itemName;

        public ViewHolder(View view){
            super(view);
            homeView=view;
            itemIcon=(ImageView)view.findViewById(R.id.item_image);
            itemName=(TextView)view.findViewById(R.id.item_name);
        }
    }
    private void startItemActivity(HomeItem homeItem,ViewGroup parent){
        Context context=parent.getContext();
        switch (homeItem.getId()){
            case MainActivity.CHECKIN_ITEM:
                CheckinActivity.startCheckinActivity(context);
                break;
            case MainActivity.CHECKOUT_ITEM:
                CheckoutActivity.startCheckoutActivity(context);
                break;
            case MainActivity.ACCOUNT_ITEM:
                AccountActivity.startAccountActivity(context);
                break;
            case MainActivity.ANALYZE_ITEM:
                AnalyzeActivity.startAnalyzeActivity(context);
                break;
            case MainActivity.BUYER_ITEM:
                NewBuyerActivity.startBuyerActivity(context);
                break;
            case MainActivity.DIANHUO_ITEM:
                ChecklistActivity.startChecklistActivity(context);
                break;
            case MainActivity.GOOD_ITEM:
                NewGoodsActivity.startGoodsActivity(context);
                break;
            case MainActivity.SEARCH_ITEM:
                SearchActivity.startSearchActivity(context);
                break;
            case MainActivity.SETTING_ITEM:
                SettingActivity.startSettingActivity(context);
                break;
            case MainActivity.SUPPILER_ITEM:
                NewSupplierActivity.startSupplierActivity(context);
                break;
            default:
                break;
        }
    }

}
