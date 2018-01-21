package com.baosight.brightfish;

import android.app.Application;

import com.baosight.brightfish.domain.Account;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2018/1/15.
 */

public class MyApp extends LitePalApplication{
    private Account account;


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
