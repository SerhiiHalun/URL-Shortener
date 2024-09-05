# URL Shortener Application

## Overview

This is a URL shortener application built with Spring Boot. This guide will help you set up and run the application in a production environment.

## Prerequisites

- Java 21 or higher
- PostgreSQL database
- PowerShell or terminal access

### Environment Variables

The application uses environment variables for database configuration. Ensure the following environment variables are set before running the application:

- `DB_URL` - The JDBC URL for your PostgreSQL database.
- `DB_USERNAME` - The username for your PostgreSQL database.
- `DB_PASSWORD` - The password for your PostgreSQL database.

### Run the Application

Navigate to the root folder of the project (where the src folder is located):
cd path\to\URL-Shortener

Run the script to start the application with the prod profile:
.\run-app.ps1 -profile prod

For the default profile, use:
.\run-app.ps1

To verify the application is running, open a web browser and navigate to http://localhost:9999