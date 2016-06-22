package com.henry.freakcompany.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.henry.freakcompany.R;
import com.henry.freakcompany.adapter.MainAdapter;
import com.henry.freakcompany.ui.fragment.GanHuoFragment;
import com.henry.freakcompany.ui.fragment.MainFragment;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    String[] titles = {"奇葩公司", "干货", "福利"};

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        setSupportActionBar(toolbar);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), initFragment(), titles));

        viewPager.addOnPageChangeListener(getOnPageChangeListener());

        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GanHuoActivity.actionStart(context, "提交", "https://github.com/henry-newbie/FreakCompany/issues/new");
            }
        });

        // 引导界面
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("如果你求职碰到了奇葩公司，先提交到GitHub的issues里吧！")
                .setTarget(fab)
                .setUsageId("submit")
                .show();
    }

    private List<Fragment> initFragment() {
        List<Fragment> list = new ArrayList<>(3);
//        for (int i = 0; i < 3; i ++) {
//            list.add(MainFragment.newInstance(titles[i]));
//        }
        list.add(MainFragment.newInstance());
        list.add(GanHuoFragment.newInstance(GanHuoFragment.TYPE_GANHUO));
        list.add(GanHuoFragment.newInstance(GanHuoFragment.TYPE_FULI));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about_app:
                AboutAppActivity.actionStart(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.show(true);
                } else {
                    fab.hide(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    public void setFab(RecyclerView recyclerView) {
        fab.attachToRecyclerView(recyclerView);
    }
}
