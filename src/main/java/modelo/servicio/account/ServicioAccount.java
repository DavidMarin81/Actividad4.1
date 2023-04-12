package modelo.servicio.account;

import java.util.List;

import modelo.Account;
import modelo.dao.account.AccountNeoDatisDao;
import modelo.dao.account.IAccountDao;
import modelo.exceptions.DuplicateInstanceException;
import modelo.exceptions.InstanceNotFoundException;

public class ServicioAccount implements IServicioAccount {
	
	private IAccountDao accountDao;
	
	public ServicioAccount() {
		accountDao = new AccountNeoDatisDao();
	}

	@Override
	public long createAcccount(Account cuenta) throws DuplicateInstanceException {
		return accountDao.create(cuenta);
	}

	@Override
	public boolean deleteAccount(Account cuenta) {
		return accountDao.delete(cuenta);
	}

	@Override
	public boolean updateAccount(Account cuenta) {
		return accountDao.update(cuenta);
	}

	@Override
	public Account findByOID(long longOid) throws InstanceNotFoundException {
		return accountDao.read(longOid);
	}

	@Override
	public List<Account> findAll() {
		return accountDao.findAll();
	}


}
