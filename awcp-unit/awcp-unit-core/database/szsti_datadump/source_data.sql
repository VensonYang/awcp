



USE obpm;
#User base info
SELECT a.ID AS USER_ID, a.LOGINPWD AS `USER_PWD`, a.NAME, 
a.EMAIL AS USER_EMAIL,a.FIELD2 AS USER_ID_CARD_NUMBER, 
a.FIELD3 AS ORG_CODE,a.TELEPHONE AS MOBILE
FROM t_user a
WHERE LENGTH(a.FIELD2)=15 OR LENGTH(a.FIELD2)=18

#user role info
SELECT a.ID AS USER_ID, a.FIELD2 AS USER_ID_CARD_NUMBER, 
a.FIELD3 AS ORG_CODE,c.NAME AS ROLE_NAME FROM t_user a
INNER JOIN t_user_role_set b ON a.Id = b.USERID
INNER jOIN t_role  c ON c.ID = b.ROLEID
WHERE LENGTH(a.FIELD2)=15 OR LENGTH(a.FIELD2)=18

#company info
SELECT  b.ID AS GROUP_ID , 
b.ITEM_COMP_NAME AS GROUP_CH_NAME,
b.ITEM_COMP_CODE AS ORG_CODE,
b.ITEM_COMP_ADDR AS GROUP_ADDRESS,
b.ITEM_BIZ_SCOPE AS GROUP_BUSINESS_SPHERE,
b.ITEM_CONTACT_PERSONS_MOBILE_TELEPHONE AS CONTACT_NUMBER FROM 
(SELECT ITEM_COMP_CODE, MAX(ITEM_YEAR) AS ITEM_YEAR FROM szstieos.tlk_企业基础信息
WHERE LENGTH(ITEM_COMP_NAME) > 0 #必须要有公司名称
GROUP BY ITEM_COMP_CODE) AS a 
INNER JOIN szstieos.tlk_企业基础信息 b ON a.ITEM_COMP_CODE = b.ITEM_COMP_CODE AND a.ITEM_YEAR = b.ITEM_YEAR
