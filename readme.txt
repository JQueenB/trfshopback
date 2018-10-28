v1.0
项目开发采用四层架构：
view-->视图层
controller-->控制层 接收前端的输入并调用service层
service-->业务逻辑层 处理业务逻辑并调用dao层
dao-->持久层 操作数据库 MyBatis
接口隔离原则-->扩展
dao接口和dao实现类
service接口和service实现类

vo-->view object(value object)实体类数据格式与前端页面数据格式转换

