Application for parsing of catalogs and products of the site http://old.relefopt.ru

This online store displays prices only to authorized users.
The content of specific products is formed by a separate ajax request.

## Requirements

* JDK 1.8

* Maven 3

* PostgreSQL 9.x

## Install and setup

1. Setup JAVA_HOME (https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html)

2. Setup MAVEN_HOME (https://www.mkyong.com/maven/how-to-install-maven-in-windows/ or
https://www.tutorialspoint.com/maven/maven_environment_setup.htm)

3. Create PostgreSQL database

All properties for connecting to the database are stored in the database.properties file:

db.url=jdbc:postgresql://_localhost_:_5432_/_old_relefopt_ , where

_localhost_ - address of the database

_5432_ - port of the database

_old_relefopt_ - name of the database

db.username=_postgres_ , where

_postgres_ - login for connecting to the database

db.password=_postgres_ , where

_postgres_ - password for connecting to the database

4. Setup authorization credentials:

site.user.login=_your_login_ , where _your_login_ is your login for authorization on the site

site.user.password=_your_password_ , where _your_password_ is your password for authorization on the site

## Run

1. To build jar file, execute next command:

mvn clean package

Result: relef-parser-{version}.jar file will be created in the /target directory.

2. To run jar file, execute next command:

java -jar relef-parser-{version}.jar

The application prints help if you run it without arguments.

## Run examples

1. Start fast full parsing:

java -jar relef-parser-{version}.jar -pf 0

2. Export products:

java -jar relef-parser-{version}.jar -ep

Creates a file products_{datetime}.xlsx in the directory /exports

3. Download images of all products:

java -jar relef-parser-{version}.jar -dpi

Creates many images in directory /downloads

## Post install (optional)

1. If you need to analyze percent of matching names with products from MySklad system then you need to create PostgreSQL
indexes:

1.1. https://stackoverflow.com/a/16552593/8035065

CREATE EXTENSION pg_trgm;

1.2. https://www.postgresql.org/docs/9.3/static/pgtrgm.html

CREATE INDEX trgm_idx_t_product_name ON t_product USING gist (name gist_trgm_ops);

## History

1.7 (2017-06-08): Changed address of the site.

1.6 (2017-06-08): Changed DB to PostgreSQL. Added import of products from MySklad system.

1.5 (2017-05-23): Added authorization.

1.4 (2017-05-15): Added export to xlsx.

1.3 (2017-05-13): Added strategies for parsing.

1.2 (2017-05-07): Added Liquibase support.

1.1 (2017-05-01): Added Hibernate support.

1.0 (2017-04-29): First commit.
