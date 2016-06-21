package com.henry.freakcompany.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.henry.freakcompany.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by henry on 2016/6/12.
 * 用于展示无数据，错误界面，没有网络
 */
public class EmptyView extends FrameLayout {

    public enum Type {NO_NETWORK, NO_DATA, ERROR}

    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_no_network)
    TextView tvNoNetwork;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.layout_empty, this);
        ButterKnife.bind(this, view);
    }

    public void setType(Type type) {
        switch (type) {
            case NO_NETWORK:
                tvNoNetwork.setVisibility(VISIBLE);
                tvNoData.setVisibility(GONE);
                tvError.setVisibility(GONE);
                break;
            case NO_DATA:
                tvNoNetwork.setVisibility(GONE);
                tvNoData.setVisibility(VISIBLE);
                tvError.setVisibility(GONE);
                break;
            case ERROR:
                tvNoNetwork.setVisibility(GONE);
                tvNoData.setVisibility(GONE);
                tvError.setVisibility(VISIBLE);
                break;
        }
    }
}
