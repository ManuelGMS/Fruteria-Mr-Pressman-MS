package integracion.query.imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import integracion.query.Query;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.cliente.ClienteMes;
import negocio.producto.TProducto;


public class ProductoMasCompradoPorUnCliente implements Query {
	
	public Object execute(Object param) {
		int mes = ((ClienteMes) param).getMes();
		int id = ((ClienteMes) param).getIdCliente();
		ArrayList<TProducto> listaProductos = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				
				try {
					Statement statement = connection.createStatement();
					String query = "SELECT `id`,`nombre`,`precio`,`unidades`,`activo` FROM producto"
							+ " INNER JOIN (SELECT registrado.producto_id, SUM(registrado.unidades) suma FROM registrado"
							+ " INNER JOIN venta ON venta.id = registrado.venta_id "
							+ "WHERE venta.cliente_id = " + id + " AND MONTH(venta.fecha) = " + mes
							+ " GROUP BY registrado.producto_id HAVING SUM(registrado.unidades) = "
							+ "( SELECT SUM(registrado.unidades) max_suma FROM registrado "
							+ "INNER JOIN venta ON venta.id = registrado.venta_id "
							+ "WHERE venta.cliente_id = " + id + " AND MONTH(venta.fecha) = " + mes 
							+ " GROUP BY registrado.producto_id ORDER BY max_suma DESC LIMIT 1 ) )"
							+ " AS MaxProductos ON MaxProductos.producto_id = producto.id";
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