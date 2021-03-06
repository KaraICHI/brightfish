package com.baosight.brightfish.ui.checkin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Checkin;
import com.baosight.brightfish.domain.Goods;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.ui.CheckBasicActivity;
import com.baosight.brightfish.ui.goods.NewGoodsActivity;
import com.baosight.brightfish.ui.supplier.NewSupplierActivity;
import com.baosight.brightfish.ui.album.CheckinAblumActivity;
import com.baosight.brightfish.ui.search.choose.ChooseGoodsActivity;
import com.baosight.brightfish.ui.search.choose.ChooseSupplierActivity;
import com.baosight.brightfish.ui.search.choose.ChooseSupplierDialogAdapter;
import com.baosight.brightfish.util.CurrentTimeUtil;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CheckinActivity extends CheckBasicActivity implements View.OnClickListener {
    static EditText supplierSku;
    static EditText supplierName;
    ImageButton supplierMenu, supplierRefesh;
    String checkinPhotoPath;
    public static Goods goods;
    public static Supplier supplier;


    public static void startCheckinActivity(Context context) {
        Intent intent = new Intent(context, CheckinActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        initToolbar(R.color.colorGreen);
        initSupplierPart();
        initControls();
        setCheckLisener();
        CurrentTimeUtil.currentTime=currentTime;
        new CurrentTimeUtil().new TimeThread().start();

    }
    protected void initSupplierPart(){

        supplierName = (EditText) findViewById(R.id.supplier_name_checkin);
        supplierSku = (EditText) findViewById(R.id.supplier_sku_checkin);
        supplierMenu = (ImageButton) findViewById(R.id.check_supplier_menu);
        supplierRefesh = (ImageButton) findViewById(R.id.check_good_refesh);
        supplierMenu.setOnClickListener(this);
        supplierRefesh.setOnClickListener(this);
        editTexts.add(supplierSku);
        editTexts.add(supplierName);

    }


    @Override
    public void onClick(View v) {
        if (v.getClass() == android.support.v7.widget.AppCompatEditText.class) {
            isFocus = true;
            invalidateOptionsMenu();
        }
        switch (v.getId()) {
            case R.id.check_good_menu:
                Intent intent = new Intent(this, ChooseGoodsActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.check_good_refesh:
                goodsSku.setText("");
                goodsName.setText("");
                break;
            case R.id.check_supplier_menu:
                Intent intent2 = new Intent(this, ChooseSupplierActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.check_suplier_refesh:
                supplierSku.setText("");
                supplierName.setText("");
                break;
            case R.id.photo_goods:
                CheckinAblumActivity.startCheckinAblumActivity(CheckinActivity.this);
                break;
            case R.id.checkin_commit:
                    saveCheckin();

                break;
            case R.id.add_new_goods:
                NewGoodsActivity.startGoodsActivity(CheckinActivity.this);
                break;
            case R.id.add_cancel:
                dialog.dismiss();
            case R.id.add_new_supplier:
                NewSupplierActivity.startSupplierActivity(CheckinActivity.this);
                break;
        }
    }


    private boolean isSupplierExist(){
        if (!selectSupplier(supplierSku.getText().toString())) {
            showNoThatSupplierDialog();
            return false;
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    goods = (Goods) data.getBundleExtra("bundle").getSerializable("goods");
                }
            case 2:
                if (resultCode == RESULT_OK) {
                    supplier = (Supplier) data.getBundleExtra("bundle").getSerializable("supplier");
                }
        }
        refesh();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_mark:
                saveCheckin();
                break;
            /*case R.id.blueTooth:
                showBlueToothDialog();
                break;*/
            case R.id.default_param:
                Toast.makeText(CheckinActivity.this, "货品默认参数", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kuaiSao:
                Toast.makeText(CheckinActivity.this, "开启快扫模式", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCheckin() {
        if((!isEditTextBlank()&&isSupplierExist())){
            Checkin checkin = new Checkin();
            checkin.setGoodsId(goods.getId());
            checkin.setPrice(Long.parseLong(price.getText().toString()));
            checkin.setAmount(Integer.parseInt(amount.getText().toString()));
            checkin.setDescr(description.getText().toString());
            checkin.setSupplierId(supplier.getId());
            SimpleDateFormat format=new SimpleDateFormat("yyyy MM月 dd HH:mm");
            checkin.setCheckinDate(format.format(new Date(System.currentTimeMillis())));
            checkin.setPhoto(checkinPhotoPath);
            checkin.save();
            clearEditText();
            supplierSku.setText("");
            supplierName.setText("");
            Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
        }

    }



    private Boolean selectSupplier(String sku) {
        List<Supplier> supplierList = DataSupport.where("sku = ?", sku).find(Supplier.class);
        if (supplierList.size() >= 1) {
            supplier = supplierList.get(0);
            return true;
        } else {
            return false;
        }
    }
    public static void refesh(){
        if (goods != null) {
            goodsSku.setText(goods.getSku());
            goodsName.setText(goods.getName());
        }
        if (supplier != null) {
            supplierSku.setText(supplier.getSku());
            supplierName.setText(supplier.getName());
        }


    }
    protected void showNoThatSupplierDialog() {
        dialog = new Dialog(CheckinActivity.this);
        dialog.setTitle("没有该供应商！");
        dialog.setContentView(R.layout.dialog_no_supplier);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.dialog_rec);
        Button addNew=(Button) dialog.findViewById(R.id.add_new_supplier);
        Button addCancel=(Button) dialog.findViewById(R.id.add_cancel);
        addCancel.setOnClickListener(this);
        addNew.setOnClickListener(this);
        ChooseSupplierDialogAdapter adapter = new ChooseSupplierDialogAdapter(DataSupport.findAll(Supplier.class),dialog);
        LinearLayoutManager layoutManager=new LinearLayoutManager(CheckinActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dialog.show();

    }


}
