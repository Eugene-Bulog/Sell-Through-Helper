package sellthrough;

public class SKU {
	/**
	 * Object class representing a product, contains data such as
	 * the SOH level and sales for this product (this week). equals()
	 * method overridden to compare item codes only.
	 * @author Eugene Bulog
	 */

	private String _code;
	private String _description;
	private int _wk1Sold;
	private int _soh;

	/**
	 * Constructor for SKU object representing a single product line
	 * @param code The reseller's code for this product
	 * @param desc The product name / description
	 * @param wk1 This week's sales for this product
	 * @param soh The current SOH for this product
	 */
	public SKU(String code, String desc, int wk1, int soh) {
		_code = code;
		_description = desc;
		_wk1Sold = wk1;
		_soh = soh;
	}

	/**
	 * @return the code for this SKU
	 */
	public String getCode() {
		return _code;
	}
	
	/**
	 * @return the description for this SKU
	 */
	public String getDescription() {
		return _description;
	}

	
	/**
	 * @return the number sold for this SKU
	 */
	public int getWk1Sold() {
		return _wk1Sold;
	}

	
	/**
	 * @return the current SOH for this SKU
	 */
	public int getSHOH() {
		return _soh;
	}


}
