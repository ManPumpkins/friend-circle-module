package com.g.friendcirclemodule.dp;

public class DMEntryBase {
    int friendName;
    int friendHead;
    String decStr;
    Integer[] friendImageId;
    int time;
    Integer friendVideoId;

    public DMEntryBase(int friendName, int friendHead, String decStr, Integer[] friendImageId, int time, Integer friendVideoId) {
        this.friendName = friendName;
        this.friendHead = friendHead;
        this.decStr = decStr;
        this.friendImageId = friendImageId;
        this.time = time;
        this.friendVideoId = friendVideoId;
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

    public Integer[] getFriendImageId() {
        return friendImageId;
    }

    public void setFriendImageId(Integer[] friendImageId) {
        this.friendImageId = friendImageId;
    }

    public Integer getFriendVideoId() {
        return friendVideoId;
    }

    public void setFriendVideoId(Integer friendVideoId) {
        this.friendVideoId = friendVideoId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
