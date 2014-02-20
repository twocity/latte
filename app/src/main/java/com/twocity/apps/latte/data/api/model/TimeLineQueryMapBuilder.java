package com.twocity.apps.latte.data.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twocity on 14-2-18.
 */
public class TimeLineQueryMapBuilder {

    private long sinceId = 0;

    private long maxId = 0;

    private int count = 20;

    private int page = 1;

    private int baseApp = 0;

    private int feature = 0;

    private int trimUser = 0;

    public TimeLineQueryMapBuilder() {

    }

    public TimeLineQueryMapBuilder sinceId(long id) {
        sinceId = id;
        return this;
    }

    public TimeLineQueryMapBuilder maxId(long id) {
        maxId = id;
        return this;
    }

    public TimeLineQueryMapBuilder count(int c) {
        count = c;
        return this;
    }

    public TimeLineQueryMapBuilder page(int p) {
        page = p;
        return this;
    }

    public TimeLineQueryMapBuilder baseApp(int b) {
        baseApp = b;
        return this;
    }

    public TimeLineQueryMapBuilder feature(int f) {
        feature = f;
        return this;
    }

    public TimeLineQueryMapBuilder trimUser(int t) {
        trimUser = t;
        return this;
    }

    public Map<String, String> build() {
        Map<String, String> map = new HashMap<String, String>();
        if (sinceId != 0) {
            map.put("since_id", String.valueOf(sinceId));
        }
        if (maxId != 0) {
            map.put("max_id", String.valueOf(maxId));
        }
        map.put("count", String.valueOf(count));
        if (page != 1) {
            map.put("page", String.valueOf(page));
        }
        map.put("base_app", String.valueOf(baseApp));
        map.put("feature", String.valueOf(feature));
        map.put("trim_user", String.valueOf(trimUser));
        return map;
    }
}
