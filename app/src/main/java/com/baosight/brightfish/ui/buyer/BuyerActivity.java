package com.baosight.brightfish.ui.buyer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.ui.EditActivity;
import com.baosight.brightfish.ui.ModifyBuyerActivity;

import java.util.ArrayList;
import java.util.List;

public class BuyerActivity extends EditActivity {


    private TabLayout mTabLayout;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    View view1, view2, view3, view4;
    Buyer buyer;
    TextView supplierSku, supplierName;

    public static void startBuyerActivity(Context context, Buyer buyer) {
        Intent intent = new Intent(context, BuyerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("buyer", buyer);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        initToolbar(R.color.colorBlue);
        initTab();
        initControls();
        showBuyer();
    }

    protected void initControls() {
        photo = (ImageView) view1.findViewById(R.id.photo);
        selectAblum = (ImageView) view1.findViewById(R.id.select_ablum_btn);
        supplierSku = (TextView) view1.findViewById(R.id.sku_edit);
        supplierName = (TextView) view1.findViewById(R.id.name_edit);
        address = (EditText) view1.findViewById(R.id.address_edit);
        telephone = (EditText) view1.findViewById(R.id.telephone_edit);
        cellphone = (EditText) view1.findViewById(R.id.cellphone_edit);
        email = (EditText) view1.findViewById(R.id.email_edit);
        descr = (EditText) view1.findViewById(R.id.describe_edit);
        qq = (EditText) view1.findViewById(R.id.qq_edit);
        wechat = (EditText) view1.findViewById(R.id.wechat_edit);
        website = (EditText) view1.findViewById(R.id.web_edit);

    }

    @SuppressLint("InflateParams")
    private void initTab() {

        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);

        LayoutInflater mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.frag_tab_buyer, null);
        view2 = mInflater.inflate(R.layout.frag_tab_activity_buyer, null);
        view3 = mInflater.inflate(R.layout.frag_tab_buyer, null);
        view4 = mInflater.inflate(R.layout.frag_tab_buyer, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);

        //添加页卡标题
        mTitleList.add("基本参数");
        mTitleList.add("最近活动");
        mTitleList.add("商家分析");
        mTitleList.add("数据分析");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));

        BuyerActivity.MyPagerAdapter mAdapter = new BuyerActivity.MyPagerAdapter(mViewList);
        assert mViewPager != null;
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

    }


    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_mark:
                ModifyBuyerActivity.startModifyActivity(BuyerActivity.this, buyer);
                break;
            case R.id.delete_btn:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("确定删除");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBuyer(buyer.getId());
                        finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showBuyer() {
        buyer = (Buyer) getIntent().getBundleExtra("bundle").getSerializable("buyer");
        supplierSku.setText(buyer.getSku());
        supplierName.setText(buyer.getName());
        address.setText(buyer.getAddress());
        telephone.setText(buyer.getTelephone());
        cellphone.setText(buyer.getCellphone());
        email.setText(buyer.getEmail());
        wechat.setText(buyer.getWechat());
        website.setText(buyer.getWebsite());
        qq.setText(buyer.getQq());
        descr.setText(buyer.getDescr());
        Bitmap bitmap = BitmapFactory.decodeFile(buyer.getPhoto());
        photo.setImageBitmap(bitmap);
    }
}
