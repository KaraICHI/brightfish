package com.baosight.brightfish.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baosight.brightfish.GoodsActivity;
import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Checklist;
import com.baosight.brightfish.model.ChecklistNote;
import com.baosight.brightfish.model.Goods;
import com.baosight.brightfish.util.CurrentTime;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.content.ContentValues.TAG;


public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> implements View.OnClickListener {

    private List<Checklist> mChecklists;
    private Context mContext;
    private TextView amountDev,chooseReason,currentTime;
    private EditText checklistAmount,editDescription;
    private boolean isOpen;
    private static Checklist currChecklist;
    private static Goods currGoods;
    private int reason=R.string.reversion;
    private Dialog chooseReasonDialog;
    private Dialog dialog ;
    private ViewHolder currHolder;
    private static final String TAG = "ChecklistAdapter";
    public ChecklistAdapter(List<Checklist> checklists){
        mChecklists=checklists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Checklist checklist=mChecklists.get(position);
        final Goods goods= DataSupport.find(Goods.class,checklist.getGoodsId());
        holder.sku.setText(goods.getSku());
        holder.name.setText(goods.getName());
        holder.amount.setText(String.valueOf(checklist.getAmount()));
        holder.amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currChecklist=checklist;
                currGoods=goods;
                currHolder=holder;
                showDialog();
            }
        });
    }

    private void showAmountDev(int amount,int checkAmount) {
        if(amount>checkAmount){
            amountDev.setText(" ~ 生 "+(amount-checkAmount));
        }else if(amount<checkAmount){
            amountDev.setText(" ~ 亏 "+(checkAmount-amount));
        }else {
            amountDev.setText(" ~ 持平");
        }
    }


    @Override
    public int getItemCount() {
        return mChecklists.size();
    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {
        int amount;
        switch (v.getId()){
            case R.id.add_amount_btn:
                amount=Integer.parseInt(checklistAmount.getText().toString());
                amount+=1;
                checklistAmount.setText(amount+"");
                showAmountDev(amount,currChecklist.getAmount());
                break;
            case R.id.remove_amount_btn:
                amount=Integer.parseInt(checklistAmount.getText().toString());
                amount-=1;
                checklistAmount.setText(amount+"");
                showAmountDev(amount,currChecklist.getAmount());
                break;
            case R.id.to_goods:
                GoodsActivity.startGoodsActivity(mContext,currGoods);
                break;
            case R.id.add_reason:
                if(!isOpen){
                    editDescription.setVisibility(View.VISIBLE);
                    isOpen=true;
                }else {
                    editDescription.setVisibility(View.GONE);
                    isOpen=false;
                }
                break;
            case R.id.choose_reasons:
                showChooseReasonDialog();
                break;
            case R.id.commit_reason:
                chooseReason.setText(reason);
                chooseReasonDialog.dismiss();
                break;
            case R.id.cancleDialog:
                dialog.dismiss();
                break;
            case R.id.cancle_reason:
                chooseReasonDialog.dismiss();
                break;
            case R.id.okDialog:
                if(Integer.parseInt(checklistAmount.getText().toString())==currChecklist.getAmount()){
                    Toast.makeText(mContext,"库存未变",Toast.LENGTH_SHORT).show();
                }else {
                    saveChecklistNote();
                    dialog.dismiss();
                    currHolder.amount.setText(checklistAmount.getText().toString());
                    Checklist checklist=new Checklist();
                    checklist.setAmount(Integer.parseInt(checklistAmount.getText().toString()));
                    Log.d(TAG, "onClick: ------id=="+currChecklist.getId()+" "+currGoods.getId());
                    checklist.update(currChecklist.getId());

                }

        }

    }

    private void saveChecklistNote() {
        ChecklistNote checklistNote=new ChecklistNote();
        checklistNote.setGoodsId(currGoods.getId());
        checklistNote.setChange(amountDev.getText().toString());
        checklistNote.setLastAmount(currChecklist.getAmount());
        checklistNote.setCurrAmount(Integer.parseInt(checklistAmount.getText().toString()));
        checklistNote.setNoteDate(currentTime.getText().toString());
        checklistNote.setReason(chooseReason.getText().toString());
        if(editDescription!=null){
            checklistNote.setDescription(editDescription.getText().toString());
        }
        checklistNote.save();

    }

    private void showChooseReasonDialog() {
        chooseReasonDialog=new Dialog(mContext,R.style.NoTitleDialog);
        chooseReasonDialog.setContentView(R.layout.dialog_choose_reason);
        RadioGroup chooseReasonGroup=(RadioGroup) chooseReasonDialog.findViewById(R.id.reasons);
        chooseReasonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.reason_consumption:
                        reason=R.string.consumption;
                        break;
                    case R.id.reason_output:
                        reason=R.string.output;
                        break;
                    case R.id.reason_lost:
                        reason=R.string.lost;
                        break;
                    case R.id.reason_wear:
                        reason=R.string.wear;
                        break;
                    case R.id.reason_reversion_mistake:
                        reason=R.string.reversion;
                        break;
                    default:
                        break;

                }
            }
        });
        Button commitReason=(Button) chooseReasonDialog.findViewById(R.id.commit_reason);
        commitReason.setOnClickListener(this);
        Button cancelReason=(Button) chooseReasonDialog.findViewById(R.id.cancle_reason);
        cancelReason.setOnClickListener(this);
        chooseReasonDialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView sku,name;
        Button amount;
        public ViewHolder(View itemView) {
            super(itemView);
            sku=(TextView) itemView.findViewById(R.id.goods_sku);
            name=(TextView) itemView.findViewById(R.id.goods_name);
            amount=(Button) itemView.findViewById(R.id.checklist_amount);
        }
    }

    private void showDialog(){
        dialog = new Dialog(mContext,R.style.NoTitleDialog);
        dialog.setContentView(R.layout.dialog_checklist);
        amountDev=(TextView) dialog.findViewById(R.id.amount_dev);
        checklistAmount=(EditText) dialog.findViewById(R.id.checklist_amount);
        ImageButton addAmount=(ImageButton) dialog.findViewById(R.id.add_amount_btn);
        ImageButton removeAmount=(ImageButton) dialog.findViewById(R.id.remove_amount_btn);
        currentTime=(TextView) dialog.findViewById(R.id.current_time);
        ImageButton addImage=(ImageButton) dialog.findViewById(R.id.add_image);
        TextView goodsName=(TextView) dialog.findViewById(R.id.goods_name);
        TextView goodsSku=(TextView) dialog.findViewById(R.id.goods_sku);
        ImageButton toGoods=(ImageButton) dialog.findViewById(R.id.to_goods);
        chooseReason=(TextView) dialog.findViewById(R.id.choose_reasons);
        editDescription=(EditText) dialog.findViewById(R.id.edit_description);
        ImageButton addReason=(ImageButton) dialog.findViewById(R.id.add_reason);
        Button cancel=(Button) dialog.findViewById(R.id.cancleDialog);
        Button commit=(Button) dialog.findViewById(R.id.okDialog);
        addAmount.setOnClickListener(this);
        removeAmount.setOnClickListener(this);
        checklistAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                int amount=Integer.parseInt(checklistAmount.getText().toString());
                showAmountDev(amount,currChecklist.getAmount());
            }
        });
        addImage.setOnClickListener(this);
        toGoods.setOnClickListener(this);
        chooseReason.setOnClickListener(this);
        addReason.setOnClickListener(this);
        cancel.setOnClickListener(this);
        commit.setOnClickListener(this);
        checklistAmount.setText(currChecklist.getAmount()+"");
        goodsName.setText(currGoods.getName());
        goodsSku.setText(currGoods.getSku());
        CurrentTime.currentTime=currentTime;
        new CurrentTime().new TimeThread().start();
        dialog.show();
    }


}
