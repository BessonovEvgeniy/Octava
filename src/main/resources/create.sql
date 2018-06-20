INSERT INTO ROLES (id, role) VALUES (1, 'ADMIN');
INSERT INTO ROLES (id, role) VALUES (2, 'DIRECTOR');
INSERT INTO ROLES (id, role) VALUES (3, 'MANAGER');
INSERT INTO ROLES (id, role) VALUES (4, 'SURVEYOR');
INSERT INTO ROLES (id, role) VALUES (5, 'GUEST');

INSERT INTO USERS
    (id, name, account_non_expired, account_non_locked, credentials_non_expired, login, role_id, password, enabled)
VALUES
    (1, 'ADMIN', true, true, true, 'admin', 1, '$2a$04$L5JtzYADN0v1Yi8Kou29zeRi52gJ4BnZjJNZ32P5KV3PyZ7pCxSGm', '1'); -- Use 'admin' as a password. Password encrypted by BCrypt

INSERT INTO COMPANY (id, name) VALUES (1, 'UNKNOWN');

INSERT INTO DEPARTMENT VALUES (1, 'UNKNOWN');
INSERT INTO COMPANY_DEPARTMENT (company_id, department_id) VALUES (1,1);



