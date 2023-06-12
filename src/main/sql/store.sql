-- menu 테이블 생성
-- drop table munu;
-- drop table menu_seq;

CREATE TABLE MENU
(
    MENU_IDX NUMBER NOT NULL
    , STORE_IDX NUMBER NOT NULL
    , FOOD_NAME VARCHAR2(100 BYTE) NOT NULL
    , PRICE NUMBER NOT NULL
    , CONSTRAINT MENU_PK PRIMARY KEY
    (
     MENU_IDX
        )
);

CREATE SEQUENCE munu_seq;

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 1, '국밥', 9000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 1, '국밥(특)', 11000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 1, '정식', 14000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 1, '술국', 18000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 2, '가정식백반', 8000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 2, '현미백반', 9000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 2, '생삼겹살', 15000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '슈퍼심플 버거', 4500);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '슈퍼심플 치즈버거', 5500);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '갈릭 감자튀김', 4000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '리코타치즈 샐러드', 5600);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '슈퍼심플버거 세트', 8000);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '밀크쉐이크', 3500);

INSERT INTO MENU(MENU_IDX, STORE_IDX, FOOD_NAME, PRICE)
VALUES (munu_seq.NEXTVAL, 3, '피치망고에이드', 2000);


COMMIT;
----------------------------------------------------------


--drop table store;
--drop sequence store_seq;

CREATE TABLE STORE
(
    STORE_IDX NUMBER NOT NULL
    , USER_IDX NUMBER NOT NULL
    , STORE_NAME VARCHAR2(100 BYTE) NOT NULL
    , CATEGORY1 VARCHAR2(50 BYTE) NOT NULL
    , STORE_ADDRESS VARCHAR2(200 BYTE) NOT NULL
    , STORE_lati NUMBER
    , STORE_longi NUMBER
    , PHONE_NUMBER VARCHAR2(100 BYTE)
    , STORE_COUNT NUMBER DEFAULT 0
    , FILENAME VARCHAR2(200 BYTE)
    , ORI_FILENAME VARCHAR2(200 BYTE)
    , DISTANCE NUMBER
    , CONSTRAINT TABLE1_PK PRIMARY KEY
    (
     STORE_IDX
        )
);


CREATE SEQUENCE store_seq;

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 1, '농민백암순대', '한식',
        '서울 강남구 역삼로3길 20-4', 37.49490175032281, 127.03149331806011, '02-501-2772', 4, 431.3423337460997,
        '농민백암순대.jpg');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 1, '청목', '한식',
        '서울 강남구 테헤란로 124 삼원타워', 37.49877828305107,127.0316730592617, '02-557-5534', 5, 0,
        '청목.png');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 1, '슈퍼심플', '양식',
        '서울 강남구 테헤란로8길 28 1층', 37.49707535532538,  127.03176846245647, '0507-1352-4838', 1,189.54387449306788,
        '슈퍼심플.png');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 1, '먼키 강남역점', '푸드코트',
        '서울 강남구 테헤란로 123 여삼빌딩 B1층', 37.49948341606353, 127.0313143000319, '0507-1477-7117', 0, 84.55382485654852,
        '먼키.jpg');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 1, '오미라식당', '퓨전',
        '서울 강남구 테헤란로 124 삼원타워 지하1층', 37.49877828305107,127.0316730592617, '0507-1384-0510', 3, 0,
        '오미라식당.png');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 1, '백초밥', '일식',
        '서울 강남구 논현로95길 29-16', 37.5010198294737,127.033862319913, '02-556-8516', 2, 315.3157895068992,
        '백초밥.png');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 3, '나이스샤워', '일식',
        '서울 강남구 테헤란로10길 25 뜨라네아파트', '37.4975892274124', '127.032981074946', '0507-1335-6008', 0,175.48960789818707,
        '나이스샤워.png');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 3, '봉된장', '한식',
        '서울 강남구 테헤란로10길 25 뜨라네', '37.4975892274124', '127.032981074946', '070-8800-2345', 1,175.48960789818707,
        '봉된장.jpg');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 3, '미엔아이', '중식',
        '서울 강남구 봉은사로4길 32 1층', '37.5027658420773', '127.026657450782', '0507-1312-0652', 0,626.3930311569922,
        '미엔아이.jpg');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 3, '싸다김밥', '한식',
        '서울 강남구 테헤란로4길 6', '37.4976674173806', '127.029286360295', '02-553-1503', 0, 244.11069714257468,
        '싸다김밥.jpg');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 4, '역전우동', '한식',
        '서울 강남구 테헤란로10길 25 104호', '37.4975892274124', '127.032981074946', '02-553-2111', 0,175.48960789818707,
        '역전우동.jpg');

INSERT INTO STORE (STORE_IDX, USER_IDX, STORE_NAME, CATEGORY1,
                   STORE_ADDRESS, STORE_LATI, STORE_LONGI, PHONE_NUMBER, STORE_COUNT, DISTANCE,
                   FILENAME)
