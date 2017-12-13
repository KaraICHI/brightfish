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
        import android.view.LayoutInflater;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
        import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.TextView;

 import com.baosight.brightfish.model.Supplier;

        import java.util.ArrayList;
        import java.util.List;


public class SupplierActivity extends EditActivity {
    private TabLayout mTabLayout;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    View view1,view2,view3,view4;
    Supplier supplier;
    TextView supplierSku,supplierName;

    public static void startSupplierActivity(Context context, Supplier supplier){
        Intent intent=new Intent(context,SupplierActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("supplier",supplier);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        initToolbar(R.color.colorGreen);
        initTab();
        initControls();
        showSupplier();
    }

    protected void initControls() {
        photo = (ImageView) view1.findViewById(R.id.photo);
        selectAblum = (ImageView) view1.findViewById(R.id.select_ablum_btn);
        supplierSku=(TextView) view1.findViewById(R.id.sku_edit);
        supplierName=(TextView) view1.findViewById(R.id.name_edit);
        address=(EditText) view1.findViewById(R.id.address_edit);
        telephone=(EditText) view1.findViewById(R.id.telephone_edit);
        cellphone=(EditText)view1.findViewById(R.id.cellphone_edit);
        email=(EditText) view1.findViewById(R.id.email_edit);
        descr=(EditText) view1.findViewById(R.id.describe_edit);
        qq=(EditText) view1.findViewById(R.id.qq_edit);
        wechat=(EditText) view1.findViewById(R.id.wechat_edit);
        website=(EditText) view1.findViewById(R.id.web_edit);

    }

    @SuppressLint("InflateParams")
    private void initTab(){

        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);

        LayoutInflater mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.frag_tab_supplier, null);
        view2 = mInflater.inflate(R.layout.frag_tab_supplier, null);
        view3 = mInflater.inflate(R.layout.frag_tab_supplier, null);
        view4 = mInflater.inflate(R.layout.frag_tab_supplier, null);

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

        SupplierActivity.MyPagerAdapter mAdapter = new SupplierActivity.MyPagerAdapter(mViewList);
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
                ModifySupplierActivity.startModifySupplierActivity(SupplierActivity.this,supplier);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showSupplier(){
        supplier=(Supplier)getIntent().getBundleExtra("bundle").getSerializable("supplier");
        supplierSku.setText(supplier.getSku());
        supplierName.setText(supplier.getName());
        address.setText(supplier.getAddress());
        telephone.setText(supplier.getTelephone());
        cellphone.setText(supplier.getCellphone());
        email.setText(supplier.getEmail());
        wechat.setText(supplier.getWechat());
        website.setText(supplier.getWebsite());
        qq.setText(supplier.getQq());
        descr.setText(supplier.getDescr());
        Bitmap bitmap = BitmapFactory.decodeFile(supplier.getPhoto());
        photo.setImageBitmap(bitmap);
    }

}
