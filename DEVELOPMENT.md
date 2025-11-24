# 开发指南

## 项目架构

```
Blog System (Spring MVC)
│
├── 表现层 (View)
│   └── JSP 页面 + Bootstrap CSS
│
├── 控制层 (Controller)
│   ├── UserController
│   ├── FileController
│   ├── SpaceController
│   └── SpaceApplyController
│
├── 业务层 (Service)
│   ├── UserService
│   ├── FileService
│   ├── SpaceService
│   ├── SpaceApplyService
│   ├── FollowService
│   └── NotificationService
│
├── 数据访问层 (Mapper)
│   └── MyBatis 接口和 XML 映射
│
└── 支撑层 (Util & Filter)
    ├── MD5Util - 密码加密
    ├── FileUtil - 文件操作
    ├── SessionUtil - 会话管理
    ├── LoginFilter - 登录拦截
    └── AuthorityFilter - 权限验证
```

## 开发环境配置

### IDE 推荐
- IntelliJ IDEA（推荐）
- Eclipse
- VSCode + Maven

### 快速启动

```bash
# 1. 克隆项目
git clone <repository-url>
cd blog-system

# 2. 配置 MySQL 连接
vi src/main/resources/application.properties

# 3. 创建数据库
mysql -u root -p < db/init.sql

# 4. 编译项目
mvn clean compile

# 5. 运行测试
mvn test

# 6. 打包
mvn clean package

# 7. 部署到本地 Tomcat
cp target/*.war $CATALINA_HOME/webapps/blog-system.war
$CATALINA_HOME/bin/startup.sh
```

## 代码规范

### 包命名规范
```
com.blog.controller    - 控制器层
com.blog.service       - 业务逻辑层
com.blog.service.impl  - 业务实现层
com.blog.mapper        - 数据访问层
com.blog.domain        - 实体类
com.blog.filter        - 过滤器
com.blog.util          - 工具类
```

### 类命名规范
- 控制器：`UserController`（必须以 Controller 结尾）
- 服务接口：`UserService`（必须以 Service 结尾）
- 服务实现：`UserServiceImpl`（必须以 ServiceImpl 结尾）
- Mapper 接口：`UserMapper`（必须以 Mapper 结尾）
- 实体类：`User`（业务名词）
- 工具类：`FileUtil`（必须以 Util 结尾）
- 过滤器：`LoginFilter`（必须以 Filter 结尾）

### 命名约定

#### 数据库字段
```
创建时间：create_time
修改时间：update_time
状态：status（0=disabled, 1=enabled）
删除标志：deleted（0=not deleted, 1=deleted）
```

#### 方法命名
```
get*    - 获取单个对象
get*List - 获取列表
select* - 数据库查询
insert* - 数据库插入
update* - 数据库更新
delete* - 数据库删除
```

#### 变量命名
```
使用驼峰式命名
例如：userId, userName, totalSize
```

## 常见开发任务

### 1. 添加新的业务模块

#### 步骤 1：创建实体类
```java
// src/main/java/com/blog/domain/YourEntity.java
@Data
public class YourEntity {
    private Integer id;
    private String name;
    // ... 其他属性
}
```

#### 步骤 2：创建 Mapper 接口
```java
// src/main/java/com/blog/mapper/YourEntityMapper.java
@Repository
public interface YourEntityMapper {
    YourEntity selectById(Integer id);
    int insert(YourEntity entity);
    int update(YourEntity entity);
    int delete(Integer id);
}
```

#### 步骤 3：创建 Mapper XML
```xml
<!-- src/main/resources/mybatis/mapper/YourEntityMapper.xml -->
<mapper namespace="com.blog.mapper.YourEntityMapper">
    <resultMap id="YourEntityResultMap" type="YourEntity">
        <!-- 映射关系 -->
    </resultMap>
    <!-- SQL 语句 -->
</mapper>
```

#### 步骤 4：创建 Service 接口
```java
// src/main/java/com/blog/service/YourEntityService.java
public interface YourEntityService {
    YourEntity getById(Integer id);
    boolean create(YourEntity entity);
    boolean update(YourEntity entity);
    boolean delete(Integer id);
}
```

#### 步骤 5：创建 Service 实现
```java
// src/main/java/com/blog/service/impl/YourEntityServiceImpl.java
@Service
@Transactional
public class YourEntityServiceImpl implements YourEntityService {
    @Autowired
    private YourEntityMapper mapper;
    
    // 实现接口方法
}
```

#### 步骤 6：创建 Controller
```java
// src/main/java/com/blog/controller/YourEntityController.java
@Controller
@RequestMapping("/yourEntity")
public class YourEntityController {
    @Autowired
    private YourEntityService service;
    
    @GetMapping("/list")
    public String list(Model model) {
        // 业务逻辑
        return "view/list";
    }
}
```

#### 步骤 7：创建 JSP 页面
```jsp
<!-- src/main/webapp/WEB-INF/jsp/yourEntity/list.jsp -->
```

### 2. 添加新的 API 端点

