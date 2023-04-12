package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.Account;
import modelo.Empleado;
import modelo.exceptions.DuplicateInstanceException;
import modelo.exceptions.InstanceNotFoundException;
import modelo.servicio.account.IServicioAccount;
import modelo.servicio.account.ServicioAccount;
import modelo.servicio.empleado.IServicioEmpleado;
import modelo.servicio.empleado.ServicioEmpleado;
import util.Utils;

public class MainAccount {
	
	private static IServicioAccount accountServicio = new ServicioAccount();
	private static IServicioEmpleado empleadoServicio = new ServicioEmpleado();

	public static void main(String[] args) {
		
		//crearCuentas();
		
	}
	
	private static void crearCuentas() {

		List<Account> cuentas = new ArrayList<>();
		Empleado empleado1 = null;
		Empleado empleado2 = null;
		try {
			empleado1 = empleadoServicio.findByOID(3);
			empleado2 = empleadoServicio.findByOID(4);
		} catch (InstanceNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Account cuenta1 = crearCuenta(empleado1, 3000);
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
