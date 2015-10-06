package gxt.common.proxy;

import java.util.ArrayList;
import java.util.List;

import gxt.common.Func1;
import gxt.common.Functor;
import gxt.common.Maybe;
import gxt.common.Monad;

public class ArrayListProxy <Ta> implements Functor<Ta>, Monad<Ta,ArrayListProxyBase> {
	ArrayList<Ta> array;
	
	public int size() {
		return array.size();
	}
	
	public Maybe<Ta> get(int index) {
		if (index < 0 || index >= array.size()) {
			return Maybe.<Ta>Nothing("array index out of bounds");
		} else {
			return Maybe.<Ta>Just(array.get(index), "array element found");
		}
	}
	
	public Maybe<List<Ta>> subList(int begin, int end) {
		if (begin < 0 || begin >= array.size() || end < 0 || end >= array.size() || begin > end) {
			return Maybe.<List<Ta>>Nothing("array indexes out of bounds");
		} else {
			return Maybe.<List<Ta>>Just(array.subList(begin, end), "array sub list copied");
		}
	}
	
	private ArrayListProxy() { }
	
	public static <Ta> ArrayListProxy<Ta> Array(ArrayList<Ta> array) {
		ArrayListProxy<Ta> r = new ArrayListProxy<Ta>();
		r.array = array;
		return r;
	}
	
	@Override
	public ArrayListProxy<Ta> unit(Ta a) {
		ArrayList<Ta> r = new ArrayList<Ta>();
		r.add(a);
		return Array(r);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <Tb, Tmb extends Monad<Tb, ArrayListProxyBase>> Tmb bind(
			Func1<Ta, Tmb> f) {
		ArrayList<Tb> r = new ArrayList<Tb>(size());
		for(Ta a : array) {
			r.addAll(((ArrayListProxy<Tb>)(f.func(a))).array);
		}
		return (Tmb) Array(r);
	}
		
	@Override
	public <Tb> ArrayListProxy<Tb> fmap(Func1<Ta, Tb> f) {
		ArrayList<Tb> r = new ArrayList<Tb>(size());
		for(Ta a : array) {
			r.add(f.func(a));
		}
		return Array(r);
	}
}
