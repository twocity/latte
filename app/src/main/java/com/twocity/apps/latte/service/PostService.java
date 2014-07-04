package com.twocity.apps.latte.service;

import com.twocity.apps.latte.LatteApp;
import com.twocity.apps.latte.R;
import com.twocity.apps.latte.data.api.WeiboService;
import com.twocity.apps.latte.data.api.model.Status;
import com.twocity.apps.latte.utils.Image;
import com.twocity.apps.latte.utils.SubscriptionManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.NotificationCompat;

import java.io.FileDescriptor;
import java.io.IOException;

import javax.inject.Inject;
import retrofit.mime.TypedString;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-20.
 */
public class PostService extends Service {

  private static final int NOTIFICATION_ID = 1024;

  private static final int FAILED_NOTIFICAION_ID = NOTIFICATION_ID - 1;

  private static final int SUCCESS_NOTIFICAION_ID = FAILED_NOTIFICAION_ID - 1;

  public static String EXTRA_STATUS_CONTENT = "extra_status_content";

  public static String EXTRA_STATUS_IMAGE_URL = "extra_status_image_url";

  @Inject WeiboService mWeiboService;

  private SubscriptionManager subscriptionManager = SubscriptionManager.create();

  private NotificationManager notificationManager;

  public static void post(Context context, String content, Uri uri) {
    Intent service = new Intent(context, PostService.class);
    service.putExtra(EXTRA_STATUS_CONTENT, content);
    service.putExtra(EXTRA_STATUS_IMAGE_URL, uri);
    context.startService(service);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    LatteApp.get(this).inject(this);
    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent == null) {
      return START_NOT_STICKY;
    }
    String content = intent.getStringExtra(EXTRA_STATUS_CONTENT);
    Uri imageUri = intent.getParcelableExtra(EXTRA_STATUS_IMAGE_URL);
    postStatus(content, imageUri);
    return START_NOT_STICKY;
  }

  private void postStatus(String content, Uri imageUri) {
    showSendingNotification(content, imageUri);
    Subscription s;
    if (hasPic(imageUri)) {
      s = mWeiboService.postStatusWithPic(new TypedString(content),
          new Image(getContentResolver(), imageUri))
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(mObserver);
    } else {
      s = mWeiboService.postStatus(content)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(mObserver);
    }
    subscriptionManager.add(s);
  }

  private boolean hasPic(Uri imageUri) {
    if (imageUri != null) {
      return true;
    }
    return false;
  }

  private void showSendingNotification(String content, Uri uri) {
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(this).setSmallIcon(R.drawable.holo_dark_icon_send)
            .setContentTitle(getString(R.string.post_status_title))
            .setOngoing(true)
            .setContentText(content)
            .setProgress(0, 0, true);
    if (hasPic(uri)) {
      Bitmap bm = getBitmapFromUri(uri);
      NotificationCompat.BigPictureStyle style =
          new NotificationCompat.BigPictureStyle().bigPicture(bm);
      builder.setStyle(style);
    }
    notificationManager.notify(NOTIFICATION_ID, builder.build());
  }

  private void showSuccessNotification() {
    Notification notification = new NotificationCompat.Builder(PostService.this).setSmallIcon(
        R.drawable.holo_dark_icon_accept)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(getString(R.string.post_status_success_title))
        .setTicker(getString(R.string.post_status_success_title))
        .setOngoing(false)
        .setOngoing(false)
        .build();
    notificationManager.notify(SUCCESS_NOTIFICAION_ID, notification);
  }

  private void showFailedNotification() {
    Notification notification = new NotificationCompat.Builder(PostService.this).setSmallIcon(
        R.drawable.holo_dark_icon_cancel)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(getString(R.string.post_status_failed_title))
        .setTicker(getString(R.string.post_status_failed_title))
        .setOngoing(false)
        .setOngoing(false)
        .build();
    notificationManager.notify(FAILED_NOTIFICAION_ID, notification);
  }

  private final Observer mObserver = new Observer<Status>() {
    @Override
    public void onCompleted() {
      notificationManager.cancel(NOTIFICATION_ID);
    }

    @Override
    public void onError(Throwable e) {
      Timber.e(e, "post status failed");
      showFailedNotification();
    }

    @Override
    public void onNext(Status status) {
      Timber.d("post status: %s,create at: %s", status.getContent(), status.getCreateAt());
      notificationManager.cancel(NOTIFICATION_ID);
      showSuccessNotification();
    }
  };

  private Bitmap getBitmapFromUri(Uri uri) {
    ParcelFileDescriptor parcelFileDescriptor = null;
    try {
      parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
      FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
      Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
      parcelFileDescriptor.close();
      return image;
    } catch (Exception e) {
      Timber.e(e, "Failed to load image.", e);
      return null;
    } finally {
      try {
        if (parcelFileDescriptor != null) {
          parcelFileDescriptor.close();
        }
      } catch (IOException e) {
        Timber.e(e, "Error closing ParcelFile Descriptor");
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    subscriptionManager.unSubscribe();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
