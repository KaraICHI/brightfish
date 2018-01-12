package com.baosight.brightfish.ui.search.choose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkout;
import com.baosight.brightfish.ui.BasicActivity;
import com.baosight.brightfish.ui.checkout.CheckoutAdapter;
import com.baosight.brightfish.ui.checkout.CheckoutNoteActivity;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseCheckoutActivity extends BasicActivity {
    RelativeLayout currentSortMethod;
    boolean sortdesc;
    List<Checkout> checkoutList= DataSupport.findAll(Checkout.class);
    SwipeMenuListView listView;
    CheckoutAdapter adapter;
    private static final String TAG = "ChooseCheckoutActivity";

    public static void startChooseCheckoutActivity(Context context) {
        Intent intent = new Intent(context, ChooseCheckoutActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_checkout);
        initControls();

    }

    private void initControls() {
        initToolbar(R.color.colorBlue);
        listView = (SwipeMenuListView) findViewById(R.id.check_list);

        adapter=new CheckoutAdapter(this,R.layout.item_checkin_note,checkoutList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listView.setMenuCreator(getSlideMenuCreator());
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        CheckoutNoteActivity.startCheckoutNoteActivity(ChooseCheckoutActivity.this,checkoutList.get(position));
                        break;
                    case 1:
                        checkoutList.remove(position);
                        DataSupport.delete(Checkout.class,position+1);
                        adapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
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
        RelativeLayout sortPrice = (RelativeLayout) sortDialog.findViewById(R.id.sort_price);
        ImageView sortArrowPriceIn = (ImageView) sortDialog.findViewById(R.id.sort_arrow_price_in);
        sortMethods.put(sortPrice, sortArrowPriceIn);
        RelativeLayout sortTime = (RelativeLayout) sortDialog.findViewById(R.id.sort_time);
        ImageView sortArrowTime = (ImageView) sortDialog.findViewById(R.id.sort_arrow_time);
        sortMethods.put(sortTime, sortArrowTime);
        final RelativeLayout sortAmount = (RelativeLayout) sortDialog.findViewById(R.id.sort_amount);
        ImageView sortArrowAmount = (ImageView) sortDialog.findViewById(R.id.sort_arrow_amount);
        sortMethods.put(sortAmount, sortArrowAmount);
        currentSortMethod = sortAmount;
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
                if(currentSortMethod==sortAmount){
                    if(sortdesc){
                        checkoutList.clear();
                        checkoutList.addAll(DataSupport.order("amount desc").find(Checkout.class));
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onClick: ==============="+checkoutList.get(0).getAmount());

                    }else {
                        checkoutList.clear();
                        checkoutList.addAll(DataSupport.order("amount asc").find(Checkout.class));
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onClick: ================"+checkoutList.get(0).getAmount());
                    }


                }

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
