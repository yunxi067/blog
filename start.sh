#!/bin/bash

# Blog System Startup Script
# This script sets up the environment and starts the blog system

set -e

echo "=== Blog System Startup Script ==="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 1.8 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1-2)
echo "Java version: $JAVA_VERSION"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

# Check Maven version
MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
echo "Maven version: $MVN_VERSION"

# Create upload directory
UPLOAD_DIR="/opt/blog_system/upload"
echo "Creating upload directory: $UPLOAD_DIR"
sudo mkdir -p "$UPLOAD_DIR"
sudo chmod 777 "$UPLOAD_DIR"

# Check MySQL connection
echo "Checking MySQL connection..."
if ! command -v mysql &> /dev/null; then
    echo "Warning: MySQL client is not installed. Please ensure MySQL server is running."
else
    # Test MySQL connection
    if mysql -u root -p123456 -e "USE blog_system;" 2>/dev/null; then
        echo "MySQL connection successful, database exists."
    else
        echo "Initializing database..."
        mysql -u root -p123456 < db/init.sql
        if [ $? -eq 0 ]; then
            echo "Database initialized successfully."
            
            # Insert default admin user
            echo "Creating default admin user..."
            mysql -u root -p123456 blog_system -e "
                INSERT INTO user (username, password, email, role, status) 
                VALUES ('admin', '5f9c4ab08cac7457e9111a30e4664882', 'admin@blog.com', 1, 1)
                ON DUPLICATE KEY UPDATE username=username;
                
                INSERT INTO user (username, password, email, role, status) 
                VALUES ('user1', '5f9c4ab08cac7457e9111a30e4664882', 'user1@blog.com', 2, 1)
                ON DUPLICATE KEY UPDATE username=username;
            "
            
            # Create spaces for users
            echo "Creating user spaces..."
            mysql -u root -p123456 blog_system -e "
                INSERT INTO space (uid, ssize_total, ssize_used, download_count, status) 
                SELECT uid, 104857600, 0, 0, 1 FROM user 
                WHERE uid NOT IN (SELECT uid FROM space);
            "
            
            echo "Default users created:"
            echo "  Admin: admin / admin123"
            echo "  User:  user1 / admin123"
        else
            echo "Error: Database initialization failed. Please check MySQL configuration."
            exit 1
        fi
    fi
fi

# Compile and package the project
echo "Building project..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Error: Maven build failed"
    exit 1
fi

# Start embedded Tomcat
echo "Starting embedded Tomcat..."
echo "Application will be available at: http://localhost:8080/blog-system"
echo "Press Ctrl+C to stop the server"
echo ""

mvn tomcat7:run