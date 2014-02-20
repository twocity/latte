package com.twocity.apps.latte.ui;

import com.twocity.apps.latte.LatteApp;
import com.twocity.apps.latte.R;
import com.twocity.apps.latte.data.api.WeiboService;
import com.twocity.apps.latte.data.api.model.Statueses;
import com.twocity.apps.latte.data.api.model.Status;
import com.twocity.apps.latte.data.api.model.Statuses2List;
import com.twocity.apps.latte.data.api.model.TimeLineQueryMapBuilder;
import com.twocity.apps.latte.ui.custom.EndlessScrollListener;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.util.functions.Func1;
import timber.log.Timber;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;


public class TimeLineFragment extends ListFragment implements OnRefreshListener {

    private WeiboService mWeiboService;

    private Subscription mSubscription;

    private Subscription mRefreshSubscription;

    private TimeLineAdapter mAdapter;

    private PublishSubject<Map<String, String>> mSubject = PublishSubject.create();

    private PullToRefreshLayout mPullToRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LatteApp app = LatteApp.get(getActivity());
        mWeiboService = app.getApiClient().getWeiboService();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO fix WindowLeaked exception when rotate device
        // setup pulltorefresh staff
        ViewGroup group = (ViewGroup) view;
        mPullToRefreshLayout = new PullToRefreshLayout(group.getContext());
        ActionBarPullToRefresh.from(getActivity())
                .insertLayoutInto(group)
                .theseChildrenArePullable(getListView(), getListView().getEmptyView())
                .listener(this)
                .setup(mPullToRefreshLayout);

        mAdapter = new TimeLineAdapter(getActivity(), null);
        getListView().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                final long maxId = mAdapter.getLastId();
                mSubject.onNext(new TimeLineQueryMapBuilder().maxId(maxId).build());
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSubscription = setup();
        mSubject.onNext(new TimeLineQueryMapBuilder().build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(getActivity(), PostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.time_line, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private Subscription setup() {
        return mSubject.flatMap(new Func1<Map<String, String>, Observable<Statueses>>() {
            @Override
            public Observable<Statueses> call(Map<String, String> map) {
                return mWeiboService.homeTimeLineRx(map).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        }).map(new Statuses2List())
                .subscribe(timelineObserver);
    }

    final private Observer<List<Status>> timelineObserver = new Observer<List<Status>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(List<Status> list) {
            if (getListAdapter() == null) {
                setListAdapter(mAdapter);
            }
            Timber.i("add list size: %d", list == null ? -1
                    : list.size());
            mAdapter.addAll(list);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRefreshSubscription != null) {
            mRefreshSubscription.unsubscribe();
        }
        mSubscription.unsubscribe();
    }

    @Override
    public void onRefreshStarted(View view) {
        final long sinceId = mAdapter.getHeaderId();
        Map<String, String> queryMap = new TimeLineQueryMapBuilder().sinceId(sinceId).maxId(0)
                .build();
        // TODO there must be some better way to do this.
        if (mRefreshSubscription != null) {
            mRefreshSubscription.unsubscribe();
            mRefreshSubscription = null;
        }
        mRefreshSubscription = mWeiboService.homeTimeLineRx(queryMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Statuses2List())
                .subscribe(new Observer<List<Status>>() {
                    @Override
                    public void onCompleted() {
                        mPullToRefreshLayout.setRefreshComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPullToRefreshLayout.setRefreshComplete();
                    }

                    @Override
                    public void onNext(List<Status> list) {
                        int size = list == null ? 0 : list.size();
                        Timber.i("add new list size %d", size);
                        mPullToRefreshLayout.setRefreshComplete();
                        mAdapter.addHeader(list);
                    }
                });
    }

}