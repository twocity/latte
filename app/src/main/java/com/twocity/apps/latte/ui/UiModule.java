package com.twocity.apps.latte.ui;

import com.twocity.apps.latte.service.PostService;
import dagger.Module;

/**
 * Created by twocity on 14-6-12.
 */
@Module(
    injects = {
        LatteActivity.class, TimeLineFragment.class, TimeLineFragment2.class, OAuthActivity.class,
        PostService.class
    },
    complete = false,
    library = true)
public class UiModule {
}
