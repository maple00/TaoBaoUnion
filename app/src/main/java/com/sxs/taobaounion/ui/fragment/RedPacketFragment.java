package com.sxs.taobaounion.ui.fragment;

import android.view.View;

import com.sxs.taobaounion.R;
import com.sxs.taobaounion.base.BaseFragment;

/**
 * @Author: a797s
 * @Date: 2020/4/17 10:51
 * @Desc: 特惠
 */
public class RedPacketFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_red_packet;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }
}
