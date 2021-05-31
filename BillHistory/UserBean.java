package BillHistory;

public class UserBean 
{
	String mob,noofdays,amount,dob,Status;
	UserBean(){}
	public UserBean(String mob, String noofdays, String amount, String dob, String Status) {
		super();
		this.mob = mob;
		this.noofdays = noofdays;
		this.amount = amount;
		this.dob = dob;
		Status = Status;
	}
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
	public String getNoofdays() {
		return noofdays;
	}
	public void setNoofdays(String noofdays) {
		this.noofdays = noofdays;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String Status) {
		Status = Status;
	}
	
}
