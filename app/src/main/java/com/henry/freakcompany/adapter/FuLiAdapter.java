package com.henry.freakcompany.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.henry.freakcompany.R;
import com.henry.freakcompany.model.GanHuoResponse;
import com.henry.freakcompany.ui.activity.FuLiActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import javax.security.auth.login.LoginException;

/**
 * Created by henry on 2016/6/16.
 */
public class FuLiAdapter extends RecyclerArrayAdapter {
    public FuLiAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FuLiViewHolder(parent);
    }

    public static class FuLiViewHolder extends BaseViewHolder<GanHuoResponse.GanHuoModel> {

        ImageView imageView;

        public FuLiViewHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_fuli);
            imageView = $(R.id.image);
        }

        @Override
        public void setData(GanHuoResponse.GanHuoModel data) {
            super.setData(data);
            Glide.with(getContext())
                    .load(data.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }
}
