package com.sxs.taobaounion.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.sxs.taobaounion.utils.LogUtils;

/**
 * @Author: a797s
 * @Date: 2020/4/26 17:37
 * @Desc:
 */
public class TBNestedScrollView extends NestedScrollView {

    private int mHeadHeight = 0;
    private int originScroll = 0;

    public TBNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public TBNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TBNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeadHeight(int headHeight){
        this.mHeadHeight = headHeight;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (originScroll < mHeadHeight){        // 小于某个控件高度的时
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.originScroll = t;
        LogUtils.d(this, "vertical --- >" + t);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
