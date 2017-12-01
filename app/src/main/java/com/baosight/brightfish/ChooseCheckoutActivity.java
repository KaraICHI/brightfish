package com.baosight.brightfish;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ChooseCheckoutActivity extends AppCompatActivity {

    RelativeLayout currentSortMethod;
    boolean sortdesc;
    Toolbar toolbar;

    public static void startChooseCheckoutActivity(Context context) {
        Intent intent = new Intent(context, ChooseCheckoutActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_checkin);
        initControls();

    }

    private void initControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        dialog.setContentView(R.layout.dialog_skip_checkout);
        dialog.setCanceledOnTouchOutside(true);
        Button cancel = (Button) dialog.findViewById(R.id.cancleDialog);
        Button ok = (Button) dialog.findViewById(R.id.okDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCheckoutActivity.this, "取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCheckoutActivity.this, "确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openSortMenu() {
        final Dialog sortDialog = new Dialog(this, R.style.NoTitleDialog);
        sortDialog.setContentView(R.layout.dialog_sort_checkout);
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
        RelativeLayout sortPriceIn = (RelativeLayout) sortDialog.findViewById(R.id.sort_price_in);
        ImageView sortArrowPriceIn = (ImageView) sortDialog.findViewById(R.id.sort_arrow_price_in);
        sortMethods.put(sortPriceIn, sortArrowPriceIn);
        RelativeLayout sortPriceOut = (RelativeLayout) sortDialog.findViewById(R.id.sort_price_out);
        ImageView sortArrowPriceOut = (ImageView) sortDialog.findViewById(R.id.sort_arrow_price_out);
        sortMethods.put(sortPriceOut, sortArrowPriceOut);
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
                Toast.makeText(ChooseCheckoutActivity.this, "取消", Toast.LENGTH_SHORT).show();
                sortDialog.dismiss();
            }
        });
        sortOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCheckoutActivity.this, "确定", Toast.LENGTH_SHORT).show();
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
