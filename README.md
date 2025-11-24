# Spring MVC Blog System

一个基于Spring MVC的博客系统，包含用户管理、文件上传/下载、空间管理等功能。

## 系统功能

### 阶段三：用户与空间基础功能开发
- **模块 3：用户注册/登录/角色管理**
  - 用户注册（事务：插入用户+插入空间+创建文件夹）
  - 用户登录与退出
  - 角色判断与权限拦截（管理员/普通用户）

- **模块 4：空间管理**
  - 查询用户空间信息（总空间、已用、剩余）
  - 冻结/解冻空间（管理员）
  - 更新已用空间（供文件模块调用）

### 阶段四：文件上传/管理/下载（核心）
- **模块 5：文件上传**
  - 多文件上传支持
  - 剩余空间检测
  - 物理文件存储到对应目录
  - 数据库文件记录维护
  - 空间使用更新

- **模块 6：文件列表与分类管理**
  - 按空间展示文件树/表格
  - 支持分类管理（图片、音频、视频、文档等）
  - 文件删除、重命名、分类调整
  - 文件冻结/解冻

- **模块 7：文件下载与统计**
  - 下载权限检查（用户、文件、空间冻结状态）
  - 下载次数统计
  - 空间总下载量更新

### 阶段五：扩容申请与管理员后台
- **模块 8：扩容申请**
  - 空间扩容申请表单
  - 下载次数验证（>=50次）
  - 申请状态管理

- **模块 9：后台管理**
  - 扩容申请审批（列表、详情、通过/驳回）
  - 用户管理（冻结/解冻）
  - 空间管理（冻结/解冻）
  - 文件管理（审核、删除、冻结）

### 阶段六：加分功能
- **模块 10：热力榜**
  - 用户排行统计
  - 下载量+访问量综合算法

- **模块 11：在线预览**
  - 图片轮播
  - 音频/视频在线播放
  - 权限与冻结控制

- **模块 12：关注与通知**
  - 用户关注功能
  - 新文件通知
  - 消息气泡提示

## 技术栈

- **后端框架**：Spring MVC 5.3.20
- **数据库**：MySQL 8.0
- **ORM**：MyBatis 3.5.10 + MyBatis-Spring 2.0.7
- **连接池**：Apache DBCP2 2.9.0
- **日志**：Log4j2 2.17.2
- **文件上传**：Commons FileUpload 1.5
- **前端**：Bootstrap 5 + JSP
- **Java版本**：1.8

## 项目结构

```
blog-system/
├── src/
│   ├── main/
│   │   ├── java/com/blog/
│   │   │   ├── controller/        # 控制器层
│   │   │   ├── service/           # 业务逻辑层
│   │   │   ├── mapper/            # 数据访问层接口
│   │   │   ├── domain/            # 领域对象
│   │   │   ├── filter/            # 过滤器
│   │   │   └── util/              # 工具类
│   │   ├── resources/
│   │   │   ├── spring-mvc.xml     # Spring MVC配置
│   │   │   ├── spring-context.xml # Spring应用配置
│   │   │   ├── application.properties  # 应用配置
│   │   │   ├── mybatis-config.xml # MyBatis配置
│   │   │   ├── mybatis/mapper/    # MyBatis映射文件
│   │   │   └── log4j2.xml         # 日志配置
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml        # Web应用配置
│   │       │   ├── jsp/           # JSP页面
│   │       │   └── views/
│   │       ├── static/            # 静态资源
│   │       └── upload/            # 文件上传目录
│   └── test/
└── db/
    └── init.sql                  # 数据库初始化脚本
```

## 数据库设计

### 表结构

1. **user** - 用户表
   - uid: 用户ID
   - username: 用户名
   - password: 密码（MD5加密）
   - email: 邮箱
   - role: 角色（1=admin, 2=user）
   - status: 状态（0=frozen, 1=normal）

2. **space** - 空间表
   - sid: 空间ID
   - uid: 用户ID
   - ssize_total: 总空间大小
   - ssize_used: 已用空间
   - download_count: 总下载次数
   - status: 状态（0=frozen, 1=normal）

3. **file** - 文件表
   - fid: 文件ID
   - uid: 用户ID
   - file_name: 存储文件名
   - original_name: 原始文件名
   - file_size: 文件大小
   - file_type: 文件类型
   - category: 分类
   - download_count: 下载次数
   - status: 状态（0=frozen, 1=normal）
   - file_path: 物理文件路径

4. **space_apply** - 扩容申请表
   - apply_id: 申请ID
   - uid: 用户ID
   - apply_size: 申请大小
   - status: 状态（0=pending, 1=approved, 2=rejected）

5. **follow** - 关注表
   - follow_id: 关注ID
   - uid: 关注者ID
   - follow_uid: 被关注者ID

6. **notification** - 通知表
   - notification_id: 通知ID
   - uid: 接收者ID
   - follow_uid: 来源用户ID
   - content: 通知内容
   - status: 状态（0=unread, 1=read）

## 快速开始

### 一键启动（推荐）

#### Linux / macOS
```bash
# 给启动脚本执行权限
chmod +x start.sh

# 一键启动（包含数据库初始化）
./start.sh
```

