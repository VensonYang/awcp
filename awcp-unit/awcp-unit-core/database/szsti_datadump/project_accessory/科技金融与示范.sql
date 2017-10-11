#银政企合作项目
SELECT ID,ITEM_可行性报告,NULL AS ITEM_合作协议书复印件,ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_银政企合作项目申请书_2014
UNION
SELECT ID,ITEM_可行性报告,NULL AS ITEM_合作协议书复印件,ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_银政企合作项目申请书_2015

UNION

#银政企合作项目
SELECT ID,ITEM_可行性研究报告,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
ITEM_贷款合同复印件,ITEM_贷款进帐单复印件,ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_银政企合作贴息申请书

UNION

#科技金融服务体系建设申请书
SELECT ID,ITEM_可行性报告,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_科技金融服务体系建设申请书_2014
UNION
SELECT ID,ITEM_可行性报告,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_科技金融服务体系建设申请书_2015

UNION

#科技保险保费补贴申请书
SELECT ID,NULL AS ITEM_可行性报告,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
ITEM_保险合同及保单,ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_科技保险保费补贴申请书
UNION
SELECT ID,NULL AS ITEM_可行性报告,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_项目研究水平相关的证明材料复印件,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
ITEM_保险合同及保单,ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_科技保险保费补贴申请书_2015

UNION

#股权有偿资助申请书
SELECT ID,ITEM_可行性,NULL AS ITEM_合作协议书复印件,ITEM_技术水平材料,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_股权有偿资助申请书_2014
UNION
SELECT ID,ITEM_可行性,NULL AS ITEM_合作协议书复印件,ITEM_技术水平材料,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_股权有偿资助申请书_2015

UNION

# 天使投资引导项目申请书
SELECT ID,NULL AS ITEM_可行性,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_技术水平材料,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_天使投资引导项目申请书_2014
UNION
SELECT ID,NULL AS ITEM_可行性,NULL AS ITEM_合作协议书复印件,NULL AS ITEM_技术水平材料,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,NULL AS ITEM_自主产权,NULL AS ITEM_项目实施方案,NULL AS ITEM_其他相关材料
FROM tlk_天使投资引导项目申请书_2015

UNION

#科技应用示范项目申请书
SELECT ID,NULL AS ITEM_可行性,ITEM_合作协议,NULL AS ITEM_技术水平材料,ITEM_组织机构代码,
ITEM_营业执照或登记证书复印件,ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_完税,ITEM_国际相关标准批准文件,ITEM_决算表,
NULL AS ITEM_贷款合同复印件,NULL AS ITEM_贷款进帐单复印件,NULL AS ITEM_付息凭证复印件,
NULL AS ITEM_保险合同及保单,NULL AS ITEM_支付发票,
NULL AS ITEM_担保股东承诺书,ITEM_自主产权,ITEM_项目实施方案,ITEM_其他相关材料
FROM tlk_科技应用示范项目申请书2014;