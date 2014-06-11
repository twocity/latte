package com.twocity.apps.latte.ui;

import com.squareup.picasso.Picasso;
import com.twocity.apps.latte.LatteApp;
import com.twocity.apps.latte.R;
import com.twocity.apps.latte.data.api.UserManager;
import com.twocity.apps.latte.data.api.UserService;
import com.twocity.apps.latte.data.api.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class UserDetailActivity extends Activity {

  Subscription mSubscription;

  @InjectView(R.id.userAvatar) ImageView mUserAvatar;

  @InjectView(R.id.userProfileAvatar) ImageView mUserProfile;

  UserService mUserService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_detail);

    mUserService = LatteApp.get(this).getApiClient().getUserService();

    ButterKnife.inject(this);

    mSubscription = create().subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<User>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override
          public void onNext(User user) {
            setTitle(user.getScreenName());
            Picasso.with(getApplicationContext()).load(user.getUserAvatarLarge()).into(mUserAvatar);
          }
        });
  }

  private Observable<User> create() {

    return Observable.create(new Observable.OnSubscribeFunc<User>() {
      @Override
      public Subscription onSubscribe(Observer<? super User> observer) {
        try {
          String threadName = Thread.currentThread().getName();
          UserManager manager = new UserManager(getApplicationContext());
          Log.d("UserDetailActivity", "on thread: " + threadName);
          User user = mUserService.userDetail(manager.getUID());
          observer.onNext(user);
          observer.onCompleted();
        } catch (Exception e) {
          observer.onError(e);
          e.printStackTrace();
        }
        return Subscriptions.empty();
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mSubscription.unsubscribe();
  }
}
