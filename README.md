# Spring Cloud GCP - Cloud SQL Getting Started
Spring Cloud GCP offers a wide collection of libraries that make it easier to use Google Cloud Platform from Spring Framework applications.

This tutorial shows **Spring Data Cloud SQL**.

## Description
### Spring Cloud GCP
#### Spring Cloud GCP - Cloud SQL Dependency
```kotlin
dependencies {
	implementation("org.springframework.cloud:spring-cloud-gcp-starter-sql-mysql")
}
```

#### Spring Cloud GCP - Cloud SQL Configuration

|Property Name|Description|
|-------------|-----------|
|spring.cloud.gcp.sql.instance-connection-name|Cloud SQL Instance Connection Name<br>`project-id:region:instance-name`<br>You can get by `gcloud sql instances describe my-mysql --format='value(connectionName)'`|
|spring.cloud.gcp.sql.database-name|Database Name|
|spring.cloud.gcp.sql.credentials|Authenticate and authorize new connection by Google OAuth2 credential private key|
|spring.cloud.gcp.sql.credentials.location|`classpath:<UNDER_RESOURCES_DIR>/key.json` <br>`file:/<FILE_PATH>/shinyay/key.json`|
|spring.cloud.gcp.sql.credentials.encoded-key|Base64-encoded strings <br> `base64 key.json`|
|spring.datasource.username|User name for database access|
|spring.datasource.password|User name for database password|
|spring.datasource.driver-class-name|JDBC Driver<br>8.x: `com.mysql.cj.jdbc.Driver`<br>before 8.x: `com.mysql.jdbc.Driver`|

```yaml
spring:
  cloud:
    gcp:
      sql:
        instance-connection-name: ${CONNECTION_NAME}
        database-name: employee
        credentials:
### either one of the followings
#          encoded-key: <BASE64_ENCODED_SERVICE_ACCOUNT_KEY>
#          location: classpath:credential/key.json
#          location: file:/Users/shinyay/key.json
  datasource:
    username: ${USERNAME:root}
    password: ${PASSWORD:admin}
    driver-class-name: com.mysql.cj.jdbc.Driver

```

#### Spring JDBC - JdbcTemplate
**JdbcTemplate** makes it easy to work with SQL relational databases and JDBC. 

```kotlin
fun getEmployees(): List<String>? = jdbcTemplate.queryForList("SELECT * FROM employee")
        .map { m: Map<String?, Any?> -> m.values.toString() }
        .toList()
```

#### Spring Data JPA
**Spring Data JPA** makes it easy to easily implement JPA based repositories.

##### Dependency for Spring Data JPA
```kotlin
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
```

##### Entity for Department

- `@Entity`: Specify this class is JPA entity
- `@Table` : Specify table name
- `@Id`: Specify Primary key parameter
- `@GeneratedValue`: Generate value for key id
  - `GenerationType.AUTO`
    - `GenerationType.IDENTITY`: using a database identity column
    - `GenerationType.SEQUENCE`: using a database sequence
    - `GenerationType.TABLE`: using an underlying database table to ensure uniqueness
- `@Column`: Specify column info like name

```kotlin
@Entity
@Table(name = "department")
data class Department(@Id @GeneratedValue(strategy = GenerationType.AUTO) val department_id: Long,
                      @Column(nullable = false, name = "department_name") val name: String)
```

##### Repository for Department

- `@Repository`: This is applied over DAO and DDD style repositories
- `JpaRepository`: Extending JpaRepository adds a bunch of generic CRUD functions

```kotlin
@Repository
interface DepartmentRepository : JpaRepository<Department, Int>
```

##### Configuration for JPA

- `kotlin("plugin.jpa")`: kotlin-jpa is wrapped on top of no-arg which specifies @Entity, @Embeddable and @MappedSuperclass no-arg annotations automatically

```kotlin
plugins {
	kotlin("plugin.jpa") version "1.3.72"
	kotlin("plugin.noarg") version "1.3.72"
}
```

### Cloud SQL Socket Factory for JDBC drivers
The Cloud SQL Socket Factory is a library for the MySQL/Postgres JDBC drivers that allows a user with the appropriate permissions to connect to a Cloud SQL database without having to deal with IP whitelisting or SSL certificates manually.
- [github](https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory)


## Demo
### 1. Cloud SQL
#### 1.1. Login
```shell script
$ gcloud beta auth login
```

#### 1.2. Enable API
```shell script
$ gcloud services enable sqladmin.googleapis.com
```

#### 1.3. Create MySQL Instance
```shell script
$ gcloud sql instances create my-mysql \
    --database-version=MYSQL_5_7 \
    --region=us-central1 \
    --cpu=2 \
    --memory=4G \
    --root-password=[ROOT_PASSWORD]
```

#### 1.4. Create Employee Database
```shell script
$ gcloud sql databases create employee --instance=my-mysql
```

#### 1.5. Connect MySQL Instance
```shell script
$ gcloud beta sql connect my-mysql
```

`gclod beta sql` uses Cloud SQL Proxy.

#### 1.6. Create Table
```shell script
mysql> use employee
```

