package com.baosight.brightfish.ui.search.choose;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Supplier;
import com.baosight.brightfish.ui.CheckinActivity;

import java.util.List;


public class ChooseSupplierDialogAdapter extends RecyclerView.Adapter<ChooseSupplierDialogAdapter.ViewHolder> {

    private List<Supplier> mChooseItemList;
    private Dialog mDialog;

    public ChooseSupplierDialogAdapter(List<Supplier> chooseItemList, Dialog dialog) {
        mChooseItemList = chooseItemList;
        mDialog=dialog;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_choose, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Supplier supplier = mChooseItemList.get(position);
        holder.chooseName.setText(supplier.getName());
        holder.chooseSku.setText(supplier.getSku());

        holder.chooseChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckinActivity.supplier=supplier;
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

        public ViewHolder(View itemView) {
            super(itemView);
            chooseName = (TextView) itemView.findViewById(R.id.choose_name);
            chooseSku = (TextView) itemView.findViewById(R.id.choose_sku);
            chooseChecked =itemView;

        }
    }


}
