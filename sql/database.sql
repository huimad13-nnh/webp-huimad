-- Chạy bằng SSMS hoặc: sqlcmd -S localhost -E -C -i sql\database.sql
IF DB_ID('ShoppingServiceMVC') IS NULL CREATE DATABASE ShoppingServiceMVC;
GO
USE ShoppingServiceMVC;
GO
SET QUOTED_IDENTIFIER ON
GO
IF OBJECT_ID('[User]') IS NULL
CREATE TABLE [User](
	[id]          [int] IDENTITY(1,1) PRIMARY KEY,
	[email]       [nvarchar](255) NULL,
	[username]    [nvarchar](50)  NOT NULL UNIQUE,
	[fullname]    [nvarchar](255) NULL,
	[password]    [nvarchar](255) NOT NULL,
	[avatar]      [nvarchar](255) NULL,
	[roleid]      [int] NOT NULL DEFAULT 5,   -- 1: admin, 2: manager, 5: user
	[phone]       [nvarchar](20)  NULL,
	[createdDate] [date] NULL
);
GO
IF OBJECT_ID('Category') IS NULL
CREATE TABLE Category(
	[cate_id]   [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[cate_name] [nvarchar](255) NOT NULL,
	[icons]     [nvarchar](255) NULL
);
GO
-- 1 user - n video
IF OBJECT_ID('Videos') IS NULL
CREATE TABLE Videos(
	[video_id]     [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[title]        [nvarchar](255) NOT NULL,
	[description]  [nvarchar](max) NULL,
	[url]          [nvarchar](255) NOT NULL,   -- tên file video trong D:\upload
	[created_date] [datetime2](6) NOT NULL,
	[user_id]      [int] NOT NULL FOREIGN KEY REFERENCES [User](id)
);
GO
-- Tài khoản mẫu
IF NOT EXISTS (SELECT 1 FROM [User] WHERE username = 'admin')
INSERT INTO [User](email, username, fullname, password, roleid, phone, createdDate)
VALUES (N'admin@gmail.com', N'admin', N'Quản trị viên', N'123', 1, N'0900000001', GETDATE()),
       (N'manager@gmail.com', N'manager', N'Người quản lý', N'123', 2, N'0900000002', GETDATE()),
       (N'user@gmail.com', N'user', N'Người dùng', N'123', 5, N'0900000003', GETDATE());
GO
IF NOT EXISTS (SELECT 1 FROM Category)
INSERT INTO Category(cate_name, icons) VALUES (N'Điện thoại', NULL), (N'Laptop', NULL);
GO
