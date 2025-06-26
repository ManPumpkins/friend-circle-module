package com.g.friendcirclemodule.dp;

public class DMEntryBase {
    long id;
    int friendName;
    int friendHead;
    String decStr;
    String friendImageId;
    String time;
    String friendVideoId;

    public DMEntryBase(long id, int friendName, int friendHead, String decStr, String friendImageId, String time, String friendVideoId) {
        this.id = id;
        this.friendName = friendName;
        this.friendHead = friendHead;
        this.decStr = decStr;
        this.friendImageId = friendImageId;
        this.time = time;
        this.friendVideoId = friendVideoId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFriendName() {
        return friendName;
    }

    public void setFriendName(int friendName) {
        this.friendName = friendName;
    }

    public int getFriendHead() {
        return friendHead;
    }

    public void setFriendHead(int friendHead) {
        this.friendHead = friendHead;
    }

    public String getDecStr() {
        return decStr;
    }

    public void setDecStr(String decStr) {
        this.decStr = decStr;
    }

    public String getFriendImageId() {
        return friendImageId;
    }

    public void setFriendImageId(String friendImageId) {
        this.friendImageId = friendImageId;
    }

    public String getFriendVideoId() {
        return friendVideoId;
    }

    public void setFriendVideoId(String friendVideoId) {
        this.friendVideoId = friendVideoId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
