package com.tastemate.domain;


import java.sql.Date;

public class ChatMessageVO {
        private int messageIdx;
        private int roomIdx;
        private  int userIdx;
        private String messageContent;
        private String time;
        private  String userName;

        private String userProfile;

        public int getMessageIdx() {
                return messageIdx;
        }

        public void setMessageIdx(int messageIdx) {
                this.messageIdx = messageIdx;
        }

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

        public String getMessageContent() {
                return messageContent;
        }

        public void setMessageContent(String messageContent) {
                this.messageContent = messageContent;
        }

        public String getTime() {
                return time;
        }

        public void setTime(String time) {
                this.time = time;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getUserProfile() {
                return userProfile;
        }

        public void setUserProfile(String userProfile) {
                this.userProfile = userProfile;
        }

        @Override
        public String toString() {
                return "ChatMessageVO{" +
                        "messageIdx=" + messageIdx +
                        ", roomIdx=" + roomIdx +
                        ", userIdx=" + userIdx +
                        ", messageContent='" + messageContent + '\'' +
                        ", time='" + time + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userProfile='" + userProfile + '\'' +
                        '}';
        }
}



