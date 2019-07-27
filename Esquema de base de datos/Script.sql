CREATE TABLE producto (
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(30) NOT NULL,
	precio FLOAT NOT NULL,
	unidades INT NOT NULL,
	activo BIT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE registrado (
	unidades INT NOT NULL,
	precio_venta FLOAT NOT NULL,
	producto_id INT NOT NULL,
	venta_id INT NOT NULL,
	PRIMARY KEY (venta_id,producto_id)
);

CREATE TABLE venta (
	id INT NOT NULL AUTO_INCREMENT,
	precio_total FLOAT NOT NULL,
	fecha DATE NOT NULL,
	cliente_id INT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE cliente (
	id INT NOT NULL AUTO_INCREMENT,
	dni VARCHAR(9) NOT NULL,
	nombre VARCHAR(30) NOT NULL,
	telefono INT NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	activo BIT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE cliente_vip (
	cod_tarjeta_oro INT NOT NULL,
	id INT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE cliente_novip (
	limite_credito INT NOT NULL,
	id INT NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE registrado ADD CONSTRAINT registrado_producto_FK FOREIGN KEY (producto_id)
	REFERENCES producto (id)
	ON DELETE CASCADE;

ALTER TABLE registrado ADD CONSTRAINT registrado_venta_FK FOREIGN KEY (venta_id)
	REFERENCES venta (id)
	ON DELETE CASCADE;

ALTER TABLE venta ADD CONSTRAINT venta_cliente_FK FOREIGN KEY (cliente_id)
	REFERENCES cliente (id)
	ON DELETE CASCADE;

ALTER TABLE cliente_vip ADD CONSTRAINT vip_cliente_FK FOREIGN KEY (id)
	REFERENCES cliente (id)
	ON DELETE CASCADE;

ALTER TABLE cliente_novip ADD CONSTRAINT no_vip_cliente_FK FOREIGN KEY (id)
	REFERENCES cliente (id)
	ON DELETE CASCADE;

