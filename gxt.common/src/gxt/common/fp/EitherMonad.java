package gxt.common.fp;

import gxt.common.Func1;

public class EitherMonad {

	public <L,R> Either<L,R> unit(R r) {
		return Either.Right(r, "unit'd");
	}

	public <L,R,S> Either<L,S> bind(Either<L,R> m, Func1<R,Either<L, S>> f) {
		if (m.isRight()) {
			return f.func(m.right());
		} else {
			return Either.Left(m.left(), m.why());
		}
	}
	
}
