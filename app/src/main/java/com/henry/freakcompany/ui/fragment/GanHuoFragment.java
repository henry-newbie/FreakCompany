package com.henry.freakcompany.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.henry.freakcompany.R;
import com.henry.freakcompany.adapter.FuLiAdapter;
import com.henry.freakcompany.adapter.GanHuoAdapter;
import com.henry.freakcompany.model.GanHuoResponse;
import com.henry.freakcompany.retrofit.GankService;
import com.henry.freakcompany.retrofit.RetrofitFactory;
import com.henry.freakcompany.ui.activity.FuLiActivity;
import com.henry.freakcompany.ui.activity.GanHuoActivity;
import com.henry.freakcompany.utils.NetWorkUtils;
import com.henry.freakcompany.view.EmptyView;
import com.henry.freakcompany.view.FooterErrorView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 干货fragment和福利fragment
 */
public class GanHuoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    public static final int PAGE_COUNT = 10;    //  一页十条数据

    Context context;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

//    GanHuoAdapter ganHuoAdapter;
//
//    FuLiAdapter fuLiAdapter;

    RecyclerArrayAdapter<GanHuoResponse.GanHuoModel> adapter;

    int page = 1;                               // 页数

    private Handler handler = new Handler();

    EmptyView emptyView;                        // 展示错误界面的控件，第一次加载数据出错时显示

    FooterErrorView footerErrorView;            // 列表尾部加载更多数据出错的控件

    public static final int TYPE_FIRST = 1;             // 第一次加载数据

    public static final int TYPE_REFRESH = 2;           // 下拉刷新

    public static final int TYPE_LOAD_MORE = 3;         // 加载更多

    public static final int ERROR_NO_NETWORK = 1;       // 没有网络

    public static final int ERROR_ERROR = 2;            // 调接口出错

    public static final int TYPE_GANHUO = 1;            // 干货

    public static final int TYPE_FULI = 2;              // 福利

    int fraType;

    public GanHuoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GanHuoFragment.
     */
    public static GanHuoFragment newInstance(int fraType) {
        GanHuoFragment fragment = new GanHuoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("fraType", fraType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        fraType = bundle.getInt("fraType");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gan_huo, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setRefreshListener(this);

        if(fraType == TYPE_GANHUO) {    // 干货
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new GanHuoAdapter(context);

            // 分割线间距
            DividerDecoration dividerDecoration = new DividerDecoration(Color.TRANSPARENT, 20);
            recyclerView.addItemDecoration(dividerDecoration);
        } else if(fraType == TYPE_FULI) {   // 福利
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            adapter = new FuLiAdapter(getContext());

            // 分割线间距
            SpaceDecoration spaceDecoration = new SpaceDecoration(5);
            recyclerView.addItemDecoration(spaceDecoration);
        }

        initView();

        getData(TYPE_FIRST);
        return view;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        recyclerView.setAdapterWithProgress(adapter);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GanHuoResponse.GanHuoModel model = adapter.getItem(position);
                if(fraType == TYPE_GANHUO) {
                    GanHuoActivity.actionStart(context, model.getDesc(), model.getUrl());
                } else if(fraType == TYPE_FULI) {
                    FuLiActivity.actionStart(context, model.getUrl());
                }
            }
        });

        emptyView = new EmptyView(context);
        recyclerView.setEmptyView(emptyView);

        // 设置加载更多视图
        adapter.setMore(R.layout.layout_load_more, this);
        // 设置加载完成视图
        adapter.setNoMore(R.layout.layout_no_more);
        footerErrorView = new FooterErrorView(context);
        footerErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        // 设置加载出错视图
        adapter.setError(footerErrorView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * 获取干货数据
     * @param type
     */
    private void getData(final int type) {
        // 判断网络
        if(!NetWorkUtils.isConnected(context)) {
            recyclerView.setRefreshing(false);
            showError(type, ERROR_NO_NETWORK);
            return;
        }
        String dataType = null;
        if(fraType == TYPE_GANHUO) {
            dataType = "Android";
        } else if(fraType == TYPE_FULI) {
            dataType = "福利";
        }
        RetrofitFactory.getRetrofit()
                .create(GankService.class)
                .getGanHuo(dataType, PAGE_COUNT, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GanHuoResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", "onError");
                        showError(type, ERROR_ERROR);
                    }

                    @Override
                    public void onNext(GanHuoResponse ganHuoResponse) {
                        if(ganHuoResponse.error) {
                            // 调接口失败
                            showError(type, ERROR_ERROR);
                            return;
                        }
                        // 刷新要清空原来的数据
                        if(type == TYPE_REFRESH) {
                            adapter.clear();
                        }
                        adapter.addAll(ganHuoResponse.getResults());
                        // 数据全部加载完成
                        List<GanHuoResponse.GanHuoModel> list = ganHuoResponse.getResults();
                        if(list == null || list.size() < 10) {
                            adapter.stopMore();
                        }
                    }
                });
    }

    /**
     * 展示错误界面
     * @param type
     * @param error
     */
    private void showError(int type, int error) {
        switch (type) {
            case TYPE_FIRST:
                recyclerView.showEmpty();
                if(error == ERROR_NO_NETWORK) {
                    emptyView.setType(EmptyView.Type.NO_NETWORK);
                } else if(error == ERROR_ERROR) {
                    emptyView.setType(EmptyView.Type.ERROR);
                }
                break;
            case TYPE_REFRESH:
                Snackbar snackbar = getSnackbar(recyclerView);
                if(error == ERROR_NO_NETWORK) {
                    snackbar.setText("呜呜，没有网络啦！");
                } else if(error ==ERROR_ERROR) {
                    snackbar.setText("呜呜，没有出错啦！");
                }
                snackbar.show();
                break;
            case TYPE_LOAD_MORE:
                if(error == ERROR_NO_NETWORK) {
                    footerErrorView.setType(FooterErrorView.Type.NO_NETWORK);
                } else if(error == ERROR_ERROR) {
                    footerErrorView.setType(FooterErrorView.Type.ERROR);
                }
                adapter.pauseMore();
                break;
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                getData(TYPE_REFRESH);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        Log.e("onLoadMore", "onLoadMore");
        page ++;
        getData(TYPE_LOAD_MORE);
    }

    private Snackbar getSnackbar(View view) {
        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
        snackbar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        return snackbar;
    }
}
