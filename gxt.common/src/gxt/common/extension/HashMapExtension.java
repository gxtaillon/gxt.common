package gxt.common.extension;

import gxt.common.Maybe;

import java.util.HashMap;

public class HashMapExtension {
	public static <Ta, Tk> Maybe<Ta> get(HashMap<Tk, Ta> hm, Tk key) {
		if (hm.containsKey(key)) {
			return Maybe.<Ta> Just(hm.get(key), "obtained value with key");
		} else {
			return Maybe.<Ta> Nothing("key does not exists");
		}
	}

	public static <Ta, Tk> Maybe<Ta> get(HashMap<Tk, Ta> hm, Tk key, Ta a) {
		Boolean exists = hm.containsKey(key);
		Ta old = hm.put(key, a);
		if (exists) {
			return Maybe.<Ta> Just(old, "replaced value at key");
		} else {
			return Maybe.<Ta> Nothing("no previous value at key");
		}
	}
}
