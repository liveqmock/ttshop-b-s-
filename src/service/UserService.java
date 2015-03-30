package service;

import java.util.List;

import pojo.User;

public interface UserService extends BaseService<User> {
	
	public abstract boolean checkUsername(String username);
	public abstract boolean addUser(User user);
	public abstract User findbyUserid(String userid);
	public abstract List<User> findBykeyword(String keyword);
	public abstract User Login(String userid , String password);
	
}
