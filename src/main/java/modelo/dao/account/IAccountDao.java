package modelo.dao.account;

import java.util.List;

import modelo.Account;
import modelo.dao.IGenericDao;

public interface IAccountDao extends IGenericDao<Account>{
	
	boolean exists(Integer accountno);
	
	List<Account> obtenerCuentas(int empno);

}
