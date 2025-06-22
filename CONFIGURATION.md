# Portfolio Application Configuration

## Security Configuration

### Admin Access

The admin panel is protected by a secret code. For production deployment, you should set the secret code using one of these methods:

#### Method 1: Environment Variable (Recommended for Production)

```bash
export ADMIN_SECRET_CODE="your-secure-secret-code-here"
```

#### Method 2: System Property

```bash
java -Dadmin.secret.code="your-secure-secret-code-here" -jar your-app.jar
```

#### Method 3: Application Properties File

Edit `src/main/resources/application.properties`:

```properties
admin.secret.code=your-secure-secret-code-here
```

**⚠️ Security Warning:** Never commit the actual secret code to version control. Use environment variables in production.

## Database Configuration

Database settings are configured in `src/main/resources/database.properties`:

```properties
db.url=jdbc:mysql://127.0.0.1:3306/portfolio
db.username=root
db.password=your-database-password
db.driver=com.mysql.cj.jdbc.Driver
```

## Logging Configuration

The application uses Java's built-in logging. Log levels and settings can be configured in `application.properties`:

```properties
logging.level=INFO
logging.file=portfolio.log
```

## Feature Flags

Control application features using `application.properties`:

```properties
feature.analytics.enabled=true
feature.admin.enabled=true
```

## Production Deployment Checklist

- [ ] Set `ADMIN_SECRET_CODE` environment variable
- [ ] Update database credentials
- [ ] Configure proper logging
- [ ] Set up SSL/TLS certificates
- [ ] Configure web server (Tomcat, etc.)
- [ ] Set up database connection pooling
- [ ] Configure backup strategies
- [ ] Set up monitoring and alerting

## Development Setup

For development, the application will use default values if no configuration is provided. Check the logs for warnings about using default values.
