package integracion.producto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.producto.TProducto;


public class DAOProductoImp implements DAOProducto {

	public int create(TProducto tProducto) {
		int id = -100;
	
		TransactionManager transactionManager = TransactionManager
				.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				String nombre = tProducto.getNombre();
				float precio = tProducto.getPrecio();
				int unidades = tProducto.getUnidades();
				boolean activo = tProducto.getActivo();
				try {
					Statement statement = connection.createStatement();
					String query = "INSERT INTO producto (nombre, precio, unidades, activo)"
							+ " VALUES ('"
							+ nombre
							+ "', "
							+ precio
							+ ", "
							+ unidades + ", " + (activo ? 1 : 0) + ");";
					statement.executeUpdate(query);
					query = "SELECT last_insert_id() as last_id from producto";
					ResultSet resultSet = statement.executeQuery(query);
					if (resultSet.next()) {
						id = resultSet.getInt("last_id");
					}
				} catch (SQLException e) {
					id = -100;
				}
			}
		}
	
		return id;
	}

	public TProducto findByName(String nombre) {
		TProducto tProducto = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try{
					Statement statement =  connection.createStatement();
					String query = "SELECT * FROM producto WHERE nombre='" + nombre + "' FOR UPDATE";
					ResultSet resultSet = statement.executeQuery(query);
					if(resultSet.next()) {
						tProducto = new TProducto(
								resultSet.getInt("id"),
								nombre,
								resultSet.getFloat("precio"),
								resultSet.getInt("unidades"),
								resultSet.getBoolean("activo")
								);
					}
				} catch (SQLException e) {
					tProducto = null;
				}
			}

		}
		return tProducto;
	}

	public TProducto read(int id) {
		TProducto tProducto = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "SELECT * FROM producto WHERE id=" +id +" FOR UPDATE";
					ResultSet resultSet = statement.executeQuery(query);
					if(resultSet.next()) {
						tProducto = new TProducto(
								id,
								resultSet.getString("nombre"),
								resultSet.getFloat("precio"),
								resultSet.getInt("unidades"),
								resultSet.getBoolean("activo")
								);
					}
				} catch (SQLException e) {
					tProducto = null;
				}
			}

		}
		return tProducto;
	}

	public ArrayList<TProducto> readAll() {
		ArrayList<TProducto>  listaProductos = new ArrayList<>();
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "SELECT * FROM producto WHERE activo=1";
					ResultSet resultSet = statement.executeQuery(query);
					TProducto tProducto;
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

	public int update(TProducto tProducto) {
		int id = -100;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "UPDATE producto SET "
							+ "nombre='" + tProducto.getNombre() + "', "
							+ "precio=" + tProducto.getPrecio() + ", "
							+ "unidades=" + tProducto.getUnidades() + ", "
							+ "activo=" + (tProducto.getActivo() ? 1 : 0) + " "
							+ "WHERE id=" + tProducto.getId();
					statement.executeUpdate(query);
					id = tProducto.getId();
				} catch (SQLException e) {
					id = -100;
				}
			}

		}
		return id;
	}

	public int delete(int id) {
		int r = -100;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "UPDATE producto SET activo=0 WHERE id=" + id;
					statement.executeUpdate(query);
					r = id;
				} catch (SQLException e) {
					r = -100;
				}
			}

		}
		return r;
	}
}