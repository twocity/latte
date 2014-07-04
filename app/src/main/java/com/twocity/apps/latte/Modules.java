package com.twocity.apps.latte;

/**
 * Created by twocity on 14-6-12.
 */
public class Modules {

  private Modules() {

  }

  static Object[] list(LatteApp app) {
    return new Object[] {
        new LatteModule(app)
    };
  }
}
