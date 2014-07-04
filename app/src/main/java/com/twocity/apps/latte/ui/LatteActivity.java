package com.twocity.apps.latte.ui;

import com.twocity.apps.latte.LatteApp;
import com.twocity.apps.latte.data.api.UserManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import javax.inject.Inject;

public class LatteActivity extends Activity {
  @Inject UserManager userManager;

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    LatteApp.get(this).inject(this);
    setup();
  }

  private void setup() {
    LatteApp app = LatteApp.get(this);
    boolean hasUser = userManager.hasUser();

    if (hasUser) {
      app.plusUserObjectGraph(userManager.getCurrentUserOAuthToken());
      startActivity(new Intent(this, HomeActivity.class));
    } else {
      app.plusUserObjectGraph("");
      startActivity(new Intent(this, OAuthActivity.class));
    }
    finish();
  }
}