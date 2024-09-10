INSERT INTO users (username, password)
VALUES ('JonYon', '$2a$12$Aj3GZ4yoTTCoNZRbbbyeJOJYSuf0ncV2JKATFYKdY6i44qn1DSmpG'),
       ('LisaDou', '$2a$12$Aj3GZ4yoTTCoNZRbbbyeJOJYSuf0ncV2JKATFYKdY6i44qn1DSmpG'),
       ('ErikLehnsherr', '$2a$12$Aj3GZ4yoTTCoNZRbbbyeJOJYSuf0ncV2JKATFYKdY6i44qn1DSmpG');

INSERT INTO link (full_url, short_url, order_status, user_id, transition_counter)
VALUES ('https://www.baeldung.com/spring-boot-h2-database', '7ppk09', 'ACTIVE', 1, 0),
       ('https://mvnrepository.com/artifact/com.h2database/h2/2.2.224', 'ewq12e4e', 'ACTIVE', 2, 0),
       ('https://jobs.dou.ua/vacancies/?category=Java', 'tgb7yhf', 'ACTIVE', 3, 0),
       ('https://github.com/Prosperro-de/Dev17Final/tree/main/src/main', 'ghfb766', 'ACTIVE', 1, 0),
       ('https://www.h2database.com/html/systemtables.html?highlight=DEFAULT_ON_NULL&search=default%20#information_schema_schemata', 'uhhtu7r4', 'ACTIVE', 1, 0),
       ('https://www.oracle.com/developer/devlive/', 'cvbt6ju8', 'ACTIVE', 3, 0);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');