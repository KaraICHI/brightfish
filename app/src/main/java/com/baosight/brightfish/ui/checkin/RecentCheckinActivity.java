package com.baosight.brightfish.ui.checkin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.ui.BasicActivity;
import com.baosight.brightfish.ui.CheckAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecentCheckinActivity extends BasicActivity {

    private static final String TAG = "ChooseCheckinActivity";
    RelativeLayout currentSortMethod;
    boolean sortdesc;
    static List<Checkin> mCheckinList;
    SwipeMenuListView listView;
    CheckAdapter adapter;

    public static void startRecentCheckinActivity(Context context,List<Checkin> checkinList) {
        Intent intent = new Intent(context, RecentCheckinActivity.class);
        context.startActivity(intent);
        mCheckinList=checkinList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_checkin);
        initControls();

    }

    private void initControls() {
        initToolbar(R.color.colorGreen);
        listView = (SwipeMenuListView) findViewById(R.id.check_list);

        adapter=new CheckAdapter(this,R.layout.item_checkin_note,mCheckinList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(180);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_white_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        CheckinNoteActivity.startCheckinNoteActivity(RecentCheckinActivity.this,mCheckinList.get(position));
                        break;
                    case 1:
                        mCheckinList.remove(position);
                        DataSupport.delete(Checkin.class,position+1);
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
        dialog.setContentView(R.layout.dialog_skip_checkin);
        dialog.setCanceledOnTouchOutside(true);
        Button cancel = (Button) dialog.findViewById(R.id.cancleDialog);
        Button ok = (Button) dialog.findViewById(R.id.okDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecentCheckinActivity.this, "取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RecentCheckinActivity.this, "确定", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openSortMenu() {
        final Dialog sortDialog = new Dialog(this, R.style.NoTitleDialog);
        sortDialog.setContentView(R.layout.dialog_sort_checkin);
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
                Toast.makeText(RecentCheckinActivity.this, "取消", Toast.LENGTH_SHORT).show();
                sortDialog.dismiss();
            }
        });
        sortOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecentCheckinActivity.this, "确定", Toast.LENGTH_SHORT).show();
                if(currentSortMethod==sortAmount){
                    if(sortdesc){
                        mCheckinList.clear();
                        mCheckinList.addAll(DataSupport.order("amount desc").find(Checkin.class));
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onClick: ==============="+mCheckinList.get(0).getAmount());

                    }else {
                        mCheckinList.clear();
                        mCheckinList.addAll(DataSupport.order("amount asc").find(Checkin.class));
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onClick: ================"+mCheckinList.get(0).getAmount());
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
