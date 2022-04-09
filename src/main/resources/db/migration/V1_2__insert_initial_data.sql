INSERT INTO role(id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

INSERT INTO users(login, password, role_id) VALUES
        ('admin', '$2a$10$p7vHgPB4Zdu3aBnv.xt/3ekKYGWb8s4oTimLn7FFbrRlzA7kpgYAe', 1);