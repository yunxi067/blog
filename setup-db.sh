#!/bin/bash

# Database Setup Script for Blog System
# This script handles database initialization and user creation

set -e

DB_NAME="blog_system"
DB_USER="root"
DB_PASS="123456"

echo "=== Blog System Database Setup ==="

# Check if MySQL is running
if ! pgrep -x "mysqld" > /dev/null; then
    echo "Starting MySQL service..."
    sudo systemctl start mysql 2>/dev/null || sudo service mysql start 2>/dev/null || sudo /etc/init.d/mysql start 2>/dev/null
    sleep 3
fi

# Test MySQL connection
echo "Testing MySQL connection..."
if ! mysql -u "$DB_USER" -p"$DB_PASS" -e "SELECT 1;" > /dev/null 2>&1; then
    echo "Error: Cannot connect to MySQL with user '$DB_USER'"
    echo "Please check your MySQL configuration and password"
    exit 1
fi

echo "MySQL connection successful!"

# Initialize database
echo "Initializing database '$DB_NAME'..."
mysql -u "$DB_USER" -p"$DB_PASS" < db/init.sql

if [ $? -eq 0 ]; then
    echo "Database initialized successfully!"
else
    echo "Error: Database initialization failed"
    exit 1
fi

# Insert default users
echo "Creating default users..."

# Admin user (password: admin123)
mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" -e "
    INSERT INTO user (username, password, email, role, status) 
    VALUES ('admin', '5f9c4ab08cac7457e9111a30e4664882', 'admin@blog.com', 1, 1)
    ON DUPLICATE KEY UPDATE username=username;
"

# Regular user (password: admin123)  
mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" -e "
    INSERT INTO user (username, password, email, role, status) 
    VALUES ('user1', '5f9c4ab08cac7457e9111a30e4664882', 'user1@blog.com', 2, 1)
    ON DUPLICATE KEY UPDATE username=username;
"

# Create spaces for users
echo "Creating user spaces..."
mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" -e "
    INSERT INTO space (uid, ssize_total, ssize_used, download_count, status) 
    SELECT uid, 104857600, 0, 0, 1 FROM user 
    WHERE uid NOT IN (SELECT uid FROM space);
"

echo ""
echo "=== Database Setup Complete ==="
echo "Database: $DB_NAME"
echo "Default users created:"
echo "  Admin: admin / admin123"
echo "  User:  user1 / admin123"
echo ""
echo "You can now start the application with: ./start.sh"