/**
 * 
 */
package integracion.transaction.transactionManager;

import integracion.transaction.Transaction;

public abstract class TransactionManager {

	private static TransactionManager instance;

	public synchronized static TransactionManager getInstance() {
		if(instance == null){
			instance = new TransactionManagerImp();
		}
		return instance;
	}

	public abstract void eliminaTransaccion();

	public abstract Transaction nuevaTransaccion();

	public abstract Transaction getTransaccion();
}