```java
@PostMapping("/doSomething")
@ResponseBody
public Map<String, Object> doSomething(@RequestParam String param, HttpSession session) {
    Map<String, Object> result = new HashMap<>();
    Integer uid = SessionUtil.getLoginUid(session);
    
    if (uid == null) {
        result.put("success", false);
        result.put("message", "not logged in");
        return result;
    }
    
    // 业务逻辑
    
    result.put("success", true);
    result.put("message", "operation successful");
    return result;
}
```

### 3. 添加数据库迁移

```sql
-- db/migration/add_new_table.sql
ALTER TABLE `table_name` ADD COLUMN `new_column` VARCHAR(100);
CREATE INDEX idx_table_column ON table_name(new_column);
```

### 4. 添加工具类

```java
// src/main/java/com/blog/util/YourUtil.java
public class YourUtil {
    /**
     * 工具方法描述
     */
    public static String yourMethod(String param) {
        // 实现逻辑
        return result;
    }
}
```

## 事务管理

### 使用注解事务
```java
@Service
@Transactional
public class UserServiceImpl implements UserService {
    // 所有方法都在事务中执行
    
    @Transactional(readOnly = true)
    public User getUserById(Integer uid) {
        // 只读事务
    }
}
```

### 传播行为
```java
// 创建新事务
@Transactional(propagation = Propagation.REQUIRES_NEW)

// 支持当前事务
@Transactional(propagation = Propagation.REQUIRED)

// 不需要事务
@Transactional(propagation = Propagation.NEVER)
```

## 异常处理

### 全局异常处理
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        logger.error("Unexpected error", e);
        return "error/500";
    }
}
```

### 业务异常
```java
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
```

## 日志使用

### 配置日志
```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YourClass {
    private static final Logger logger = LogManager.getLogger(YourClass.class);
    
    public void doSomething() {
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warning message");
        logger.error("Error message", exception);
    }
}
```

## 单元测试

### 创建测试类
```java
// src/test/java/com/blog/service/UserServiceTest.java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @Test
    public void testGetUser() {
        User user = userService.getUserById(1);
        assertNotNull(user);
    }
}
```

## 版本控制规范

### Git 分支策略
```
main             - 主分支（生产环境）
develop          - 开发分支
feature/*        - 功能分支
bugfix/*         - bug修复分支
release/*        - 发布分支
```

### Git 提交信息格式
```
feat: 新功能描述
fix: bug修复描述
refactor: 重构描述
test: 测试相关
docs: 文档更新
style: 代码风格调整
chore: 其他更改
```

### 提交示例
```bash
git checkout -b feature/add-notification

# 编写代码

git add .
git commit -m "feat: add notification system for new files"
git push origin feature/add-notification

# 创建 Pull Request
```

## 性能优化建议

### 1. 数据库优化
- 添加适当的索引
- 使用查询优化
- 避免 N+1 问题

### 2. 缓存策略
```java
@Cacheable(value = "users", key = "#id")
public User getUserById(Integer id) {
    return userMapper.selectById(id);
}
```

### 3. 懒加载
```java
// MyBatis 配置
<setting name="lazyLoadingEnabled" value="true"/>
```

### 4. 分页查询
```java
// 使用 PageHelper
Page<User> page = PageHelper.startPage(1, 10)
    .doSelectPage(() -> userMapper.selectAll());
```

## 安全最佳实践

### 1. 输入验证
```java
if (username == null || username.trim().isEmpty()) {
    throw new IllegalArgumentException("Username cannot be empty");
}
```

### 2. 参数化查询
```xml
<!-- 自动防止 SQL 注入 -->
SELECT * FROM user WHERE username = #{username}
```

### 3. XSS 防护
```jsp
<!-- 使用 JSTL 标签进行转义 -->
<c:out value="${user.name}"/>
```

### 4. CSRF 防护
```java
// 在表单中添加 CSRF token
<input type="hidden" name="csrf" value="${csrfToken}"/>
```

## 调试技巧

### 1. 使用日志调试
```java
logger.debug("User ID: {}, Username: {}", uid, username);
```

### 2. IDE 断点调试
- 设置断点
- 运行调试模式
- 单步执行

### 3. 查看 SQL 执行
```properties
# application.properties
logging.level.com.blog.mapper=DEBUG
```

### 4. 数据库查看
```bash
# 连接到数据库
mysql -u root -p blog_system

# 查看日志
SELECT * FROM user LIMIT 10;
```

## 常见问题

### Q: 如何添加新的验证规则？
A: 创建自定义 Validator 或在 Service 中添加验证逻辑

### Q: 如何处理大文件上传？
A: 配置 MultipartResolver 的 maxUploadSize

### Q: 如何实现权限细粒度控制？
A: 扩展 AuthorityFilter，添加更细致的权限检查

### Q: 如何处理并发问题？
A: 使用 synchronized、Lock 或数据库锁

## 资源链接

- [Spring MVC 文档](https://spring.io/projects/spring-framework)
- [MyBatis 文档](https://mybatis.org/)
- [Bootstrap 文档](https://getbootstrap.com/)
- [MySQL 文档](https://dev.mysql.com/doc/)

## 联系支持

如有疑问，请查看项目文档或提交 Issue。
