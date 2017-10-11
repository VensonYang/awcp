





#导出国高评审结果；
SET group_concat_max_len=200000;
DROP TABLE tmp_ind_review;
CREATE TEMPORARY TABLE tmp_ind_review
SELECT a.PROJECT_ID,GROUP_CONCAT(CONCAT(e.NAME , ':',b.TOTAL_FOR_GQ) SEPARATOR '、') AS TOTAL,AVG(b.TOTAL_FOR_GQ) AS GQ,
GROUP_CONCAT(CONCAT(e.NAME , ':',b.EVALUATION_OPINIONS) SEPARATOR '、') AS EVALUATION_OPINIONS,
GROUP_CONCAT(CONCAT(e.NAME , ':',g.HIGH_TECH_PROD_COMMENT) SEPARATOR '、') AS HIGH_TECH_PROD_COMMENT
FROM env_project_base_info a
INNER JOIN env_review_table b ON a.PROJECT_ID = b.PROJECT_ID AND b.REVIEW_TYPE IS NULL AND b.STATUS=20
INNER JOIN expert_base_info e ON e.ID = b.EXPERT_ID
INNER JOIN env_system_business_item i ON i.ITEM_ID = a.PROJECT_TYPE
LEFT JOIN gq_individual_review_table g ON g.REVIEW_ID = b.ID
WHERE i.PROJECT_TYPE_NAME LIKE '%国家高新%'
GROUP BY a.PROJECT_ID;

SET @i=0;
SELECT(@i:=@i+1) AS 序号, com.COMPANY_NAME AS 企业名称,b.REVIEW_TIME AS 评审时间,
t.TOTAL AS 技术专家打分,t.EVALUATION_OPINIONS AS 技术专家企业评价意见,
c.EVALUATION_OPINIONS AS 专家组意见,b.EVALUATION_OPINIONS AS 财务专家意见,ROUND(t.GQ,1) AS 专家组平均分,
com.ORG_CODE AS 组织机构代码,g.TAX_CODE AS 税务登记号,dict.DATA_VALUE AS 主营产品所属技术领域 ,
g.PATENT AS 近三年发明专利,g.PRA_PATENT AS 近三年实用型发明专利,g.AUTHORISED_SOFTWARE AS 近三年软件著作权数,
d.AUDIT_TOTAL_EMPLOYEE AS 职工总数,d.AUDIT_HIGH_EDU_EMPLOYEE AS　大专以上学历科技人员, d.HIGH_EDU_EMPLOYEE_RATIO AS 大专占比,d.AUDIT_RD_EMPLOYEE 从事研究开发人员,d.RD_EMPLOYEE_RATIO AS 研发占比,
g.SALES_REVENUE_1 AS 去年销售收入,g.SALES_REVENUE_2 AS 前年年销售收入,g.SALES_REVENUE_3 AS 大前年销售收入, bt.SALES_INCREASE_RATIO AS 销售增长率,
g.TOTAL_ASSETS_1 AS 去年总资产,g.TOTAL_ASSETS_2 AS 前年总资产,g.TOTAL_ASSETS_3 AS 大前年总资产,bt.ASSET_INCREASE_RATIO AS 资产增长率,
bt.HIGH_TECH_PROD_INCOME  AS 去年高新技术产品收入（万元）,d.TOTAL_RD_EXPENSE AS 近三年研发费用总额（万元）,d.RD_EXPENSE_RATIO AS 三年研发费占比,
d.INTERNAL_RD_EXPENSE AS 近三年中国境内研发费用总额（万元）, bt.INTERNAL_RD_EXPENSE_RATIO AS 境内研发费占比,
bt.OTHER_RD_EXPENSE AS 近三年其它研发费用合计, bt.OTHER_RD_EXPENSE_RATION AS 近三年其它研发费用占比,
t.HIGH_TECH_PROD_COMMENT AS 企业项目和产品的评价
FROM env_project_base_info a
INNER JOIN env_review_table b ON a.PROJECT_ID = b.PROJECT_ID AND b.REVIEW_TYPE LIKE '%国高财务评审表%'
INNER JOIN gq_individual_review_table bt ON bt.REVIEW_ID = b.ID
INNER JOIN env_review_table c ON a.PROJECT_ID = c.PROJECT_ID AND c.REVIEW_TYPE LIKE '%国高综合评审表%'
INNER JOIN gq_individual_review_table d ON d.REVIEW_ID = c.ID
INNER JOIN env_company_base_info com ON a.COMPANY_BASE_INFO_ID = com.ID
INNER JOIN gq_national_application_info g ON g.ORG_CODE = com.ORG_CODE
INNER JOIN tmp_ind_review t ON t.PROJECT_ID = a.PROJECT_ID
LEFT JOIN p_data_dictionary dict ON dict.CODE = a.HIGH_TECH_FIELD
WHERE a.STATUS >= 30 AND b.STATUS=20 AND c.STATUS=20
ORDER BY a.STATUS ASC;