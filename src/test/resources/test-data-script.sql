CREATE TABLE IF NOT EXISTS EMPLOYEES (
    EMPLOYEE_ID UUID PRIMARY KEY,
    NAME VARCHAR(255),
    EMAIL VARCHAR(255),
    SALARY FLOAT,
    POSITION VARCHAR(255),
    CREATED_AT TIMESTAMP,
    MODIFIED_AT TIMESTAMP
);

CREATE TABLE IF NOT EXISTS REQUEST_CATEGORY (
    REQUEST_CATEGORY_ID UUID PRIMARY KEY,
    NAME VARCHAR(255)
);


INSERT INTO EMPLOYEES (EMPLOYEE_ID, NAME, EMAIL, SALARY, POSITION, CREATED_AT, MODIFIED_AT)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'John Doe', 'john.doe@example.com', 50000.0, 'Software Engineer', '2024-05-30 08:24:07', '2024-05-30 08:24:07'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d480', 'Jane Smith', 'jane.smith@example.com', 60000.0, 'Senior Developer', '2024-05-30 08:24:07', '2024-05-30 08:24:07'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d481', 'Alice Johnson', 'alice.johnson@example.com', 70000.0, 'Project Manager', '2024-05-30 08:24:07', '2024-05-30 08:24:07');

INSERT INTO REQUEST_CATEGORY (REQUEST_CATEGORY_ID, NAME)
VALUES
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479','Work Remote'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d480','Annual Leave'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d481','Sick Leave');
