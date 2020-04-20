package com.sxs.taobaounion.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sxs.taobaounion.R;
import com.sxs.taobaounion.base.BaseFragment;
import com.sxs.taobaounion.ui.fragment.HomeFragment;
import com.sxs.taobaounion.ui.fragment.RedPacketFragment;
import com.sxs.taobaounion.ui.fragment.SearchFragment;
import com.sxs.taobaounion.ui.fragment.SelectedFragment;
import com.sxs.taobaounion.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.bnv_bar)
    BottomNavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private RedPacketFragment mRedPacketFragment;
    private SearchFragment mSearchFragment;
    private SelectedFragment mSelectedFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // annotation bind
       // ViewBind.inject(this);
        ButterKnife.bind(this);
        initListener();
        mHomeFragment = new HomeFragment();
        mRedPacketFragment = new RedPacketFragment();
        mSearchFragment = new SearchFragment();
        mSelectedFragment = new SelectedFragment();
        mFragmentManager = getSupportFragmentManager();
        switchFragment(mHomeFragment);
    }

    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.text_home:
                    LogUtils.d(MainActivity.this, "切换到首页");
                    switchFragment(mHomeFragment);
                    break;
                case R.id.text_selected:
                    LogUtils.i(MainActivity.this, "切换到精选");
                    switchFragment(mSelectedFragment);
                    break;
                case R.id.text_packet:
                    LogUtils.w(MainActivity.this, "切换到特惠");
                    switchFragment(mRedPacketFragment);
                    break;
                case R.id.text_search:
                    LogUtils.e(MainActivity.this, "切换到搜索");
                    switchFragment(mSearchFragment);
                    break;
            }
            return true;
        });
    }

    private void switchFragment(BaseFragment targetFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 替换fragment
        transaction.replace(R.id.fl_container, targetFragment);
        transaction.commit();
    }

}
