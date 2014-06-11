package com.twocity.apps.latte.data.api.model;

import java.util.List;

import rx.util.functions.Func1;

/**
 * Created by twocity on 14-2-19.
 */
public class Statuses2List implements Func1<Statueses, List<Status>> {

  @Override
  public List<Status> call(Statueses statueses) {
    return statueses.getStatuses();
  }
}
