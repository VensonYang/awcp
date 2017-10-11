#查询是否有未导入的项目；
SELECT * FROM project_group_info a
INNER JOIN project_project_group b ON a.ID = b.PROJECT_GROUP_ID
LEFT JOIN env_project_base_info p On p.PROJECT_ID = b.PROJECT_ID
WHERE a.REVIEW_TIME = '2015-04-16' AND p.PROJECT_ID IS NULL 
#AND b.PROJECT_ID NOT IN(SELECT PROJECT_ID FROM env_project_accessory_info);

#判断项目的PDF是否被导入；
SELECT CONCAT('''' , b.PROJECT_ID, ''',') FROM project_group_info a
INNER JOIN project_project_group b ON a.ID = b.PROJECT_GROUP_ID
LEFT JOIN env_project_accessory_info c ON b.PROJECT_ID = c.PROJECT_ID  AND c.TYPE LIKE 'dwxmcllx,pdf,'
WHERE a.REVIEW_TIME = '2015-04-16' AND c.PROJECT_ID IS NULL;

#判断项目的附件是否被导入；
SELECT CONCAT('''' , b.PROJECT_ID, ''',') FROM project_group_info a
INNER JOIN project_project_group b ON a.ID = b.PROJECT_GROUP_ID
LEFT JOIN env_project_accessory_info c ON b.PROJECT_ID = c.PROJECT_ID  AND c.TYPE NOT LIKE 'dwxmcllx,pdf,'
WHERE a.REVIEW_TIME = '2015-04-16' AND c.PROJECT_ID IS NULL;

#查询是否未导入的专家；
SELECT * FROM expert_group_info a
INNER JOIN expert_expert_group b ON a.ID = b.EXPERT_GROUP_ID 
LEFT JOIN expert_base_info c on c.ID = b.EXPERT_ID
WHERE a.REVIEW_TIME = '2015-04-16' AND c.USER_ID IS NULL
