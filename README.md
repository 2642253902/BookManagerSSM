# 📚 BookManage - 图书管理系统

## 项目简介

BookManage 是一个基于 Spring Framework 的现代化图书管理系统，为学校、图书馆或教育机构提供完整的图书和借阅管理解决方案。系统支持图书和学生的完整生命周期管理，包括图书的增删改查、学生信息管理以及图书借阅记录跟踪。

## ✨ 核心功能

### 📖 图书管理
- **查看图书** - 浏览全部图书及其详细信息（标题、描述、价格）
- **添加图书** - 新增图书记录
- **删除图书** - 移除图书记录
- **借阅状态** - 实时显示图书的借阅状态

### 👥 学生管理  
- **学生信息维护** - 管理学生基本信息（姓名、性别、年级）
- **学生列表** - 查看所有已注册学生

### 📋 借阅管理
- **借阅记录** - 跟踪学生借阅图书的历史
- **借阅状态** - 实时掌握图书的借出状态
- **数据关联** - 学生、图书、借阅记录完整关联

### 🔐 用户认证
- **用户登录** - 基于 Spring Security 的安全认证
- **权限管理** - 访问控制和权限验证
- **会话管理** - 支持记住登录状态（持久化登录）

## 🏗️ 系统架构

### 技术栈
- **后端框架** - Spring Framework / Spring MVC
- **ORM框架** - MyBatis（数据映射）
- **安全框架** - Spring Security（身份验证与授权）
- **数据库** - MySQL 5.7+
- **前端** - Thymeleaf 模板引擎 & Bootstrap
- **构建工具** - Maven
- **Java 版本** - Java 17

### 项目结构

```
BookManage/
├── src/main/java/com/study/
│   ├── config/                 # 配置类
│   │   ├── MainConfiguration.java       # 主配置
│   │   ├── MvcConfiguration.java        # MVC配置
│   │   └── SecurityConfiguration.java   # Spring Security配置
│   ├── controller/             # 控制器层
│   │   ├── BookController.java          # 图书相关接口
│   │   ├── BorrowController.java        # 借阅相关接口
│   │   ├── StudentController.java       # 学生相关接口
│   │   └── IndexController.java         # 首页相关接口
│   ├── entity/                 # 实体类
│   │   ├── Book.java           # 图书实体
│   │   ├── Student.java        # 学生实体
│   │   ├── Borrow.java         # 借阅实体
│   │   └── Account.java        # 账户实体
│   ├── mapper/                 # 数据访问层（MyBatis）
│   │   ├── BookMapper.java
│   │   ├── StudentMapper.java
│   │   └── UserMapper.java
│   ├── service/                # 业务逻辑层
│   │   ├── BookService.java
│   │   ├── StudentService.java
│   │   ├── UserService.java
│   │   └── impl/               # 服务实现类
│   ├── initialize/             # 初始化相关
│   │   ├── MvcInitializer.java
│   │   └── SecurityInitializer.java
│   └── listener/               # 监听器
│       ├── MySqlListener.java
│       └── DataSourceCleanup.java
├── src/main/resources/
│   ├── static/                 # 静态资源
│   │   ├── css/                # 样式表
│   │   ├── js/                 # JavaScript文件
│   │   ├── image/              # 图片
│   │   └── font/               # 字体
│   └── templates/              # Thymeleaf模板
│       ├── index.html          # 首页
│       ├── books.html          # 图书列表
│       ├── add-book.html       # 添加图书
│       ├── students.html       # 学生列表
│       ├── borrow.html         # 借阅列表
│       ├── login.html          # 登录页面
│       └── header.html         # 页眉模板
├── db/
│   └── study.sql               # 数据库初始化脚本
└── pom.xml                     # Maven项目配置
```

## 📊 数据库设计

### 表结构

