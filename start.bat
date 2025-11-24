@echo off
REM Blog System Startup Script for Windows
REM This script sets up the environment and starts the blog system

echo === Blog System Startup Script ===

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 1.8 or higher
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr "version"') do set JAVA_VERSION=%%i
echo Java version: %JAVA_VERSION%

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

REM Check Maven version
for /f "tokens=3" %%i in ('mvn -version 2^>^&1 ^| findstr "Apache Maven"') do set MVN_VERSION=%%i
echo Maven version: %MVN_VERSION%

REM Create upload directory
set UPLOAD_DIR=C:\blog_system\upload
echo Creating upload directory: %UPLOAD_DIR%
if not exist "%UPLOAD_DIR%" (
    mkdir "%UPLOAD_DIR%"
)

REM Check MySQL connection
echo Checking MySQL connection...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Warning: MySQL client is not installed. Please ensure MySQL server is running.
) else (
    REM Test MySQL connection
    mysql -u root -p123456 -e "USE blog_system;" >nul 2>&1
    if %errorlevel% equ 0 (
        echo MySQL connection successful, database exists.
    ) else (
        echo Initializing database...
        mysql -u root -p123456 < db\init.sql
        if %errorlevel% equ 0 (
            echo Database initialized successfully.
            
            REM Insert default admin user
            echo Creating default admin user...
            mysql -u root -p123456 blog_system -e "INSERT INTO user (username, password, email, role, status) VALUES ('admin', '5f9c4ab08cac7457e9111a30e4664882', 'admin@blog.com', 1, 1) ON DUPLICATE KEY UPDATE username=username;"
            mysql -u root -p123456 blog_system -e "INSERT INTO user (username, password, email, role, status) VALUES ('user1', '5f9c4ab08cac7457e9111a30e4664882', 'user1@blog.com', 2, 1) ON DUPLICATE KEY UPDATE username=username;"
            
            REM Create spaces for users
            echo Creating user spaces...
            mysql -u root -p123456 blog_system -e "INSERT INTO space (uid, ssize_total, ssize_used, download_count, status) SELECT uid, 104857600, 0, 0, 1 FROM user WHERE uid NOT IN (SELECT uid FROM space);"
            
            echo Default users created:
            echo   Admin: admin / admin123
            echo   User:  user1 / admin123
        ) else (
            echo Error: Database initialization failed. Please check MySQL configuration.
            pause
            exit /b 1
        )
    )
)

REM Compile and package the project
echo Building project...
mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo Error: Maven build failed
    pause
    exit /b 1
)

REM Start embedded Tomcat
echo Starting embedded Tomcat...
echo Application will be available at: http://localhost:8080/blog-system
echo Press Ctrl+C to stop the server
echo.

mvn tomcat7:run