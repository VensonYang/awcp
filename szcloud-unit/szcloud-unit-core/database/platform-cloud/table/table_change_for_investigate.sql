
#table变更：
#1.处室人员与项目的对应关系，用于考察、审批等;
#officer_project_mapping
CREATE TABLE `officer_project_mapping` (
  `USER_ID` BIGINT NOT NULL COMMENT '处室人员ID',
  `PROJECT_ID` VARCHAR(36) NOT NULL COMMENT '项目ID',
  PRIMARY KEY(`USER_ID`,`PROJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#2.处室人员与业务类型、项目的高新技术子领域对应关系,用于生成OFFICER_PROJECT_MAPPING表；
#drop table officer_sub_high_biztype_mapping
CREATE TABLE `officer_sub_high_biztype_mapping` (
  `ID` VARCHAR(36) NOT NULL COMMENT '唯一表示',
  `USER_ID` BIGINT NOT NULL COMMENT '处室人员ID',
  `SUB_HIGH_TECH_FIELD_LIST` VARCHAR(3000) NOT NULL COMMENT '高新技术子领域清单，以分号分隔',
  `BIZ_TYPE_LIST` VARCHAR(3000) NOT NULL COMMENT '业务类型(env_system_business_item.ITEM_ID)清单，以分号分隔',
  PRIMARY KEY(`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE officer_sub_high_biztype_mapping ADD INDEX IX_officer_sub_high_biztype_mapping_tech_type(SUB_HIGH_TECH_FIELD_LIST,BIZ_TYPE_LIST);
ALTER TABLE officer_sub_high_biztype_mapping ADD INDEX IX_officer_sub_high_biztype_mapping_user_id(USER_ID);

#3.业务类型与评审表（技术专家评审表、技术专家综合评审表和财务专家评审表）和考察表的对应关系,另外增加业务类型的申报起止日期;
# env_system_business_item 
 ALTER TABLE env_system_business_item ADD TECH_REVIEW_TABLE VARCHAR(100) COMMENT '专家评审表';
 ALTER TABLE env_system_business_item ADD TECH_OVERALL_REVIEW_TABLE VARCHAR(100) COMMENT '专家综合评审表';
 ALTER TABLE env_system_business_item ADD FINANCE_REVIEW_TABLE VARCHAR(100) COMMENT '财务评审表';
 ALTER TABLE env_system_business_item ADD INVESTIGATE_TABLE VARCHAR(100) COMMENT '财务评审表';
 
#4.项目主表增加评审表和考察表相关的字段,作为冗余，在项目考察和评审时可快速打开相应的表格； 
# env_project_base_info 
 ALTER TABLE env_project_base_info ADD TECH_REVIEW_TABLE VARCHAR(100) COMMENT '专家评审表';
 ALTER TABLE env_project_base_info ADD TECH_OVERALL_REVIEW_TABLE VARCHAR(100) COMMENT '专家综合评审表';
 ALTER TABLE env_project_base_info ADD FINANCE_REVIEW_TABLE VARCHAR(100) COMMENT '财务评审表';
 ALTER TABLE env_project_base_info ADD INVESTIGATE_TABLE VARCHAR(100) COMMENT '财务评审表';
 
 

