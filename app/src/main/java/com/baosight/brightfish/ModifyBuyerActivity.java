package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.model.Buyer;
import com.baosight.brightfish.model.Goods;

public class ModifyBuyerActivity extends EditActivity {

    Buyer buyer;

    public static void startModifyActivity(Context context, Buyer buyer) {
        Intent intent = new Intent(context, ModifyBuyerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("buyer", buyer);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_new);
        initToolbar(R.color.colorLightBlue);
        initControls();
        showBuyer();
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
            saveBuyer();
            buyer.update(buyer.getId());
            Toast.makeText(ModifyBuyerActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
        }

        clearEditText();


    }

    private void saveBuyer() {
        buyer.setName(name.getText().toString());
        buyer.setSku(sku.getText().toString());
        buyer.setCellphone(cellphone.getText().toString());
        buyer.setTelephone(telephone.getText().toString());
        buyer.setDescr(descr.getText().toString());
        buyer.setAddress(address.getText().toString());
        buyer.setEmail(email.getText().toString());
        buyer.setQq(qq.getText().toString());
        buyer.setWechat(wechat.getText().toString());
        buyer.setWebsite(website.getText().toString());
        if (photoOutputUri != null) {
            buyer.setPhoto(Uri.parse(getPhotoOutputUri()).getPath());

        }

    }

    private void showBuyer(){
        buyer=(Buyer)getIntent().getBundleExtra("bundle").getSerializable("buyer");
        sku.setText(buyer.getSku());
        name.setText(buyer.getName());
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
