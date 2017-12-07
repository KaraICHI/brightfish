package com.baosight.brightfish;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baosight.brightfish.model.Account;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.baosight.brightfish.ModifyAccountActivity.startModifyAccountActivity;

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
        getMenuInflater().inflate(R.menu.edit_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_mark:
              ModifyAccountActivity.startModifyAccountActivity(this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void initEditText(){
        Account account= DataSupport.find(Account.class,1);
        accountSku.setText(account.getSku());
        accountName.setText(account.getName());
        address.setText(account.getAddress());
        telephone.setText(account.getTelephone());
        cellphone.setText(account.getCellphoto());
        email.setText(account.getEmail());
        wechat.setText(account.getWechat());
        qq.setText(account.getQq());
        descr.setText(account.getDescr());
        website.setText(account.getWebsite());


    }
}
