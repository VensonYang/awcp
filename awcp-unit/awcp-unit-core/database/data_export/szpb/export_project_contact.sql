

SET @rownum = 0;
SELECT (@rownum := @rownum + 1) as 序号,
a.PROJECT_NAME AS 项目名称,
b.COMPANY_NAME AS 项目单位名称,
c.MEMBER_NAME AS 项目负责人,
c.SIGNATURE AS 项目负责人职务,
c.PHONE AS 项目负责人电话,
c.MOBILE AS 项目负责人手机,
c.FAX AS 项目负责人传真,
c.EMAIL AS 项目负责人邮箱,
d.MEMBER_NAME AS 项目联系人,
d.MOBILE AS 项目联系人手机,
d.PHONE AS 项目联系人电话,
d.EMAIL AS 项目联系人邮箱,
NULL AS 申报专员姓名,
NULL AS 申报专员手机号码,
NULL AS 申报专员邮箱,
e.NAME AS 法人代表姓名,
e.MOBILE AS 法人代表电话
FROM env_project_base_info a
INNER JOIN env_company_base_info b ON a.COMPANY_BASE_INFO_ID = b.ID
INNER JOIN env_system_business_item sbi ON sbi.ITEM_ID = a.PROJECT_TYPE
LEFT JOIN ENV_PROJECT_MEMBER_INFO c ON c.PROJECT_ID = a.PROJECT_ID AND c.ROLE = 'pm' 
LEFT JOIN ENV_PROJECT_MEMBER_INFO d ON d.PROJECT_ID = a.PROJECT_ID AND d.ROLE = 'pc'
LEFT JOIN env_company_eployee_info e ON e.COMPANY_BASE_INFO_ID = a.COMPANY_BASE_INFO_ID AND e.TITLE = 'dwzyglrjs,fddbr,'
WHERE sbi.SYS_ID=91 # a.PROJECT_TYPE IN('2f6ab65e-a0b9-4d3b-ac15-7b7ee4ebed2a','eb5af39e-766f-44ec-a97d-ed8eb9c90090')
AND a.STATUS > 20
#LIMIT 10;