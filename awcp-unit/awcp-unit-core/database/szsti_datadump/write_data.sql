
TRUNCATE TABLE temp_szsti_user;
TRUNCATE TABLE temp_szsti_group;
TRUNCATE TABLE temp_szsti_user_role;


#create index on temp tables;
ALTER TABLE temp_szsti_user ADD INDEX IX_temp_szsti_user_USER_ID_CARD_NUMBER(USER_ID_CARD_NUMBER);
ALTER TABLE temp_szsti_user ADD INDEX IX_temp_szsti_user_ORG_CODE(ORG_CODE);
ALTER TABLE temp_szsti_group ADD INDEX IX_temp_szsti_group_ORG_CODE(ORG_CODE);

#create index for destination tables
ALTER TABLE p_un_user_base_info ADD INDEX IX_user_base_info_GROUP_ID(GROUP_ID);
ALTER TABLE p_un_user_base_info ADD INDEX IX_user_base_info_USER_ID_CARD_NUMBER(USER_ID_CARD_NUMBER);
ALTER TABLE p_un_group ADD INDEX IX_group_PID(PID);
ALTER TABLE p_un_group ADD INDEX IX_group_PARENT_GROUP_ID(PARENT_GROUP_ID);
ALTER TABLE bpm_party_entity ADD INDEX IX_bpm_party_entity_REF(REF);

#删除已存在的group
DELETE FROM p_un_group
WHERE ORG_CODE IN(
SELECT ORG_CODE FROM temp_szsti_group
);
#删除没有组织的用户
DELETE FROM p_un_user_base_info 
WHERE GROUP_ID NOT IN (
SELECT GROUP_ID FROM p_un_group
);
#删除没组织的系统与组织的关系；
DELETE FROM p_un_group_sys
WHERE GROUP_ID NOT IN (
SELECT GROUP_ID FROM p_un_group
);

#删除没有用户的用户与角色的关系;
DELETE FROM p_un_user_role 
WHERE USER_ID NOT IN (
SELECT USER_ID FROM p_un_user_base_info
);

#删除专家组下的所有用户
DELETE FROM p_un_user_base_info WHERE GROUP_ID = 4;

#删除科创委下所有用户
DELETE FROM p_un_user_base_info WHERE GROUP_ID = 24;

#generate p_un_group
INSERT INTO p_un_group(GROUP_TYPE, GROUP_CH_NAME, ORG_CODE, GROUP_ADDRESS, GROUP_BUSINESS_SPHERE, CONTACT_NUMBER, PID)
SELECT 1, GROUP_CH_NAME, ORG_CODE, GROUP_ADDRESS, GROUP_BUSINESS_SPHERE, CONTACT_NUMBER, '0' FROM temp_szsti_group
WHERE ORG_CODE NOT IN(
SELECT ORG_CODE FROM p_un_group WHERE ORG_CODE IS NOT NULL
);

#企业与申报系统关联,申报系统的SystemId为87
INSERT INTO p_un_group_sys(GROUP_ID, SYS_ID)
SELECT b.GROUP_ID,  87 FROM temp_szsti_group a
INNER JOIN p_un_group b ON a.ORG_CODE = b.ORG_CODE AND b.PID= '0'
WHERE b.GROUP_ID NOT IN(
SELECT GROUP_ID FROM p_un_group_sys WHERE SYS_ID =87);



#generate p_un_user_base_info 
#it does not contain expert
INSERT INTO p_un_user_base_info(USER_NAME, USER_PWD, USER_ID_CARD_NUMBER, NAME,MOBILE, USER_EMAIL,GROUP_ID)
SELECT USER_ID_CARD_NUMBER, USER_PWD, USER_ID_CARD_NUMBER, NAME,MOBILE, USER_EMAIL,b.GROUP_ID  FROM temp_szsti_user a
INNER JOIN p_un_group b ON a.ORG_CODE = b.ORG_CODE
WHERE b.GROUP_TYPE=1 AND USER_ID_CARD_NUMBER NOT IN(
  SELECT USER_ID_CARD_NUMBER FROM p_un_user_base_info WHERE USER_ID_CARD_NUMBER IS NOT NULL
);

#generate 专家信息,科创委专家的组织ID为“４”
INSERT INTO p_un_user_base_info(USER_NAME, USER_PWD, USER_ID_CARD_NUMBER, NAME,MOBILE, USER_EMAIL,GROUP_ID)
SELECT USER_ID_CARD_NUMBER, USER_PWD, USER_ID_CARD_NUMBER, NAME,MOBILE, USER_EMAIL,4 from temp_szsti_user 
WHERE ORG_CODE = 'expert' AND USER_ID_CARD_NUMBER NOT IN(
SELECT USER_ID_CARD_NUMBER FROM p_un_user_base_info where GROUP_ID=4
);

