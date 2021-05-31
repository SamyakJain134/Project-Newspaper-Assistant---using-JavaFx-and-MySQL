package CustomerDisplay;

public class UserBean 
{
	String name,num,addr,hawker,paper;
	UserBean(){}
	public UserBean(String name, String num, String addr, String hawker, String paper) {
		super();
		this.name = name;
		this.num = num;
		this.addr = addr;
		this.hawker = hawker;
		this.paper = paper;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getHawker() {
		return hawker;
	}
	public void setHawker(String hawker) {
		this.hawker = hawker;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	
	
}
