package com.henry.freakcompany.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henry.freakcompany.R;
import com.henry.freakcompany.model.GanHuoResponse;
import com.henry.freakcompany.utils.DateUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by henry on 2016/6/15.
 */
public class GanHuoAdapter extends RecyclerArrayAdapter {

    public GanHuoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GanHuoViewHolder(parent);
    }

    public static class GanHuoViewHolder extends BaseViewHolder<GanHuoResponse.GanHuoModel> {

        TextView titleTv;

        TextView timeTv;

        public GanHuoViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_ganhuo);
            titleTv = $(R.id.tv_title);
            timeTv = $(R.id.tv_time);
        }

        @Override
        public void setData(GanHuoResponse.GanHuoModel data) {
            super.setData(data);
            titleTv.setText(data.getDesc());
            timeTv.setText(DateUtil.friendlyForTime(DateUtil.StringToDate(data.getPublishedAt(), GanHuoResponse.GanHuoModel.DATE_STYLE)));
        }
    }
}
