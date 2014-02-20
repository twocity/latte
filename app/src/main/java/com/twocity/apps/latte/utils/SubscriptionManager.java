package com.twocity.apps.latte.utils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by twocity on 14-2-20.
 */
public class SubscriptionManager {

    private List<Subscription> subscriptions;

    private SubscriptionManager() {
        subscriptions = new ArrayList<Subscription>();
    }

    public void add(Subscription s) {
        if (s != null) {
            subscriptions.add(s);
        }
    }

    public void unSubscribe() {
        if (subscriptions == null || subscriptions.isEmpty()) {
            return;
        }
        for (Subscription s : subscriptions) {
            if (s != null) {
                s.unsubscribe();
            }
        }
    }

    public static SubscriptionManager create() {
        return new SubscriptionManager();
    }

}
