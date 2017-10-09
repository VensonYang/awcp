

#计算国高数据的存储过程,算法请参考《3.国家高新技术企业认定财务专家评价表-模板.doc》
DROP PROCEDURE IF EXISTS computeIncreaseRatio;
CREATE PROCEDURE `computeIncreaseRatio`()
BEGIN  
  DECLARE l_ID varchar(100);
  DECLARE l_ASSETS1 DECIMAL(17,5);
  DECLARE l_ASSETS2 DECIMAL(17,5);
  DECLARE l_ASSETS3 DECIMAL(17,5);
  DECLARE l_SALE1 DECIMAL(17,5);
  DECLARE l_SALE2 DECIMAL(17,5);
  DECLARE l_SALE3 DECIMAL(17,5);  
  DECLARE l_ASSETS_RATIO DECIMAL(17,5); 
  DECLARE l_SALE_RATIO DECIMAL(17,5); 
  DECLARE l_temp_ratio1 DECIMAL(17,5);
  DECLARE l_temp_ratio2 DECIMAL(17,5);
  DECLARE done INT DEFAULT FALSE;
  DECLARE cur CURSOR FOR SELECT ID,IFNULL(TOTAL_ASSETS_1,0),IFNULL(TOTAL_ASSETS_2,0),IFNULL(TOTAL_ASSETS_3,0),IFNULL(SALES_REVENUE_1,0),IFNULL(SALES_REVENUE_2,0),IFNULL(SALES_REVENUE_3,0) FROM gq_national_application_info;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  OPEN cur;  
    f_loop:LOOP
      FETCH cur INTO l_ID,l_ASSETS1,l_ASSETS2,l_ASSETS3,l_SALE1,l_SALE2,l_SALE3;
      IF done THEN
        LEAVE f_loop;
      END IF;
      SET l_ASSETS_RATIO = 0;
      SET l_SALE_RATIO = 0;
      SET l_temp_ratio1 = 0;
      SET l_temp_ratio2 = 0;
      IF l_ASSETS2 > 0 THEN
          IF l_ASSETS1 > 0 THEN
            SET l_temp_ratio1 = (l_ASSETS1-l_ASSETS2)/l_ASSETS2;
          END IF;
          IF l_ASSETS3 > 0 THEN
           SET l_temp_ratio2 = (l_ASSETS2-l_ASSETS3)/l_ASSETS3;
          END IF;
          IF l_ASSETS1 <=0 THEN
           SET l_ASSETS_RATIO = l_temp_ratio2;
          END IF;          
          IF l_ASSETS3 <=0 THEN
            SET l_ASSETS_RATIO = l_temp_ratio1;
          END IF;
          IF l_ASSETS1 > 0 &&  l_ASSETS3 > 0 THEN
            SET l_ASSETS_RATIO = (l_temp_ratio1 + l_temp_ratio2)/2;
          END IF;
      END IF;      
      SET l_temp_ratio1 = 0;
      SET l_temp_ratio2 = 0;      
      IF l_SALE2 > 0 THEN
          IF l_SALE1 > 0 THEN
            SET l_temp_ratio1 = (l_SALE1-l_SALE2)/l_SALE2;
          END IF;
          IF l_SALE3 > 0 THEN
           SET l_temp_ratio2 = (l_SALE2-l_SALE3)/l_SALE3;
          END IF;
          IF l_SALE1 <= 0 THEN 
            SET l_SALE_RATIO = l_temp_ratio2;
          END IF;          
          IF l_SALE3 <= 0 THEN 
             SET l_SALE_RATIO = l_temp_ratio1;
          END IF;
          IF l_SALE1 > 0 && l_SALE3 > 0 THEN
            SET l_SALE_RATIO = (l_temp_ratio1 + l_temp_ratio2)/2;
          END IF;
      END IF;
      UPDATE gq_national_application_info
      SET ASSETS_INS_RATIO = 100*l_ASSETS_RATIO,
          SALES_INS_RATIO =  100*l_SALE_RATIO
      WHERE ID = l_ID;
    END LOOP f_loop;
  -- 关闭游标
  CLOSE cur;
END


#call computeIncreaseRatio;