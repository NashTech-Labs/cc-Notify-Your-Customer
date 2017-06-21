INSERT INTO client(`name`, user_name, password, email, phone) VALUES ('Girish', '_girish', 'i_dont_know', 'girish@knoldus.com', '+919876543210');

INSERT INTO access_token(client_id, token, created_at) VALUES (1, 'client_access_token', NOW());
