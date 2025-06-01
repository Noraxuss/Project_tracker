-- Drop the existing status_id column from tasks
ALTER TABLE tasks DROP COLUMN status;

-- Drop the existing status_id column from projects
ALTER TABLE projects DROP COLUMN status;

-- Create the statuses table
CREATE TABLE statuses
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255) NOT NULL,
    status_purpose VARCHAR(255) NOT NULL,
    CONSTRAINT pk_statuses PRIMARY KEY (id)
);

-- Insert default status into statuses table
INSERT INTO statuses (name, status_purpose)
VALUES ('To Do', 'BOTH');

-- Add the status_id column back to tasks with a default value and foreign key constraint
ALTER TABLE tasks
    ADD COLUMN status_id BIGINT DEFAULT 1;
ALTER TABLE tasks
    ADD CONSTRAINT fk_tasks_statuses FOREIGN KEY (status_id) REFERENCES statuses (id);

-- Add the status_id column back to projects with a default value and foreign key constraint
ALTER TABLE projects
    ADD COLUMN status_id BIGINT DEFAULT 1;
ALTER TABLE tasks
    ADD CONSTRAINT fk_projects_statuses FOREIGN KEY (status_id) REFERENCES statuses (id);