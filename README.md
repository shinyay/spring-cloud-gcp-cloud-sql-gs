# Name

Overview

## Description
### Spring Cloud GCP
### Cloud SQL Socket Factory for JDBC drivers
The Cloud SQL Socket Factory is a library for the MySQL/Postgres JDBC drivers that allows a user with the appropriate permissions to connect to a Cloud SQL database without having to deal with IP whitelisting or SSL certificates manually.

- [github](https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory)


## Demo
### Cloud SQL
#### Enable API
```shell script
$ gcloud services enable sqladmin.googleapis.com
```

#### Create MySQL Instance
```shell script
$ gcloud sql instances create my-mysql \
    --database-version=MYSQL_5_7 \
    --region=us-central1 \
    --cpu=2 \
    --memory=4G \
    --root-password=[ROOT_PASSWORD]
```

#### Create Employee Database
```shell script
$ gcloud sql databases create employee --instance=my-mysql
```

#### Connect MySQL Instance
```shell script
$ gcloud beta sql connect my-mysql
```

`gclod beta sql` uses Cloud SQL Proxy.

#### Create Table
```shell script
mysql> use employee
```

```mysql
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
