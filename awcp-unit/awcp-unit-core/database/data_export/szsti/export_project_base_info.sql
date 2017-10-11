
#导出项目基础信息；
SELECT a.WATERMARK_NUMBER AS 文本号,a.ACCEPT_NUMBER AS 受理号, a.PROJECT_NAME AS 项目名称,DATE_FORMAT(a.START_DATE,'%Y-%m-%d') AS 开始日期,
DATE_FORMAT(a.END_DATE,'%Y-%m-%d') AS 完成日期,b.PROJECT_TYPE_NAME AS 项目列表,
first_tec.DATA_VALUE AS 第一技术领域, second_tec.DATA_VALUE AS 第二技术领域,
high_tect.DATA_VALUE AS 高新技术主领域,sub_high_tect.DATA_VALUE AS 高新技术子领域,phase.DATA_VALUE AS 项目所处阶段,
c.COMPANY_NAME AS 单位名称, c.COMPANY_ADDRESS AS 单位地址,
m.MEMBER_NAME AS 项目联系人, m.MOBILE AS 联系人手机, m.EMAIL AS 联系人邮箱, m.FAX AS 联系人传真
FROM env_project_base_info a
INNER JOIN env_system_business_item b ON a.PROJECT_TYPE = b.ITEM_ID AND b.SYS_ID = 87
INNER JOIN env_company_base_info c ON a.COMPANY_BASE_INFO_ID = c.ID
LEFT JOIN env_project_member_info m ON a.PROJECT_ID = m.PROJECT_ID AND m.ROLE = 'pc'
LEFT JOIN p_data_dictionary first_tec ON a.FIRST_TECH_DISCIPLINE = first_tec.CODE
LEFT JOIN p_data_dictionary second_tec ON a.SECOND_TECH_DISCIPLINE = second_tec.CODE
LEFT JOIN p_data_dictionary high_tect ON a.HIGH_TECH_FIELD = high_tect.CODE
LEFT JOIN p_data_dictionary sub_high_tect ON a.SUB_HIGH_TECH_FIELD = sub_high_tect.CODE
LEFT JOIN p_data_dictionary phase ON a.PROJECT_PHASE = phase.CODE
WHERE a.STATUS 20
