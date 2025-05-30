UPDATE statuses SET status_purpose = 'TASK' WHERE name = 'To Do';

INSERT INTO statuses (name, status_purpose)
VALUES ('New', 'BOTH'),
    ('In Progress', 'PROJECT'),
    ('Halted', 'PROJECT'),
    ('Finished', 'BOTH');