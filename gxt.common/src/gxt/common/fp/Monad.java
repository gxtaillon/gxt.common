package gxt.common.fp;

import gxt.common.Func1;

public interface Monad <A, M> {
	public Monad<A,M> unit(A a);
	public <B> Monad<A,M> bind(B b, Func1<B, M> f);
}
