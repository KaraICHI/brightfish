package com.baosight.brightfish.ui.goods;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Goods;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class GoodsAdapter extends ArrayAdapter<Goods> {
    private int resourceId;
    public GoodsAdapter(@NonNull Context context, int resource, @NonNull List<Goods> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Goods goods=getItem(position);
        View view= LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
        TextView noteIcon=(TextView) view.findViewById(R.id.note_icon);
        TextView checkName=(TextView) view.findViewById(R.id.choose_name);
        TextView checkSku=(TextView) view.findViewById(R.id.choose_sku);
        TextView checkAmount=(TextView) view.findViewById(R.id.check_amount);

        if(position%2==0){
            noteIcon.setBackground(parent.getContext().getResources().getDrawable(R.drawable.rect_mgreen));
        }else if(position%3==0){
            noteIcon.setBackground(parent.getContext().getResources().getDrawable(R.drawable.rect_blue));
        }   if(TextUtils.isEmpty(goods.getName())){
            goods.setName("emtpy");
        }
            char firstName = goods.getName().charAt(0);
            noteIcon.setText(firstName + "");
            checkName.setText(goods.getName());
            checkSku.setText(goods.getSku());
            int checkinAmount = DataSupport.where("goodsId='" + goods.getId() + "'").sum(Checkin.class, "amount", int.class);
            int checkoutAmount = DataSupport.where("goodsId='" + goods.getId() + "'").sum(Checkout.class, "amount", int.class);
            checkAmount.setText(checkinAmount - checkoutAmount + "");



        return view;
    }


}
