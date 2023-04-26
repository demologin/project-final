DELETE FROM activity;

DELETE FROM profile;

DELETE FROM user_role;

DELETE FROM user_belong;

values ('task', 'Task', 2),

('ready', 'Ready', 3),

('in progress', 'In progress', 3),

('done', 'Done', 3),

('in test', 'In test', 3),

-- SPRINT_STATUS

('planning', 'Planning', 4),

('implementation', 'Implementation', 4),

values (1, 'skype', 'userSkype'),

(1, 'mobile', '+01234567890'),

(1, 'website', 'user.com'),

(2, 'github', 'adminGitHub'),

(2, 'tg', 'adminTg');





-- bugtracking

INSERT INTO project (id, code, title, description, type_code, startpoint, endpoint, parent_id) VALUES (22, 'task tracker', 'PROJECT-1', 'test project', 'task tracker', null, null, null);




INSERT INTO sprint (id, status_code, startpoint, endpoint, title, project_id) VALUES (21, 'planning', '2023-04-09 23:05:05.000000', '2023-04-12 23:05:12.000000', 'Sprint-1', 22);




INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id, sprint_id, parent_id, startpoint, endpoint) VALUES (22, 'Task-1', 'short test task', 'task', 'in progress', 'high', null, null, 22, 21, null, null, null);

INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id, sprint_id, parent_id, startpoint, endpoint) VALUES (23, 'Task-2', 'test 2 task', 'bug', 'ready', 'normal', null, null, 22, 21, null, null, null);

INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id, sprint_id, parent_id, startpoint, endpoint) VALUES (25, 'Task-4', 'test 4', 'bug', 'in progress', 'normal', null, null, 22, 21, null, null, null);

INSERT INTO task (id, title, description, type_code, status_code, priority_code, estimate, updated, project_id, sprint_id, parent_id, startpoint, endpoint) VALUES (24, 'Task-3', 'test 3 descr', 'task', 'done', 'low', null, null, 22, 21, null, null, null);




INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code, startpoint, endpoint) VALUES (23, 22, 2, 2, 'admin', null, null);

INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code, startpoint, endpoint) VALUES (24, 23, 2, 2, 'admin', null, null);

INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code, startpoint, endpoint) VALUES (25, 24, 2, 2, 'admin', null, null);

INSERT INTO user_belong (id, object_id, object_type, user_id, user_type_code, startpoint, endpoint) VALUES (26, 25, 2, 2, 'admin', null, null);




INSERT INTO activity (id, author_id, task_id, updated, comment, title, description, estimate, type_code, status_code, priority_code) VALUES (10, 2, 22, '2023-04-09 23:05:05.000000', 'test comment', 'Task-1', 'short test task', null, 'task', 'in progress', 'high');

INSERT INTO activity (id, author_id, task_id, updated, comment, title, description, estimate, type_code, status_code, priority_code) VALUES (11, 2, 22, '2023-04-11 15:35:00.000000', 'test comment', 'Task-1', 'short test task', null, 'task', 'in test', 'high');

INSERT INTO activity (id, author_id, task_id, updated, comment, title, description, estimate, type_code, status_code, priority_code) VALUES (12, 2, 22, '2023-04-11 15:25:00.000000', 'lower time', 'Task-1', 'short test task', null, 'task', 'in test', 'high');

INSERT INTO activity (id, author_id, task_id, updated, comment, title, description, estimate, type_code, status_code, priority_code) VALUES (13, 2, 22, '2023-04-14 09:10:35.000000', 'new comment', 'Task-1', 'short test task', null, 'task', null,'high');

INSERT INTO activity (id, author_id, task_id, updated, comment, title, description, estimate, type_code, status_code, priority_code) VALUES (14, 2, 22, '2023-04-14 09:20:35.000000', 'test comment', 'Task-1', 'short test task', null, 'task', 'done','high');

INSERT INTO activity (id, author_id, task_id, updated, comment, title, description, estimate, type_code, status_code, priority_code) VALUES (15, 2, 22, null, 'test comment', 'Task-1', 'short test task', null, 'task', 'done','high');