package com.twocity.apps.latte.ui.custom;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.twocity.apps.latte.R;
import rx.Observer;

/**
 * Created by twocity on 14-3-24.
 */
public abstract class RicebookListFragment<T> extends Fragment implements Observer<T> {

  private BetterViewAnimator parentView;
  private ListView listView;
  private ViewGroup emptyContainer;
  private ViewGroup errorContainer;
  private View progressBar;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    parentView = (BetterViewAnimator) inflater.inflate(R.layout.common_fragment_list, null);
    return parentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ensureViews();
  }

  // make sure that ours view are not null.
  private void ensureViews() {
    View view = getView();
    if (listView == null) listView = (ListView) view.findViewById(R.id.ricebook_listview);
    if (emptyContainer == null) {
      emptyContainer = (ViewGroup) view.findViewById(R.id.empty_container);
    }
    if (errorContainer == null) {
      errorContainer = (ViewGroup) view.findViewById(R.id.error_container);
    }
    if (progressBar == null) {
      progressBar = view.findViewById(R.id.ricebook_progressbar);
    }
  }

  protected ListView getListView() {
    ensureViews();
    return listView;
  }

  protected ViewGroup getEmptyContainer() {
    ensureViews();
    return emptyContainer;
  }

  protected ViewGroup getErrorContainer() {
    ensureViews();
    return errorContainer;
  }

  protected View getProgressBar() {
    ensureViews();
    return progressBar;
  }

  protected void displayProgressBar() {
    parentView.setDisplayedChildId(R.id.ricebook_progressbar);
  }

  protected void displayListView() {
    parentView.setDisplayedChildId(R.id.ricebook_listview);
  }

  protected void displayErrorView() {
    parentView.setDisplayedChildId(R.id.error_container);
  }

  protected void displayEmptyView() {
    parentView.setDisplayedChildId(R.id.empty_container);
  }

  public void onCompleted() {
  }
}
