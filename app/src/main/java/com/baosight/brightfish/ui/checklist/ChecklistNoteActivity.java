package com.baosight.brightfish.ui.checklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.ChecklistNote;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.ui.BasicActivity;
import com.baosight.brightfish.ui.goods.GoodsActivity;

import org.litepal.crud.DataSupport;

public class ChecklistNoteActivity extends BasicActivity {
    ChecklistNote checklistNote;
    Goods goods;
    EditText goodsName,goodsSku,currAmount,lastAmount,amountChange,reason,description;
    TextView currTime;
    ImageButton toGoods;


    public static void startChecklistNoteActivity(Context context, ChecklistNote checklistNote){
        Intent intent=new Intent(context,ChecklistNoteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("checklistNote",checklistNote);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_note);
        initControls();
        showChecklistNote();
    }

    private void initControls() {
        initToolbar(R.color.colorOrange);
        goodsName=(EditText) findViewById(R.id.goods_name);
        goodsSku=(EditText) findViewById(R.id.goods_sku);
        currAmount=(EditText) findViewById(R.id.current_amount);
        lastAmount=(EditText) findViewById(R.id.last_amount);
        amountChange=(EditText) findViewById(R.id.amount_change);
        reason=(EditText) findViewById(R.id.reason);
        description=(EditText) findViewById(R.id.descripe_checkin);
        currTime=(TextView) findViewById(R.id.current_time);
        toGoods=(ImageButton) findViewById(R.id.to_goods);

    }
    private void showChecklistNote() {
        checklistNote=(ChecklistNote) getIntent().getBundleExtra("bundle").getSerializable("checklistNote");
        goods= DataSupport.find(Goods.class,checklistNote.getGoodsId());
        goodsSku.setText(goods.getSku());
        goodsName.setText(goods.getName());
        currAmount.setText(checklistNote.getCurrAmount()+"");
        lastAmount.setText(checklistNote.getLastAmount()+"");
        amountChange.setText(checklistNote.getChange());
        reason.setText(checklistNote.getReason());
        description.setText(checklistNote.getDescription());
        currTime.setText(checklistNote.getNoteDate());
        toGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsActivity.startGoodsActivity(ChecklistNoteActivity.this,goods);
            }
        });
    }

}
