package pojo;

import java.util.Date;

import tools.DateTool;

public class User {
	
	private Integer id;
	private String userid;
	private String username;
	private String password;
	private Integer status;
	private String mail;
	private Integer role; //0-系统管理者 1-销售 2-仓管 3-经理/boss
	private String telephone;
	private String updatetime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public User(String userid,String username, String password) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public User() {
		super();
	}
	
	
	
	
	
	
}
