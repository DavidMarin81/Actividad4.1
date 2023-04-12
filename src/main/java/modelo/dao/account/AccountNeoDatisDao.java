package modelo.dao.account;

import java.util.List;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.oid.OIDFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

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
		query.orderByAsc("accountno");
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

	
}
