CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  address VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  address VARCHAR(50),
  password VARCHAR(50) NOT NULL,
  access_token VARCHAR(50) NOT NULL,
  mail_enabled BOOLEAN  NOT NULL,
  slack_enabled BOOLEAN  NOT NULL,
  twilio_enabled BOOLEAN  NOT NULL
);

CREATE TABLE IF NOT EXISTS mail_config (
  id BIGSERIAL PRIMARY KEY,
  client_id BIGINT REFERENCES client(id),
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS slack_config (
  id BIGSERIAL PRIMARY KEY,
  client_id BIGINT REFERENCES client(id),
  token VARCHAR(100) NOT NULL,
  defaul_channel VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS twillio_config (
  id BIGSERIAL PRIMARY KEY,
  client_id BIGINT REFERENCES client(id),
  phone_no VARCHAR(15) NOT NULL,
  token VARCHAR(50) NOT NULL,
  s_id VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ping_logs (
  id BIGSERIAL PRIMARY KEY,
  log_uuid VARCHAR(50) NOT NULL,
  client_id BIGINT REFERENCES client(id),
  message_type VARCHAR(30),
  message VARCHAR(160),
  sent_to VARCHAR(50),
  sent_at TIMESTAMP DEFAULT NOW(),
  status VARCHAR(50)
);
