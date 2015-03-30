package pojo;

public class Myid {
	
	private Integer id;
	private String type;
	private Integer number;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Myid(String type, Integer number) {
		super();
		this.type = type;
		this.number = number;
	}
	public Myid() {
		super();
	}
	
}
