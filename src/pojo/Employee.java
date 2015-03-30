package pojo;

import java.util.Date;

import tools.DateTool;

public class Employee {
	
	private Integer id;
	private String userid;
	private String empName;
	private String username;
	private String telephone;
	private String address;
	private String level;
	private String hiredate;
	private String updatetime;
	private Integer status;
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
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Employee(String userid,String username, String empName, String telephone) {
		super();
		this.userid = userid;
		this.username = username;
		this.empName = empName;
		this.telephone = telephone;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public Employee() {
		super();
	}
	
	
	
	
}
