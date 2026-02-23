CREATE TABLE COLORPALETTE (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    ColorName varchar(100) NOT NULL,
    HexValue varchar(7) NOT NULL,
    Description varchar(255) NULL,
    ISDELETED BIT DEFAULT 0,
);
GO

INSERT INTO ColorPalette (ColorName, HexValue, Description) VALUES
    ('subtitle-gray',       '#7a7a7a', 'subtítulo gris'),
    ('heading-black',       '#121212', 'heading negro'),
    ('title-orange',        '#f39200', 'título naranja'),
    ('ui-orange',           '#ff8201', 'fondo naranja'),
    ('icon-gray',           '#706f6f', 'iconos grises'),
    ('bg-pink',             '#f4e1ce', 'fondo rosa'),
    ('title-white',         '#e8e8e8', 'título blanco'),
    ('table-heading-gray',  '#707070', 'gris heading tabla'),
    ('ui-gray',             '#f7f7f7', 'ui gris'),
    ('table-head-text',     '#ffffff', 'texto cabecera tabla'),
    ('gray-transparent',    '#d8d8d8', 'gris transparente');
GO