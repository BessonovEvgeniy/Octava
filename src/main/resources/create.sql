INSERT INTO ROLES (id, role) VALUES (1, 'ADMIN');
INSERT INTO ROLES (id, role) VALUES (2, 'DIRECTOR');
INSERT INTO ROLES (id, role) VALUES (3, 'MANAGER');
INSERT INTO ROLES (id, role) VALUES (4, 'SURVEYOR');
INSERT INTO ROLES (id, role) VALUES (5, 'GUEST');

INSERT INTO USERS (id, name, role_id, password) VALUES (1, 'ADMIN', 1, 'NTPzGf+R2ubzWDVEfk/ZQj/Iu5pWjBqKecoEFF7pPPlnmtqRZvy9J7wdEbdLaY5T'); -- Use 'Admin' as a password. Password encrypted by jasypt

INSERT INTO COMPANY (id, name) VALUES (1, 'UNKNOWN');

INSERT INTO DEPARTMENT VALUES (1, 'UNKNOWN');
INSERT INTO COMPANY_DEPARTMENT (company_id, department_id) VALUES (1,1);



