



SET @rownum = 0;
SELECT (@rownum := @rownum + 1) as 序号, a.COMPANY_NAME AS 单位名称,
field_dic.DATA_VALUE AS 所属细分领域,
e.NAME AS 联系人,	
e.MOBILE AS 联系电话,
a.TOTAL_EPLOYEE_COUNT AS 职工总人数,
reg_type.DATA_VALUE AS 登记注册类型,
a.REGISTER_DATE AS 注册时间,
a.REGISTER_CAPITAL AS 注册资金,
a.ORG_CODE AS 组织机构代码,
CASE WHEN a.QUALIFICATION LIKE '%szsdwzz,1,%' THEN '是' ELSE '否' END AS 是否国高,
a.CREDIT_DEGREE AS 银行信用等级,
a.BUSINESS_SCOPE AS 经营范围,
a.SUMMARY AS 单位简介,
reg_region.DATA_VALUE AS 单位所属区域,
a.COMPANY_ADDRESS AS 单位注册地址,
a.WEBSITE_URL AS 单位网址,
f1.TOTAL_ASSETS AS 总资产2014,
f1.TOTAL_INCOME AS 营业收入2014,
f1.MAIN_INCOME AS 其中产业营业收入2014,	
(f1.TOTAL_ASSETS-f1.TOTAL_LIABILITY) AS 净资产2014,	
f1.TOTAL_FIXED_ASSETS AS 固定资产净值2014,	
f1.NET_MARGIN AS 净利润2014,
(f1.TOTAL_LIABILITY/f1.TOTAL_ASSETS)*100 AS 资产负债率2014,
f1.TOTAL_TAX AS 纳税额2014,
#前年
f2.TOTAL_ASSETS AS 总资产2013,
f2.TOTAL_INCOME AS 营业收入2013,
f2.MAIN_INCOME AS 其中产业营业收入2013,	
(f2.TOTAL_ASSETS-f2.TOTAL_LIABILITY) AS 净资产2013,	
f2.TOTAL_FIXED_ASSETS AS 固定资产净值2013,	
f2.NET_MARGIN AS 净利润2013,
(f2.TOTAL_LIABILITY/f2.TOTAL_ASSETS)*100 AS 资产负债率2013,
f2.TOTAL_TAX AS 纳税额2013,
#大前年
f3.TOTAL_ASSETS AS 总资产2012,
f3.TOTAL_INCOME AS 营业收入2012,
f3.MAIN_INCOME AS 其中产业营业收入2012,	
(f3.TOTAL_ASSETS-f3.TOTAL_LIABILITY) AS 净资产2012,	
f3.TOTAL_FIXED_ASSETS AS 固定资产净值2012,	
f3.NET_MARGIN AS 净利润2012,
(f3.TOTAL_LIABILITY/f3.TOTAL_ASSETS)*100 AS 资产负债率2012,
f3.TOTAL_TAX AS 纳税额2012,
(r.APP_PATENT + r.APP_PRA_PATENT + r. APP_SUR_PATENT) AS 申请专利总数,
r.APP_PATENT AS 发明专利申请数,
(r.OWN_PATENT + r.OWN_PRA_PATENT + r. OWN_SUR_PATENT) AS 授权专利总数,
r.OWN_PATENT AS 发明专利授权数,
r.OWN_SOFTWARE AS 软件著作权申请总数,
r.AUTHORISED_SOFTWARE AS 软件著作权授权数,
a.RD_EPLOYEE_COUNT AS 研发人员总数,
(a.RD_EPLOYEE_COUNT/a.TOTAL_EPLOYEE_COUNT)*100 AS 研发人员占公司比重,
f1.RD_EXPENSE AS 上年度研发投入,
(f1.RD_EXPENSE/f1.TOTAL_INCOME) AS 上年度研发投入占营业收入比重,
f1.RD_EXPENSE AS 研发设备原值
FROM env_company_base_info a 
LEFT JOIN env_company_base_info b ON b.COMPANY_ID = a.COMPANY_ID AND b.YEAR = 2014
LEFT JOIN env_company_base_info c ON c.COMPANY_ID = a.COMPANY_ID AND c.YEAR = 2013
LEFT JOIN env_company_base_info d ON d.COMPANY_ID = a.COMPANY_ID AND d.YEAR = 2012
LEFT JOIN env_company_finance_info f1 on f1.COMPANY_BASE_INFO_ID = b.ID
LEFT JOIN env_company_finance_info f2 on f2.COMPANY_BASE_INFO_ID = c.ID
LEFT JOIN env_company_finance_info f3 on f3.COMPANY_BASE_INFO_ID = d.ID
LEFT JOIN env_company_eployee_info e ON e.COMPANY_BASE_INFO_ID =  a.ID AND e.TITLE = 'dwzyglrjs,dwlxr,'
LEFT JOIN env_company_rd_info r ON r.COMPANY_BASE_INFO_ID = b.ID
LEFT JOIN p_data_dictionary reg_type ON reg_type.CODE = a.REGISTER_TYPE
LEFT JOIN p_data_dictionary reg_region ON reg_region.CODE = a.REGISTER_REGION
LEFT JOIN p_data_dictionary field_dic ON field_dic.CODE = a.INDUSTRY_SMALL_KIND
WHERE a.YEAR = 2015 AND a.STATUS = 20 #提交后的单位信息
AND a.ID IN(SELECT COMPANY_BASE_INFO_ID FROM env_project_base_info
WHERE STATUS = 20 AND PROJECT_TYPE IN('2f6ab65e-a0b9-4d3b-ac15-7b7ee4ebed2a',
'eb5af39e-766f-44ec-a97d-ed8eb9c90090')
)
LIMIT 10;



