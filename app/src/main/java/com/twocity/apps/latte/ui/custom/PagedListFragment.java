package com.twocity.apps.latte.ui.custom;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by twocity on 14-3-24.
 */
public abstract class PagedListFragment<T> extends RicebookListFragment<T> {
  private static final boolean DEBUG = true;
  private PublishSubject<Integer> pagedSubject = PublishSubject.create();
  private Subscription subscription;

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    subscription = setupRequestSubject();
    setupListViewScrollListener();
  }

  protected Subscription setupRequestSubject() {
    return convertObservable(pagedSubject).observeOn(AndroidSchedulers.mainThread())
        .subscribe(this);
  }

  protected void setupListViewScrollListener() {
    final ListView listView = getListView();
    listView.setOnScrollListener(new EndlessScrollListener() {
      @Override public void onLoadMore(int page, int totalItemsCount) {
        onPageLoaded(page, totalItemsCount);
      }
    });
  }

  protected void startLoadingData() {
    pagedSubject.onNext(1);
  }

  protected void onPageLoaded(int page, int totalItemsCount) {
    if (DEBUG) Timber.i("===on page loaded:%d", page);
    pagedSubject.onNext(page);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (subscription != null) subscription.unsubscribe();
  }

  abstract protected Observable<T> convertObservable(Observable<Integer> observable);
}
