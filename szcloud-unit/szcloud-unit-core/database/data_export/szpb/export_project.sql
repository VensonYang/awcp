


SET @rownum = 0;
SELECT (@rownum := @rownum + 1) as 序号, CONCAT(b.COMPANY_NAME, '-',a.PROJECT_NAME) AS 建设单位和项目名称,
CONCAT(a.START_DATE, '-',a.END_DATE) AS 起止年限,
c.TARGET AS 主要建设目标,
c.NECESSITY AS 项目建设必要性,
a.PROJECT_ADDRESS AS 项目建设地址,
a.PROJECT_TOTAL_BUDGET AS 总投资,
a.SELF_FINANCING AS 自有资金,
a.BANK_LOAD AS 银行贷款,
a.OTHER_FINANCING AS 其他,	
a.FIXED_ASSET_INVESTMENT AS 固定资产投资,
a.CIVIL_ENGINEERING_INVESTMENT AS 土建投资,	
a.EQUIPMENT_INVESTMENT AS 设备投资,	
a.LIQUIDITY AS 流动资金,	
a.APPLY_BUDGET AS 申请补助资金,	
0 AS 项目达产后销售收入,
field_dic.DATA_VALUE AS  项目所属细分领域,
sbi.PROJECT_TYPE_NAME AS 申报扶持类别,
'' AS 备注
FROM env_project_base_info a
INNER JOIN env_company_base_info b ON a.COMPANY_BASE_INFO_ID = b.ID
LEFT JOIN ENV_PROJECT_ADDITIONAL_INFO c ON c.PROJECT_ID = a.PROJECT_ID
LEFT JOIN p_data_dictionary field_dic ON field_dic.CODE = a.SUB_HIGH_TECH_FIELD
LEFT JOIN env_system_business_item sbi ON sbi.ITEM_ID = a.PROJECT_TYPE
WHERE a.PROJECT_TYPE IN('2f6ab65e-a0b9-4d3b-ac15-7b7ee4ebed2a','eb5af39e-766f-44ec-a97d-ed8eb9c90090')
AND a.STATUS = 20
#LIMIT 10;