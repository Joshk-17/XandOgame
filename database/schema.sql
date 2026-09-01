CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE games (
    game_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    result VARCHAR(10) NOT NULL,
    duration INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);