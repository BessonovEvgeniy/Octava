INSERT INTO ROLES (id, role) VALUES (1, 'ADMIN') ON CONFLICT (id) DO NOTHING;
INSERT INTO ROLES (id, role) VALUES (2, 'DIRECTOR') ON CONFLICT (id) DO NOTHING;
INSERT INTO ROLES (id, role) VALUES (3, 'MANAGER') ON CONFLICT (id) DO NOTHING;
INSERT INTO ROLES (id, role) VALUES (4, 'SURVEYOR') ON CONFLICT (id) DO NOTHING;
INSERT INTO ROLES (id, role) VALUES (5, 'GUEST') ON CONFLICT (id) DO NOTHING;

INSERT INTO USERS
    (id, name, account_non_expired, account_non_locked, credentials_non_expired, login, role_id, password, enabled)
VALUES
    (1, 'ADMIN', true, true, true, 'admin', 1, '$2a$04$L5JtzYADN0v1Yi8Kou29zeRi52gJ4BnZjJNZ32P5KV3PyZ7pCxSGm', '1') ON CONFLICT (id) DO NOTHING; -- Use 'admin' as a password. Password encrypted by BCrypt

INSERT INTO COMPANIES (id, name) VALUES (1, 'UNKNOWN') ON CONFLICT (id) DO NOTHING;

-- INSERT INTO DEPARTMENTS VALUES (1, 'UNKNOWN') ON CONFLICT (id) DO NOTHING;
-- INSERT INTO COMPANY_DEPARTMENT (company_id, department_id) VALUES (1,1) ON CONFLICT (id) DO NOTHING;



