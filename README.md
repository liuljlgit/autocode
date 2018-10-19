# autocode
自动代码生成工具，只需一键就能生成包括controller,service,dao,xml,redis等文件。使项目可高效开发，项目集成了复杂查询和简单查询，都使用redis进行缓存存储。使用此项目，只需一键生成代码，后续的业务逻辑我们就不再需要写sql文件，只需调用系统封装好的函数进行高效开发。


注意：release为最新的稳定版本分支

操作步骤：
1、拉取frame-util和autocade这两个代码的仓库到本地

2、mvn install frame-util项目

3、配置GenProperties.java文件，主要配置mysql地址，需要生成的表名（逗号分隔），还有生成实体的路径

4、运行GenMain即可生成代码

5、如需运行，还需配置application.yml这个配置文件

6、启动，使用postman进行测试
