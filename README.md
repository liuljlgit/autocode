一、概述
autocode是自动代码生成工具，只需一键就能生成包括controller,service,dao,xml,redis等文件。使项目可高效开发，项目集成了复杂查询和简单查询，都使用redis进行缓存存储。使用此项目，只需一键生成代码，后续的业务逻辑我们就不再需要写sql文件，只需调用系统封装好的函数进行高效开发。

一、表设计原则
表的主键类型需为BIGINT类型

二、使用流程
a)下载frame-util仓库,使用mvn install生成jar包
b)下载autocode仓库,使用mvn install生成jar包
c)新生成一个项目,在pom.xml中导入jar包，代码如下：
    <dependency>
			<groupId>com.gen</groupId>
			<artifactId>autocode</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
d)运行Test,生成代码
    @Test
    public void autocode() {
      //数据库配置
      GenProperties.URL = "jdbc:mysql://192.168.1.136:3306/test";
      GenProperties.NAME = "root";
      GenProperties.PASS = "root";
      GenProperties.DRIVER = "com.mysql.jdbc.Driver";
      //表配置:可配置表或者视图（视图仅用来查询,视图不生成缓存，没有主键，只生成查询方法）
      GenProperties.tablenames = "daily_amount,load_time";
      GenProperties.useCache = Boolean.TRUE;
      //包路径配置
      GenProperties.entityPackageOutPath = "com.gen.autocodecall.test.entity";
      GenProperties.daoPackageOutPath = "com.gen.autocodecall.test.dao";
      GenProperties.servicePackageOutPath = "com.gen.autocodecall.test.service";
      GenProperties.redisPackageOutPath = "com.gen.autocodecall.test.cache";
      GenProperties.controllerPackageOutPath = "com.gen.autocodecall.test.controller";
      GenProperties.respPackageOutPath = "com.gen.autocodecall.test.webentity";
      GenProperties.xmlPackageOutPath = "mybatis.mapper.test";
      //有一下的模板可供选择。cache_template_v1、cache_template_v2、no_cache_template、sub_table_template、view_template
      GenProperties.templatePath = "template/cache_template_v2";
      //运行代码生成
      GenMain.main(null);
    }
    
三、总结
我们只需要先生成frame-util的jar包，然后再生成autocode的jar包。在我们的项目中加入依赖即可。使用缓存时GenProperties.useCache必须配置未TRUE，否则必须配置为FALSE,我们有五种模板可供选择。其中cache_template_v2是cache_template_v1的升级,改变了其中的缓存结构，其余模板分别为不使用缓存模板、分表模板和视图模板。
