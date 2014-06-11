package com.twocity.apps.latte.data.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-2-12.
 */
public class User {

  private long id;

  private long city;

  private String url;

  @SerializedName("profile_image_url")
  private String profileImageUrl;

  @SerializedName("followers_count")
  private long followersCount;

  @SerializedName("friends_count")
  private long friendsCount;

  @SerializedName("statuses_count")
  private long statusesCount;

  @SerializedName("favourites_count")
  private long favouritesCount;

  @SerializedName("created_at")
  private String createAt;

  @SerializedName("following")
  private boolean isFollowing;

  @SerializedName("verified")
  private boolean isVerified;

  @SerializedName("avatar_large")
  private String userAvatarLarge;

  @SerializedName("verified_reason")
  private String verifiedReason;

  @SerializedName("follow_me")
  private boolean isFollowMe;

  @SerializedName("online_status")
  private String onlineStatus;

  @SerializedName("screen_name")
  private String screenName;

  @SerializedName("name")
  private String name;

  private String location;

  private String description;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCity() {
    return city;
  }

  public void setCity(long city) {
    this.city = city;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public long getFollowersCount() {
    return followersCount;
  }

  public void setFollowersCount(long followersCount) {
    this.followersCount = followersCount;
  }

  public long getFriendsCount() {
    return friendsCount;
  }

  public void setFriendsCount(long friendsCount) {
    this.friendsCount = friendsCount;
  }

  public long getStatusesCount() {
    return statusesCount;
  }

  public void setStatusesCount(long statusesCount) {
    this.statusesCount = statusesCount;
  }

  public long getFavouritesCount() {
    return favouritesCount;
  }

  public void setFavouritesCount(long favouritesCount) {
    this.favouritesCount = favouritesCount;
  }

  public String getCreateAt() {
    return createAt;
  }

  public void setCreateAt(String createAt) {
    this.createAt = createAt;
  }

  public boolean isFollowing() {
    return isFollowing;
  }

  public void setFollowing(boolean isFollowing) {
    this.isFollowing = isFollowing;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }

  public String getUserAvatarLarge() {
    return userAvatarLarge;
  }

  public void setUserAvatarLarge(String userAvatarLarge) {
    this.userAvatarLarge = userAvatarLarge;
  }

  public String getVerifiedReason() {
    return verifiedReason;
  }

  public void setVerifiedReason(String verifiedReason) {
    this.verifiedReason = verifiedReason;
  }

  public boolean isFollowMe() {
    return isFollowMe;
  }

  public void setFollowMe(boolean isFollowMe) {
    this.isFollowMe = isFollowMe;
  }

  public String getOnlineStatus() {
    return onlineStatus;
  }

  public void setOnlineStatus(String onlineStatus) {
    this.onlineStatus = onlineStatus;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", city=" + city +
        ", url='" + url + '\'' +
        ", profileImageUrl='" + profileImageUrl + '\'' +
        ", followersCount=" + followersCount +
        ", friendsCount=" + friendsCount +
        ", statusesCount=" + statusesCount +
        ", favouritesCount=" + favouritesCount +
        ", createAt='" + createAt + '\'' +
        ", isFollowing=" + isFollowing +
        ", isVerified=" + isVerified +
        ", userAvatarLarge='" + userAvatarLarge + '\'' +
        ", verifiedReason='" + verifiedReason + '\'' +
        ", isFollowMe=" + isFollowMe +
        ", onlineStatus='" + onlineStatus + '\'' +
        ", screenName='" + screenName + '\'' +
        ", name='" + name + '\'' +
        ", location='" + location + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
