package com.henry.freakcompany.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.henry.freakcompany.R;
import com.henry.freakcompany.adapter.MainAdapter;
import com.henry.freakcompany.ui.fragment.GanHuoFragment;
import com.henry.freakcompany.ui.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), initFragment(), titles));

        tabLayout.setupWithViewPager(viewPager);
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
}
