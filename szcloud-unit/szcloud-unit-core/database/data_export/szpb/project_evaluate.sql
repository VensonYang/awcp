

SET @rownum = 0;
SELECT (@rownum := @rownum + 1) as 序号,
a.PROJECT_NAME AS 建设单位和项目名称,
b.COMPANY_NAME AS  项目单位,
field_dic.DATA_VALUE AS  项目所属细分领域,
b.REGISTER_CAPITAL AS 注册资金,
f1.TOTAL_INCOME AS 2014年营业收入,
f1.ENV_PRO_INCOME AS 2014年节能环保产业营业收入,
f2.TOTAL_INCOME  AS 2013年营业收入,
f2.ENV_PRO_INCOME AS 2013年节能环保产业营业收入,
f3.TOTAL_INCOME  AS 2012年营业收入,
f3.ENV_PRO_INCOME AS 2012年节能环保产业营业收入,
a.PROJECT_TOTAL_BUDGET AS 总投资,
a.SELF_FINANCING AS 自有资金,
a.BANK_LOAD AS 银行贷款,
a.APPLY_BUDGET AS 申请补助,
'√' AS 营业执照,
'√' AS 财务审计报告,
'√' AS 自有资金证明,
'√' AS 银行贷款合同或承诺,
'×' AS 招标事项意见表,
'√' AS 核准备案,
'√' AS 环评证明,
'√' AS 场地证明,
'√' AS 真实性声明,
pc.MEMBER_NAME AS 联系人,
pc.PHONE AS 联系电话,
pc.MOBILE AS 联系手机,
pm.MEMBER_NAME AS 负责人,
pm.MOBILE AS 负责人手机,
pm.EMAIL AS 负责人邮箱,
NULL AS 初核意见,
NULL AS 初核结果,
NULL AS 复核意见,
NULL AS 复核结果
FROM env_project_base_info a
INNER JOIN env_company_base_info b ON a.COMPANY_BASE_INFO_ID = b.ID
LEFT JOIN env_company_base_info c1 ON c1.COMPANY_ID = b.COMPANY_ID AND c1.`YEAR` = 2014
LEFT JOIN env_company_base_info c2 ON c2.COMPANY_ID = b.COMPANY_ID AND c2.`YEAR` = 2013
LEFT JOIN env_company_base_info c3 ON c3.COMPANY_ID = b.COMPANY_ID AND c3.`YEAR` = 2012
LEFT JOIN env_company_finance_info f1 ON f1.COMPANY_BASE_INFO_ID = c1.ID
LEFT JOIN env_company_finance_info f2 ON f2.COMPANY_BASE_INFO_ID = c1.ID
LEFT JOIN env_company_finance_info f3 ON f3.COMPANY_BASE_INFO_ID = c1.ID
LEFT JOIN ENV_PROJECT_ADDITIONAL_INFO c ON c.PROJECT_ID = a.PROJECT_ID
LEFT JOIN p_data_dictionary field_dic ON field_dic.CODE = a.SUB_HIGH_TECH_FIELD
LEFT JOIN env_system_business_item sbi ON sbi.ITEM_ID = a.PROJECT_TYPE
LEFT JOIN env_project_member_info pm ON pm.PROJECT_ID = a.PROJECT_ID AND pm.ROLE = 'pm'
LEFT JOIN env_project_member_info pc ON pc.PROJECT_ID = a.PROJECT_ID AND pc.ROLE = 'pc'
WHERE a.PROJECT_TYPE IN('2f6ab65e-a0b9-4d3b-ac15-7b7ee4ebed2a','eb5af39e-766f-44ec-a97d-ed8eb9c90090')
AND a.STATUS = 20
#LIMIT 10;