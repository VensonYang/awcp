
#3材料科技处
SELECT p.ACCEPT_NUMBER AS 受理号, p.PROJECT_NAME AS 项目名称, com.COMPANY_NAME AS 单位名称,ft.DATA_VALUE AS 高新技术领用, st.DATA_VALUE AS 高新技术子领域,
pt.PROJECT_TYPE_NAME AS 计划类别,p.PRINCIPAL_PERSON AS 负责人,p.CONTACT_PERSON AS 联系人, p.CONTACT_PERSON_MOBILE AS 联系电话 ,AVG(rev.TOTAL) AS 平均分,
GROUP_CONCAT(CASE WHEN rev.EVALUATION_OPINIONS IS NULL THEn '' ELSE rev.EVALUATION_OPINIONS END  SEPARATOR ' || ') AS 专家意见
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
LEFT JOIN env_review_table rev ON rev.EXPERT_ID = eeg.EXPERT_ID AND rev.PROJECT_ID = b.PROJECT_ID
WHERE a.REVIEW_TIME >= '2015-04-03'  AND rev.REVIEW_TYPE IS NULL
AND rev.TOTAL IS NOT  NULL AND IS_AVOID != 1 AND p.SUB_HIGH_TECH_FIELD IN
('gxjsly,xnyjnjs,zsqjnyjs,',
'gxjsly,xnyjnjs,xxgxnl,',
'gxjsly,gjsfw,xny,',
'gxjsly,xcljs,wjfjscl,',
'gxjsly,xnyjnjs,hnqn,',
'gxjsly,zyhjjs,qjsc,',
'gxjsly,xcljs,swyycl,',
'gxjsly,xcljs,jxhxp,',
'gxjsly,zyhjjs,zygxkf,',
'gxjsly,xcljs,jscl,',
'gxjsly,xcljs,gfzcl,',
'gxjsly,xnyjnjs,gxjn,'
) AND p.PROJECT_TYPE NOT IN('4e65e3ad-1d95-495e-b506-25571f212ef0',
'9d405e97-e608-4816-985f-fcc36d590919',
'b340bc5a-7cc7-465f-8eae-f35893617a0f',
'016184e9-0b52-4df5-af03-99e322a632f5',
'1cc82ba6-608d-42f6-8923-6e325f5c4f7a',
'4ddd9d85-91ad-4880-8280-1230b6de063b',
'4eebd084-0659-45dd-aefd-91d534739714',
'c05ba8ba-ce7d-470e-bcd5-20a8f6233beb',
'8441e012-ee99-48f5-b430-9d5b45921dce',
'9dfc860d-888e-4597-93f5-e7e7031427f6',
'6d450e3c-77dd-4582-b6d1-cdf7323ee9a5',
'795058e7-6859-4a07-b20f-b2c46beb1fe7',
'966a1dd0-aeb3-48a8-ab6e-d11e97638201',
'25979cf5-c61d-4861-baf6-00b0c4ed8ec4')
GROUP BY p.PROJECT_NAME, com.COMPANY_NAME,ft.DATA_VALUE, st.DATA_VALUE,p.CONTACT_PERSON, p.CONTACT_PERSON_MOBILE;
