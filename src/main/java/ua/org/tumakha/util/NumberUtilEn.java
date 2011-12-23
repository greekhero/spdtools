package ua.org.tumakha.util;

/**
 * @author Yuriy Tumakha
 */
public class NumberUtilEn {

	/** Holds the number 1-19, dual purpose for special cases (teens) **/
	private static final String[] UNITS = { "one", "two", "three", "four",
			"five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
			"thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
			"eighteen", "nineteen" };
	/** Holds the tens places **/
	private static final String[] TENS = { "ten", "twenty", "thirty", "forty",
			"fifty", "sixty", "seventy", "eighty", "ninty" };
	/** Covers max value of Long **/
	private static final String[] THOUSANDS = { "", "thousand", "million",
			"billion", "trillion", "quadrillion", "quintillion", "sextillion" };

	/**
	 * Represents a number in words (seven hundred thirty four, two hundred and
	 * seven, etc...)
	 * 
	 * The largest number this will accept is
	 * 
	 * <pre>
	 * 999,999,999,999,999,999,999
	 * </pre>
	 * 
	 * but that's okay becasue the largest value of Long is
	 * 
	 * <pre>
	 * 9,223,372,036,854,775,807
	 * </pre>
	 * 
	 * . The smallest number is
	 * 
	 * <pre>
	 * -9,223,372,036,854,775,807
	 * </pre>
	 * 
	 * (Long.MIN_VALUE +1) due to a limitation of Java.
	 * 
	 * @param number
	 *            between Long.MIN_VALUE and Long.MAX_VALUE
	 * @return the number writen in words
	 */
	public static String numberInWords(long number) {
		StringBuilder sb = new StringBuilder();
		// Zero is an easy one, but not technically a number :P
		if (number == 0) {
			return "zero";
		}
		// Negative numbers are easy too
		if (number < 0) {
			sb.append("negative ");
			number = Math.abs(number);
		}

		// Log keeps track of which Thousands place we're in
		long log = 1000000000000000000L, sub = number;
		int i = 7;
		do {
			// This is the 1-999 subset of the current thousand's place
			sub = number / log;
			// Cut down number for the next loop
			number = number % log;
			// Cut down log for the next loop
			log = log / 1000;
			i--; // tracks the big number place
			if (sub != 0) {
				// If this thousandths place is not 0 (that's okay, just skip)
				// tack it on
				sb.append(hundredsInWords((int) sub));
				sb.append(" ");
				sb.append(THOUSANDS[i]);
				if (number != 0) {
					sb.append(" ");
				}
			}
		} while (number != 0);

		return sb.toString().trim();
	}

	/**
	 * Converts a number into hundreds.
	 * 
	 * The basic unit of the American numbering system is "hundreds". Thus 1,024
	 * = (one thousand) twenty four 1,048,576 = (one million) (fourty eight
	 * thousand) (five hundred seventy six) so there's no need to break it down
	 * further than that.
	 * 
	 * @param n
	 *            between 1 and 999
	 * @return
	 */
	private static String hundredsInWords(int n) {
		if (n < 1 || n > 999) {
			throw new AssertionError(n); // Use assersion errors in private
											// methods only!
		}
		StringBuilder sb = new StringBuilder();

		// Handle the "hundred", with a special provision for x01-x09
		if (n > 99) {
			sb.append(UNITS[(n / 100) - 1]);
			sb.append(" hundred");
			n = n % 100;
			if (n != 0) {
				sb.append(" ");
			}
			if (n < 10) {
				// sb.append("and ");
				sb.append(" ");
			}
		}

		// Handle the special cases and the tens place at the same time.
		if (n > 19) {
			sb.append(TENS[(n / 10) - 1]);
			n = n % 10;
			if (n != 0) {
				sb.append(" ");
			}
		}

		// This is the ones place
		if (n > 0) {
			sb.append(UNITS[n - 1]);
		}
		return sb.toString();
	}

}
