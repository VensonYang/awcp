
#import project investigation data.

ALTER TABLE project_investigate_table
ADD SERVICE_CONTENT INT NULL DEFAULT NULL COMMENT '服务内容评分';

ALTER TABLE project_investigate_table
ADD SERVICE_PERFORMANE INT NULL DEFAULT NULL COMMENT '服务绩效评分';
ALTER TABLE project_investigate_table
ADD INSTITUTION_OPERATION_PERFORMANE INT NULL DEFAULT NULL COMMENT '机构经营业绩评分结果';

ALTER TABLE project_investigate_table
ADD COMPETITION_YEAR_RANK VARCHAR(500) NULL DEFAULT NULL COMMENT '竞赛组别获奖名次';

ALTER TABLE env_project_base_info
ADD COMPANY_HIGH_EDU_EMPLOYEE INT NULL DEFAULT NULL COMMENT '单位大专以上学历或者中级以上职称的科技人员数';