#### book 表 - 图书信息
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| title | VARCHAR(255) | 图书标题 |
| desc | VARCHAR(255) | 图书描述 |
| price | DECIMAL(10,2) | 图书价格 |

#### student 表 - 学生信息
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| name | VARCHAR(255) | 学生姓名 |
| sex | VARCHAR(255) | 学生性别 |
| grade | VARCHAR(255) | 学生年级 |

#### borrow 表 - 借阅记录
| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 主键，自增 |
| sid | INT | 学生ID（外键） |
| bid | INT | 图书ID（外键） |
| time | DATETIME | 借阅时间 |

#### persistent_logins 表 - 持久化登录
| 字段 | 类型 | 说明 |
|------|------|------|
| username | VARCHAR(64) | 用户名 |
| series | VARCHAR(64) | 登录序列 |
| token | VARCHAR(64) | 登录令牌 |
| last_used | TIMESTAMP | 最后使用时间 |

## 🚀 快速开始

### 前置条件
- JDK 17+
- MySQL 5.7+
- Maven 3.6+

### 安装步骤

1. **克隆或下载项目**
   ```bash
   cd BookManage
   ```

2. **创建数据库**
   ```bash
   # 使用 MySQL 客户端导入数据库脚本
   mysql -u 用户名 -p < db/study.sql
   ```

3. **修改数据库配置**
   - 编辑配置文件中的数据库连接参数
   - 修改用户名、密码和数据库地址

4. **构建项目**
   ```bash
   mvn clean install
   ```

5. **运行项目**
   ```bash
   # 使用 Tomcat 部署 WAR 文件
   # 或使用 IDE 直接运行
   ```

6. **访问系统**
   - 打开浏览器访问 `http://localhost:8080/BookManage`
   - 默认登录账号：admin

## 📱 功能页面

| 页面 | URL | 说明 |
|------|-----|------|
| 首页 | `/` 或 `/index` | 系统首页DashBoard |
| 图书列表 | `/books` | 查看所有图书 |
| 添加图书 | `/add-book` | 添加新图书表单 |
| 学生列表 | `/students` | 查看学生信息 |
| 借阅列表 | `/borrow` | 查看借阅记录 |
| 登录 | `/login` | 用户登录 |

## 🔑 关键API端点

### 图书管理
- `GET /books` - 获取图书列表页面
- `GET /add-book` - 获取添加图书页面
- `POST /add-book` - 添加新图书
- `GET /delete-book?id={bookId}` - 删除图书

### 学生管理
- `GET /students` - 获取学生列表页面
- `POST /add-student` - 添加学生

### 借阅管理
- `GET /borrow` - 获取借阅列表页面
- `POST /add-borrow` - 添加借阅记录

## 🛠️ 主要依赖

- **Spring Web** - Web开发支持
- **Spring Security** - 安全认证
- **MyBatis** - 数据库映射
- **MySQL Connector** - MySQL驱动
- **Lombok** - 代码简化
- **Bootstrap** - 前端框架
- **DataTables** - 数据表格组件
- **jQuery** - JavaScript库

详见 [pom.xml](pom.xml)

## 📝 开发规范

### 代码层级
- **Controller** - 处理HTTP请求和响应
- **Service** - 业务逻辑实现
- **Mapper** - 数据库操作
- **Entity** - 数据模型

### 命名规范
- 类名采用 PascalCase
- 方法名采用 camelCase
- 常量采用 UPPER_SNAKE_CASE

## 🔒 安全特性

- ✅ Spring Security 身份验证
- ✅ 会话管理和超时控制
- ✅ 记住我功能（持久化登录）
- ✅ SQL注入防护（通过MyBatis）
- ✅ CSRF 保护

## 📈 未来规划

- [ ] 图书馆超期提醒功能
- [ ] 用户评分和评论功能
- [ ] 图表数据分析
- [ ] 导出功能（Excel、PDF）
- [ ] 移动端适配
- [ ] API 文档（Swagger）
