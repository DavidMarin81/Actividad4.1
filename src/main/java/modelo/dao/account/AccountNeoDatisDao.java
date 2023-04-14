package modelo.dao.account;

import java.util.ArrayList;
import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.OID;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import modelo.Account;
import modelo.dao.AbstractGenericDao;
import modelo.exceptions.InstanceNotFoundException;
import util.ConnectionFactory;
import util.Utils;

public class AccountNeoDatisDao<Accounts>
extends AbstractGenericDao<Account>
implements IAccountDao {
	
	private ODB dataSource;
	
	public AccountNeoDatisDao() {
		this.dataSource = ConnectionFactory.getConnection();
	}

	@Override
	public long create(Account entity) {
		OID oid = null;
		long oidlong =-1;
		try {
			
			oid = this.dataSource.store(entity);
			this.dataSource.commit();

		} catch (Exception ex) {
			
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			this.dataSource.rollback();
			oid = null;
		}
		if(oid!=null) {
			oidlong= oid.getObjectId();
		}
		return oidlong;
	}

	@Override
	public Account read(long id) throws InstanceNotFoundException {
		Account cuenta = null;
		try {
			OID oid = OIDFactory.buildObjectOID(id);
			cuenta = (Account) this.dataSource.getObjectFromId(oid);
		} catch (ODBRuntimeException ex) {
		
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
//Suponemos que no lo encuentra
			throw new InstanceNotFoundException(id, getEntityClass());
		}
		catch(Exception ex) {
			
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());

		}
		return cuenta;
	}

	@Override
	public boolean update(Account entity) {
		boolean exito = false;
		try {
			this.dataSource.store(entity);
			this.dataSource.commit();
			exito = true;
		} catch (Exception ex) {			
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			this.dataSource.rollback();
		}
		return exito;		
	}

	@Override
	public boolean delete(Account entity) {
		boolean exito = false;
		try {
			this.dataSource.delete(entity);
			this.dataSource.commit();
			exito = true;
		} catch (Exception ex) {
			System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
			this.dataSource.rollback();
		}
		
		return exito;
	}

	@Override
	public List<Account> findAll() {
		IQuery query = new CriteriaQuery(Account.class);
		Objects<Account> cuentas = dataSource.getObjects(query);
		
		return Utils.toList(cuentas);
	}

	@Override
	public boolean exists(Integer accountno) {
		boolean exito = false;
		
		CriteriaQuery query = new CriteriaQuery(Account.class, Where.equal("accountno", accountno));
		Objects<Account> cuentas = dataSource.getObjects(query);
		return (cuentas.size()==1);
	}

	@Override
	public List<Account> obtenerCuentas(int empno) {
		IQuery query = new CriteriaQuery(Account.class, Where.equal("emp.empno", empno));
		Objects<Account> cuentas = dataSource.getObjects(query);
		
		return Utils.toList(cuentas);
	}

	@Override
	public List<Object> obtenerDatos(int empno) {
		
		Values values = dataSource.getValues(new
				ValuesCriteriaQuery(Account.class, Where.equal("emp.empno", empno))
				.field("emp.ename")
				.field("emp.empno")
				.field("amount")
				.field("accountno"));
		
		//¿Como se puede iniciar un List?
		ArrayList<Object> datos = new ArrayList<>();
		
		for (ObjectValues valor : values) {
			
			datos.add(valor.getByIndex(0));
			datos.add(valor.getByIndex(1));
			datos.add(valor.getByIndex(2));
			datos.add(valor.getByIndex(3));
			
			//Por index
			System.out.println("\nPor index:\n");
			System.out.println("Nombre: " + valor.getByIndex(0));
			System.out.println("NumEmp: " + valor.getByIndex(1));
			System.out.println("Cantidad: " + valor.getByIndex(2));
			System.out.println("NumCuenta: " + valor.getByIndex(3));
			
			//Por alias
			System.out.println("\nPor alias:\n");
			System.out.println("Nombre: " + valor.getByAlias("emp.ename"));
			System.out.println("NumEmp: " + valor.getByAlias("emp.empno"));
			System.out.println("Cantidad: " + valor.getByAlias("amount"));
			System.out.println("NumCuenta: " + valor.getByAlias("accountno"));
			
			//Por valor
			System.out.println("\nPor valor:\n");
			System.out.println(valor.toString());
		}
		
		//Se imprimen los datos de la lista
		System.out.println("Se imprime la lista: ");
		for (Object dato : datos) {
			System.out.println(dato);
		}
			
		return datos;
	}

	
}
