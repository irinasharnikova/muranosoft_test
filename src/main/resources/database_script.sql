-- Create database and table for Ubuntu.

-- Create user and database.
-- 1. Ctrl + Alt + T
-- 2. sudo -u postgres psql
CREATE DATABASE muranosoft;
CREATE USER admin WITH password 'admin';
GRANT ALL ON DATABASE muranosoft TO admin;
-- 6. \q

-- Create table.
-- 1. psql -h localhost muranosoft admin
CREATE TABLE IF NOT EXISTS employee (
	id SERIAL PRIMARY KEY,
	name CHAR (30) NOT NULL,
	surname CHAR (50) NOT NULL,
	phone BIGINT,
	department_id INTEGER
);