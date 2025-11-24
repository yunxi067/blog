@echo off
REM Database Setup Script for Blog System (Windows)
REM This script handles database initialization and user creation

set DB_NAME=blog_system
set DB_USER=root
set DB_PASS=123456

echo === Blog System Database Setup ===

REM Check if MySQL client is available
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: MySQL client is not installed or not in PATH
    echo Please install MySQL and ensure mysql.exe is accessible
    pause
    exit /b 1
)

REM Test MySQL connection
echo Testing MySQL connection...
mysql -u %DB_USER% -p%DB_PASS% -e "SELECT 1;" >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Cannot connect to MySQL with user '%DB_USER%'
    echo Please check your MySQL configuration and password
    pause
    exit /b 1
)

echo MySQL connection successful!

REM Initialize database
echo Initializing database '%DB_NAME%'...
mysql -u %DB_USER% -p%DB_PASS% < db\init.sql

if %errorlevel% equ 0 (
    echo Database initialized successfully!
) else (
    echo Error: Database initialization failed
    pause
    exit /b 1
)

REM Insert default users
echo Creating default users...

REM Admin user (password: admin123)
mysql -u %DB_USER% -p%DB_PASS% %DB_NAME% -e "INSERT INTO user (username, password, email, role, status) VALUES ('admin', '5f9c4ab08cac7457e9111a30e4664882', 'admin@blog.com', 1, 1) ON DUPLICATE KEY UPDATE username=username;"

REM Regular user (password: admin123)
mysql -u %DB_USER% -p%DB_PASS% %DB_NAME% -e "INSERT INTO user (username, password, email, role, status) VALUES ('user1', '5f9c4ab08cac7457e9111a30e4664882', 'user1@blog.com', 2, 1) ON DUPLICATE KEY UPDATE username=username;"

REM Create spaces for users
echo Creating user spaces...
mysql -u %DB_USER% -p%DB_PASS% %DB_NAME% -e "INSERT INTO space (uid, ssize_total, ssize_used, download_count, status) SELECT uid, 104857600, 0, 0, 1 FROM user WHERE uid NOT IN (SELECT uid FROM space);"

echo.
echo === Database Setup Complete ===
echo Database: %DB_NAME%
echo Default users created:
echo   Admin: admin / admin123
echo   User:  user1 / admin123
echo.
echo You can now start the application with: start.bat
pause