/**
 * 
 */
package integracion.transaction.transactionManager;

import java.util.concurrent.ConcurrentHashMap;

import integracion.transaction.Transaction;
import integracion.transaction.TransactionFactory;



public class TransactionManagerImp extends TransactionManager {
	
	private ConcurrentHashMap<Thread, Transaction> transacciones;
	
	public TransactionManagerImp(){
		transacciones = new ConcurrentHashMap<Thread, Transaction>();
	}

	public void eliminaTransaccion() {
		Thread thread = Thread.currentThread();
		if(this.transacciones != null){
			this.transacciones.remove(thread);
		}
	}

	public Transaction nuevaTransaccion() {
		Thread thread = Thread.currentThread();
		Transaction transaction = null;
		if(this.transacciones != null){
			transaction = this.transacciones.get(thread);
			if(transaction == null){
				TransactionFactory transactionFactory = TransactionFactory.getInstance();
				transaction = transactionFactory.createTransaction();
				this.transacciones.put(thread, transaction);
			}
		}
		return transaction;
	}

	public Transaction getTransaccion() {
		Thread thread = Thread.currentThread();
		Transaction transaction = null;
		if(this.transacciones != null){
			transaction = this.transacciones.get(thread);
		}
		return transaction;
	}
}