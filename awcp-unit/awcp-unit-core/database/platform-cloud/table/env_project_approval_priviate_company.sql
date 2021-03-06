

CREATE TABLE `env_project_approval_priviate_company` (
  `APPROVAL_ID` varchar(36) NOT NULL COMMENT '评审表ID',
  RD_EMPLOYEE_AMOUNT_RESULT VARCHAR(100) NULL DEFAULT NULL COMMENT '研发技术人员与20的关系,枚举，小于、等于和大于',
  RD_EMPLOYEE_RATIO_RESULT VARCHAR(100) NULL DEFAULT NULL COMMENT '研发技术人员比例与60%的关系,枚举，小于、等于和大于',
  TOTAL_ASSETS_RESULT VARCHAR(100) NULL DEFAULT NULL COMMENT '总资产与300万的关系，枚举，小于、等于和大于',
  RD_FACTORY_SQUARE_RESULT VARCHAR(100) NULL DEFAULT NULL COMMENT '研究用房面积与300平米的关系,枚举，小于、等于和大于',
  PRIMARY KEY (`APPROVAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '民办非企确认的审批表的特殊信息表';
