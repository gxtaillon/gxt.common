package gxt.common.proxy;

import gxt.common.Maybe;

import java.util.HashMap;

public class HashMapProxy<Tk,Ta> {
	HashMap<Tk, Ta> map;
	private HashMapProxy() {
		// TODO Auto-generated constructor stub
	}
	
	public static <Tk, Ta> HashMapProxy<Tk, Ta> Map(HashMap<Tk, Ta> map) {
		HashMapProxy<Tk, Ta> r = new HashMapProxy<Tk, Ta>();
		r.map = map;
		return r;
	}
	
	public Maybe<Ta> get(Tk key) {
		if (map.containsKey(key)) {
			return Maybe.<Ta>Just(map.get(key), "obtained value with key");
		} else {
			return Maybe.<Ta>Nothing("key does not exists");
		}
	}

	public Maybe<Ta> put(Tk key, Ta a) {
		Boolean exists = map.containsKey(key);
		Ta old = map.put(key, a);
		if (exists) {
			return Maybe.<Ta>Just(old, "replaced value at key");
		} else {
			return Maybe.<Ta>Nothing("no previous value at key");
		}
	}
}
