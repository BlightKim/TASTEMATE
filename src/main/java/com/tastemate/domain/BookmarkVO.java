package com.tastemate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkVO extends StoreVO{
    private int bookmarkIdx;
    private int memberIdx;
    private int storeIdx;
    private String memberId;
    private String memberName;
    private String storeName;
    private String category1;
    private String storeAddress;
    private double storeLati;
    private double storeLongi;
    private String phoneNumber;
    private int storeCount;
    private StoreVO storeVO;

    public StoreVO getStoreVO() {
        return storeVO;
    }

    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
    }

    public int getBookmarkIdx() {
        return bookmarkIdx;
    }

    public void setBookmarkIdx(int bookmarkIdx) {
        this.bookmarkIdx = bookmarkIdx;
    }

    public int getMemberIdx() {
        return memberIdx;
    }

    public void setMemberIdx(int memberIdx) {
        this.memberIdx = memberIdx;
    }

    public int getStoreIdx() {
        return storeIdx;
    }

    public void setStoreIdx(int storeIdx) {
        this.storeIdx = storeIdx;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public double getStoreLati() {
        return storeLati;
    }

    public void setStoreLati(double storeLati) {
        this.storeLati = storeLati;
    }

    public double getStoreLongi() {
        return storeLongi;
    }

    public void setStoreLongi(double storeLongi) {
        this.storeLongi = storeLongi;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }
}
