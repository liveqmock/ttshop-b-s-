package pojo;

public class tag {
	
	private Integer id;
	private String tagName;
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public tag() {
		super();
	}
	public tag(String tagName) {
		super();
		this.tagName = tagName;
		this.status = 1;
	}
	
}
