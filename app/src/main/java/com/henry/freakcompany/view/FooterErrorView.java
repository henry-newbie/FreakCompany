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
 * Created by henry on 2016/6/16.
 */
public class FooterErrorView extends FrameLayout {

    public enum Type {NO_NETWORK, ERROR}

    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_no_network)
    TextView tvNoNetwork;

    public FooterErrorView(Context context) {
        super(context);
        init(context);
    }

    public FooterErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FooterErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.layout_error, this);
        ButterKnife.bind(this, view);
    }

    public void setType(Type type) {
        switch (type) {
            case NO_NETWORK:
                tvNoNetwork.setVisibility(VISIBLE);
                tvError.setVisibility(GONE);
                break;
            case ERROR:
                tvNoNetwork.setVisibility(GONE);
                tvError.setVisibility(VISIBLE);
                break;
        }
    }
}
