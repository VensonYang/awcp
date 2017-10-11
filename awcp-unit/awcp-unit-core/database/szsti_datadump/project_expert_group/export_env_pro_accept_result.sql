#导出节能环保受理数据
SELECT a.ACCEPT_NUMBER AS 受理号,a.STATUS,a.PROJECT_NAME AS 项目名称, c.COMPANY_NAME AS 单位名称,c.COMPANY_ADDRESS AS 单位地址,
a.CONTACT_PERSON AS 联系人,a.CONTACT_PERSON_MOBILE AS 联系电话,h.DATA_VALUE AS 高新技术领域,s.DATA_VALUE AS 高新技术子领域,
d.PROJECT_TYPE_NAME AS 计划类型,b.PROJECT_TYPE_NAME AS 项目类别 FROM env_project_base_info a
INNER JOIN env_system_business_item b ON a.project_type = b.ITEM_ID
INNER JOIN env_company_base_info c ON c.id = a.COMPANY_BASE_INFO_ID
LEFT JOIN env_system_business_item d ON d.ITEM_ID = b.PARENT_ID
LEFT JOIN p_data_dictionary h ON h.CODE = a.HIGH_TECH_FIELD
LEFT JOIN p_data_dictionary s ON s.CODE = a.SUB_HIGH_TECH_FIELD
where b.SYS_ID = 87 
AND a.STATUS>= 30 AND a.ACCEPT_TIME > '2015-04-04';
