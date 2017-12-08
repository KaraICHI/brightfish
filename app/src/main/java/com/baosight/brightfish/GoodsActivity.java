package com.baosight.brightfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baosight.brightfish.model.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsActivity extends BasicActivity implements View.OnClickListener{
    private static final String TAG = "GoodsActivity";
    private TabLayout mTabLayout;
    ImageView photo;
    ImageView selectAblum;
    TextView sku, name;
    EditText  brand, catagory, size, color, spec, descr;
    View view1,view2,view3,view4;
    Goods goods;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    public static void startGoodsActivity(Context context,Goods goods){
        Intent intent=new Intent(context,GoodsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", goods);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initTab();
        initControls();
        initToolbar(R.color.colorOrange);
        showGoods();
    }
    @SuppressLint("InflateParams")
    private void initTab(){

        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);

        LayoutInflater mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.frag_tab_goods, null);
        view2 = mInflater.inflate(R.layout.frag_tab_goods, null);
        view3 = mInflater.inflate(R.layout.frag_tab_goods, null);
        view4 = mInflater.inflate(R.layout.frag_tab_goods, null);

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

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        assert mViewPager != null;
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器


    }
    private void initControls(){
        photo = (ImageView) view1.findViewById(R.id.photo);
        Log.d(TAG, "initControls: =========="+photo);
        selectAblum = (ImageView) view1.findViewById(R.id.select_ablum_btn);
        Log.d(TAG, "initControls: ==========="+selectAblum);
        assert selectAblum != null;
        selectAblum.setOnClickListener(this);
        sku = (TextView) view1.findViewById(R.id.goods_sku);
        name = (TextView) view1.findViewById(R.id.goods_name);
        brand = (EditText) view1.findViewById(R.id.goods_brand);
        catagory = (EditText) view1.findViewById(R.id.goods_cata);
        size = (EditText) view1.findViewById(R.id.goods_size);
        color = (EditText) view1.findViewById(R.id.goods_color);
        spec = (EditText) view1.findViewById(R.id.goods_spec);
        descr = (EditText) view1.findViewById(R.id.goods_descr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_ablum_btn:
                GoodsAblumActivity.startGoodsAblumActivity(GoodsActivity.this);
        }
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
    private void showGoods(){
        goods=(Goods)getIntent().getBundleExtra("bundle").getSerializable("goods");
        sku.setText(goods.getSku());
        name.setText(goods.getName());
        brand.setText(goods.getBrand());
        catagory.setText(goods.getCatagory());
        spec.setText(goods.getSpec());
        size.setText(goods.getSize());
        color.setText(goods.getColor());
        descr.setText(goods.getDescr());
        Log.d(TAG, "showGoods: =================goods photo"+goods.getPhoto());
        Bitmap bitmap = BitmapFactory.decodeFile(goods.getPhoto());
        photo.setImageBitmap(bitmap);

    }




}


