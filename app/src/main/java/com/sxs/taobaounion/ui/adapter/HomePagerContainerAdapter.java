package com.sxs.taobaounion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sxs.taobaounion.R;
import com.sxs.taobaounion.model.domain.HomePagerBean;
import com.sxs.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: a797s
 * @Date: 2020/4/26 9:35
 * @Desc: 首页轮播图
 */
public class HomePagerContainerAdapter extends RecyclerView.Adapter<HomePagerContainerAdapter.ViewHolder> {

    List<HomePagerBean.DataBean> mData = new ArrayList<>();


    @NonNull
    @Override
    public HomePagerContainerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerContainerAdapter.ViewHolder holder, int position) {
        // 数据绑定
        HomePagerBean.DataBean dataBean = mData.get(position);
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<HomePagerBean.DataBean> contents) {
        mData.clear();
        mData.addAll(contents);
        notifyDataSetChanged();
    }

    public void addData(List<HomePagerBean.DataBean> contents) {
        int oldSize = mData.size();
        mData.addAll(contents);
        // 更新局部UI
        notifyItemRangeChanged(oldSize, contents.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        public TextView title;

        @BindView(R.id.iv_goods_cover)
        public ImageView goodsCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(HomePagerBean.DataBean dataBean) {
            title.setText(dataBean.getTitle());
            ViewGroup.LayoutParams layoutParams = goodsCover.getLayoutParams();
            int height = layoutParams.height;
            int width = layoutParams.width;
            int imgSize = (width > height ? width : height) / 2;
            Glide.with(itemView.getContext()).load(UrlUtils.getImagePath(dataBean.getPict_url(), imgSize)).into(goodsCover);
        }
    }
}
