package com.baosight.brightfish.ui.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baosight.brightfish.MyApp;
import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Account;
import com.baosight.brightfish.ui.EditActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AccountActivity extends EditActivity {
    MyApp app;
    TextView accountSku,accountName;
    private static final String TAG = "AccountActivity";
    Account account;
    public static void startAccountActivity(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        app= (MyApp) getApplication();
        initToolbar(R.color.colorHome);
        initControls();

        accountSku=(TextView) findViewById(R.id.account_sku);
        accountName=(TextView) findViewById(R.id.account_name);
        initEditText();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_mark:
                Intent intent=new Intent(this,ModifyAccountActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("account",account);
                intent.putExtra("bundle",bundle);
                startActivityForResult(intent,1);
           //   ModifyAccountActivity.startModifyAccountActivity(this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void initEditText(){
        account= app.getAccount();
        if(account!=null) {
            accountSku.setText(account.getSku());
            accountName.setText(account.getName());
            address.setText(account.getAddress());
            telephone.setText(account.getTelephone());
            cellphone.setText(account.getCellphone());
            email.setText(account.getEmail());
            wechat.setText(account.getWechat());
            qq.setText(account.getQq());
            descr.setText(account.getDescr());
            website.setText(account.getWebsite());
            Bitmap bitmap = BitmapFactory.decodeFile(account.getPhoto());
            photo.setImageBitmap(bitmap);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    Log.e(TAG, "onActivityResult: =======================");
                    initEditText();
                }
                break;
        }
    }

}
