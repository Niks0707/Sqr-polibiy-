package secur;

import java.util.ArrayList;
import java.util.HashMap;

public class Polibiy {

	private static String standartSquare = "àáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿ" + "abcdefghijklmnopqrstuvwxyz"
			+ "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789 ,.!?;:()-+=*\\/\n";
	private static String square;

	static {
		square = standartSquare;
	}

	public static void setKey(String key) {
		if (key == "") {
			square = standartSquare;
			return;
		}

		StringBuffer withoutKey = new StringBuffer(standartSquare.length());
		StringBuffer withoutRepeatKey = new StringBuffer();
		for (Character c : key.toCharArray()) {
			if (withoutRepeatKey.indexOf(c.toString()) == -1 && standartSquare.indexOf(c) != -1) {
				withoutRepeatKey.append(c);
			}
		}
		for (Character c : standartSquare.toCharArray()) {
			if (withoutRepeatKey.indexOf(c.toString()) == -1) {
				withoutKey.append(c);
			}
		}
		square = withoutRepeatKey.toString() + withoutKey.toString();
	}

	public static String getStandartSquare() {
		return standartSquare;
	}

	public static String getSquare() {
		return square;
	}

	public static String methodFirstEncryption(String text, int shift) {
		StringBuffer result = new StringBuffer(text.length());
		for (Character c : text.toCharArray()) {
			int n = square.indexOf(c);
			if (n == -1) {
				result.append(c);
			} else {
				n = (n + 12 * shift) % 144;
				result.append(square.charAt(n));
			}
		}
		return result.toString();
	}

	public static String methodFirstDecryption(String text, int shift) {
		return methodFirstEncryption(text, 12 - shift);
	}

	public static String methodSecondEncryption(String text) {
		return methodThirdEncryption(text, 0);
	}

	public static String methodSecondDecryption(String text) {
		return methodThirdDecryption(text, 0);
	}

	public static String methodThirdEncryption(String text, int shift) {
		StringBuffer result = new StringBuffer(text.length());
		ArrayList<Integer> verticalCoord = new ArrayList<>();
		ArrayList<Integer> horizontalCoord = new ArrayList<>();
		HashMap<Integer, Character> unsupport = new HashMap<>();
		for (int i = 0; i < text.length(); i++) {
			int n = square.indexOf(text.charAt(i));
			if (n == -1) {
				unsupport.put(i, text.charAt(i));
			} else {
				verticalCoord.add(n / 12);
				horizontalCoord.add(n % 12);
			}
		}
		ArrayList<Integer> temp = new ArrayList<>(verticalCoord);
		temp.addAll(horizontalCoord);
		ArrayList<Integer> coord = new ArrayList<>(temp.size());

		for (int i = 0; i < temp.size(); i++) {
			coord.add(temp.get(((i + shift) % temp.size())));
		}

		for (int i = 0; i < coord.size(); i += 2) {
			result.append(square.charAt((coord.get(i) * 12 + coord.get(i + 1))));
		}
		for (Integer i : unsupport.keySet()) {
			result.insert(i.intValue(), unsupport.get(i));
		}
		return result.toString();
	}

	public static String methodThirdDecryption(String text, int shift) {
		StringBuffer result = new StringBuffer(text.length());
		ArrayList<Integer> verticalCoord = new ArrayList<>();
		ArrayList<Integer> horizontalCoord = new ArrayList<>();
		HashMap<Integer, Character> unsupport = new HashMap<>();
		for (int i = 0; i < text.length(); i++) {
			int n = square.indexOf(text.charAt(i));
			if (n == -1) {
				unsupport.put(i, text.charAt(i));
			} else {
				verticalCoord.add(n / 12);
				horizontalCoord.add(n % 12);
			}
		}
		ArrayList<Integer> temp = new ArrayList<>(verticalCoord.size());
		for (int i = 0; i < verticalCoord.size(); i++) {
			temp.add(verticalCoord.get(i));
			temp.add(horizontalCoord.get(i));
		}
		ArrayList<Integer> coord = new ArrayList<>(temp.size());

		for (int i = 0; i < temp.size(); i++) {
			coord.add(temp.get(((temp.size() + i - shift) % temp.size())));
		}

		for (int i = 0; i < coord.size() / 2; i++) {
			result.append(square.charAt((coord.get(i) * 12 + coord.get(i + coord.size() / 2))));
		}

		for (Integer i : unsupport.keySet()) {
			result.insert(i.intValue(), unsupport.get(i));
		}
		return result.toString();
	}

}
