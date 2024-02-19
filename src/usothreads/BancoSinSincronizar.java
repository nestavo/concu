package usothreads;

import java.util.Iterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BancoSinSincronizar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Banco b = new Banco();

		for (int i = 0; i < 100; i++) {

			EjecucionTransferncias r = new EjecucionTransferncias(b, i, 2000);

			Thread t = new Thread(r);
			t.start();
		}

	}
}

class Banco {

	public Banco() {

		cuentas = new double[100];// creamos 100 cuentas

		for (int i = 0; i < cuentas.length; i++) {

			cuentas[i] = 2000;// cargamos las 100 cuentas con 2000 cada una
		}

		// saldoSuficiente = cierreBanco.newCondition();

	}

	public synchronized void transferencia(int cuentaOriengen, int cuentaDestino, double cantidad)
			throws InterruptedException {

		// cierreBanco.lock();

		// try {

		while (cuentas[cuentaOriengen] < cantidad) {// evalua saldo no es inferior a la transferencia

			// System.out.println("----------------CANTIDAD INSUFICIENTE "+
			// cuentaOriengen+"......SALDO: " + cuentas[cuentaOriengen]+
			// "....."+ cantidad);

			// return;

			// }else {

			// System.out.println("-----------CANTIDAD OK-----"+ cuentaOriengen);

			// saldoSuficiente.await();
			wait();

		}

		System.out.println(Thread.currentThread());

		cuentas[cuentaOriengen] -= cantidad;// dinero que sale de la cuenta origen

		System.out.printf("%10.2f de %d para %d", cantidad, cuentaOriengen, cuentaDestino);

		cuentas[cuentaDestino] += cantidad;

		System.out.printf(" Saldo total: %10.2f", getSaldoTotal());

		notifyAll();
		// saldoSuficiente.signalAll();

	} // finally {

	// cierreBanco.unlock();
	// }

	// }

	public double getSaldoTotal() {

		double suma_cuentas = 0;

		for (double a : cuentas) {

			suma_cuentas += a;

		}

		return suma_cuentas;

	}

	private final double[] cuentas;

	// private Lock cierreBanco = new ReentrantLock();

	// private Condition saldoSuficiente;

}

class EjecucionTransferncias implements Runnable {

	public EjecucionTransferncias(Banco b, int de, double max) {

		banco = b;
		deLaCuenta = de;
		cantidadMax = max;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {

			int paraLaCuenta = (int) (100 * Math.random());

			double cantidad = cantidadMax * Math.random();

			try {
				banco.transferencia(deLaCuenta, paraLaCuenta, cantidad);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				Thread.sleep((int) (Math.random() * 10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Banco banco;
	private int deLaCuenta;
	private double cantidadMax;

}
