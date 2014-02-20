package com.twocity.apps.latte.data.api;

import com.twocity.apps.latte.data.prefs.LongPreference;
import com.twocity.apps.latte.data.prefs.StringPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import timber.log.Timber;

/**
 * Created by twocity on 14-2-14.
 */
public class UserManager {

    private static final String PREF_NAME = "user_shared_pref";

    private Context mContext;

    private StringPreference mTokenPreference;

    private LongPreference mUIdPreference;

    public UserManager(Context context) {
        mContext = context;
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mTokenPreference = new StringPreference(pref, "oauth_token", null);
        mUIdPreference = new LongPreference(pref, "user_id", -1);
    }

    public String getCurrentUserOAuthToken() {
        return mTokenPreference.get();
    }

    public void setOAuthToken(String token) {
        mTokenPreference.set(token);
    }

    public long getUID() {
        return mUIdPreference.get();
    }

    public void setUID(long id) {
        mUIdPreference.set(id);
    }

    public boolean hasUser() {
        String token = mTokenPreference.get();
        long id = mUIdPreference.get();
        if (TextUtils.isEmpty(token) || id == -1) {
            Timber.i("no user currently");
            return false;
        }
        return true;
    }
}
