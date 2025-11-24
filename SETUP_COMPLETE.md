# Blog System Auto-Run Setup

## 概述

本项目已配置好完整的自动运行环境，包含 Tomcat 和数据库的自动配置。用户只需运行简单的命令即可启动整个系统。

## 快速启动

### 方法一：一键启动（推荐）

```bash
# Linux/macOS
./start.sh

# Windows
start.bat
```

### 方法二：分步启动

1. **初始化数据库**（如果需要）
   ```bash
   # Linux/macOS
   ./setup-db.sh
   
   # Windows
   setup-db.bat
   ```

2. **启动应用**
   ```bash
   mvn clean package -DskipTests
   mvn tomcat7:run
   ```

## 已配置的功能

### ✅ 自动化脚本
- `start.sh` / `start.bat` - 一键启动脚本
- `setup-db.sh` / `setup-db.bat` - 数据库初始化脚本
- `verify-setup.sh` - 环境验证脚本

### ✅ Maven 配置
- 添加了 Tomcat 7 Maven 插件
- 配置端口 8080，上下文路径 `/blog-system`
- UTF-8 编码支持
- 开发环境配置

### ✅ 数据库配置
- 完整的数据库初始化脚本
- 自动创建默认用户账户
- 外键约束和索引优化

### ✅ 默认账户
| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin123 | 管理员 | 系统管理员账户 |
| user1 | admin123 | 普通用户 | 测试用户账户 |

### ✅ 环境要求检查
启动脚本会自动检查：
- Java 1.8+ 安装状态
- Maven 3.6+ 安装状态
- MySQL 服务状态
- 文件上传目录权限

## 项目特性

### 核心功能
- **用户管理**：注册、登录、角色权限控制
- **空间管理**：存储空间分配、使用统计、扩容申请
- **文件管理**：上传、下载、分类、权限控制
- **管理后台**：用户管理、文件审核、扩容审批

### 技术特性
- **安全性**：MD5 + 盐加密、登录拦截、权限验证
- **事务性**：用户注册、文件上传等关键操作使用事务
- **性能优化**：数据库索引、连接池配置
- **易部署**：内嵌 Tomcat、一键启动脚本

## 访问地址

启动成功后访问：
```
http://localhost:8080/blog-system
```

## 文件结构

```
blog-system/
├── start.sh                 # Linux/macOS 启动脚本
├── start.bat               # Windows 启动脚本
├── setup-db.sh             # Linux 数据库初始化
├── setup-db.bat            # Windows 数据库初始化
├── verify-setup.sh         # 环境验证脚本
├── QUICK_START.md          # 快速开始指南
├── README.md              # 项目说明文档
├── pom.xml                # Maven 配置（含 Tomcat 插件）
├── src/                   # 源代码
├── db/init.sql           # 数据库初始化脚本
└── target/               # 编译输出目录
```

## 故障排除

### 1. Java/Maven 未安装
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-8-jdk maven

# CentOS/RHEL
sudo yum install java-1.8.0-openjdk-devel maven
```

### 2. MySQL 连接问题
- 检查 MySQL 服务是否启动：`sudo service mysql status`
- 重启 MySQL：`sudo service mysql restart`
- 检查密码配置：默认为 `123456`

### 3. 端口占用
修改 `pom.xml` 中的端口配置：
```xml
<configuration>
    <port>8081</port>
    <path>/blog-system</path>
</configuration>
```

### 4. 文件上传权限
```bash
# 创建上传目录
sudo mkdir -p /opt/blog_system/upload
sudo chmod 777 /opt/blog_system/upload
```

## 开发模式

开发时可以使用热重载：
```bash
mvn tomcat7:run-war -Dmaven.tomcat.fork=true
```

## 生产部署

生产环境部署请参考 `DEPLOYMENT.md` 文档。

## 技术栈

- **后端**：Spring MVC 5.3.20 + MyBatis 3.5.10
- **数据库**：MySQL 8.0 + DBCP2 连接池
- **前端**：JSP + Bootstrap 5
- **构建工具**：Maven 3.6+
- **应用服务器**：Apache Tomcat 7（内嵌）
- **Java 版本**：1.8

---

## 总结

本项目已完全配置好自动运行环境，用户只需：

1. 安装 Java 1.8+、Maven 3.6+、MySQL 8.0+
2. 运行 `./start.sh`（Linux/macOS）或 `start.bat`（Windows）
3. 访问 `http://localhost:8080/blog-system`
4. 使用默认账户登录：admin/admin123

所有配置文件、脚本和文档都已准备就绪，开箱即用！