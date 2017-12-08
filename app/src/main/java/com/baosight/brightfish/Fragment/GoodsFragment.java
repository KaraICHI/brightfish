package com.baosight.brightfish.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baosight.brightfish.R;

/**
 * Created by Administrator on 2017/12/4.
 */

public class GoodsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.frag_tab_goods,container);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected View initView() {
        return null;
    }
}
