package ua.org.tumakha.util;

/**
 * @author Yuriy Tumakha
 */
public class NumberUtilUa {

	public final static int DG_POWER = 6;

	// private final static String[][] a_power = new String[][] {
	// { "0", "", "", "" }, // 1
	// { "1", "������ ", "������ ", "����� " }, // 2
	// { "0", "������� ", "�������� ", "��������� " }, // 3
	// { "0", "�������� ", "��������� ", "���������� " }, // 4
	// { "0", "�������� ", "��������� ", "���������� " }, // 5
	// { "0", "����������� ", "������������ ", "������������� " }, // 6
	// { "0", "����������� ", "������������ ", "������������� " } // 7
	// };

	// private final static String[][] digit = new String[][] {
	// { "", "", "������ ", "", "" },
	// { "���� ", "���� ", "����������� ", "������ ", "��� " },
	// { "��� ", "��� ", "���������� ", "�������� ", "������ " },
	// { "��� ", "��� ", "���������� ", "�������� ", "������ " },
	// { "������ ", "������ ", "������������ ", "����� ", "��������� " },
	// { "���� ", "���� ", "���������� ", "��������� ", "������� " },
	// { "����� ", "����� ", "����������� ", "���������� ", "�������� " },
	// { "���� ", "���� ", "���������� ", "��������� ", "������� " },
	// { "������ ", "������ ", "������������ ", "����������� ",
	// "��������� " },
	// { "������ ", "������ ", "������������ ", "��������� ", "��������� " } };

	private final static String[][] a_power = new String[][] {
			{ "0", "", "", "" }, // 1
			{ "1", "������ ", "������ ", "����� " }, // 2
			{ "0", "������ ", "������� ", "������� " }, // 3
			{ "0", "������ ", "������� ", "������� " }, // 4
			{ "0", "�������� ", "��������� ", "��������� " }, // 5
			{ "0", "�����������", "������������", "������������" }, // 6
			{ "0", "����������", "�����������", "�����������" } // 7
	};

	private final static String[][] digit = new String[][] {
			{ "", "", "������ ", "", "" },
			{ "���� ", "���� ", "���������� ", "������ ", "��� " },
			{ "��� ", "�� ", "���������� ", "�������� ", "���� " },
			{ "��� ", "��� ", "���������� ", "�������� ", "������ " },
			{ "������ ", "������ ", "������������ ", "����� ", "��������� " },
			{ "�'��� ", "�'��� ", "�'��������� ", "�'������� ", "�'����� " },
			{ "����� ", "����� ", "���������� ", "��������� ", "������� " },
			{ "�� ", "�� ", "��������� ", "������� ", "����� " },
			{ "��� ", "��� ", "���������� ", "�������� ", "������ " },
			{ "���'��� ", "���'��� ", "���'��������� ", "���'������ ",
					"���'����� " } };

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
			return "���� ";
		if (sum < 0) {
			result.append("���� ");
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
