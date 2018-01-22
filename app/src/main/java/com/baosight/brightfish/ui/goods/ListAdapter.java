package com.baosight.brightfish.ui.goods;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.BuyerData;
import com.baosight.brightfish.domain.SupplierData;

import java.text.DecimalFormat;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/1/22.
 */

public class ListAdapter extends BaseAdapter {
    Object[] mSet;

    public ListAdapter(Set set) {
        this.mSet = set.toArray();
    }

    @Override
    public int getCount() {
        return mSet.length;
    }

    @Override
    public Object getItem(int position) {
        return mSet[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView name,data,percentText;
        ProgressBar percent;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list,parent,false);
            name= (TextView) convertView.findViewById(R.id.name);
            data= (TextView) convertView.findViewById(R.id.data);
            percent= (ProgressBar) convertView.findViewById(R.id.percent);
            percentText= (TextView) convertView.findViewById(R.id.percent_text);
            if(mSet[position]instanceof SupplierData){
                SupplierData supplierData=(SupplierData)mSet[position];
                name.setText(supplierData.getName());
                data.setText(supplierData.getAmount()+"");
                percent.setMax(supplierData.getAmountSum());
                percent.setProgress(supplierData.getAmount());
                DecimalFormat df = new DecimalFormat("#.00");
                percentText.setText(df.format(supplierData.getPrecent())+"%");
            }else if (mSet[position]instanceof BuyerData){
                BuyerData buyerData=(BuyerData)mSet[position];
                name.setText(buyerData.getName());
                data.setText(buyerData.getAmount()+"");
                percent.setMax(buyerData.getAmountSum());
                percent.setProgress(buyerData.getAmount());
                percent.setProgressTintList(parent.getResources().getColorStateList(R.color.colorBlue));
                DecimalFormat df = new DecimalFormat("#.00");
                percentText.setText(df.format(buyerData.getPrecent())+"%");
            }
        }

        return convertView;
    }

}
