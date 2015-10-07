package gxt.common.extension;

import gxt.common.Func1;
import gxt.common.Maybe;

import java.util.ArrayList;
import java.util.List;

public class ArrayListExtension {
	public static <Ta> Maybe<Ta> get(ArrayList<Ta> al, int index) {
		if (index < 0 || index >= al.size()) {
			return Maybe.<Ta> Nothing("array index out of bounds");
		} else {
			return Maybe.<Ta> Just(al.get(index), "array element found");
		}
	}

	public static <Ta> Maybe<List<Ta>> subList(ArrayList<Ta> al, int begin, int end) {
		if (begin < 0 || begin >= al.size() || end < 0
				|| end >= al.size() || begin > end) {
			return Maybe.<List<Ta>> Nothing("array indexes out of bounds");
		} else {
			return Maybe.<List<Ta>> Just(al.subList(begin, end),
					"array sub list copied");
		}
	}

	public <Ta, Tb> ArrayList<Tb> bind(
			ArrayList<Ta> al,
			Func1<Ta, ArrayList<Tb>> f) {
		ArrayList<Tb> r = new ArrayList<Tb>(al.size());
		for (Ta a : al) {
			r.addAll(f.func(a));
		}
		return r;
	}

	public <Ta, Tb> ArrayList<Tb> fmap(ArrayList<Ta> al, Func1<Ta, Tb> f) {
		ArrayList<Tb> r = new ArrayList<Tb>(al.size());
		for (Ta a : al) {
			r.add(f.func(a));
		}
		return r;
	}
}
