INSERT INTO subsidiary ( group_name, description)
VALUES
    ('Banco Internacional','Polizas BI');
GO


INSERT INTO color_pallete (pallete_name, hex_value, description, pallete_type, subsidiary_id)
VALUES
    ('subtitle-gray', '#7a7a7a', 'subtítulo gris', 'ui-setting',1),
    ('heading-black', '#121212', 'heading negro', 'ui-setting',1),
    ('title-orange', '#f39200', 'título naranja', 'ui-setting',1),
    ('ui-orange', '#ff8201', 'fondo naranja', 'ui-setting',1),
    ('icon-gray', '#706f6f', 'iconos grises', 'ui-setting',1),
    ('bg-pink', '#f4e1ce', 'fondo rosa', 'ui-setting',1),
    ('title-white', '#e8e8e8', 'título blanco', 'ui-setting',1),
    ('table-heading-gray', '#707070', 'gris heading tabla', 'ui-setting',1),
    ('ui-gray', '#f7f7f7', 'ui gris', 'ui-setting',1),
    ('table-head-text', '#ffffff', 'texto cabecera tabla', 'ui-setting',1),
    ('gray-transparent', '#d8d8d8', 'gris transparente', 'ui-setting',1);
GO