SELECT p.PROJECT_ID ,p.ACCEPT_NUMBER AS 受理号, p.PROJECT_NAME AS 项目名称, com.COMPANY_NAME AS 单位名称,ft.DATA_VALUE AS 高新技术领域, st.DATA_VALUE AS 高新技术子领域,
pt.PROJECT_TYPE_NAME AS 计划类别,p.PRINCIPAL_PERSON AS 负责人,p.CONTACT_PERSON AS 联系人, p.CONTACT_PERSON_MOBILE AS 联系电话 ,round(AVG(rev.TOTAL)) AS 平均分,
agg.EVALUATION_OPINIONS AS 专家综合意见
FROM project_group_info a
INNER JOIN project_project_group b ON a.ID = b.PROJECT_GROUP_ID
INNER JOIN env_project_base_info p ON p.PROJECT_ID = b.PROJECT_ID
INNER JOIN env_company_base_info com ON com.ID = p.COMPANY_BASE_INFO_ID
INNER JOIN project_expert_match m ON b.PROJECT_GROUP_ID = m.PROJECT_GROUP_ID
INNER JOIN expert_group_info eg ON eg.ID = m.EXPERT_GROUP_ID
LEFT JOIN p_data_dictionary ft ON ft.CODE = p.HIGH_TECH_FIELD
LEFT JOIN p_data_dictionary st ON st.CODE = p.SUB_HIGH_TECH_FIELD
LEFT JOIN env_system_business_item pt ON pt.ITEM_ID = p.PROJECT_TYPE
LEFT JOIN expert_expert_group eeg ON eeg.EXPERT_GROUP_ID = eg.ID
LEFT JOIN env_review_table rev ON rev.EXPERT_ID = eeg.EXPERT_ID AND rev.PROJECT_ID = b.PROJECT_ID AND rev.REVIEW_TYPE is null
LEFT JOIN env_review_table agg ON agg.PROJECT_ID = b.PROJECT_ID AND agg.REVIEW_TYPE LIKE '验收综合评审表'
WHERE a.REVIEW_TIME >= '2015-04-03'
AND rev.TOTAL IS NOT NULL AND rev.IS_AVOID != 1
and agg.REVIEW_TYPE LIKE '验收综合评审表%'
GROUP BY rev.PROJECT_ID;