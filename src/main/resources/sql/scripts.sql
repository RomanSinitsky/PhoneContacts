  CREATE TABLE "PHONECONTACTS"."USERS"
   (	"ID" NUMBER(19,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOT NULL ENABLE,
	"ACTIVE" NUMBER(1,0) NOT NULL ENABLE,
	"NAME" VARCHAR2(255 CHAR),
	"PASSWORD" VARCHAR2(255 CHAR),
	 PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

  CREATE TABLE "PHONECONTACTS"."USER_ROLES"
   (	"USER_ID" NUMBER(19,0) NOT NULL ENABLE,
	"ROLES_SET" VARCHAR2(255 CHAR),
	 CONSTRAINT "FKHFH9DX7W3UBF1CO1VDEV94G3F" FOREIGN KEY ("USER_ID")
	  REFERENCES "PHONECONTACTS"."USERS" ("ID") ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

  CREATE TABLE "PHONECONTACTS"."CONTACTS"
   (	"CONTACT_ID" NUMBER(19,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOT NULL ENABLE,
	"NAME" VARCHAR2(255 CHAR),
	"USER_ID" NUMBER(19,0) NOT NULL ENABLE,
	 PRIMARY KEY ("CONTACT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE,
	 CONSTRAINT "FKNA8BDDYGR3L3KQ1IMGHGCSKT8" FOREIGN KEY ("USER_ID")
	  REFERENCES "PHONECONTACTS"."USERS" ("ID") ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

    CREATE TABLE "PHONECONTACTS"."EMAILS"
   (	"ID" NUMBER(19,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOT NULL ENABLE,
	"EMAIL" VARCHAR2(255 CHAR),
	"CONTACT_ID" NUMBER(19,0) NOT NULL ENABLE,
	 PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE,
	 CONSTRAINT "FKO1IMU8TIM78Y26LLVMCAVWL4" FOREIGN KEY ("CONTACT_ID")
	  REFERENCES "PHONECONTACTS"."CONTACTS" ("CONTACT_ID") ON DELETE CASCADE ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

    CREATE TABLE "PHONECONTACTS"."PHONES"
   (	"ID" NUMBER(19,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOT NULL ENABLE,
	"PHONE_NBR" VARCHAR2(255 CHAR),
	"CONTACT_ID" NUMBER(19,0) NOT NULL ENABLE,
	 PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE,
	 CONSTRAINT "FKL3OPENK36N2UMVV6OCFJLCQKN" FOREIGN KEY ("CONTACT_ID")
	  REFERENCES "PHONECONTACTS"."CONTACTS" ("CONTACT_ID") ON DELETE CASCADE ENABLE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;

INSERT INTO users(name, password, active)
VALUES('R', '$2a$16$NB/wgf3WZENqEfmXGuxbEOoWn/hPGpwCauKhs4bzN6KoMGUE8ZvAy', 1);

INSERT INTO user_roles(user_id, roles_set)
VALUES(1, 'USER');