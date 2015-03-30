package serviceImpl;


import java.util.List;

import pojo.User;
import service.UserService;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	public boolean checkUsername(String username){
		List<User> users = this.findByHQL("FROM User as u WHERE u.userid = ?", username);
		if(users.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean addUser(User user){
		if(!this.checkUsername(user.getUsername())){
			this.baseDao.add(user);
			return true;
		}
		return false;
	}

	public User findbyUserid(String userid) {
		List<User> users = this.findByHQL("FROM User as u WHERE u.userid = ? AND u.status = 1", userid);
		User user = null;
		if(users.size()>0){
			user = users.get(0);
		}
		return user;
	}

	public List<User> findBykeyword(String keyword) {
		String hql = "FROM User as u WHERE (u.userid LIKE ? OR u.username LIKE ? OR u.mail LIKE ? OR u.telephone LIKE ?) AND u.status = 1";
		List<User> users =null;
		users = this.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
		return users;
	}

	public User Login(String userid, String password) {
		User user = null;
		String hql = "FROM User as u WHERE u.userid = ? and u.password = ? and u.status = 1";
		List<User> users = this.findByHQL(hql, userid,password);
		if(users.size()>0 && users.size()==1){
			user = users.get(0);
		}else{
			user = null;
		}
		return user;
	}
	
	

}
