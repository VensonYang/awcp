

#env_company_base_info
ALTER TABLE env_company_base_info ADD INDEX IX_env_company_base_info_company_id_year(COMPANY_ID,YEAR);
ALTER TABLE env_company_base_info ADD INDEX IX_env_company_base_info_year(YEAR);


#env_company_eployee_info
ALTER TABLE env_company_eployee_info ADD INDEX 
IX_env_env_company_eployee_info_company_base_info_id(COMPANY_BASE_INFO_ID);

#expert_base_info
ALTER TABLE expert_base_info ADD INDEX 
IX_expert_base_info_user_id(USER_ID);