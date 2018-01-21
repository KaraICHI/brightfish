package com.baosight.brightfish.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baosight.brightfish.MyApp;
import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Account;
import com.baosight.brightfish.ui.EditActivity;
import com.baosight.brightfish.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ModifyAccountActivity extends EditActivity {
    MyApp app;
    Account account;
    private static final String TAG = "ModifyAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        initToolbar(R.color.colorLightGery);
        initControls();
        account = (Account) getIntent().getBundleExtra("bundle").getSerializable("account");
        if (account != null) {
            initAccount();
        }


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

    private void initAccount() {
        sku.setText(account.getSku());
        name.setText(account.getName());
        address.setText(account.getAddress());
        telephone.setText(account.getTelephone());
        cellphone.setText(account.getCellphone());
        email.setText(account.getEmail());
        wechat.setText(account.getWechat());
        qq.setText(account.getQq());
        descr.setText(account.getDescr());
        Bitmap bitmap = BitmapFactory.decodeFile(account.getPhoto());
        photo.setImageBitmap(bitmap);
    }


    private void saveAccount() {
      //  account = new Account();
        account.setName(name.getText().toString());
        account.setSku(sku.getText().toString());
        account.setCellphone(cellphone.getText().toString());
        account.setTelephone(telephone.getText().toString());
        account.setDescr(descr.getText().toString());
        account.setAddress(address.getText().toString());
        account.setEmail(email.getText().toString());
        account.setQq(qq.getText().toString());
        account.setWechat(wechat.getText().toString());
        account.setWebsite(website.getText().toString());
        if (photoOutputUri != null) {
            account.setPhoto(Uri.parse(getPhotoOutputUri()).getPath());
        }else {
            account.setPhoto("");
        }


    }


    private void commit() {
        if (TextUtils.isEmpty(sku.getText()) || TextUtils.isEmpty(name.getText())) {
            setAlertDialog();
        } else {
            saveAccount();
            modifyOnServer();
           // Toast.makeText(ModifyAccountActivity.this, "更新成功", Toast.LENGTH_SHORT).show();

         //   finish();
        }


    }


    private void modifyOnServer() {
        String address = getString(R.string.path) + "/modify";
        int id = getApplicationContext().getSharedPreferences("userRecent", Context.MODE_PRIVATE).getInt("accountServerId", 0);
        final RequestBody requestBody = new FormBody.Builder().add("id", id + "")
                .add("username", account.getName())
                .add("password",account.getPassword())
                .add("sku", account.getSku())
                .add("address", account.getAddress())
                .add("telephone", account.getTelephone())
                .add("cellphone", account.getCellphone())
                .add("email", account.getEmail())
                .add("wechat", account.getWechat())
                .add("qq", account.getQq())
                .add("website", account.getWebsite())
                .add("descr", account.getDescr())
                .add("photo",account.getPhoto())
                .build();
        HttpUtil.sendOkHttpRequestPut(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "run: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ModifyAccountActivity.this, R.string.net_connect_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string().toString();
                try {
                    JSONObject result = new JSONObject(body);
                    int code = result.getInt("code");
                    final String msg = result.getString("msg");
                    if(code==6){
                        app= (MyApp) getApplication();
                        app.setAccount(account);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("account", account);
                        intent.putExtra("bundle", bundle);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ModifyAccountActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }




}
