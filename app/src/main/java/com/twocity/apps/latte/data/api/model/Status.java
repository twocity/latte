package com.twocity.apps.latte.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by twocity on 14-2-18.
 */
public class Status {

    @SerializedName("created_at")
    private String createAt;

    private long id;

    @SerializedName("text")
    private String content;

    private String source;

    @SerializedName("favorited")
    private boolean isFavorited;

    @SerializedName("truncated")
    private boolean isTruncated;

    @SerializedName("thumbnail_pic")
    private String thumbnailPic;

    @SerializedName("bmiddle_pic")
    private String bmiddlePic;

    @SerializedName("original_pic")
    private String originalPic;

    @SerializedName("reposts_count")
    private int repostsCount;

    @SerializedName("comments_count")
    private int commentsCount;

    @SerializedName("attitudes_count")
    private int attitudesCount;

    @SerializedName("pic_urls")
    private List<PictureUrl> picUrls;

    @SerializedName("user")
    private User user;

    @SerializedName("retweeted_status")
    private Status retweetedStatus;

    private Geo geo;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean isTruncated) {
        this.isTruncated = isTruncated;
    }

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public String getBmiddlePic() {
        return bmiddlePic;
    }

    public void setBmiddlePic(String bmiddlePic) {
        this.bmiddlePic = bmiddlePic;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public void setOriginalPic(String originalPic) {
        this.originalPic = originalPic;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getAttitudesCount() {
        return attitudesCount;
    }

    public void setAttitudesCount(int attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    public List<PictureUrl> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<PictureUrl> picUrls) {
        this.picUrls = picUrls;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getRetweetedStatus() {
        return retweetedStatus;
    }

    public void setRetweetedStatus(Status retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
