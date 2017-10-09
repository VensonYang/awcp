#市高
select ID, 
ITEM_审计报告,ITEM_知识产权证明,ITEM_产品质量检验报告,ITEM_总结报告,ITEM_人员信息表,ITEM_许可证,ITEM_新产品附件清单其他说明,
ITEM_其他证明材料, ITEM_组织机构代码,ITEM_营业执照或登记证书复印件, ITEM_法人代表,ITEM_税务,ITEM_地税登记,ITEM_单位资质,ITEM_决算表,
ITEM_完税,ITEM_国际相关标准批准文件
from tlk_高企认定项目申请书2015 where ITEM_状态字段='已受理';