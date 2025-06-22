# Portfolio Application Setup Guide

## Initial Setup

After cloning this repository, follow these steps to set up the application:

### 1. Database Configuration

1. **Copy the database template:**

   ```bash
   cp src/main/resources/database.properties.template src/main/resources/database.properties
   ```

2. **Edit `src/main/resources/database.properties`:**

   ```properties
   db.url=jdbc:mysql://localhost:3306/portfolio
   db.username=your_actual_username
   db.password=your_actual_password
   db.driver=com.mysql.cj.jdbc.Driver
   ```

3. **Create the database:**
   ```sql
   CREATE DATABASE portfolio;
   ```

### 2. Application Configuration

1. **Copy the application template:**

   ```bash
   cp src/main/resources/application.properties.template src/main/resources/application.properties
   ```

2. **Edit `src/main/resources/application.properties`:**
   ```properties
   admin.secret.code=your-secure-secret-code-here
   ```

### 3. Build and Run

1. **Build the project:**

   ```bash
   mvn clean compile
   ```

2. **Run the application:**

   ```bash
   mvn tomcat7:run
   ```

3. **Access the application:**
   - Main site: http://localhost:8080/portfolio
   - Admin panel: http://localhost:8080/portfolio/admin/secret-access

## Security Notes

- **Never commit** `database.properties` or `application.properties` to version control
- **Use environment variables** for production deployment
- **Change the default secret code** before going live

## Environment Variables (Production)

For production deployment, use environment variables:

```bash
export ADMIN_SECRET_CODE="your-production-secret"
export DB_URL="jdbc:mysql://your-db-host:3306/portfolio"
export DB_USERNAME="your-db-user"
export DB_PASSWORD="your-db-password"
```

## Troubleshooting

- **Database connection issues**: Check your MySQL server and credentials
- **Admin access denied**: Verify your secret code in application.properties
- **Build errors**: Ensure you have Java 8+ and Maven installed
