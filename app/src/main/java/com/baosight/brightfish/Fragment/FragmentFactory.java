package com.baosight.brightfish.Fragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/7.
 */

public class FragmentFactory {
    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, BaseFragment>();
    public static BaseFragment createFragment(int pos) {
        BaseFragment baseFragment = mBaseFragments.get(pos);

        if (baseFragment == null) {
            switch (pos) {
                case 0:
                    baseFragment = new GoodsFragment();
                    break;
                case 1:
                    baseFragment = new GoodsFragment();
                    break;
                case 2:
                    baseFragment = new GoodsFragment();
                    break;
                case 3:
                    baseFragment = new GoodsFragment();
                    break;
                default:
                    break;

            }
            mBaseFragments.put(pos, baseFragment);
        }
        return baseFragment;
    }
}
