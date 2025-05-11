CREATE TABLE profiles (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    bio TEXT,
    phone_number VARCHAR(15),
    date_of_birth DATE,
    loyalty_points INTEGER DEFAULT 0 CHECK(loyalty_points > 0)
);
