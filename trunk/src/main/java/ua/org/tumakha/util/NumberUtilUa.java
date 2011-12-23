package ua.org.tumakha.util;

/**
 * @author Yuriy Tumakha
 */
public class NumberUtilUa {

	public final static int DG_POWER = 6;

	// private final static String[][] a_power = new String[][] {
	// { "0", "", "", "" }, // 1
	// { "1", "тыс€ча ", "тыс€чи ", "тыс€ч " }, // 2
	// { "0", "миллион ", "миллиона ", "миллионов " }, // 3
	// { "0", "миллиард ", "миллиарда ", "миллиардов " }, // 4
	// { "0", "триллион ", "триллиона ", "триллионов " }, // 5
	// { "0", "квадриллион ", "квадриллиона ", "квадриллионов " }, // 6
	// { "0", "квинтиллион ", "квинтиллиона ", "квинтиллионов " } // 7
	// };

	// private final static String[][] digit = new String[][] {
	// { "", "", "дес€ть ", "", "" },
	// { "один ", "одна ", "одиннадцать ", "дес€ть ", "сто " },
	// { "два ", "две ", "двенадцать ", "двадцать ", "двести " },
	// { "три ", "три ", "тринадцать ", "тридцать ", "триста " },
	// { "четыре ", "четыре ", "четырнадцать ", "сорок ", "четыреста " },
	// { "п€ть ", "п€ть ", "п€тнадцать ", "п€тьдес€т ", "п€тьсот " },
	// { "шесть ", "шесть ", "шестнадцать ", "шестьдес€т ", "шестьсот " },
	// { "семь ", "семь ", "семнадцать ", "семьдес€т ", "семьсот " },
	// { "восемь ", "восемь ", "восемнадцать ", "восемьдес€т ",
	// "восемьсот " },
	// { "дев€ть ", "дев€ть ", "дев€тнадцать ", "дев€носто ", "дев€тьсот " } };

	private final static String[][] a_power = new String[][] {
			{ "0", "", "", "" }, // 1
			{ "1", "тис€ча ", "тис€ч≥ ", "тис€ч " }, // 2
			{ "0", "м≥льйон ", "м≥льйони ", "м≥льйон≥в " }, // 3
			{ "0", "м≥ль€рд ", "м≥ль€рди ", "м≥ль€рд≥в " }, // 4
			{ "0", "трильйон ", "трильйона ", "трильйон≥в " }, // 5
			{ "0", "квадрильйон", "квадрильйона", "квадрильйон≥в" }, // 6
			{ "0", "кв≥нтильйон", "кв≥нтильйона", "кв≥нтильйон≥в" } // 7
	};

	private final static String[][] digit = new String[][] {
			{ "", "", "дес€ть ", "", "" },
			{ "один ", "одна ", "одинадц€ть ", "дес€ть ", "сто " },
			{ "два ", "дв≥ ", "дванадц€ть ", "двадц€ть ", "дв≥ст≥ " },
			{ "три ", "три ", "тринадц€ть ", "тридц€ть ", "триста " },
			{ "чотири ", "чотири ", "чотирнадц€ть ", "сорок ", "чотириста " },
			{ "п'€ть ", "п'€ть ", "п'€тнадц€ть ", "п'€тдес€т ", "п'€тсот " },
			{ "ш≥сть ", "ш≥сть ", "ш≥снадц€ть ", "ш≥стдес€т ", "ш≥стсот " },
			{ "с≥м ", "с≥м ", "с≥мнадцать ", "с≥мдес€т ", "с≥мсот " },
			{ "в≥с≥м ", "в≥с≥м ", "в≥с≥мнадцать ", "в≥с≥мдес€т ", "в≥с≥мсот " },
			{ "дев'€ть ", "дев'€ть ", "дев'€тнадцать ", "дев'€носто ",
					"дев'€тсот " } };

	public static String toString(int sum) {
		int i, mny;
		StringBuffer result = new StringBuffer("");
		long divisor;
		int psum = sum;

		int one = 1;
		int four = 2;
		int many = 3;

		int hun = 4;
		int dec = 3;
		int dec2 = 2;

		if (sum == 0)
			return "нуль ";
		if (sum < 0) {
			result.append("м≥нус ");
			psum = -psum;
		}

		for (i = 0, divisor = 1; i < DG_POWER; i++)
			divisor *= 1000;

		for (i = DG_POWER - 1; i >= 0; i--) {
			divisor /= 1000;
			mny = (int) (psum / divisor);
			psum %= divisor;
			// str="";
			if (mny == 0) {
				if (i > 0)
					continue;
				result.append(a_power[i][one]);
			} else {
				if (mny >= 100) {
					result.append(digit[mny / 100][hun]);
					mny %= 100;
				}
				if (mny >= 20) {
					result.append(digit[mny / 10][dec]);
					mny %= 10;
				}
				if (mny >= 10) {
					result.append(digit[mny - 10][dec2]);
				} else {
					if (mny >= 1)
						result.append(digit[mny]["0".equals(a_power[i][0]) ? 0
								: 1]);
				}
				switch (mny) {
				case 1:
					result.append(a_power[i][one]);
					break;
				case 2:
				case 3:
				case 4:
					result.append(a_power[i][four]);
					break;
				default:
					result.append(a_power[i][many]);
					break;
				}
				;
			}
		}
		return result.toString();
	}

	// public static String toString(double num) {
	// return toString((int) num) + "."
	// + toString((int) (num * 100 - ((int) num) * 100));
	// }

	public static String numberInWords(long number) {
		return toString((int) number).trim();
	}

}
