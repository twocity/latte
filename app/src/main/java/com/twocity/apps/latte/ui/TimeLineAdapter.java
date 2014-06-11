package com.twocity.apps.latte.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import com.twocity.apps.latte.R;
import com.twocity.apps.latte.data.api.model.Status;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class TimeLineAdapter extends BaseAdapter {

  private List<Status> mList = new ArrayList<Status>();

  private Context mContext;

  private LayoutInflater mLayoutInflater;

  public TimeLineAdapter(Context context, List<Status> list) {
    if (list != null) {
      mList = list;
    }
    mContext = context;
    mLayoutInflater = LayoutInflater.from(context);
  }

  public void addHeader(List<Status> list) {
    if (list != null && list.size() > 0) {
      mList.addAll(0, list);
      notifyDataSetChanged();
    }
  }

  public void addAll(List<Status> list) {
    if (list != null) {
      mList.addAll(list);
      notifyDataSetChanged();
    }
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public Status getItem(int position) {
    return mList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).getId();
  }

  public long getHeaderId() {
    if (getCount() <= 0) {
      return 0;
    }
    return mList.get(0).getId();
  }

  public long getLastId() {
    int c = getCount();
    Timber.i("timeline list size %d", c);
    if (getCount() <= 0) {
      return 0;
    }
    return mList.get(getCount() - 1).getId();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.layout_status_item, parent, false);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    final Status status = getItem(position);
    Picasso.with(mContext)
        .load(status.getUser().getUserAvatarLarge())
        .resize(100, 100)
        .into(holder.userAvatar);
    holder.userTitle.setText(status.getUser().getScreenName());
    holder.statusContent.setText(status.getContent());
    String url = status.getBmiddlePic();
    if (!TextUtils.isEmpty(url)) {
      holder.statusImage.setVisibility(View.VISIBLE);
      Picasso.with(mContext).load(url).into(holder.statusImage);
    } else {
      holder.statusImage.setVisibility(View.GONE);
    }
    return convertView;
  }

  static class ViewHolder {

    @InjectView(R.id.userAvatar) ImageView userAvatar;

    @InjectView(R.id.userTitle) TextView userTitle;

    @InjectView(R.id.statusContent) TextView statusContent;

    @InjectView(R.id.statusImageView) ImageView statusImage;

    public ViewHolder(View v) {
      ButterKnife.inject(this, v);
    }
  }
}