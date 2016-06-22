package com.henry.freakcompany.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.henry.freakcompany.R;
import com.henry.freakcompany.adapter.FreakCompanyAdapter;
import com.henry.freakcompany.model.FreakCompanyModel;
import com.henry.freakcompany.model.FreakCompanyResponse;
import com.henry.freakcompany.ui.activity.FreakCompanyDetailActivity;
import com.henry.freakcompany.ui.activity.MainActivity;
import com.henry.freakcompany.utils.FileUtils;
import com.henry.freakcompany.view.EmptyView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
//    private static final String EXTRA_TITLE = "title";

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

//    @BindView(R.id.fab)
//    FloatingActionButton fab;

//    private String title;

    FreakCompanyAdapter freakCompanyAdapter;

    EmptyView emptyView;

    Context context;

    private Handler handler = new Handler();

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle();
//        args.putString(EXTRA_TITLE, title);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            title = getArguments().getString(EXTRA_TITLE);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setRefreshListener(this);

//        emptyView = new EmptyView(context);
//        recyclerView.setEmptyView(emptyView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        freakCompanyAdapter = new FreakCompanyAdapter(context);
        recyclerView.setAdapterWithProgress(freakCompanyAdapter);
        freakCompanyAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FreakCompanyModel model = freakCompanyAdapter.getItem(position);
                FreakCompanyDetailActivity.actionStart(context, model.companyName, model.description);
            }
        });

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setFab(recyclerView.getRecyclerView());
        }

        getData();
        return view;
    }

    private void getData() {
        Observable.create(new Observable.OnSubscribe<FreakCompanyResponse>() {
            @Override
            public void call(Subscriber<? super FreakCompanyResponse> subscriber) {
                FreakCompanyResponse response = getFcData();
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FreakCompanyResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FreakCompanyResponse freakCompanyResponse) {
                        freakCompanyResponse.results.addAll(freakCompanyResponse.results);
                        freakCompanyAdapter.addAll(freakCompanyResponse.results);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * 从asset文件中获取奇葩公司数据
     *
     * @return
     */
    private FreakCompanyResponse getFcData() {
        String fcJson = FileUtils.getFromAssets(context, "freak_company.json");
        Gson gson = new Gson();
        FreakCompanyResponse response = gson.fromJson(fcJson, FreakCompanyResponse.class);
        return response;
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                freakCompanyAdapter.clear();
                getData();
            }
        }, 1000);
    }
}
