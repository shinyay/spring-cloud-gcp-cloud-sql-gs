DROP TABLE IF EXISTS department;
CREATE TABLE department (
                            department_id decimal(2,0) DEFAULT NULL,
                            department_name varchar(14) DEFAULT NULL
);

DROP TABLE IF EXISTS location;
CREATE TABLE location (
                            location_id decimal(2,0) DEFAULT NULL,
                            city varchar(64) DEFAULT NULL,
                            postal_code varchar(8) DEFAULT NULL
);