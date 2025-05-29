
create table usuarios(
    id integer primary key not null,
    rut varchar2 (255) unique not null,
    nombre varchar2 (255) not null,
    apellido varchar2 (255) not null,
    email varchar2 (255) unique not null,
    telefono integer unique not null,
    direccion varchar2 (255) not null,
    usuario varchar2 (255) unique not null,
    contrasenia varchar2 (255) unique not null,
    rol varchar2 (255) unique not null,
    resenias varchar2 (255)
);

create table productos(
    id integer primary key not null,
    nombre varchar2 (100) not null,
    descripcion varchar2 (255) not null,
    precio double not null,
    stock inteher not null,
    creador_id integer references usuarios(id),
    categoria varchar2 (50) not null,
    fecha_creacion date not null,
    fecha_actualizacion date not null
);