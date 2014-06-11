package com.twocity.apps.latte.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit.mime.TypedOutput;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-20.
 */
public class Image implements TypedOutput {

  private static final int BUFFER_SIZE = 4 * 1024;

  private static String IMAGE_MIME = "image/*";

  private final ContentResolver contentResolver;

  private final Uri imageUri;

  private final String mimeType = IMAGE_MIME;

  private final String displayName;

  private final long length;

  public Image(ContentResolver cr, Uri uri) {
    imageUri = uri;
    contentResolver = cr;

    // The query, since it only applies to a single document, will only return one row.
    // no need to filter, sort, or select fields, since we want all fields for one
    // document.
    Cursor cursor = contentResolver.query(uri, null, null, null, null);

    try {
      if (cursor != null && cursor.moveToFirst()) {

        // Note it's called "Display Name".  This is provider-specific, and
        // might not necessarily be the file name.
        this.displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        // If the size is unknown, the value stored is null.  But since an int can't be
        // null in java, the behavior is implementation-specific, which is just a fancy
        // term for "unpredictable".  So as a rule, check if it's null before assigning
        // to an int.  This will happen often:  The storage API allows for remote
        // files, whose size might not be locally known.
        String size;
        if (!cursor.isNull(sizeIndex)) {
          // Technically the column stores an int, but cursor.getString will do the
          // conversion automatically.
          size = cursor.getString(sizeIndex);
        } else {
          size = "Unknown";
        }
        length = Utils.parseLongSafely(size, 0);
      } else {
        displayName = "";
        length = 0;
      }
    } finally {
      cursor.close();
    }
    Timber.i("Image display name: %s", displayName);
    Timber.i("Image length: %d", length);
  }

  @Override
  public String mimeType() {
    return mimeType;
  }

  @Override
  public long length() {
    return this.length;
  }

  @Override
  public String fileName() {
    return displayName;
  }

  // TODO resize target image if needed.
  @Override
  public void writeTo(OutputStream out) throws IOException {
    byte[] buffer = new byte[BUFFER_SIZE];
    InputStream in = contentResolver.openInputStream(imageUri);
    try {
      int read;
      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }
    } finally {
      Utils.closeSafely(in);
    }
  }
}
