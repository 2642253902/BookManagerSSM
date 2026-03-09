# 📡 API 和功能说明文档

## 目录
1. [图书管理 API](#图书管理-api)
2. [学生管理 API](#学生管理-api)
3. [借阅管理 API](#借阅管理-api)
4. [用户认证 API](#用户认证-api)
5. [功能界面说明](#功能界面说明)
6. [常见场景说明](#常见场景说明)

---

## 图书管理 API

### 获取图书列表
**请求**
```http
GET /books
Accept: text/html
```

**响应**
- **状态码**: 200 OK
- **返回类型**: HTML 页面
- **说明**: 显示所有可用图书的列表

**响应示例**
```html
<!-- 页面包含以下内容 -->
<table>
  <tr>
    <td>书名</td>
    <td>描述</td>
    <td>价格</td>
    <td>借阅状态</td>
    <td>操作</td>
  </tr>
  <tr>
    <td>三体</td>
    <td>刘慈欣科幻小说，三部曲第一部。</td>
    <td>¥68.00</td>
    <td>可借</td>
    <td><button>借阅</button></td>
  </tr>
</table>
```

**错误处理**
| 状态码 | 错误信息 | 原因 |
|--------|---------|------|
| 401 | Unauthorized | 未登录，请先登录 |
| 500 | Internal Server Error | 服务器错误 |

---

### 添加图书 - 跳转到添加页面
**请求**
```http
GET /add-book
Accept: text/html
```

**响应**
- **状态码**: 200 OK
- **返回类型**: HTML 表单页面
- **表单字段**:
  - `title` (String): 图书标题，必填
  - `desc` (String): 图书描述，选填
  - `price` (Double): 图书价格，必填

**表单示例**
```html
<form method="POST" action="/add-book">
  <label>书名:</label>
  <input type="text" name="title" required>
  
  <label>描述:</label>
  <textarea name="desc"></textarea>
  
  <label>价格:</label>
  <input type="number" name="price" step="0.01" required>
  
  <button type="submit">添加</button>
</form>
```

---

### 添加图书 - 提交表单
**请求**
```http
POST /add-book
Content-Type: application/x-www-form-urlencoded

title=新书名&desc=描述&price=99.99
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 是 | 图书标题，长度1-255 |
| desc | String | 否 | 图书描述，长度0-255 |
| price | Double | 是 | 图书价格，范围0-99999.99 |

**响应**
- **状态码**: 302 See Other（重定向）
- **重定向目标**: `/books`
- **说明**: 新图书添加成功后，重定向回图书列表页面

**响应示例**
```
HTTP/1.1 302 Found
Location: /books
```

**错误处理**
| 描述 | 原因 | 处理 |
|------|------|------|
| 参数缺失 | title 或 price 为空 | 返回400错误 |
| 参数格式错误 | price 不是有效数字 | 返回400错误 |
| 数据库错误 | 插入失败 | 返回500错误 |

---

### 删除图书
**请求**
```http
GET /delete-book?id=1
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 要删除的图书ID |

**响应**
- **状态码**: 302 See Other（重定向）
- **重定向目标**: `/books`
- **说明**: 图书删除成功后，重定向回图书列表页面

**响应示例**
```
HTTP/1.1 302 Found
Location: /books
```

**错误处理**
| 描述 | 原因 | 处理 |
|------|------|------|
| 图书不存在 | 指定ID的图书不存在 | 返回404错误 |
| 数据库错误 | 删除失败 | 返回500错误 |
| 参数缺失 | 未提供id参数 | 返回400错误 |

---

## 学生管理 API

### 获取学生列表
**请求**
```http
GET /students
Accept: text/html
```

**响应**
- **状态码**: 200 OK
- **返回类型**: HTML 页面
- **说明**: 显示所有已注册学生的列表

**响应示例**
```html
<table>
  <tr>
    <th>姓名</th>
    <th>性别</th>
    <th>年级</th>
    <th>操作</th>
  </tr>
  <tr>
    <td>张伟</td>
    <td>男</td>
    <td>20</td>
    <td>
      <button>编辑</button>
      <button>删除</button>
    </td>
  </tr>
</table>
```

**字段说明**
| 字段 | 类型 | 说明 |
|------|------|------|
| id | Integer | 学生ID（系统自动生成） |
| name | String | 学生姓名 |
| sex | String | 学生性别（男/女） |
| grade | String | 学生年级 |

---

### 添加学生
**请求**
```http
POST /add-student
Content-Type: application/x-www-form-urlencoded

name=李明&sex=男&grade=21
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | String | 是 | 学生姓名 |
| sex | String | 是 | 学生性别（男/女） |
| grade | String | 是 | 学生年级 |

**响应**
- **状态码**: 200/302
- **说明**: 返回成功或重定向回学生列表

---

### 删除学生
**请求**
```http
GET /delete-student?id=1
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 要删除的学生ID |

**响应**
- **状态码**: 302 See Other
- **重定向目标**: `/students`

---

## 借阅管理 API

### 获取借阅列表
**请求**
```http
GET /borrow
Accept: text/html
```

**响应**
- **状态码**: 200 OK
- **返回类型**: HTML 页面
- **说明**: 显示所有借阅记录

**响应示例**
```html
<table>
  <tr>
    <th>学生名</th>
    <th>书名</th>
    <th>借阅时间</th>
    <th>操作</th>
  </tr>
  <tr>
    <td>张伟</td>
    <td>三体</td>
    <td>2026-03-08 04:54:12</td>
    <td>
      <button>归还</button>
      <button>删除</button>
    </td>
  </tr>
</table>
```

**字段说明**
| 字段 | 类型 | 说明 |
|------|------|------|
| id | Integer | 借阅记录ID |
| sid | Integer | 学生ID（关联 student 表） |
| bid | Integer | 图书ID（关联 book 表） |
| time | DateTime | 借阅时间 |

---

### 添加借阅记录
**请求**
```http
POST /add-borrow
Content-Type: application/x-www-form-urlencoded

sid=1&bid=1
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| sid | Integer | 是 | 学生ID |
| bid | Integer | 是 | 图书ID |

**响应**
- **状态码**: 302 See Other
- **重定向目标**: `/borrow`
- **说明**: 借阅记录添加成功后重定向

**业务规则**
- ✅ 同一学生可以借阅多本书
- ✅ 同一本书可以被多个学生借过（不同时间）
- ✅ 记录借阅的时间戳

---

### 删除借阅记录
**请求**
```http
GET /delete-borrow?id=13
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Integer | 是 | 要删除的借阅记录ID |

**响应**
- **状态码**: 302 See Other
- **重定向目标**: `/borrow`

---

## 用户认证 API

### 登录表单
**请求**
```http
GET /login
Accept: text/html
```

**响应**
- **状态码**: 200 OK
- **返回类型**: HTML 登录表单

**表单示例**
```html
<form method="POST" action="/login">
  <label>用户名:</label>
  <input type="text" name="username" required>
  
  <label>密码:</label>
  <input type="password" name="password" required>
  
  <label>
    <input type="checkbox" name="remember-me">
    记住我
  </label>
  
  <button type="submit">登录</button>
</form>
```

---

### 提交登录
**请求**
```http
POST /login
Content-Type: application/x-www-form-urlencoded

username=admin&password=admin&remember-me=on
```

**请求参数**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |
| remember-me | Boolean | 否 | 是否记住登录状态 |

**响应**
- **成功**: 302 重定向到首页或原始页面
- **失败**: 302 重定向回登录页，显示错误信息

**响应示例 - 成功**
```
HTTP/1.1 302 Found
Location: /
Set-Cookie: JSESSIONID=...; Path=/; HttpOnly
```

**响应示例 - 失败**
```
HTTP/1.1 302 Found
Location: /login?error
```

---

### 登出
**请求**
```http
GET /logout
或
POST /logout (POST更安全)
```

**响应**
- **状态码**: 302 See Other
- **重定向目标**: `/login`
- **清除**: 销毁会话，清除Cookie

---

### 获取当前用户信息
**说明**: 通过 `SecurityContextHolder.getContext().getAuthentication().getPrincipal()` 获取

**返回数据示例**
```java
Object principal = SecurityContextHolder
    .getContext()
    .getAuthentication()
    .getPrincipal();
// 返回用户对象或 "anonymousUser" 字符串
```

---

## 功能界面说明

### 1. 首页 (/)
**路由**: `IndexController.index()`
**功能**: 系统首页，显示导航菜单和用户信息
**包含内容**:
- 顶部导航栏
- 用户欢迎信息
- 功能导航菜单
- 快捷操作链接

**页面结构**
```
┌────────────────────────────┐
│    项目标题 & 用户信息      │
├────────────────────────────┤
│  [图书] [学生] [借阅]      │
├────────────────────────────┤
│                            │
│    首页内容                │
│    - 统计信息              │
│    - 快速操作              │
│                            │
└────────────────────────────┘
```

---

### 2. 图书管理页面 (/books)
**控制器**: `BookController.book()`
**功能**: 查看、管理所有图书

**页面功能**:
| 功能 | 实现方式 | 刷新方式 |
|------|---------|---------|
| 查看全部图书 | 表格显示 | 页面加载时自动加载 |
| 添加新图书 | 链接跳转 | 添加后自动刷新 |
| 删除图书 | 按钮删除 | 删除后重定向回此页 |
| 查看借阅状态 | 状态显示 | 实时显示 |

**与其他表的关系**
```
Book (图书表)
├── 1 <- n Borrow (借阅表)
│   └── 关联 Student (学生表)
└── 显示当前借出状态
```

---

### 3. 学生管理页面 (/students)
**控制器**: `StudentController.student()`
**功能**: 查看、管理学生信息

**页面功能**:
| 功能 | 实现方式 |
|------|---------|
| 查看所有学生 | 表格显示 |
| 添加新学生 | 表单提交 |
| 删除学生 | 按钮删除 |
| 学生信息展示 | 姓名、性别、年级 |

---

### 4. 借阅管理页面 (/borrow)
**控制器**: `BorrowController.borrow()`
**功能**: 管理图书借阅记录

**页面显示**:
```
借阅记录表
├── 学生名称
├── 书籍名称
├── 借阅时间
└── 操作按钮
    ├── 归还
    └── 删除
```

**数据关联**
```
Borrow 表
├── sid → Student 表 (学生信息)
├── bid → Book 表 (图书信息)
└── time → 借阅时间戳
```

---

### 5. 登录页面 (/login)
**路由**: 由 Spring Security 管理
**功能**: 用户登录入口

**表单字段**:
- 用户名输入框
- 密码输入框
- 记住我复选框
- 登录按钮

**登录后**:
- 跳转到首页或原访问页面
- 在 `SecurityContext` 中保存用户信息
- 可选：保存持久化登录Token到数据库

---

## 常见场景说明

### 场景1: 新增图书并查看
```
用户操作:
1. 访问 /books
2. 点击"添加图书"按钮
3. 输入图书信息（标题、描述、价格）
4. 点击"提交"

系统流程:
POST /add-book
  → BookController.addBook()
  → BookService.addBook()
  → BookMapper.insertBook()
  → 数据库入库
  → 重定向到 /books
  → 自动加载最新图书列表
```

### 场景2: 学生借阅图书
```
用户操作:
1. 访问 /borrow (借阅管理)
2. 点击"添加借阅"
3. 选择学生
4. 选择要借的图书
5. 点击"确认借阅"

系统流程:
POST /add-borrow
  → BorrowController.addBorrow()
  → BorrowService.addBorrow()
  → BorrowMapper.insertBorrow()
  → 记录 (sid, bid, time)
  → 重定向到 /borrow
  → 列表中显示新的借阅记录
  → /books 页面中该书状态变为"已借出"
```

### 场景3: 用户登录和权限管理
```
用户操作:
1. 访问任何受保护页面
2. SecurityFilter 检测未认证
3. 重定向到 /login
4. 输入用户名和密码
5. 勾选"记住我"（可选）
6. 点击登录

系统流程:
POST /login
  → Spring Security 验证
  → UserService.loadUserByUsername()
  → 密码加密比对
  → 认证成功 ✓
  → 创建 SecurityContext
  → 生成 Session
  → 如果勾选"记住我"
    → 生成 Token
    → 保存到 persistent_logins 表
    → 设置长期 Cookie
  → 重定向到首页
```

### 场景4: 查询图书的借阅状态
```
数据流:
1. 访问 /books
2. BookController 调用 BookService.getBookAll()
3. 返回 Map<Book, Boolean>
   ├── Boolean = true (已借出)
   └── Boolean = false (可借)
4. 查询逻辑:
   SELECT b.*, 
          CASE WHEN br.id IS NOT NULL THEN 1 ELSE 0 END as borrowed
   FROM book b
   LEFT JOIN borrow br ON b.id = br.bid
   WHERE br.id IS NULL OR br.time = (
       SELECT MAX(time) FROM borrow WHERE bid = b.id
   )
5. 前端渲染状态图标
```

---

## 数据模型

### 完整关系图
```
┌──────────────┐
│   Student    │
│──────────────┤
│ id (PK)      │
│ name         │
│ sex          │
│ grade        │
└──────────────┘
      ▲
      │
      │ 1:n
      │
┌──────────────────┐
│     Borrow       │
│──────────────────┤
│ id (PK)          │
│ sid (FK)         │◄─── Student
│ bid (FK)         │
│ time             │
└──────────────────┘
      ▼
      │
      │ n:1
      │
┌──────────────┐
│     Book     │
│──────────────┤
│ id (PK)      │
│ title        │
│ desc         │
│ price        │
└──────────────┘
```

### 表结构总结

| 表名 | 主键 | 关键字段 | 关系 |
|------|------|---------|------|
| book | id | title, price | 1:n with borrow |
| student | id | name, grade | 1:n with borrow |
| borrow | id | sid, bid, time | 与 book 和 student 的中间表 |
| persistent_logins | series | username, token | 用户持久化登录 |

---

## 状态码参考

### HTTP 状态码
| 状态码 | 含义 | 场景 |
|--------|------|------|
| 200 | OK | 请求成功，返回页面 |
| 302 | Found | 重定向 |
| 400 | Bad Request | 请求参数错误 |
| 401 | Unauthorized | 未登录 |
| 403 | Forbidden | 无权限 |
| 404 | Not Found | 资源不存在 |
| 500 | Server Error | 服务器错误 |

---

最后更新时间: 2026年3月9日

