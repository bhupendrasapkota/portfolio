# Portfolio Application

A professional portfolio website built with Java Servlets, JSP, and MySQL.

## Features

- **Personal Information Management**: Display your professional details
- **Project Showcase**: Showcase your projects with images and technologies
- **Blog System**: Write and publish blog posts
- **Skills & Services**: Display your skills and services
- **Testimonials**: Show client testimonials
- **Contact Form**: Allow visitors to contact you
- **Admin Panel**: Manage all content through a secure admin interface
- **Analytics**: Track page views and visitor statistics

## Quick Start

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/portfolio.git
   cd portfolio
   ```

2. **Follow the setup guide:**
   See [SETUP.md](SETUP.md) for detailed configuration instructions.

3. **Configure your database and application settings**

4. **Run the application:**

   ```bash
   mvn tomcat7:run
   ```

5. **Access the application:**
   - Website: http://localhost:8080/portfolio
   - Admin: http://localhost:8080/portfolio/admin/secret-access

## Technology Stack

- **Backend**: Java Servlets, JSP
- **Database**: MySQL
- **Build Tool**: Maven
- **Server**: Apache Tomcat
- **Frontend**: HTML, CSS, JavaScript, Bootstrap

## Project Structure

```
portfolio/
├── src/main/java/com/bhupendrasapkota/portfolio/
│   ├── controller/     # Servlet controllers
│   ├── dao/           # Data Access Objects
│   ├── models/        # Entity classes
│   └── util/          # Utility classes
├── src/main/resources/
│   ├── database.properties.template
│   └── application.properties.template
├── src/main/webapp/
│   ├── WEB-INF/views/ # JSP pages
│   └── index.jsp
└── pom.xml
```

## Security

- Admin panel protected by secret code
- Database credentials stored securely
- Environment variable support for production
- CSRF and XSS protection enabled

## Configuration

- **Development**: Use `application.properties` and `database.properties`
- **Production**: Use environment variables for sensitive data
- See [CONFIGURATION.md](CONFIGURATION.md) for detailed configuration options

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and questions:

- Check the [SETUP.md](SETUP.md) guide
- Review the [CONFIGURATION.md](CONFIGURATION.md) documentation
- Create an issue on GitHub
