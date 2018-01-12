package com.baosight.brightfish.ui.buyer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Buyer;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class BuyerAdapter extends ArrayAdapter<Buyer> {
    private int resourceId;
    public BuyerAdapter(@NonNull Context context, int resource, @NonNull List<Buyer> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Buyer buyer=getItem(position);
        View view= LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
        TextView noteIcon=(TextView) view.findViewById(R.id.note_icon);
        TextView checkName=(TextView) view.findViewById(R.id.choose_name);
        TextView checkSku=(TextView) view.findViewById(R.id.choose_sku);

        if(position%2==0){
            noteIcon.setBackground(parent.getContext().getResources().getDrawable(R.drawable.rect_mgreen));
        }else if(position%3==0){
            noteIcon.setBackground(parent.getContext().getResources().getDrawable(R.drawable.rect_blue));
        }
            char firstName = buyer.getName().charAt(0);
            noteIcon.setText(firstName + "");
            checkName.setText(buyer.getName());
            checkSku.setText(buyer.getSku());



        return view;
    }


}