```sql
DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  employee_id decimal(4,0) NOT NULL,
  employee_name varchar(10) DEFAULT NULL,
  role varchar(9) DEFAULT NULL,
  department_id decimal(2,0) DEFAULT NULL
);

DROP TABLE IF EXISTS department;

CREATE TABLE department (
  department_id decimal(2,0) DEFAULT NULL,
  department_name varchar(14) DEFAULT NULL
);

INSERT INTO employee VALUES ('7369','SMITH','CLERK','20');
INSERT INTO employee VALUES ('7499','ALLEN','SALESMAN','30');
INSERT INTO employee VALUES ('7521','WARD','SALESMAN','30');
INSERT INTO employee VALUES ('7566','JONES','MANAGER','20');
INSERT INTO employee VALUES ('7654','MARTIN','SALESMAN','30');
INSERT INTO employee VALUES ('7698','BLAKE','MANAGER','30');
INSERT INTO employee VALUES ('7782','CLARK','MANAGER','10');
INSERT INTO employee VALUES ('7788','SCOTT','ANALYST','20');
INSERT INTO employee VALUES ('7839','KING','PRESIDENT','10');
INSERT INTO employee VALUES ('7844','TURNER','SALESMAN','30');
INSERT INTO employee VALUES ('7876','ADAMS','CLERK','20');
INSERT INTO employee VALUES ('7900','JAMES','CLERK','30');
INSERT INTO employee VALUES ('7902','FORD','ANALYST','20');
INSERT INTO employee VALUES ('7934','MILLER','CLERK','10');

INSERT INTO department VALUES ('10','ACCOUNTING');
INSERT INTO department VALUES ('20','RESEARCH');
INSERT INTO department VALUES ('30','SALES');
INSERT INTO department VALUES ('40','OPERATIONS');
```

#### 1.7. Add User
```shell script
$ gcloud sql users create employee-user --instance=my-mysql --host=% --password=[PASSWORD]
```

```shell script
$ gcloud sql users list --instance=my-mysql

NAME           HOST
employee-user  %
root           %
```

##### 1.8. Grant User
Grant added user to access the specific database. The following query shows user can access only employee database.

```shell script
mysql> GRANT ALL ON employee.* TO 'employee-user'@'%';
```

```shell script
mysql> select user, host from mysql.user;
+---------------+-----------+
| user          | host      |
+---------------+-----------+
| employee-user | %         |
| root          | %         |
| mysql.sys     | localhost |
+---------------+-----------+
3 rows in set (0.13 sec)
```

```shell script
mysql> show grants for 'employee-user'@'%';
+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Grants for employee-user@%                                                                                                                                                                                                                                                                                                                                           |
+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, RELOAD, SHUTDOWN, PROCESS, REFERENCES, INDEX, ALTER, SHOW DATABASES, CREATE TEMPORARY TABLES, LOCK TABLES, EXECUTE, REPLICATION SLAVE, REPLICATION CLIENT, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, CREATE USER, EVENT, TRIGGER, CREATE TABLESPACE ON *.* TO 'employee-user'@'%' WITH GRANT OPTION |
| GRANT ALL PRIVILEGES ON `employee`.* TO 'employee-user'@'%'                                                                                                                                                                                                                                                                                                          |
+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
2 rows in set (0.13 sec)
```

#### 1.9. Retrieve Instance Connection Name
```shell script
$ gcloud sql instances describe my-mysql --format='value(connectionName)'
```

### 2. Service Account for Spring Cloud GCP
#### 2.1. Create Service Account
- `gcloud iam service-accounts create <NAME> --display-name "DISPLAY_NAME"`
  - [reference](https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/create)
 
This command creates a service account with the provided name.

```shell script
gcloud iam service-accounts create spring-cloud-gcp --display-name "Spring Cloud GCP"
```

#### 2.2. List Service Account
- `gcloud iam service-accounts list`
  - [reference](https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/list)

List all of a project's service accounts.

#### 2.3. Grant Service Account to Resource
- `gcloud projects add-iam-policy-binding <PROJECT_ID> --member serviceAccount:<SERVICE_ACCOUNT> --role <ROLE>`
  - [reference](https://cloud.google.com/sdk/gcloud/reference/projects/add-iam-policy-binding)

Add a policy binding to the IAM policy of a project, given a project ID and the binding. 

```shell script
$ gcloud projects add-iam-policy-binding (gcloud config get-value project) \
    --member serviceAccount:spring-cloud-gcp@(gcloud config get-value project).iam.gserviceaccount.com \
    --role roles/editor
```

#### 2.4. Create Service Account Key
- `gcloud iam service-accounts keys create <OUTPUT_FILE> --iam-account <SERVICE_ACCOUNT>`
  - [reference](https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/keys/create)

Create a private key for a service account.

```shell script
$ gcloud iam service-accounts keys create key.json \
  --iam-account spring-cloud-gcp@(gcloud config get-value project).iam.gserviceaccount.com
```

#### 2.5. Encode Service Account Key by Base64

```shell script
$ base64 key.json

ewogICJ0eXBlIjo......bnQuY29tIgp9Cg==
```

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
