package com.eason.marker.model;

/**
 * Created by Eason on 8/23/15.
 */
public class User {

    private String username = "";
    private String password = "";
    private String gender = "";
    private String avatar = "";
    private String birthday = "";
    private String nickname = "";
    private String userid = "";
    private String pushKey = "";
    private String simpleProfile = "";
    private String longProfile = "";
    private String avatarThumb = "";

    public String getAvatarThumb() {
        return avatarThumb;
    }

    public void setAvatarThumb(String avatarThumb) {
        this.avatarThumb = avatarThumb;
    }

    public String getLongProfile() {
        return longProfile;
    }

    public void setLongProfile(String longProfile) {
        this.longProfile = longProfile;
    }

    public String getSimpleProfile() {
        return simpleProfile;
    }

    public void setSimpleProfile(String simpleProfile) {
        this.simpleProfile = simpleProfile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
