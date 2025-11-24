#!/bin/bash

# Blog System Setup Verification Script
# This script verifies that all components are properly configured

set -e

echo "=== Blog System Setup Verification ==="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print status
print_status() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ $2${NC}"
    else
        echo -e "${RED}✗ $2${NC}"
        exit 1
    fi
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

# Check Java
echo "Checking Java installation..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    print_status 0 "Java installed: $JAVA_VERSION"
else
    print_status 1 "Java is not installed"
fi

# Check Maven
echo "Checking Maven installation..."
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
    print_status 0 "Maven installed: $MVN_VERSION"
else
    print_status 1 "Maven is not installed"
fi

# Check project structure
echo "Checking project structure..."
if [ -f "pom.xml" ]; then
    print_status 0 "pom.xml exists"
else
    print_status 1 "pom.xml not found"
fi

if [ -d "src" ]; then
    print_status 0 "src directory exists"
else
    print_status 1 "src directory not found"
fi

if [ -d "db" ] && [ -f "db/init.sql" ]; then
    print_status 0 "Database scripts exist"
else
    print_status 1 "Database scripts not found"
fi

# Check startup scripts
echo "Checking startup scripts..."
if [ -f "start.sh" ]; then
    print_status 0 "Linux startup script exists"
else
    print_status 1 "start.sh not found"
fi

if [ -f "start.bat" ]; then
    print_status 0 "Windows startup script exists"
else
    print_status 1 "start.bat not found"
fi

if [ -f "setup-db.sh" ]; then
    print_status 0 "Linux database setup script exists"
else
    print_status 1 "setup-db.sh not found"
fi

if [ -f "setup-db.bat" ]; then
    print_status 0 "Windows database setup script exists"
else
    print_status 1 "setup-db.bat not found"
fi

# Check configuration files
echo "Checking configuration files..."
if [ -f "src/main/resources/application.properties" ]; then
    print_status 0 "Application configuration exists"
else
    print_status 1 "Application configuration not found"
fi

if [ -f "src/main/webapp/WEB-INF/web.xml" ]; then
    print_status 0 "Web deployment descriptor exists"
else
    print_status 1 "Web deployment descriptor not found"
fi

# Test Maven compilation
echo "Testing Maven compilation..."
if mvn clean compile -q > /dev/null 2>&1; then
    print_status 0 "Maven compilation successful"
else
    print_status 1 "Maven compilation failed"
fi

# Check Tomcat plugin
echo "Checking Tomcat Maven plugin..."
if grep -q "tomcat7-maven-plugin" pom.xml; then
    print_status 0 "Tomcat Maven plugin configured"
else
    print_status 1 "Tomcat Maven plugin not found"
fi

# Check upload directory permissions
echo "Checking upload directory..."
UPLOAD_DIR="/opt/blog_system/upload"
if [ -d "$UPLOAD_DIR" ]; then
    if [ -w "$UPLOAD_DIR" ]; then
        print_status 0 "Upload directory exists and is writable"
    else
        print_warning "Upload directory exists but may not be writable"
    fi
else
    print_warning "Upload directory does not exist (will be created by startup script)"
fi

# Check MySQL (optional)
echo "Checking MySQL (optional)..."
if command -v mysql &> /dev/null; then
    print_status 0 "MySQL client is available"
    if mysql -u root -p123456 -e "SELECT 1;" > /dev/null 2>&1; then
        print_status 0 "MySQL connection test successful"
    else
        print_warning "MySQL connection failed - check configuration"
    fi
else
    print_warning "MySQL client not available"
fi

echo ""
echo "=== Setup Verification Complete ==="
echo ""
echo "Next steps:"
echo "1. If MySQL is available, run: ./setup-db.sh"
echo "2. Start the application: ./start.sh"
echo "3. Open browser: http://localhost:8080/blog-system"
echo ""
echo "Default login credentials:"
echo "  Admin: admin / admin123"
echo "  User:  user1 / admin123"
echo ""
echo "For detailed instructions, see QUICK_START.md"