


SELECT a.ACCEPT_NUMBER AS 受理编号, a.PROJECT_NAME AS 项目名称,a.PRINCIPAL_PERSON AS 项目负责人, 
c.COMPANY_NAME AS 单位名称,c.COMPANY_ADDRESS AS 单位地址,a.CONTACT_PERSON AS 联系人,
a.CONTACT_PERSON_MOBILE AS 联系电话,d.DATA_VALUE 高新技术子领域,b.PROJECT_TYPE_NAME AS 业务小类,
f.PROJECT_TYPE_NAME AS 业务中类,'已受理' AS 状态 FROM env_project_base_info a 
INNER JOIN env_system_business_item b ON a.PROJECT_TYPE= b.ITEM_ID
INNER JOIN env_company_base_info c ON c.ID = a.COMPANY_BASE_INFO_ID
LEFT JOIN p_data_dictionary d ON a.SUB_HIGH_TECH_FIELD = d.CODE
LEFT JOIN env_system_business_item f ON b.PARENT_ID= f.ITEM_ID
WHERE b.PROJECT_TYPE_NAME LIKE '国家高新%' AND a.STATUS >= 30 