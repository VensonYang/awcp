

SELECT a.PROJECT_NAME, b.FILE_ID FROM env_project_base_info a
INNER JOIN env_project_accessory_info b ON a.PROJECT_ID = b.PROJECT_ID
WHERE a.PROJECT_TYPE IN('2f6ab65e-a0b9-4d3b-ac15-7b7ee4ebed2a',
'eb5af39e-766f-44ec-a97d-ed8eb9c90090')
AND LENGTH(b.FILE_ID) > 0 AND a.PROJECT_TYPE=20
ORDER BY a.PROJECT_NAME;