VALUES (store_seq.NEXTVAL, 4, '스노우폭스', '일식',
        '서울 강남구 테헤란로4길 6', '37.4976674173806', '127.029286360295', '02-556-8082', 0,244.11069714257468,
        '스노우폭스.jpg');

COMMIT;

select * from store;

---------------------------------------------------------------------

--drop table star;
--drop SEQUENCE star_seq;

CREATE TABLE STAR
(
    STAR_IDX NUMBER NOT NULL
    , STORE_IDX NUMBER NOT NULL
    , USER_IDX NUMBER NOT NULL
    , STORE_STAR NUMBER
    , USER_STAR NUMBER
    , STORE_COMMENT VARCHAR2(255 BYTE)
    , CONSTRAINT STAR_PK PRIMARY KEY
    (
     STAR_IDX
        )
);

CREATE SEQUENCE star_seq;


INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 1, 1, 4, 3, '맛있어요');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 1, 1, 2, 4, '불친절해요....!');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 2, 1, 5, 5, '인생 맛집!!');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 2, 1, 2, 3, '양이 적어요 ㅠㅠ');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 3, 27, 5, 5, '친절해요!');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 3, 28, 5, 5, '괜찮았어요~');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 4, 29, 1, 2, '위생이 좀...');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 4, 24, 5, 5, '또 갈거에요!!');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 8, 26, 4, 4, '또 갈거에요!!');

INSERT INTO STAR(STAR_IDX, STORE_IDX, USER_IDX, STORE_STAR, USER_STAR, STORE_COMMENT)
VALUES (star_seq.NEXTVAL, 8, 25, 5, 5, '여기 대박임ㅋㅋ');

COMMIT;

SELECT * FROM STAR;


------------------------------------------------

CREATE TABLE KAKAOPAYREADY
(
    KAKAO_READY_IDX NUMBER NOT NULL
    , USER_IDX NUMBER NOT NULL
    , STORE_IDX NUMBER NOT NULL
    , BOOKING_IDX NUMBER NOT NULL
    , PARTNER_ORDER_ID VARCHAR2(20 BYTE) NOT NULL
    , TOTAL_AMOUNT NUMBER NOT NULL
    , PG_TOKEN VARCHAR2(20 BYTE) NOT NULL
    , TID VARCHAR2(20 BYTE) NOT NULL
    , CONSTRAINT KAKAOPAYREADY_PK PRIMARY KEY
    (
     KAKAO_READY_IDX
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX KAKAOPAYAPPROVAL_PK ON KAKAOPAYREADY (KAKAO_READY_IDX ASC)
    LOGGING
    TABLESPACE SYSTEM
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    FREELISTS 1
    FREELIST GROUPS 1
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
TABLESPACE SYSTEM
PCTFREE 10
PCTUSED 40
INITRANS 1
STORAGE
(
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS UNLIMITED
  FREELISTS 1
  FREELIST GROUPS 1
  BUFFER_POOL DEFAULT
)
NOPARALLEL

    -------------------------------
CREATE TABLE KAKAOPAYAPPROVAL
(
    KAKAO_APPROVAL_IDX NUMBER NOT NULL
    , USER_IDX NUMBER
    , STORE_IDX NUMBER
    , BOOKING_IDX NUMBER
    , PARTNER_ORDER_ID VARCHAR2(20 BYTE) NOT NULL
    , STATUS VARCHAR2(20 BYTE) DEFAULT 0
    , AMOUNT NUMBER NOT NULL
    , PG_TOKEN VARCHAR2(20 BYTE) NOT NULL
    , PAYMENT_METHOD_TYPE VARCHAR2(20 BYTE) NOT NULL
    , ITEM_NAME VARCHAR2(20 BYTE) NOT NULL
    , QUANTITY NUMBER NOT NULL
    , APPROVED_AT DATE NOT NULL
    , CONSTRAINT KAKAOPAYAPPROVAL2_PK PRIMARY KEY
    (
     KAKAO_APPROVAL_IDX
        )
    USING INDEX
    (
    CREATE UNIQUE INDEX KAKAOPAYAPPROVAL2_PK ON KAKAOPAYAPPROVAL (KAKAO_APPROVAL_IDX ASC)
    LOGGING
    TABLESPACE SYSTEM
    PCTFREE 10
    INITRANS 2
    STORAGE
    (
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    FREELISTS 1
    FREELIST GROUPS 1
    BUFFER_POOL DEFAULT
    )
    NOPARALLEL
    )
    ENABLE
)
    LOGGING
    TABLESPACE SYSTEM
    PCTFREE 10
    PCTUSED 40
    INITRANS 1
    STORAGE
(
    INITIAL 65536
    NEXT 1048576
    MINEXTENTS 1
    MAXEXTENTS UNLIMITED
    FREELISTS 1
    FREELIST GROUPS 1
    BUFFER_POOL DEFAULT
)
    NOPARALLEL;
