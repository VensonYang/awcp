 SELECT
	id AS projectId,
	item_组织机构码 AS compCode,
	item_pdf_type AS type,
	author AS userId,
	item_计划年度 AS planYear
FROM
	self_all_real_projs
WHERE
	item_状态字段 LIKE '已受理'
	AND item_计划年度 > 2013;
