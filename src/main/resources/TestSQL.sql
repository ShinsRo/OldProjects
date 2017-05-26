-- DROP ALL
DROP TABLE APPLICATION;
DROP TABLE TRANSACTION;
DROP TABLE G_PRODUCT;
DROP TABLE LOC_COMMENT;
DROP TABLE G_BOARD;
DROP TABLE GD_MEMBER;

-- SEQUENCES
DROP SEQUENCE B_SEQ;
DROP SEQUENCE T_SEQ;
DROP SEQUENCE P_SEQ;
DROP SEQUENCE A_SEQ;

-- B : BOARD / T : TRANSACTION / P : PRODUCT / A : APPLICATION
CREATE SEQUENCE B_SEQ;
CREATE SEQUENCE T_SEQ;
CREATE SEQUENCE P_SEQ;
CREATE SEQUENCE A_SEQ;

-- TestSQL
/*
 *	
 */

insert into G_BOARD values(B_SEQ.nextval,'팔아여1','0',
sysdate,'java','서울특별시 강남구 테헤란로5길 28','사주세요');
insert into G_BOARD values(B_SEQ.nextval,'팔아여2222','0',
sysdate,'java','서울특별시 강남구 테헤란로5길 28','연락주세용');
insert into G_BOARD values(B_SEQ.nextval,'팔아여333','0',
sysdate,'java','서울특별시 강남구 테헤란로5길 28','급하게 팔아요');
insert into G_BOARD values(B_SEQ.nextval,'사주세요','0',
sysdate,'java','서울특별시 강남구 테헤란로5길 28','엉엉엉');
