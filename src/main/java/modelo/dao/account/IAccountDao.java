package modelo.dao.account;

import modelo.Account;
import modelo.dao.IGenericDao;

public interface IAccountDao extends IGenericDao<Account>{
	
	boolean exists(Integer accountno);

}
