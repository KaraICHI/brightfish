package com.baosight.brightfish.ui.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baosight.brightfish.R;
import com.baosight.brightfish.domain.Account;
import com.baosight.brightfish.ui.EditActivity;

import org.litepal.crud.DataSupport;

public class AccountActivity extends EditActivity {
    TextView accountSku,accountName;
    public static void startAccountActivity(Context context) {
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
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
                startActivityForResult(new Intent(this,ModifyAccountActivity.class),1);
           //   ModifyAccountActivity.startModifyAccountActivity(this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void initEditText(){
        int id=getApplicationContext().getSharedPreferences("userRecent",Context.MODE_PRIVATE).getInt("accountId",0);
        Account account= DataSupport.find(Account.class,id);
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
                if(requestCode==RESULT_OK){

                }
        }
    }
}
