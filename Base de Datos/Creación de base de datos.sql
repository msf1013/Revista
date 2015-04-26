CREATE DATABASE revista;

USE revista;

-- Creación de tabla Revista.
CREATE TABLE Revista(
	idRevista INTEGER NOT NULL AUTO_INCREMENT,
    fechaPublicacion DATE NOT NULL,
    cartaEditor VARCHAR(1000) NOT NULL,
    PRIMARY KEY(idRevista)
);

-- Creación de tabla Cuenta.
CREATE TABLE Cuenta(
	idCuenta INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    passwd VARCHAR(20) NOT NULL,
    fechaDeCreacion DATE NOT NULL,
    tipoUsuario VARCHAR(15) NOT NULL,
    PRIMARY KEY(idCuenta),
    UNIQUE(email)
);

-- Creación de tabla Suscriptor.
CREATE TABLE Suscriptor(
	idCuenta INTEGER NOT NULL,
    esCorporativa BOOLEAN NOT NULL,
    esEncargada BOOLEAN NOT NULL,
    esNuevo BOOLEAN NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    fechaDeRenovacion DATE NOT NULL,
    fechaDeVencimiento DATE NOT NULL,
    numeroCuentaPago VARCHAR(20) NOT NULL,
    banco VARCHAR(20) NOT NULL,
    cargaMensual DOUBLE NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    idEncargado INTEGER NULL,
    PRIMARY KEY(idCuenta),
    FOREIGN KEY(idCuenta) REFERENCES Cuenta(idCuenta),
    FOREIGN KEY(idEncargado) REFERENCES Suscriptor(idCuenta)
);

-- Creación de tabla Juez.
CREATE TABLE Juez(
	idCuenta INTEGER NOT NULL,
    numJuez INTEGER NOT NULL,
    PRIMARY KEY(idCuenta),
    UNIQUE(numJuez),
    FOREIGN KEY(idCuenta) REFERENCES Cuenta(idCuenta)
);

-- Creación de tabla Administrador.
CREATE TABLE Administrador(
	idCuenta INTEGER NOT NULL,
    PRIMARY KEY(idCuenta),
    FOREIGN KEY(idCuenta) REFERENCES Cuenta(idCuenta)
);

-- Creación de tabla Escritor.
CREATE TABLE Escritor(
	idCuenta INTEGER NOT NULL,
    fechaUltimaPublicacion DATE NULL,
    PRIMARY KEY(idCuenta),
    FOREIGN KEY(idCuenta) REFERENCES Cuenta(idCuenta)
);

-- Creación de tabla Orden.
CREATE TABLE Orden(
	idOrden INTEGER NOT NULL AUTO_INCREMENT,
    cargoTotal DOUBLE NOT NULL,
    ordenCompletada BOOLEAN NOT NULL,
    ordenEnviada BOOLEAN NOT NULL,
    numUnidades INTEGER NOT NULL,
    idSuscriptor INTEGER NOT NULL,
    PRIMARY KEY(idOrden),
    FOREIGN KEY(idSuscriptor) REFERENCES Suscriptor(idCuenta)
);

-- Creación de tabla RevistaOrden.
CREATE TABLE RevistaOrden(
	idRevista INTEGER NOT NULL,
    idOrden INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    PRIMARY KEY(idRevista, idOrden),
    FOREIGN KEY(idRevista) REFERENCES Revista(idRevista),
    FOREIGN KEY(idOrden) REFERENCES Orden(idOrden)
);

-- Creación de tabla ArticuloPendiente.
CREATE TABLE ArticuloPendiente(
	idArticuloPendiente INTEGER NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(40) NOT NULL,
    textoArticulo VARCHAR(10000) NOT NULL,
    abstract VARCHAR(1000) NOT NULL,
    fechaPubEsperada DATE NOT NULL,
    estatus VARCHAR(15) NOT NULL,
    fechaCreacion DATE NOT NULL,
    validado BOOLEAN NOT NULL DEFAULT FALSE,
    idEscritor INTEGER NOT NULL,
    PRIMARY KEY(idArticuloPendiente),
    UNIQUE(titulo),
    FOREIGN KEY(idEscritor) REFERENCES Escritor(idCuenta)
);

-- Creación de tabla Votos.
CREATE TABLE Votos(
	idArticuloPendiente INTEGER NOT NULL,
    idJuez INTEGER NOT NULL,
    voto BOOLEAN NOT NULL,
    PRIMARY KEY(idArticuloPendiente, idJuez),
    FOREIGN KEY(idArticuloPendiente) REFERENCES ArticuloPendiente(idArticuloPendiente),
    FOREIGN KEY(idJuez) REFERENCES Juez(idCuenta)
);

-- Creación de tabla Comentarios.
CREATE TABLE Comentarios(
	idArticuloPendiente INTEGER NOT NULL,
    idJuez INTEGER NOT NULL,
    comentario VARCHAR(200) NOT NULL,
    PRIMARY KEY(idArticuloPendiente, idJuez),
    FOREIGN KEY(idArticuloPendiente) REFERENCES ArticuloPendiente(idArticuloPendiente),
    FOREIGN KEY(idJuez) REFERENCES Juez(idCuenta)
);

-- Creación de tabla Articulo.
CREATE TABLE Articulo(
	idArticulo INTEGER NOT NULL AUTO_INCREMENT,
    idRevista INTEGER NULL,
    titulo VARCHAR(40) NOT NULL,
    textoArticulo VARCHAR(10000) NOT NULL,
    abstract VARCHAR(1000) NOT NULL,
    idArticuloPendiente INTEGER NOT NULL,
    fechaAprobacion DATE NOT NULL,
    idPredecesor INTEGER NULL DEFAULT NULL,
    PRIMARY KEY(idArticulo),
    UNIQUE(titulo),
    FOREIGN KEY(idRevista) REFERENCES Revista(idRevista),
    FOREIGN KEY(idArticuloPendiente) REFERENCES ArticuloPendiente(idArticuloPendiente),
    FOREIGN KEY(idPredecesor) REFERENCES Articulo(idArticulo)
);

-- Creación de tabla Resumen.
CREATE TABLE Resumen(
	idResumen INTEGER NOT NULL AUTO_INCREMENT,
    idArticulo INTEGER NOT NULL,
    textoResumen VARCHAR(1000),
    PRIMARY KEY(idResumen),
    FOREIGN KEY(idArticulo) REFERENCES Articulo(idArticulo)
);