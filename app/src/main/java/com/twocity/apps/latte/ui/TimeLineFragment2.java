package com.twocity.apps.latte.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.twocity.apps.latte.LatteApp;
import com.twocity.apps.latte.data.api.WeiboService;
import com.twocity.apps.latte.data.api.model.Statueses;
import com.twocity.apps.latte.data.api.model.Status;
import com.twocity.apps.latte.data.api.model.Statuses2List;
import com.twocity.apps.latte.data.api.model.TimeLineQueryMapBuilder;
import com.twocity.apps.latte.ui.custom.PagedListFragment;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.Observable;
import rx.util.functions.Func1;

/**
 * Created by twocity on 14-3-24.
 */
public class TimeLineFragment2 extends PagedListFragment<List<Status>> {

  @Inject WeiboService weiboService;
  private TimeLineAdapter adapter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LatteApp.get(getActivity()).inject(this);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupEmptyViews();
    setupErrorViews();
    adapter = new TimeLineAdapter(getActivity(), null);
    getListView().setAdapter(adapter);
    startLoadingData();
  }

  private void setupErrorViews() {
    ViewGroup errorContainer = getErrorContainer();
    Button button = new Button(getActivity());
    errorContainer.addView(button);
    button.setText("retry");
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(getActivity(), "retrying", Toast.LENGTH_LONG).show();
        startLoadingData();
      }
    });
  }

  private void setupEmptyViews() {
    ViewGroup emptyContainer = getEmptyContainer();
    TextView text = new TextView(getActivity());
    emptyContainer.addView(text);
    text.setText("empty");
  }

  @Override protected Observable<List<Status>> convertObservable(Observable<Integer> observable) {
    return observable.flatMap(new Func1<Integer, Observable<Statueses>>() {
      @Override public Observable<Statueses> call(Integer page) {
        //Timber.i("-----Page:%d", page);
        final long maxId = adapter.getLastId();
        Map<String, String> queryMap = new TimeLineQueryMapBuilder().maxId(maxId).build();
        return weiboService.homeTimeLineRx(queryMap);
      }
    }).map(new Statuses2List());
  }

  public void onError(Throwable exception) {
    displayErrorView();
  }

  public void onNext(List<Status> list) {
    if (list != null && !list.isEmpty()) {
      displayListView();
      adapter.addAll(list);
    } else {
      displayEmptyView();
    }
  }

  private void test() {
    ViewSwitcher switcher = new ViewSwitcher(getActivity());
    switcher.setFactory(new ViewSwitcher.ViewFactory() {
      @Override public View makeView() {
        return new TextView(getActivity());
      }
    });
  }
}
