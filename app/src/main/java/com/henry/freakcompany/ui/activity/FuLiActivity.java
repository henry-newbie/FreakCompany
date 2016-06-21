package com.henry.freakcompany.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.henry.freakcompany.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FuLiActivity extends AppCompatActivity {

    public static final String TRANSIT_VIEW = "transit_view";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_fuli)
    ImageView ivFuli;

    String url;

    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fu_li);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        url = getIntent().getStringExtra("url");

        photoViewAttacher = new PhotoViewAttacher(ivFuli);

        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        ivFuli.setImageDrawable(resource);
                        photoViewAttacher.update();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Activity context, String url, View transitView) {
        Intent intent = new Intent(context, FuLiActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, transitView, TRANSIT_VIEW);
        intent.putExtra("url", url);
        ActivityCompat.startActivity(context, intent, activityOptionsCompat.toBundle());
    }

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, FuLiActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}
