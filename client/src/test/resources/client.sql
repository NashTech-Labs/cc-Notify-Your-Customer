INSERT INTO client(`name`, email, phone) VALUES ('Girish', 'girish@knoldus.com', '+919876543210');

INSERT INTO access_token(client_id, token, created_at) VALUES (1, 'client_access_token', NOW());
