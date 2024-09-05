param (
    [string]$profile = "default"
)

if ($profile -eq "prod") {
    $env:DB_URL = "jdbc:postgresql://localhost:5437/Shortener"
    $env:DB_USERNAME = "User"
    $env:DB_PASSWORD = "Password"
}

# Running the Java application with an active profile
java -jar target\URL-Shortener-0.0.1-SNAPSHOT.jar --spring.profiles.active=$profile
