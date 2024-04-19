CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     first_name VARCHAR(255),
                                     last_name VARCHAR(255),
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS weather (
                                       id SERIAL PRIMARY KEY,
                                       city VARCHAR(255) NOT NULL,
                                       country VARCHAR(255) NOT NULL,
                                       temperature DOUBLE PRECISION NOT NULL,
                                       description VARCHAR(255),
                                       wind_speed DOUBLE PRECISION,
                                       avg_temperature_by_city DOUBLE PRECISION,
                                       timestamp DATE NOT NULL
);