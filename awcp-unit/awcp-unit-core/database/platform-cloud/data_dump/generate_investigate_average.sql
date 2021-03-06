
#批量更新考察平均分；
CREATE TEMPORARY TABLE tmp_inv_avg
SELECT PROJECT_ID,AVG(TOTAL) AS TOTAL,COUNT(1) AS CNT 
FROM project_investigate_table
WHERE  STATUS=20
GROUP BY PROJECT_ID;
UPDATE env_project_base_info a,tmp_inv_avg b
SET a.INVESTIGATE_AVERAGE=b.TOTAL
WHERE a.PROJECT_ID=b.PROJECT_ID AND a.REVIEW_AVERAGE IS NULL;