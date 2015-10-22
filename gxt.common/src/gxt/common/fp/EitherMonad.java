package gxt.common.fp;

import gxt.common.Func1;

public class EitherMonad <L,R> implements Monad<R, EitherMonad> {

	@Override
	public Monad<R, EitherMonad> unit(R a) {
		//return Either.<L,R>Right(a, "created Right");
		return null;
	}

	@Override
	public <B> Monad<R, EitherMonad> bind(B b, Func1<B, EitherMonad> f) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
