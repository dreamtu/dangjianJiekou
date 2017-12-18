
 /**
  * MySQL系统通用分页存储过程
  * 创建人 ：潘维吉
  */

-- MySQL系统通用分页存储过程sp_com_page

DROP PROCEDURE IF EXISTS sp_com_page;
CREATE  procedure sp_com_page
(
      IN tableName  VARCHAR(2000), --    表名 或者是多表连接查询部分  不可为空（必填）
      IN fields  VARCHAR(2000),    --  查询结果显示字段 可以为* 不可为空（必填）
      IN wherecase  VARCHAR(2000), --  分页查询条件 可以为空
      IN pageSize INT, --  一页显示记录数 不可为空（必填）
      IN pageNow INT ,  --  当前页数 不可为空（必填）
      IN orderField VARCHAR(1000), --  order by 后排序字段，为空表示不排序  可以多个,号分割
      IN  orderFlag  INT,     --  排序标识 0：正序 1：倒序 可以为空
      OUT myrows  INT,   --  总记录数  输出参数
      OUT  myPageCount  INT --  总分页数  输出参数
)
--  执行主体部分
BEGIN
  --  定义部分变量
  --  定义两个整数并且赋值
  SET @v_begin = (pageNow-1)*pagesize; --  开始记录数 limit从0开始
  SET @v_end =pageSize;         --  每页的个数

  SET @v_orderSql ='';  --  排序sql
  SET @v_wherecase =''; --  where查询sql

 --  如果orderField不为空，则进行排序，如果orderFlag=0为升序，1为降序
  if orderField is not null then
    if orderFlag=0 then
      SET @v_orderSql=concat(' order by ',orderField);  -- 升序 concat函数mysql存储过程字符串
    elseif orderFlag=1 then
      SET @v_orderSql=concat(' order by ',orderField,' desc'); -- 降序 concat函数mysql存储过程字符串
    end if;
  end if;

   -- 条件判断语句
  if wherecase is not null then
    SET  @v_wherecase=concat('  where ',wherecase);
  end if;


   -- 计算总的记录数(列也影响记录数 如去重复)
   -- 计算myrows总记录数和myPageCount总的分页数
   -- 组织一个sql  统计总记录数  CONCAT函数链接
  SET @v_sql=CONCAT(' select count(*) into @myrows from ( select ', fields ,' from  ', tableName, @v_wherecase ,' ',@v_orderSql ,' ) t  ');

   -- 执行sql,并把返回的值赋给myrows总记录数;
     PREPARE sqlstrrow from @v_sql; -- 预处理需要执行的动态SQL，其中sqlstrrow是一个变量
     EXECUTE  sqlstrrow ; -- 执行SQL语句
     deallocate prepare sqlstrrow;     -- 释放掉预处理段

    SET myrows = @myrows;  -- 总的记录数赋值

   -- 计算myPageCount总的分页数 mod函数是一个求余函数 MOD函数是用于返回两数相除的余数
  if mod(myrows,pagesize)=0 then
    SET myPageCount =myrows/pagesize;  -- 总的记录数和一页显示记录数是正好是整数关系 相除计算总页数
else
    SET myPageCount=floor(myrows/pagesize)+1;  -- floor()函数取整数截取 总的记录数和一页显示记录数不是整数关系 有剩余 相除计算总页数+1（剩余页）

 end if;

  -- 拼接分页查询语句CONCAT函数链接
   set @v_sql =CONCAT('select t1.*  from (select ', fields ,' from ', tableName, @v_wherecase ,' ',@v_orderSql ,') t1  limit  ' , @v_begin ,' , ', @v_end ) ;

   -- 执行sql,并把返回的值赋给myrows总记录数;
     PREPARE sqlstr from @v_sql; -- 预处理需要执行的动态SQL，其中sqlstr是一个变量
     EXECUTE  sqlstr ; -- 执行SQL语句
     deallocate prepare sqlstr;     -- 释放掉预处理段

END ;

-- 调试存储过程call sp_com_page('admin_user','*',null,5,1,null,1,@a,@b);select @a,@b;

