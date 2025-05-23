UPDATE statuses.s SET s.status_purpose = 'TASK' WHERE s.name = 'To Do'

INSERT INTO statuses (name, status_purpose)
VALUES ('In Progress', 'PROJECT'),
        ('Halted', 'PROJECT'),
        ('Finished', 'BOTH');