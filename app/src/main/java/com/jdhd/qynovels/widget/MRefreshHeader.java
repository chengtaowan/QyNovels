package com.jdhd.qynovels.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;

import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class MRefreshHeader extends LinearLayout implements RefreshHeader {
    private ImageView img;
    private int type;
    public MRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public MRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_irecyclerview_refresh_header, this);
        img = view.findViewById(R.id.header);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            //1,下拉刷新的开始状态：下拉可以刷新
            case PullDownToRefresh:
                Glide.with(MyApp.getAppContext()).load(R.mipmap.re).into(img);
                break;
            //2,下拉到最底部的状态：释放立即刷新
            case ReleaseToRefresh:
                Glide.with(MyApp.getAppContext()).load(R.mipmap.re).into(img);
                break;
            //3,下拉到最底部后松手的状态：正在刷新
            case RefreshFinish:
                Glide.with(MyApp.getAppContext()).load(R.mipmap.re).into(img);
                break;
        }
    }
}