#### Windows
```cmd
# 双击运行或在命令行执行
start.bat
```

启动脚本会自动：
- ✅ 检查 Java 和 Maven 环境
- ✅ 创建文件上传目录
- ✅ 初始化数据库和表结构
- ✅ 创建默认管理员账户 (admin/admin123)
- ✅ 编译并启动 Tomcat 服务器

### 手动启动

如果需要手动配置：

1. **初始化数据库**
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

### 访问应用

启动成功后访问：http://localhost:8080/blog-system

**默认账户：**
- 管理员：admin / admin123  
- 普通用户：user1 / admin123

> 💡 **提示**: 首次启动建议使用一键启动脚本，会自动处理所有配置。

---

## 安装与配置（传统方式）

### 前置条件
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Tomcat 9.0+

### 安装步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd blog-system
```

2. **创建数据库**
```bash
mysql -u root -p < db/init.sql
```

3. **配置数据库连接**
编辑 `src/main/resources/application.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/blog_system?useUnicode=true&characterEncoding=utf-8
db.username=root
db.password=your_password
```

4. **创建上传目录**
```bash
mkdir -p /opt/blog_system/upload
chmod 777 /opt/blog_system/upload
```

5. **编译项目**
```bash
mvn clean compile
```

6. **构建WAR**
```bash
mvn clean package
```

7. **部署到Tomcat**
```bash
cp target/blog-system-1.0.0.war $CATALINA_HOME/webapps/blog-system.war
```

8. **启动Tomcat**
```bash
$CATALINA_HOME/bin/startup.sh
```

9. **访问应用**
```
http://localhost:8080/blog-system
```

## 默认账户

系统会自动创建以下默认账户：

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin123 | 管理员 | 系统管理员账户 |
| user1 | admin123 | 普通用户 | 测试用户账户 |

> ⚠️ **安全提醒**: 生产环境中请修改默认密码。

## API文档

### 用户接口

- `GET /user/login` - 登录页面
- `POST /user/doLogin` - 执行登录
- `GET /user/register` - 注册页面
- `POST /user/doRegister` - 执行注册
- `GET /user/space` - 用户空间
- `GET /user/profile` - 用户资料
- `POST /user/updateProfile` - 更新资料
- `GET /user/logout` - 登出
- `GET /user/list` - 用户列表（管理员）
- `POST /user/freeze/{uid}` - 冻结用户（管理员）
- `POST /user/unfreeze/{uid}` - 解冻用户（管理员）

### 文件接口

- `GET /file/upload` - 上传页面
- `POST /file/doUpload` - 执行上传
- `GET /file/list` - 文件列表
- `GET /file/download/{fid}` - 文件下载
- `POST /file/delete/{fid}` - 删除文件
- `POST /file/rename/{fid}` - 重命名文件
- `POST /file/changeCategory/{fid}` - 更改分类
- `POST /file/freeze/{fid}` - 冻结文件（管理员）
- `POST /file/unfreeze/{fid}` - 解冻文件（管理员）

### 空间接口

- `GET /space/info` - 空间信息
- `GET /space/list` - 空间列表（管理员）
- `POST /space/freeze/{uid}` - 冻结空间（管理员）
- `POST /space/unfreeze/{uid}` - 解冻空间（管理员）

### 扩容接口

- `GET /apply/request` - 申请页面
- `POST /apply/doRequest` - 提交申请
- `GET /apply/list` - 申请列表（管理员）
- `GET /apply/myApplies` - 我的申请
- `POST /apply/approve/{applyId}` - 批准申请（管理员）
- `POST /apply/reject/{applyId}` - 拒绝申请（管理员）

## 权限说明

- **普通用户**：
  - 上传/删除/下载自己的文件
  - 查看自己的空间
  - 申请扩容
  - 关注其他用户
  - 查看通知

- **管理员**：
  - 所有普通用户权限
  - 管理所有用户（冻结/解冻）
  - 管理所有空间（冻结/解冻）
  - 审批扩容申请
  - 管理文件（冻结/删除）

## 安全特性

- 密码使用MD5+盐加密
- 登录拦截（Filter）
- 权限验证
- 文件访问控制
- XSS防护
- CSRF防护

## 常见问题

### 文件上传失败
- 检查上传目录是否存在且有写权限
- 检查文件大小是否超过限制
- 检查剩余空间是否充足

### 数据库连接失败
- 检查MySQL服务是否运行
- 验证数据库连接配置
- 检查用户权限

### 404错误
- 检查URL路径是否正确
- 确认WAR文件已正确部署
- 检查Tomcat日志

## 维护与扩展

### 日志查看
```bash
tail -f logs/app.log
```

### 数据备份
```bash
mysqldump -u root -p blog_system > backup.sql
```

### 性能优化
- 增加数据库索引
- 启用缓存机制
- 优化查询语句
- 使用CDN加速

## 贡献指南

欢迎提交Issue和Pull Request！

## 许可证

MIT License

## 联系方式

如有问题或建议，请联系项目维护者。

## 更新日志

### v1.0.0 (2024-01-01)
- 初始版本发布
- 实现基本的用户、文件、空间管理
- 完成文件上传下载功能
- 扩容申请审批流程
