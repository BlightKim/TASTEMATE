package com.tastemate.domain;

public class ChatUserVO {
        private int roomIdx;
        private  int userIdx;
        private int userStatus;

    public int getRoomIdx() {
        return roomIdx;
    }

    public void setRoomIdx(int roomIdx) {
        this.roomIdx = roomIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "ChatUserVO{" +
                "roomIdx=" + roomIdx +
                ", userIdx=" + userIdx +
                ", userStatus=" + userStatus +
                '}';
    }
}

