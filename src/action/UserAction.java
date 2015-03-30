package action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import pojo.PrintOption;
import pojo.SaleRecord;
import pojo.User;
import tools.DateTool;
import tools.MD5Code;

@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	private User user;
	private List<User> users;
	private Integer optype;
	private String userid;
	private String password;
	private List<SaleRecord> saleRecords;
	/**
	 * check userid
	 * @throws Exception
	 */
	public void checkuserid() throws Exception{
		response.setContentType("text/html;chartset=utf-8");
		PrintWriter writer = response.getWriter();
		String userid = request.getParameter("userid").trim();
		user = this.userService.findbyUserid(userid);
		//System.out.println(user);
		//System.out.println(user.getUserid());
		if(user!=null){
			writer.print("error01:此用户ID已被使用");
			writer.flush();
			writer.close();
			return;
		}
		writer.print("success");
		writer.flush();
		writer.close();
		return;
	}
	/**
	 * 用户注册
	 * @return
	 * @throws RuntimeException
	 */
	public String register() throws RuntimeException{
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String cmfpassword = request.getParameter("cmfpassword");
		String mail = request.getParameter("mail");
		String telephone = request.getParameter("telephone");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"login.jsp");
		if(userid!=null && username!=null && password!=null && cmfpassword!=null){
			if(password.trim().equals(cmfpassword.trim())){
				Boolean b1 = userService.checkUsername(userid);
				if(b1){
					this.setMessage("抱歉，此用户名已被使用!");
					return INPUT;
				}else{
					MD5Code code = new MD5Code();
					password = code.getMD5ofStr(password);
					User u = new User(userid, username, password);
					if(mail!=null && mail.trim()!=""){
						u.setMail(mail);
					}
					if(telephone!=null && telephone.trim()!=""){
						u.setTelephone(telephone);
					}
					u.setRole(1); // 默认设置销售员
					userService.add(u);
					this.setMessage(SUCCMESSAGE);
					return SUCCESS;
				}
			}else{
				this.setMessage("两次输入密码必须相同");
				return INPUT;
			}
		}else {
			request.setAttribute("me",NECESSARY);
			return INPUT;
		}
	}
	
	/**
	 * 查找与列表USERS
	 * @return
	 * @throws RuntimeException
	 */
	public String listusers() throws RuntimeException{
		//String keyword = request.getParameter("keyword");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"homepage.jsp");
		if(keyword!=null && !keyword.trim().equals("")){
			users = this.userService.findBykeyword(keyword);
		}else{
			users = this.userService.findBykeyword(""); /** ""为全部查找 **/
		}
		return "LISTUSERS";
	}
	
	
	/**
	 * 转至UPDATEUSER
	 * @return
	 * @throws RuntimeException
	 */
	public String toupdateuser() throws RuntimeException{
		if(optype!=null && optype.equals(1)){
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"userfunction.jsp");
		}else{
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"user"+FILESEPARATOR+"listusers.action");
		}
		String userid = request.getParameter("userid");
		user = this.userService.findbyUserid(userid);
		if(user!=null){
			return SUCCESS;
		}else{
			this.setMessage("没有此用户信息");
			return INPUT;
		}
	}
	/**
	 * 转至UPDATEpassword
	 * @return
	 * @throws RuntimeException
	 */
	public String toupdatepassword() throws RuntimeException{
		if(optype!=null && optype.equals(1)){
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"userfunction.jsp");
		}else{
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"user"+FILESEPARATOR+"listusers.action");
		}
		String userid = request.getParameter("userid");
		user = this.userService.findbyUserid(userid);
		if(user!=null){
			return SUCCESS;
		}else{
			this.setMessage("没有此用户信息");
			return INPUT;
		}
	}
	
	/**
	 * 转至权限设置
	 * @return
	 * @throws RuntimeException
	 */
	public String tosetrole() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"user"+FILESEPARATOR+"listusers.action");
		String userid = request.getParameter("userid");
		user = this.userService.findbyUserid(userid);
		if(user!=null){
			return SUCCESS;
		}else{
			this.setMessage("没有此用户信息");
			return INPUT;
		}
	}
	
	
	/**
	 * 修改用户信息
	 * @return
	 * @throws RuntimeException
	 */
	public String updateuser() throws RuntimeException{
		String username = request.getParameter("username");
		String mail = request.getParameter("mail");
		String telephone = request.getParameter("telephone");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if(optype!=null && optype.equals(1)){
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"userfunction.jsp");
		}else{
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"user"+FILESEPARATOR+"listusers.action");
		}
		if(userid!=null && userid.trim()!="" && username!=null && username.trim()!=""){
			user = this.userService.findbyUserid(userid);
			MD5Code code = new MD5Code();
			password = code.getMD5ofStr(password);
			if(password.equals(user.getPassword())){
				user.setUsername(username);
				user.setMail(mail);
				user.setTelephone(telephone);
				user.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
				this.userService.update(user);
				this.setMessage(SUCCMESSAGE);
				return SUCCESS;
			}else{
				this.setMessage("请输入正确密码");
				return INPUT;
			}
		}
		//request.setAttribute("me", "*为必填项目");
		this.setMessage(NECESSARY);
		return INPUT;
	}
	

	/**
	 * 修改用户密码
	 * @return
	 * @throws RuntimeException
	 */
	public String updatpassword() throws RuntimeException{
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"userfunction.jsp"); //跳转到home页
		if(userid!=null && userid.trim()!=""){
			user = this.userService.findbyUserid(userid);
			MD5Code code = new MD5Code();
			password = code.getMD5ofStr(password);
			newpassword = code.getMD5ofStr(newpassword);
			if(password.equals(user.getPassword())){
				user.setPassword(newpassword);
				user.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
				this.userService.update(user);
				this.setMessage(SUCCMESSAGE);
				return SUCCESS;
			}else{
				this.setMessage("请输入正确密码");
				return INPUT;
			}
		}
		this.setMessage(NECESSARY);
		return INPUT;
	}
	
	/**
	 * 修改用户权限
	 * @return
	 * @throws Exception
	 */
	public String setrole() throws Exception{
		String userid = request.getParameter("userid");
		String role = request.getParameter("role");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"user"+FILESEPARATOR+"listusers.action");
		if(userid!=null && !userid.trim().equals("")){
			user = this.userService.findbyUserid(userid);
			user.setRole(Integer.valueOf(role));
			user.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
			this.userService.update(user);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}
		//request.setAttribute("me", "*为必填项目");
		this.setMessage(NECESSARY);
		return INPUT;
	}
	
	public void checkLogin() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		//String userid = request.getParameter("userid");
		//String password = request.getParameter("password");
		if(userid!=null && !userid.trim().equals("")&& password!=null && !password.trim().equals("")){
			MD5Code code = new MD5Code();
			password = code.getMD5ofStr(password);
			user = this.userService.Login(userid, password);
			if(user!=null){
				this.login();
			}else{
				printWriter.write("密码错误,请输入正确密码!");
				printWriter.flush();
				printWriter.close();
				return;
			}
		}
		printWriter.write("请输入用户名和密码!");
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * 登陆
	 * @return
	 * @throws Exception 
	 */
	public String login() throws Exception{
		//String userid = request.getParameter("userid");
		//String password = request.getParameter("password");
		if(userid!=null && !userid.trim().equals("")&& password!=null && !password.trim().equals("")){
			MD5Code code = new MD5Code();
			password = code.getMD5ofStr(password);
			user = this.userService.Login(userid, password);
			if(user!=null){
				request.getSession().setAttribute("user", user);  //因为有login过滤器，所以user失效时间不能比option失效时间迟
				List<PrintOption> printOptions = this.printOptionService.findOptionList("");
				if(printOptions!=null && printOptions.size()>0){
					option = printOptions.get(0);
					request.getSession().setAttribute("printOption", option);
				}
//				System.out.println("用户登录");
				//按职权跳转至不同界面，系统未设置自定义权限，避免使用者过多操作，系统核心是人性化，避免一切无必要的功能
				if (user.getRole()!=null) {
					switch (user.getRole()) {
						case 0:return "ADMIN";
						case 1:return "SALE";
						case 2:return "WAREHOUSE";
						case 3:return "MANAGER";
						default:return "SALE";
					}
				}
//				return "SALE";
			}else{
				this.setReturnurl(request.getContextPath()+FILESEPARATOR+"login.jsp");
				this.setMessage("请输入正确密码");
				return INPUT;
			}
		}
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"login.jsp");
		this.setMessage("请输入用户名和密码");
		return INPUT;
	}
	
	/**
	 * 登出
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception{
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("printOption");
		return SUCCESS;
	}
	
	/**
	 * 查看用户的信息
	 * @return
	 * @throws Exception
	 */
	public String viewuserinfo() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"user"+FILESEPARATOR+"listusers.action");
		if(user!=null && user.getId()!=null){
			user = this.userService.get(User.class, user.getId());
			if(user!=null){
				saleRecords = this.saleService.findByOperator(user.getUserid());
				return SUCCESS;
			}
			return INPUT;
		}
		return INPUT;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Integer getOptype() {
		return optype;
	}
	public void setOptype(Integer optype) {
		this.optype = optype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<SaleRecord> getSaleRecords() {
		return saleRecords;
	}
	public void setSaleRecords(List<SaleRecord> saleRecords) {
		this.saleRecords = saleRecords;
	}
	
	
}
