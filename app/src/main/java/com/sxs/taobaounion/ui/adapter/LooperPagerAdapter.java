package com.sxs.taobaounion.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.sxs.taobaounion.model.domain.HomePagerBean;
import com.sxs.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: a797s
 * @Date: 2020/4/26 10:34
 * @Desc: 轮播图adapter
 */
public final class LooperPagerAdapter extends PagerAdapter {

    List<HomePagerBean.DataBean> mData = new ArrayList<>();

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 处理越界问题
        int realPosition = position % mData.size();

        HomePagerBean.DataBean dataBean = mData.get(realPosition);
        String imagePath = UrlUtils.getImagePath(dataBean.getPict_url());
        ImageView imageView = new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(imagePath).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<HomePagerBean.DataBean> contents) {
        mData.clear();
        mData.addAll(contents);
        notifyDataSetChanged();
    }

    public int getDataSize(){
        return mData.size();
    }
}
