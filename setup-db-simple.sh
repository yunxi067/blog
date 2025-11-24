#!/bin/bash

# Simplified Database Setup Script for Blog System
# This script uses sudo mysql for authentication

set -e

DB_NAME="blog_system"

echo "=== Blog System Database Setup ==="

# Start MySQL if not running
echo "Starting MySQL service..."
sudo service mysql start
sleep 2

# Initialize database
echo "Initializing database '$DB_NAME'..."
sudo mysql < db/init.sql

if [ $? -eq 0 ]; then
    echo "Database initialized successfully!"
else
    echo "Error: Database initialization failed"
    exit 1
fi

# Insert default users
echo "Creating default users..."

# Admin user (password: admin123)
sudo mysql "$DB_NAME" -e "
    INSERT INTO user (username, password, email, role, status) 
    VALUES ('admin', '5f9c4ab08cac7457e9111a30e4664882', 'admin@blog.com', 1, 1)
    ON DUPLICATE KEY UPDATE username=username;
"

# Regular user (password: admin123)  
sudo mysql "$DB_NAME" -e "
    INSERT INTO user (username, password, email, role, status) 
    VALUES ('user1', '5f9c4ab08cac7457e9111a30e4664882', 'user1@blog.com', 2, 1)
    ON DUPLICATE KEY UPDATE username=username;
"

# Create spaces for users
echo "Creating user spaces..."
sudo mysql "$DB_NAME" -e "
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