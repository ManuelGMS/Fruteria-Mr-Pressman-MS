package integracion.query.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import integracion.query.Query;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.producto.TProducto;


public class ProductosCompradosPorClienteVIP implements Query {

	public Object execute(Object param) {
		int id = (int) param;
		ArrayList<TProducto> listaProductos = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "SELECT producto.id, producto.nombre, producto.precio, producto.unidades, producto.activo " +
							"FROM producto INNER JOIN (SELECT DISTINCT registrado.producto_id FROM registrado " +
							"INNER JOIN venta ON registrado.venta_id = venta.id WHERE venta.cliente_id = "+ id +
							") AS masComprados ON masComprados.producto_id = producto.id";
					ResultSet resultSet = statement.executeQuery(query);
					TProducto tProducto;
					listaProductos = new ArrayList<>();
					while(resultSet.next()) {
						tProducto = new TProducto(
								resultSet.getInt("id"),
								resultSet.getString("nombre"),
								resultSet.getFloat("precio"),
								resultSet.getInt("unidades"),
								resultSet.getBoolean("activo")
								);
						listaProductos.add(tProducto);
					}
				} catch (SQLException e) {
					listaProductos = null;
				}
			}
		}
		return listaProductos;
	}
}