#generate处室人员信息；科创委组织结构代码为SZSTI000-1
INSERT INTO p_un_user_base_info(USER_NAME, USER_PWD, USER_ID_CARD_NUMBER, NAME,MOBILE, USER_EMAIL,GROUP_ID)
SELECT USER_ID_CARD_NUMBER, USER_PWD, USER_ID_CARD_NUMBER, NAME,MOBILE, USER_EMAIL,24 from temp_szsti_user 
WHERE ORG_CODE = '1' AND USER_ID_CARD_NUMBER NOT IN(
SELECT USER_ID_CARD_NUMBER FROM p_un_user_base_info where GROUP_ID=24
);

#创建科创委与审批系统的关联；
#INSERT INTO p_un_group_sys(GROUP_ID, SYS_ID)  VALUES(24, 88);
#创建专家组织与审批系统的关联
#INSERT INTO p_un_group_sys(GROUP_ID, SYS_ID)  VALUES(4, 88);






#测试服务器上部分企业的信息不在”szstieos.tlk_企业基础信息“表中，但生成环境是全面的；
#generate company and amdin relationship;
INSERT INTO p_un_user_role(USER_ID, ROLE_ID)
SELECT b.USER_ID, 1 AS ROLE_ID FROM temp_szsti_user_role a
INNER JOIN p_un_user_base_info b ON a.USER_ID_CARD_NUMBER = b.USER_ID_CARD_NUMBER
INNER JOIN p_un_group c ON b.GROUP_ID = c.GROUP_ID AND c.ORG_CODE = a.ORG_CODE
WHERE a.ROLE_NAME = '管理员' AND b.USER_ID NOT IN(
SELECT USER_ID FROM p_un_user_role WHERE ROLE_ID = 1
);

#测试服务器上部分企业的信息不在”szstieos.tlk_企业基础信息“表中，但生成环境是全面的；
#系统管理原
#generate company and amdin relationship;
INSERT INTO p_un_user_role(USER_ID, ROLE_ID)
SELECT b.USER_ID, 47 AS ROLE_ID FROM temp_szsti_user_role a
INNER JOIN p_un_user_base_info b ON a.USER_ID_CARD_NUMBER = b.USER_ID_CARD_NUMBER
INNER JOIN p_un_group c ON b.GROUP_ID = c.GROUP_ID AND c.ORG_CODE = a.ORG_CODE
WHERE a.ROLE_NAME = '管理员' AND b.USER_ID NOT IN(
SELECT USER_ID FROM p_un_user_role WHERE ROLE_ID = 47
);

#将企业的所有人有都与申报人有的角色相管理
＃申报人员
INSERT INTO p_un_user_role(USER_ID, ROLE_ID)
SELECT c.USER_ID, d.ROLE_ID FROM p_un_group_sys a
INNER JOIN p_un_group b ON a.GROUP_ID = b.GROUP_ID
INNER JOIN p_un_user_base_info c ON c.GROUP_ID = a.GROUP_ID
INNER JOIN p_un_role_info d ON d.SYS_ID = a.SYS_ID 
WHERE a.SYS_ID = 87 AND c.USER_ID NOT IN(
  SELECT USER_ID FROM p_un_user_role WHERE ROLE_ID IN(48)#"48"为申报系统中角色ID
)
ORDER BY c.USER_ID;


#将处室的所有人有都与受理所有的角色相管理
INSERT INTO p_un_user_role(USER_ID, ROLE_ID)
SELECT c.USER_ID, d.ROLE_ID FROM p_un_group_sys a
INNER JOIN p_un_group b ON a.GROUP_ID = b.GROUP_ID
INNER JOIN p_un_user_base_info c ON c.GROUP_ID = a.GROUP_ID
INNER JOIN p_un_role_info d ON d.SYS_ID = a.SYS_ID 
WHERE a.SYS_ID = 88 AND a.GROUP_ID =24  AND c.USER_ID NOT IN(
  SELECT USER_ID FROM p_un_user_role WHERE ROLE_ID IN(49,50)
)
ORDER BY c.USER_ID;



#导出用户数据，用来修改密码用；
SELECT CASE ORG_CODE WHEN 'expert' THEN 'EXPERT01-1' WHEN '1' THEN 'SZSTI000-1' ELSE ORG_CODE END,
USER_ID_CARD_NUMBER, USER_PWD FROM temp_szsti_user a