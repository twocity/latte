package com.twocity.apps.latte.ui;

import com.twocity.apps.latte.LatteApp;
import com.twocity.apps.latte.R;
import com.twocity.apps.latte.data.api.Config;
import com.twocity.apps.latte.data.api.OAuthService;
import com.twocity.apps.latte.data.api.UserManager;
import com.twocity.apps.latte.data.api.WeiboApiException;
import com.twocity.apps.latte.data.api.model.OAuthToken;
import com.twocity.apps.latte.utils.SubscriptionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-20.
 */
public class OAuthActivity extends Activity {

    @InjectView(R.id.login_status)
    View mProgressContainer;

    @InjectView(R.id.login_status_message)
    TextView mLoginStatusMessageView;

    @InjectView(R.id.login_form)
    View mLoginContainer;

    @InjectView(R.id.email)
    EditText mEmailView;

    @InjectView(R.id.password)
    EditText mPasswordView;

    private String mEmail;

    private String mPassword;

    private OAuthService mOAuthService;

    private SubscriptionManager mSubscriptionManager = SubscriptionManager.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        LatteApp app = LatteApp.get(this);
        mOAuthService = app.getApiClient().getOAuthService();
    }

    private void Login() {
        Subscription s = mOAuthService
                .accessToken(mEmail, mPassword, Config.CLIENT_ID, Config.CLIENT_SECRET,
                        Config.GRANT_TYPE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginObserver);
        mSubscriptionManager.add(s);
    }

    @OnClick(R.id.sign_in_button)
    public void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mEmail.contains("@")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            mProgressContainer.setVisibility(View.VISIBLE);
            mLoginContainer.setVisibility(View.GONE);
            Login();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptionManager.unSubscribe();
    }

    private final Observer loginObserver = new Observer<OAuthToken>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof WeiboApiException) {
                WeiboApiException exception = (WeiboApiException) e;
                Timber.e(exception, "oauth error");
            }
        }

        @Override
        public void onNext(OAuthToken token) {
            UserManager userManager = new UserManager(getApplicationContext());
            String accessToken = token.getAccess_token();
            userManager.setOAuthToken(accessToken);
            userManager.setUID(token.getUid());
            LatteApp app = LatteApp.get(OAuthActivity.this);
            app.initWeiboClient();
            startActivity(new Intent(OAuthActivity.this, HomeActivity.class));
            finish();
        }
    };

}
