INSERT INTO tb_roles (role_id, name) VALUES (1, 'ADMIN')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (2, 'USUARIO')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO tb_roles (role_id, name) VALUES (3, 'GERENCIADOR')
ON CONFLICT (role_id) DO NOTHING;
