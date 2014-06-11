package com.twocity.apps.latte.data.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-2-18.
 */
public class PictureUrl {

  public String getThumbnailPic() {
    return thumbnailPic;
  }

  public void setThumbnailPic(String thumbnailPic) {
    this.thumbnailPic = thumbnailPic;
  }

  @SerializedName("thumbnail_pic")
  private String thumbnailPic;
}
