package com.twocity.apps.latte.rx;

import com.twocity.apps.latte.data.api.WeiboApiException;

import retrofit.RetrofitError;
import rx.Observer;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-21.
 */
public abstract class RetrofitObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof RetrofitError) {
            onException(WeiboApiException.create((RetrofitError) e));
        } else {
            Timber.e(e, "UnKnown");
        }
    }

    public abstract void onException(WeiboApiException e);

}
