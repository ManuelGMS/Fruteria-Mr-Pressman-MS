

package integracion.venta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.venta.LineaVenta;
import negocio.venta.TVenta;

public class DAOVentaImp implements DAOVenta {
	
	public int create(TVenta tVenta) {
		int id = -100;
		
		TransactionManager transactionManager = TransactionManager
				.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "INSERT INTO venta (precio_total,fecha,cliente_id)"
							+ " VALUES ("
							+ tVenta.getPrecioTotal() +", '"
							+ tVenta.getFecha() + "', "
							+ tVenta.getClienteID() + ");";
					statement.executeUpdate(query);
					query = "SELECT last_insert_id() as last_id from venta";
					ResultSet resultSet = statement.executeQuery(query);
					if(resultSet.next()) {
						id = resultSet.getInt("last_id");
						HashMap<Integer,LineaVenta> lineaVentas = tVenta.getLineaVentas();
						Collection<LineaVenta> values = lineaVentas.values();
						Iterator<LineaVenta> iterator = values.iterator();
						LineaVenta lineaVenta;
						while(iterator.hasNext()) {
							lineaVenta = iterator.next();
							query = "INSERT INTO registrado (venta_id,producto_id,unidades,precio_venta)"
									+ " VALUES ("
									+ id +", "
									+ lineaVenta.getIdProducto() + ", "
									+ lineaVenta.getUnidades() + ", "
									+ lineaVenta.getPrecio() + ");";
							statement.executeUpdate(query);
						}
					}
				} catch (SQLException e) {
					id = -100;
				}
			}
		}
		
		return id;
	}

	public TVenta read(int id) {
		TVenta tVenta = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "SELECT * FROM venta WHERE id=" + id + " FOR UPDATE";
					ResultSet resultSet = statement.executeQuery(query);
					if(resultSet.next()) {
						HashMap<Integer,LineaVenta> lineaVentas = new HashMap<>();
						tVenta = new TVenta(
								id,
								resultSet.getFloat("precio_total"),
								resultSet.getDate("fecha"),
								resultSet.getInt("cliente_id"),
								lineaVentas,
								false
								);
						query = "SELECT * FROM registrado WHERE venta_id="+ id + " FOR UPDATE";
						resultSet = statement.executeQuery(query);
						LineaVenta lineaVenta;
						while(resultSet.next()) {
							lineaVenta = new LineaVenta(
									resultSet.getInt("producto_id"),
									resultSet.getInt("unidades"),
									resultSet.getFloat("precio_venta")
									);
							lineaVentas.put(lineaVenta.getIdProducto(), lineaVenta);
						}
					}
					
				} catch (SQLException e) {
					tVenta = null;
				}
			}

		}
		return tVenta;
	}

	public ArrayList<TVenta> readAll() {
		ArrayList<TVenta> listaVentas = new ArrayList<>();
		TVenta tVenta = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement1 = connection.createStatement();
					Statement statement2 = connection.createStatement();
					String query = "SELECT * FROM venta";
					ResultSet resultSet1 = statement1.executeQuery(query);
					ResultSet resultSet2;
					HashMap<Integer,LineaVenta> lineaVentas;
					while (resultSet1.next()) {
						lineaVentas = new HashMap<>();
						tVenta = new TVenta(
								resultSet1.getInt("id"),
								resultSet1.getFloat("precio_total"),
								resultSet1.getDate("fecha"),
								resultSet1.getInt("cliente_id"),
								lineaVentas,
								false
								);
						query = "SELECT * FROM registrado WHERE venta_id="+tVenta.getId();
						resultSet2 = statement2.executeQuery(query);
						LineaVenta lineaVenta;
						while(resultSet2.next()) {
							lineaVenta = new LineaVenta(
									resultSet2.getInt("venta_id"),
									resultSet2.getInt("unidades"),
									resultSet2.getFloat("precio_venta")
									);
							lineaVentas.put(lineaVenta.getIdProducto(), lineaVenta);
						}
						listaVentas.add(tVenta);
					}
					
				} catch (SQLException e) {
					tVenta = null;
				}
			}

		}
		return listaVentas;
	}

	public int update(TVenta tVenta) {
		int id = -100;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.getTransaccion();
		if (transaction != null) {
			Connection connection = (Connection) transaction.getResource();
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String query = "UPDATE venta SET "
					+ "precio_total=" + tVenta.getPrecioTotal() + ", "
					+ "fecha='" + tVenta.getFecha() + "', "
					+ "cliente_id=" + tVenta.getClienteID() + " "
					+ "WHERE id=" + tVenta.getId();
					statement.executeUpdate(query);
					HashMap<Integer,LineaVenta> lineaVentas = tVenta.getLineaVentas();
					Collection<LineaVenta> values = lineaVentas.values();
					Iterator<LineaVenta> iterator = values.iterator();
					LineaVenta lineaVenta;
					while(iterator.hasNext()) {
						lineaVenta = iterator.next();
						query = "UPDATE registrado SET "
								+ "unidades=" + lineaVenta.getUnidades() + ", "
								+ "precio_venta=" + lineaVenta.getPrecio() + " "
								+ "WHERE venta_id=" + tVenta.getId()
								+ " AND producto_id=" + lineaVenta.getIdProducto();
						statement.executeUpdate(query);
					}
					id = tVenta.getId();
				} catch (SQLException e) {
					id = -100;
				}
			}

		}
		return id;
	}
}