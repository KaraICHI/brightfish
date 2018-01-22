package com.baosight.brightfish.ui.checklist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Checklist;
import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.ui.BasicActivity;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChecklistActivity extends BasicActivity {
    ChecklistAdapter adapter;
    RecyclerView recyclerView;
    RelativeLayout currentSortMethod;
    boolean sortdesc;
    List<Goods> goodsList;

    public static void startChecklistActivity(Context context) {
        Intent intent = new Intent(context, ChecklistActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        initControls();
        initToolbar(R.color.colorOrange);

    }

    private void initControls() {
        recyclerView=(RecyclerView) findViewById(R.id.checklist_rec);
        adapter=new ChecklistAdapter(getChecklists());
        LinearLayoutManager layoutManager=new LinearLayoutManager(ChecklistActivity.this);
        assert recyclerView != null;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    /**
     * 获取货品现存数量
     */
    private List<Checklist> getChecklists(){
        goodsList= DataSupport.findAll(Goods.class);
        if(goodsList.size()>0){
            for(Goods goods:goodsList){
                int checkinAmount=DataSupport.where("goodsId="+goods.getId()).sum(Checkin.class,"amount",int.class);
                int checkoutAmount=DataSupport.where("goodsId="+goods.getId()).sum(Checkout.class,"amount",int.class);
                Checklist checklist=new Checklist();
                checklist.setGoodsId(goods.getId());
                checklist.setAmount(checkinAmount-checkoutAmount);
                if(DataSupport.where("goodsId="+goods.getId()).find(Checklist.class).size()>0){
                    checklist.update(goods.getId());
                }else {
                    checklist.save();
                }
            }
        }
        return DataSupport.findAll(Checklist.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip_btn:
                openSkipMenu();
                break;
            case R.id.sort_btn:
                openSortMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSkipMenu() {
        final Dialog dialog = new Dialog(this, R.style.NoTitleDialog);
        dialog.setContentView(R.layout.dialog_skip_goods);
        dialog.setCanceledOnTouchOutside(true);
        Button cancel = (Button) dialog.findViewById(R.id.cancleDialog);
        Button ok = (Button) dialog.findViewById(R.id.okDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChecklistActivity.this, "取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChecklistActivity.this, "确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openSortMenu() {
        final Dialog sortDialog = new Dialog(this, R.style.NoTitleDialog);
        sortDialog.setContentView(R.layout.dialog_sort_goods);
        sortDialog.setCanceledOnTouchOutside(true);
        Button sortCancel = (Button) sortDialog.findViewById(R.id.cancleDialog);
        Button sortOk = (Button) sortDialog.findViewById(R.id.okDialog);
        final Map<RelativeLayout, ImageView> sortMethods = new HashMap<>();
        RelativeLayout sortName = (RelativeLayout) sortDialog.findViewById(R.id.sort_name);
        ImageView sortArrowName = (ImageView) sortDialog.findViewById(R.id.sort_arrow_name);
        sortMethods.put(sortName, sortArrowName);
        RelativeLayout sortSku = (RelativeLayout) sortDialog.findViewById(R.id.sort_sku);
        ImageView sortArrowSku = (ImageView) sortDialog.findViewById(R.id.sort_arrow_sku);
        sortMethods.put(sortSku, sortArrowSku);
        RelativeLayout sortTime = (RelativeLayout) sortDialog.findViewById(R.id.sort_time);
        ImageView sortArrowTime = (ImageView) sortDialog.findViewById(R.id.sort_arrow_time);
        sortMethods.put(sortTime, sortArrowTime);
        RelativeLayout sortAmount = (RelativeLayout) sortDialog.findViewById(R.id.sort_amount);
        ImageView sortArrowAmount = (ImageView) sortDialog.findViewById(R.id.sort_arrow_amount);
        sortMethods.put(sortAmount, sortArrowAmount);
        currentSortMethod = sortName;
        sortCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChecklistActivity.this, "取消", Toast.LENGTH_SHORT).show();
                sortDialog.dismiss();
            }
        });
        sortOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChecklistActivity.this, "确定", Toast.LENGTH_SHORT).show();
                sortDialog.dismiss();
            }
        });
        for (final RelativeLayout sortMethod : sortMethods.keySet()) {
            sortMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentSortMethod != sortMethod) {
                        sortMethods.get(currentSortMethod).setVisibility(View.INVISIBLE);
                        sortMethods.get(sortMethod).setVisibility(View.VISIBLE);
                        currentSortMethod = sortMethod;
                    } else {
                        if (sortdesc) {
                            sortdesc = false;
                            sortMethods.get(sortMethod).setBackgroundResource(R.drawable.ic_arrow_up_24dp);
                        } else {
                            sortdesc = true;
                            sortMethods.get(sortMethod).setBackgroundResource(R.drawable.ic_arrow_down_24dp);
                        }
                    }

                }
            });
        }

        sortDialog.show();
    }
}
