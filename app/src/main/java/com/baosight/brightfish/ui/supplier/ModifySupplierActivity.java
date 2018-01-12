package com.baosight.brightfish.ui.supplier;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Supplier;
import com.baosight.brightfish.ui.EditActivity;

public class ModifySupplierActivity extends EditActivity implements View.OnClickListener {

    Supplier supplier;

    public static void startModifySupplierActivity(Context context, Supplier supplier) {
        Intent intent = new Intent(context, ModifySupplierActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("supplier", supplier);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_suplier);
        initToolbar(R.color.colorLightGreen);
        initControls();
        showSupplier();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                showCameraDialog();
                break;
            case R.id.select_ablum_btn:
                pictureImageView = photo;
                choiceFromAlbum();
                break;
            case R.id.commit_edit:
                commit();
                break;
            default:
                break;

        }
    }

    private void commit() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        } else  {
            saveSupplier();
            supplier.update(supplier.getId());
            Toast.makeText(ModifySupplierActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }

        clearEditText();


    }

    private void saveSupplier() {
        supplier.setName(name.getText().toString());
        supplier.setSku(sku.getText().toString());
        supplier.setCellphone(cellphone.getText().toString());
        supplier.setTelephone(telephone.getText().toString());
        supplier.setDescr(descr.getText().toString());
        supplier.setAddress(address.getText().toString());
        supplier.setEmail(email.getText().toString());
        supplier.setQq(qq.getText().toString());
        supplier.setWechat(wechat.getText().toString());
        supplier.setWebsite(website.getText().toString());
        if (photoOutputUri != null) {
            supplier.setPhoto(Uri.parse(getPhotoOutputUri()).getPath());

        }

    }

    private void showSupplier() {
        supplier = (Supplier) getIntent().getBundleExtra("bundle").getSerializable("supplier");
        sku.setText(supplier.getSku());
        name.setText(supplier.getName());
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
