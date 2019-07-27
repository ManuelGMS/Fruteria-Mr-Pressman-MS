
package integracion.transaction;

import integracion.transaction.imp.TransactionMySQL;

public class TransactionFactoryImp extends TransactionFactory {

	public Transaction createTransaction() {
		Transaction t = new TransactionMySQL();
		return t;
	}
}