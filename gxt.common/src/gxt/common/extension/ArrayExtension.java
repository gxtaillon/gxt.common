package gxt.common.extension;

import java.util.Arrays;

import gxt.common.Func1;
import gxt.common.Maybe;

public class ArrayExtension {
	public static <Ta> Maybe<Ta> get(Ta[] array, int index) {
		if (index < 0 || index >= array.length) {
			return Maybe.<Ta> Nothing("array index out of bounds");
		} else {
			return Maybe.<Ta> Just(array[index], "array element found");
		}
	}

	public static <Ta> Maybe<Ta[]> copyOfRange(Ta[] array, int begin, int end) {
		if (begin < 0 || begin >= array.length || end < 0
				|| end >= array.length || begin > end) {
			return Maybe.<Ta[]> Nothing("array indexes out of bounds");
		} else {
			return Maybe.<Ta[]> Just(Arrays.copyOfRange(array, begin, end),
					"array sub list copied");
		}
	}

	public static <Ta, Tb> Tb[] fmap(Ta[] array, Tb[] result, Func1<Ta, Tb> func) {
		int len = array.length;
		for (int i = 0; i != len; ++i) {
			result[i] = func.func(array[i]);
		}
		return result;
	}

	public static <Tb> Tb[] fmap(char[] array, Tb[] result,
			Func1<Character, Tb> func) {
		int len = array.length;
		for (int i = 0; i != len; ++i) {
			result[i] = func.func(array[i]);
		}
		return result;
	}

	public static <Ta> Ta[] concat(Ta[] l, Ta[] r, Ta[] out) {
		int llen = l.length;
		int rlen = r.length;
		System.arraycopy(l, 0, out, 0, llen);
		System.arraycopy(r, 0, out, llen, rlen);
		return out;
	}
}
