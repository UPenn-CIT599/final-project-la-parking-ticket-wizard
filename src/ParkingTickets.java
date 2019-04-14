
public class ParkingTickets {

	private String ticketNumber;
	private String issueDate;
	private int issueTime;
	private String rpState;
	private int rpExpire;
	private String maker;
	private String bodyStyle;
	private String color;
	private String violationCode;
	private String violationDescription;
	private int fine;
	private double latitude;
	private double longitude;

	/**
	 * 
	 * @param rawInfo
	 * @return
	 */
	private int emptyIntCellHandler(String rawInfo) {
		if (rawInfo.isEmpty()) {
			return 0;
		} else {
			try {
				return Integer.parseInt(rawInfo);
			} catch (NumberFormatException e) {
				return 0;
			}

		}
	}

	/**
	 * 
	 * @param rawInfo
	 * @return
	 */
	private double emptyDoubleCellHandler(String rawInfo) {
		if (rawInfo.isEmpty()) {
			return 0.0;
		} else {
			try {
				return Double.parseDouble(rawInfo);
			} catch (NumberFormatException e) {
				return 0.0;
			}
		}
	}

	/**
	 * 
	 * @param ticketColumn
	 */
	public ParkingTickets(String[] ticketColumn) {

		if (ticketColumn.length == 19) {

			this.ticketNumber = ticketColumn[0].trim();
			if (!ticketColumn[1].trim().isEmpty()) {
				this.issueDate = ticketColumn[1].substring(0, 10);
			} else {
				this.issueDate = "";
			}
			this.issueTime = emptyIntCellHandler(ticketColumn[2].trim());
			this.rpState = ticketColumn[5].trim();
			this.rpExpire = emptyIntCellHandler(ticketColumn[6].trim());
			this.maker = ticketColumn[8].trim();
			this.bodyStyle = ticketColumn[9].trim();
			this.color = ticketColumn[10].trim();
			this.violationCode = ticketColumn[14].trim();
			this.violationDescription = ticketColumn[15].trim();
			this.fine = emptyIntCellHandler(ticketColumn[16].trim());
			this.latitude = emptyDoubleCellHandler(ticketColumn[17].trim());
			this.longitude = emptyDoubleCellHandler(ticketColumn[18].trim());
		}

	}

	public String[] getParkingTicketsArray() {
		String[] ticketInfoArray = new String[19];

		ticketInfoArray[0] = this.ticketNumber;
		ticketInfoArray[1] = this.issueDate;
		ticketInfoArray[2] = "" + this.issueTime;
		ticketInfoArray[3] = ""; // Meter ID
		ticketInfoArray[4] = ""; // Marked Time
		ticketInfoArray[5] = this.rpState;
		ticketInfoArray[6] = "" + this.rpExpire;
		ticketInfoArray[7] = ""; // VIN
		ticketInfoArray[8] = this.maker;
		ticketInfoArray[9] = this.bodyStyle;
		ticketInfoArray[10] = this.color;
		ticketInfoArray[11] = ""; // Location
		ticketInfoArray[12] = ""; // Route
		ticketInfoArray[13] = ""; // Agency
		ticketInfoArray[14] = this.violationCode;
		ticketInfoArray[15] = this.violationDescription;
		ticketInfoArray[16] = "" + this.fine;
		ticketInfoArray[17] = "" + this.latitude;
		ticketInfoArray[18] = "" + this.longitude;
		
		return ticketInfoArray;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public int getIssueTime() {
		return issueTime;
	}

	public String getRpState() {
		return rpState;
	}

	public int getRpExpire() {
		return rpExpire;
	}

	public String getMaker() {
		return maker;
	}

	public String getBodyStyle() {
		return bodyStyle;
	}

	public String getColor() {
		return color;
	}

	public String getViolationCode() {
		return violationCode;
	}

	public String getViolationDescription() {
		return violationDescription;
	}

	public int getFine() {
		return fine;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "Parking Ticket Info : " + issueDate + ", " + issueTime + ", " + rpState + ", " + rpExpire + ", " + maker
				+ ", " + bodyStyle + ", " + color + ", " + violationCode + ", " + violationDescription + ", " + fine
				+ ", " + latitude + ", " + longitude + "\n";
	}

}
