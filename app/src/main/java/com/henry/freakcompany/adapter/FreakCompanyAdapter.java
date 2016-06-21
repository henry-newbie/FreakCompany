package com.henry.freakcompany.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henry.freakcompany.R;
import com.henry.freakcompany.model.FreakCompanyModel;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by henry on 2016/6/12.
 */
public class FreakCompanyAdapter extends RecyclerArrayAdapter<FreakCompanyModel> {
    public FreakCompanyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreakCompanyViewHolder(parent);
    }

    public static class FreakCompanyViewHolder extends BaseViewHolder<FreakCompanyModel> {
        TextView nameTv;

        TextView desTv;

        public FreakCompanyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_freak_company);
            nameTv = $(R.id.tv_name);
            desTv = $(R.id.tv_des);
        }

        @Override
        public void setData(FreakCompanyModel data) {
            super.setData(data);
            nameTv.setText(data.companyName);
            desTv.setText(data.description);
        }
    }
}
