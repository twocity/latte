package com.twocity.apps.latte.ui;

import com.squareup.picasso.Picasso;
import com.twocity.apps.latte.R;
import com.twocity.apps.latte.service.PostService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

public class PostActivity extends Activity {

    private final static int MAX = 140;

    private final static int PICK_IMAGE = 1;

    private final static int PICK_IMAGE_KK = 2;

    @InjectView(R.id.input_edittext)
    EditText mInputView;

    @InjectView(R.id.image_thumb)
    ImageView mThumbView;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add_pic:
                pickImage();
                return true;
            case R.id.action_post:
                if (preparePost()) {
                    post();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // http://stackoverflow.com/questions/20260416/retrieve-absolute-path-when-select-image-from-gallery-kitkat-android
    private void pickImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, getString(R.string.pick_image_title)),
                    PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_KK);
        }
    }

    private boolean preparePost() {
        String message = mInputView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getApplicationContext(), "empty content", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (message.length() > MAX) {
            Toast.makeText(getApplicationContext(), "too many words", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void post() {
        String content = mInputView.getText().toString();
        PostService.post(this, content, mImageUri);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (data == null) {
            return;
        }
        Uri uri = data.getData();
        if (uri != null) {
            mImageUri = uri;
            Timber.i("data uri: %s", uri.toString());
            mThumbView.setVisibility(View.VISIBLE);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            Picasso.with(getApplicationContext())
                    .load(uri)
                    .resize(width, width)
                    .centerInside()
                    .into(mThumbView);
        }
    }
}
