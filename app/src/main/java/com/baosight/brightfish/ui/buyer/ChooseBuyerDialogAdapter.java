package com.baosight.brightfish.ui.buyer;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Buyer;
import com.baosight.brightfish.ui.checkout.CheckoutActivity;

import java.util.List;


public class ChooseBuyerDialogAdapter extends RecyclerView.Adapter<ChooseBuyerDialogAdapter.ViewHolder> {

    private List<Buyer> mChooseItemList;
    private Dialog mDialog;
    public ChooseBuyerDialogAdapter(List<Buyer> chooseItemList, Dialog dialog) {
        mChooseItemList = chooseItemList;
        mDialog=dialog;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Buyer buyer = mChooseItemList.get(position);
        holder.chooseName.setText(buyer.getName());
        holder.chooseSku.setText(buyer.getSku());

        holder.chooseChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckoutActivity.buyer=buyer;
                CheckoutActivity.refesh();

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
