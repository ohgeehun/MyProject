package Model;

public class ReservationVO {

	private int resNo;
	private String resdate;
	private int adult;
	private int child;
	private int totalprice;
	private int prCode;
	private int cusNo;
	private int totalPrCode;
	private int totalAdult;
	private int totalChild;
	private int totalAllPrice;

	public ReservationVO() {
		super();
	}

	public ReservationVO(String resdate, int adult, int child, int totalprice, int prCode, int cusNo) {
		super();
		this.resdate = resdate;
		this.adult = adult;
		this.child = child;
		this.totalprice = totalprice;
		this.prCode = prCode;
		this.cusNo = cusNo;
	}

	public ReservationVO(int resNo, String resdate, int adult, int child, int totalprice, int prCode, int cusNo) {
		super();
		this.resNo = resNo;
		this.resdate = resdate;
		this.adult = adult;
		this.child = child;
		this.totalprice = totalprice;
		this.prCode = prCode;
		this.cusNo = cusNo;
	}

	public ReservationVO(int resNo, String resdate, int adult, int child, int totalprice, int prCode, int cusNo,
			int totalPrCode, int totalAdult, int totalChild, int totalAllPrice) {
		super();
		this.resNo = resNo;
		this.resdate = resdate;
		this.adult = adult;
		this.child = child;
		this.totalprice = totalprice;
		this.prCode = prCode;
		this.cusNo = cusNo;
		this.totalPrCode = totalPrCode;
		this.totalAdult = totalAdult;
		this.totalChild = totalChild;
		this.totalAllPrice = totalAllPrice;
	}

	public int getTotalPrCode() {
		return totalPrCode;
	}

	public void setTotalPrCode(int totalPrCode) {
		this.totalPrCode = totalPrCode;
	}

	public int getTotalAdult() {
		return totalAdult;
	}

	public void setTotalAdult(int totalAdult) {
		this.totalAdult = totalAdult;
	}

	public int getTotalChild() {
		return totalChild;
	}

	public void setTotalChild(int totalChild) {
		this.totalChild = totalChild;
	}

	public int getTotalAllPrice() {
		return totalAllPrice;
	}

	public void setTotalAllPrice(int totalAllPrice) {
		this.totalAllPrice = totalAllPrice;
	}

	public int getResNo() {
		return resNo;
	}

	public void setResNo(int resNo) {
		this.resNo = resNo;
	}

	public String getResdate() {
		return resdate;
	}

	public void setResdate(String resdate) {
		this.resdate = resdate;
	}

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getPrCode() {
		return prCode;
	}

	public void setPrCode(int prCode) {
		this.prCode = prCode;
	}

	public int getCusNo() {
		return cusNo;
	}

	public void setCusNo(int cusNo) {
		this.cusNo = cusNo;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	@Override
	public String toString() {
		return "ReservationVO [resNo=" + resNo + ", resdate=" + resdate + ", adult=" + adult + ", child=" + child
				+ ", prCode=" + prCode + ", cusNo=" + cusNo + "]";
	}

}
