package gxt.common.fp;

import gxt.common.Func1;

public class MonadClass {
	public static <A,M> Monad<A,M> unit(Monad<A,M> m, A a) {
		return m.unit(a);
	}
	public static <A,B,M> Monad<B,M> bind(B b, Func1<B,Monad<B,M>> f) {
		return f.func(b);
	}
}
