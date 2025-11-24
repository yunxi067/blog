# 部署指南

## 环境要求

- Java 1.8 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或更高版本
- Tomcat 9.0 或更高版本
- 操作系统：Linux 或 Windows

## 部署步骤

### 1. 数据库初始化

```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
SOURCE /path/to/db/init.sql;

# 查看创建的数据库
SHOW DATABASES;
USE blog_system;
SHOW TABLES;
```

### 2. 项目配置

编辑 `src/main/resources/application.properties` 文件，根据实际环境配置：

```properties
# 数据库连接配置
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/blog_system?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
db.username=root
db.password=your_mysql_password

# 文件上传配置
upload.path=/opt/blog_system/upload
upload.maxSize=209715200

# 其他配置
session.timeout=1800
```

### 3. 创建上传目录

```bash
# Linux/Mac
mkdir -p /opt/blog_system/upload
chmod 777 /opt/blog_system/upload

# Windows
mkdir C:\blog_system\upload
```

### 4. 编译项目

```bash
cd /path/to/blog-system
mvn clean compile
```

### 5. 打包项目

```bash
mvn clean package -DskipTests
```

编译后的 WAR 文件位于 `target/blog-system-1.0.0.war`

### 6. 部署到 Tomcat

#### Linux/Mac
```bash
# 复制 WAR 文件到 Tomcat
cp target/blog-system-1.0.0.war $CATALINA_HOME/webapps/

# 启动 Tomcat
$CATALINA_HOME/bin/startup.sh

# 查看日志
tail -f $CATALINA_HOME/logs/catalina.out
```

#### Windows
```bash
# 复制 WAR 文件到 Tomcat
copy target\blog-system-1.0.0.war %CATALINA_HOME%\webapps\

# 启动 Tomcat
%CATALINA_HOME%\bin\startup.bat

# 查看日志
type %CATALINA_HOME%\logs\catalina.log
```

### 7. 访问应用

打开浏览器访问：
```
http://localhost:8080/blog-system
```

## 默认账户（可选）

为了快速测试，可以在数据库中插入默认账户：

```sql
-- 插入管理员账户
INSERT INTO user (username, password, email, role, status) 
VALUES ('admin', '5f9c4ab08cac7457e9111a30e4664882', 'admin@blog.com', 1, 1);

-- 插入普通用户账户
INSERT INTO user (username, password, email, role, status) 
VALUES ('user1', '5f9c4ab08cac7457e9111a30e4664882', 'user1@blog.com', 2, 1);

-- 为用户创建空间
-- admin 的 uid 是最后查询的 id，这里假设是 1
INSERT INTO space (uid, ssize_total, ssize_used, download_count, status) 
VALUES (1, 104857600, 0, 0, 1);

INSERT INTO space (uid, ssize_total, ssize_used, download_count, status) 
VALUES (2, 104857600, 0, 0, 1);
```

默认密码的 MD5 值是 `admin123` 的加密结果。

## 常见问题排查

### 404 错误
- 检查 Tomcat 是否成功部署了应用
- 查看 Tomcat 日志：`$CATALINA_HOME/logs/catalina.out`
- 确认 WAR 文件名是否正确解压

### 数据库连接失败
```
ERROR: Cannot get a connection, pool error Timeout waiting for idle object
```
解决方案：
- 检查 MySQL 服务是否启动
- 验证数据库连接配置是否正确
- 检查数据库用户权限
- 检查防火墙设置

### 文件上传失败
- 检查上传目录是否存在且有写权限
- 查看上传目录路径是否与配置一致
- 检查磁盘空间是否充足
- 查看 Tomcat 日志中的错误信息

### 页面加载缓慢
- 检查数据库查询性能
- 查看数据库索引是否合理
- 增加 Java 堆内存：
  ```bash
  export CATALINA_OPTS="-Xms256m -Xmx512m"
  ```

## 性能调优

### 1. 数据库优化
```sql
-- 添加常用查询索引
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_space_uid ON space(uid);
CREATE INDEX idx_file_uid_category ON file(uid, category);
```

### 2. 内存配置
编辑 `$CATALINA_HOME/bin/catalina.sh`（Linux）或 `catalina.bat`（Windows）：
```bash
# Linux
export CATALINA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"

# Windows
set CATALINA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC
```

### 3. 连接池配置
在 `spring-context.xml` 中调整：
```xml
<property name="initialSize" value="10"/>
<property name="maxTotal" value="50"/>
<property name="maxIdle" value="20"/>
<property name="minIdle" value="10"/>
```

## 备份与恢复

### 备份数据库
```bash
mysqldump -u root -p blog_system > backup_$(date +%Y%m%d).sql
```

### 恢复数据库
```bash
mysql -u root -p blog_system < backup_20240101.sql
```

### 备份文件
```bash
tar -czf upload_backup_$(date +%Y%m%d).tar.gz /opt/blog_system/upload/
```

## 监控与日志

### 查看应用日志
```bash
tail -f logs/app.log
```

### 查看 Tomcat 日志
```bash
tail -f $CATALINA_HOME/logs/catalina.out
```

### 监控系统资源
```bash
# Linux
top
free -m
df -h

# Windows
tasklist
systeminfo
```

## 安全建议

1. 修改默认密码
2. 更新 MySQL 用户权限
3. 禁用不必要的服务
4. 配置防火墙规则
5. 定期更新依赖库
6. 启用 HTTPS
7. 定期备份数据

## 升级指南

1. 备份数据库和文件
2. 停止 Tomcat 服务
3. 更新应用代码
4. 重新编译和打包
5. 替换旧的 WAR 文件
6. 启动 Tomcat
7. 验证应用正常运行

## 支持

遇到问题时，请检查：
- 应用日志（`logs/app.log`）
- Tomcat 日志（`$CATALINA_HOME/logs/`）
- MySQL 日志（检查 MySQL 错误日志）
- 系统日志

如需进一步帮助，请提供错误日志和系统配置信息。
