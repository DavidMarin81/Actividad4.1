package modelo.servicio.account;

import java.util.List;

import modelo.Account;
import modelo.exceptions.DuplicateInstanceException;
import modelo.exceptions.InstanceNotFoundException;

public interface IServicioAccount {
	
	public long createAcccount(Account cuenta) throws DuplicateInstanceException;
	public boolean deleteAccount(Account cuenta);
	public boolean updateAccount(Account cuenta);
	public Account findByOID(long longOid) throws InstanceNotFoundException ;
	
	public List<Account> findAll();

}
