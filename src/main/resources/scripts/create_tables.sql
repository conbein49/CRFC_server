

-- Create user table
CREATE TABLE IF NOT EXISTS user(openID varchar(50) PRIMARY KEY, sessionKey varchar(100) NOT NULL, unionID varchar(100)); 