package com.tastemate.domain;

import org.apache.ibatis.ognl.BooleanExpression;

public enum MemberMbti {
//    INFP("INFP"), INFJ("INFJ"), INTP("INTP"), INTJ("INTJ"),
//    ISFP("ISFP"), ISFJ("ISFJ"), ISTP("ISTP"), ISTJ("ISTJ"),
//    ENFP("ENFP"), ENFJ("ENFJ"), ENTP("ENTP"), ENTJ("ENTJ"),
//    ESFP("ESFP"), ESFJ("ESFJ"), ESTP("ESTP"), ESTJ("ESTJ");

//    private final String mbti;
//    MemberMbti(String mbti) {
//        this.mbti = mbti;
//    }

    INFP, INFJ, INTP, INTJ,
    ISFP, ISFJ, ISTP, ISTJ,
    ENFP, ENFJ, ENTP, ENTJ,
    ESFP, ESFJ, ESTP, ESTJ;

//    public static void main(String[] args) {
//        MemberMbti memberMbti = MemberMbti.valueOf("INFP");
//        System.out.println(memberMbti.toString());
//        memberMbti.matchMbti("INFP", "ENTP");
//        System.out.println(memberMbti.matchMbti("INFP", "ENTP"));
//    }

    public int matchMbti (String userMbti1, String userMbti2) {
        MemberMbti userMbti = MemberMbti.valueOf(userMbti1);
        MemberMbti otherMbti = MemberMbti.valueOf(userMbti2);


        Boolean infp = otherMbti.equals(INFP);
        Boolean enfp = otherMbti.equals(ENFP);
        Boolean infj = otherMbti.equals(INFJ);
        Boolean enfj = otherMbti.equals(ENFJ);
        Boolean intj = otherMbti.equals(INTJ);
        Boolean entj = otherMbti.equals(ENTJ);
        Boolean intp = otherMbti.equals(INTP);
        Boolean entp = otherMbti.equals(ENTP);


        Boolean isfp = otherMbti.equals(ISFP);
        Boolean esfp = otherMbti.equals(ESFP);
        Boolean isfj = otherMbti.equals(ISFJ);
        Boolean estj = otherMbti.equals(ESTJ);
        Boolean istj = otherMbti.equals(ISTJ);
        Boolean estp = otherMbti.equals(ESTP);
        Boolean istp = otherMbti.equals(ISTP);
        Boolean esfj = otherMbti.equals(ESFJ);

        int matchResult = 0;
        switch (userMbti) {
            case INFP:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp ) {
                        matchResult = 3;
                }
                if ( isfp || esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 1;
                }
                break;
            case INFJ:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp ) {
                    matchResult = 3;
                }
                if ( isfp || esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 1;
                }
                break;
            case INTP:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp || estj) {
                    matchResult = 3;
                }
                if ( isfp || esfp || isfj  || istj || estp || istp || esfj ) {
                    matchResult = 2;
                }

                break;
            case INTJ:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp ) {
                    matchResult = 3;
                }
                if ( isfp || esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 2;
                }

                break;
            case ISFP:
                if ( infp || enfp || infj ) {
                    matchResult = 1;
                }
                if ( enfj || estj || esfj) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp || isfp || esfp || istp || estp || isfj || istj ) {
                    matchResult = 2;
                }
                break;
            case ISFJ:
                if ( infp || enfp || infj || enfj ) {
                    matchResult = 1;
                }
                if ( isfj || istj || esfp || esfj  || estj || estp) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp || isfp ||istp  ) {
                    matchResult = 2;
                }

                break;
            case ISTP:
                if ( infp || enfp || infj || enfj) {
                    matchResult = 1;
                }
                if (estj || esfj) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp || isfp || esfp || istp || estp || isfj || istj ) {
                    matchResult = 2;
                }

                break;
            case ISTJ:
                if ( infp || enfp || infj || enfj ) {
                    matchResult = 1;
                }
                if ( isfj || istj || esfp || esfj  || estj || estp) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp || isfp ||istp  ) {
                    matchResult = 2;
                }

                break;
            case ENFP:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp ) {
                    matchResult = 3;
                }
                if ( isfp || esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 1;
                }

                break;
            case ENFJ:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp || isfp ) {
                    matchResult = 3;
                }
                if ( esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 1;
                }

                break;
            case ENTP:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp ) {
                    matchResult = 3;
                }
                if ( isfp || esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 2;
                }

                break;
            case ENTJ:
                if ( infp || enfp || infj || enfj || intj || entj || intp || entp ) {
                    matchResult = 3;
                }
                if ( isfp || esfp || isfj || estj || istj || estp || istp || esfj ) {
                    matchResult = 2;
                }

                break;
            case ESFP:
                if ( infp || enfp || infj || enfj ) {
                    matchResult = 1;
                }
                if ( isfj || istj) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp || isfp || esfp || istp || estp ||  esfj  || estj  ) {
                    matchResult = 2;
                }
                break;
            case ESFJ:
                if ( infp || enfp || infj || enfj ) {
                    matchResult = 1;
                }
                if ( isfj || istj  || esfj || estj || isfp || istp) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp  || estp || esfp ) {
                    matchResult = 2;
                }

                break;
            case ESTP:
                if ( infp || enfp || infj || enfj ) {
                    matchResult = 1;
                }
                if ( isfj || istj) {
                    matchResult = 3;
                }
                if ( intj || entj || intp || entp || isfp || esfp || istp || estp ||  esfj  || estj  ) {
                    matchResult = 2;
                }

                break;
            case ESTJ:
                if ( infp || enfp || infj || enfj ) {
                    matchResult = 1;
                }
                if ( isfj || istj  || esfj || estj || isfp || istp || intp ) {
                    matchResult = 3;
                }
                if ( intj || entj ||  entp  || estp || esfp ) {
                    matchResult = 2;
                }

                break;
        }

        return matchResult;
    }

}
