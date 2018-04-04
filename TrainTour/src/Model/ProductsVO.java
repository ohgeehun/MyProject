package Model;

public class ProductsVO {

	private int prCode;
	private String tourName;
	private String startPoint;
	private String startDate;
	private String destination;
	private String desDate;
	private String returnPoint;
	private String returnDate;
	private int adultCharge;
	private int childCharge;
	private String season;
	private String hotelName;
	private String schedule;
	private int maximum;
	private int minimum;

	public ProductsVO() {
		super();
	}

	public ProductsVO(String tourName, String startPoint, String startDate, String destination, String desDate,
			String returnPoint, String returnDate, int adultCharge, int childCharge, String season, String schedule,
			int maximum, int minimum) {
		super();
		this.tourName = tourName;
		this.startPoint = startPoint;
		this.startDate = startDate;
		this.destination = destination;
		this.desDate = desDate;
		this.returnPoint = returnPoint;
		this.returnDate = returnDate;
		this.adultCharge = adultCharge;
		this.childCharge = childCharge;
		this.season = season;
		this.schedule = schedule;
		this.maximum = maximum;
		this.minimum = minimum;
	}

	public ProductsVO(String tourName, String startPoint, String startDate, String destination, String desDate,
			String returnPoint, String returnDate, int adultCharge, int childCharge, String season, String hotelName,
			String schedule, int maximum, int minimum) {
		super();
		this.tourName = tourName;
		this.startPoint = startPoint;
		this.startDate = startDate;
		this.destination = destination;
		this.desDate = desDate;
		this.returnPoint = returnPoint;
		this.returnDate = returnDate;
		this.adultCharge = adultCharge;
		this.childCharge = childCharge;
		this.season = season;
		this.hotelName = hotelName;
		this.schedule = schedule;
		this.maximum = maximum;
		this.minimum = minimum;
	}

	public ProductsVO(int prCode, String tourName, String startPoint, String startDate, String destination,
			String desDate, String returnPoint, String returnDate, int adultCharge, int childCharge, String season,
			String schedule, int maximum, int minimum) {
		super();
		this.prCode = prCode;
		this.tourName = tourName;
		this.startPoint = startPoint;
		this.startDate = startDate;
		this.destination = destination;
		this.desDate = desDate;
		this.returnPoint = returnPoint;
		this.returnDate = returnDate;
		this.adultCharge = adultCharge;
		this.childCharge = childCharge;
		this.season = season;
		this.schedule = schedule;
		this.maximum = maximum;
		this.minimum = minimum;
	}

	public ProductsVO(int prCode, String tourName, String startPoint, String startDate, String destination,
			String desDate, String returnPoint, String returnDate, int adultCharge, int childCharge, String season,
			String hotelName, String schedule, int maximum, int minimum) {
		super();
		this.prCode = prCode;
		this.tourName = tourName;
		this.startPoint = startPoint;
		this.startDate = startDate;
		this.destination = destination;
		this.desDate = desDate;
		this.returnPoint = returnPoint;
		this.returnDate = returnDate;
		this.adultCharge = adultCharge;
		this.childCharge = childCharge;
		this.season = season;
		this.hotelName = hotelName;
		this.schedule = schedule;
		this.maximum = maximum;
		this.minimum = minimum;
	}

	public int getPrCode() {
		return prCode;
	}

	public void setPrCode(int prCode) {
		this.prCode = prCode;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDesDate() {
		return desDate;
	}

	public void setDesDate(String desDate) {
		this.desDate = desDate;
	}

	public String getReturnPoint() {
		return returnPoint;
	}

	public void setReturnPoint(String returnPoint) {
		this.returnPoint = returnPoint;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public int getAdultCharge() {
		return adultCharge;
	}

	public void setAdultCharge(int adultCharge) {
		this.adultCharge = adultCharge;
	}

	public int getChildCharge() {
		return childCharge;
	}

	public void setChildCharge(int childCharge) {
		this.childCharge = childCharge;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

}
