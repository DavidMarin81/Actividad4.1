package main;

import java.util.ArrayList;
import java.util.List;

import modelo.Account;
import modelo.Empleado;
import modelo.exceptions.DuplicateInstanceException;
import modelo.exceptions.InstanceNotFoundException;
import modelo.servicio.account.IServicioAccount;
import modelo.servicio.account.ServicioAccount;
import modelo.servicio.empleado.IServicioEmpleado;
import modelo.servicio.empleado.ServicioEmpleado;

public class MainAccount {
	
	private static IServicioAccount accountServicio = new ServicioAccount();
	private static IServicioEmpleado empleadoServicio = new ServicioEmpleado();

	public static void main(String[] args) {
		
		//Se crean las cuentas
		//crearCuentas();
		
		//Se asignan las cuentas a los empleados
		List<Account> cuentas = accountServicio.obtenerCuentas(2);
		for (Account cuenta : cuentas) {
			System.out.println(cuenta);
		}
		
		//Se muestran los datos de las cuentas
		List<Account> cuentasTotales = accountServicio.findAll();
		System.out.println("Las cuentas totales son: ");
		for (Account c : cuentasTotales) {
			String nombre = c.getEmp().getEname();
			int empno = c.getEmp().getEmpno();
			float cantidad = c.getAmount();
			//int accountno = c.getAccountno();
			
			System.out.println("=============================");
			System.out.println("Nombre: " + nombre);
			System.out.println("Empno: " + empno);
			System.out.println("Cantidad: " + cantidad);
			//System.out.println("N Cuenta: " + accountno);
			System.out.println("=============================");
		}
		
	}
	
	private static void crearCuentas() {

		List<Account> cuentas = new ArrayList<>();
		Empleado empleado1 = null;
		Empleado empleado2 = null;
		try {
			empleado1 = empleadoServicio.findByOID(1);
			empleado2 = empleadoServicio.findByOID(1);
		} catch (InstanceNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Account cuenta1 = crearCuenta(empleado1, 8000);
		Account cuenta2 = crearCuenta(empleado2, 4000);
				
		try {
			cuentas.add(cuenta1);
			cuentas.add(cuenta2);

			for (Account cuenta : cuentas) {
				accountServicio.createAcccount(cuenta);
			}
			System.out.println("Cuentas creadas correctamente");

		} catch (DuplicateInstanceException e) {
			// TODO Auto-generated catch block
			System.err.println("Se ha intentado guardar un empleado que ya existe: " + e.getMessage());
		}

	}
	
	private static Account crearCuenta(Empleado emp, float amount) {
		Account cuenta = new Account();
		cuenta.setEmp(emp);
		cuenta.setAmount(amount);

		return cuenta;
	}

}
