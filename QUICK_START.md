# Blog System Quick Start Guide

## 快速启动指南

本指南帮助您快速配置并运行博客系统。

### 环境要求

- **Java**: 1.8 或更高版本
- **Maven**: 3.6 或更高版本  
- **MySQL**: 8.0 或更高版本
- **操作系统**: Linux, macOS, 或 Windows

### 一键启动

#### Linux / macOS

```bash
# 给启动脚本执行权限
chmod +x start.sh

# 运行启动脚本
./start.sh
```

#### Windows

```cmd
# 双击运行或在命令行执行
start.bat
```

### 启动过程说明

启动脚本会自动完成以下操作：

1. **环境检查**: 验证 Java 和 Maven 是否已安装
2. **目录创建**: 自动创建文件上传目录 `/opt/blog_system/upload` (Linux) 或 `C:\blog_system\upload` (Windows)
3. **数据库初始化**: 
   - 自动创建 `blog_system` 数据库
   - 创建所有必需的数据表
   - 插入默认用户账户
4. **项目构建**: 使用 Maven 编译和打包项目
5. **启动服务**: 启动内嵌的 Tomcat 服务器

### 默认账户

系统会自动创建以下默认账户：

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin123 | 管理员 | 系统管理员账户 |
| user1 | admin123 | 普通用户 | 测试用户账户 |

### 访问应用

启动成功后，在浏览器中访问：

```
http://localhost:8080/blog-system
```

### 手动配置（可选）

如果需要手动配置，请按以下步骤：

#### 1. 数据库配置

编辑 `src/main/resources/application.properties`:

```properties
# 数据库连接配置
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/blog_system?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
db.username=root
db.password=your_mysql_password
```

#### 2. 初始化数据库

```bash
mysql -u root -p < db/init.sql
```

#### 3. 手动启动

```bash
# 编译项目
mvn clean package -DskipTests

# 启动 Tomcat
mvn tomcat7:run
```

### 项目结构

```
blog-system/
├── src/main/java/com/blog/          # Java 源代码
│   ├── controller/                  # 控制器层
│   ├── service/                     # 业务逻辑层
│   ├── mapper/                      # 数据访问层
│   ├── domain/                      # 实体类
│   ├── filter/                      # 过滤器
│   └── util/                        # 工具类
├── src/main/resources/              # 配置文件
│   ├── spring-*.xml                 # Spring 配置
│   ├── mybatis/                     # MyBatis 映射文件
│   └── application.properties       # 应用配置
├── src/main/webapp/                 # Web 资源
│   ├── WEB-INF/jsp/                 # JSP 页面
│   └── static/                      # 静态资源
├── db/                              # 数据库脚本
│   └── init.sql                     # 初始化脚本
├── start.sh                         # Linux/macOS 启动脚本
├── start.bat                        # Windows 启动脚本
└── pom.xml                          # Maven 配置
```

### 常见问题

#### 1. Java 版本问题

确保使用 Java 1.8 或更高版本：

```bash
java -version
```

#### 2. MySQL 连接失败

- 检查 MySQL 服务是否启动
- 验证用户名和密码是否正确
- 确认数据库权限设置

#### 3. 端口占用

如果 8080 端口被占用，可以修改 `pom.xml` 中的端口配置：

```xml
<configuration>
    <port>8081</port>
    <path>/blog-system</path>
</configuration>
```

#### 4. 文件上传权限

确保上传目录有写权限：

```bash
# Linux/macOS
sudo chmod 777 /opt/blog_system/upload

# Windows
# 确保应用对 C:\blog_system\upload 有写权限
```

### 开发模式

在开发时，可以使用以下命令启用热重载：

```bash
mvn tomcat7:run-war -Dmaven.tomcat.fork=true
```

### 生产部署

生产环境部署请参考 [DEPLOYMENT.md](DEPLOYMENT.md) 文档。

### 技术支持

如遇问题，请检查：

1. **应用日志**: 控制台输出
2. **数据库日志**: MySQL 错误日志
3. **系统要求**: 确保满足所有环境要求

更多详细信息请参考：
- [DEPLOYMENT.md](DEPLOYMENT.md) - 部署指南
- [DEVELOPMENT.md](DEVELOPMENT.md) - 开发指南
- [README.md](README.md) - 项目说明