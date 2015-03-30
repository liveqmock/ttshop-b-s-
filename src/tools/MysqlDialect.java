package tools;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

public class MysqlDialect extends MySQL5Dialect {

	public MysqlDialect() {
		super();
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName()); //给hibernate注册long-integer
		registerHibernateType(Types.LONGVARCHAR, 65535, "text");//给hibernate注册text类型
	}
	
	
	
}
