package com.twocity.apps.latte.ui;

import com.twocity.apps.latte.data.api.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LatteActivity extends Activity {

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setup();
  }

  private void setup() {
    UserManager userManager = new UserManager(getApplicationContext());
    boolean hasUser = userManager.hasUser();

    if (hasUser) {
      startActivity(new Intent(this, HomeActivity.class));
    } else {
      startActivity(new Intent(this, OAuthActivity.class));
    }
    finish();
  }
}