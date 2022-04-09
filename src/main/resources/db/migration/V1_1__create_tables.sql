CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(20) UNIQUE,
    password VARCHAR(100),
    name VARCHAR(20),
    surname VARCHAR(20),
    role_id INT REFERENCES role(id)
);