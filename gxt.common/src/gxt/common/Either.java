package gxt.common;

import java.io.Serializable;

public class Either<L, R> implements Why, Serializable {

	private static final long serialVersionUID = -3328798946605402708L;
	protected L left;
	protected R right;
	protected String why;
	protected Boolean isRight;

	public L left() {
		if (isRight) {
			throw new RuntimeException(
					"Invalid operation: called left on a right either.");
		}
		return left;
	}

	public R right() {
		if (!isRight) {
			throw new RuntimeException(
					"Invalid operation: called right on a left either.");
		}
		return right;
	}

	public Boolean isRight() {
		return isRight;
	}

	protected Either() {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isRight()) {
			sb.append("Right (");
			sb.append(String.valueOf(right()));
		} else {
			sb.append("Left (");
			sb.append(String.valueOf(left()));
		}
		sb.append("): ");
		sb.append(String.valueOf(why()));
		return sb.toString();
	}

	public static <L, R> Either<L, R> Left(L left, String why) {
		Either<L, R> ew = new Either<L, R>();
		ew.left = left;
		ew.isRight = false;
		ew.why = why;
		return ew;
	}

	public static <L, R> Either<L, R> Right(R right, String why) {
		Either<L, R> ew = new Either<L, R>();
		ew.right = right;
		ew.isRight = true;
		ew.why = why;
		return ew;
	}

	@Override
	public String why() {
		return why;
	}
	
	public <S> Either<L,S> fmap(Func1<R, S> f) {
		if (isRight()) {
			return Right(f.func(right()), why());
		} else {
			return Left(left(), why());
		}
	}

	public Either<L, R> unit(R u) {
		return Right(u, "unit'd");
	}

	public <S> Either<L,S> bind(Func1<R, Either<L,S>> f) {
		return bind(f, why());
	}
	
	public <S> Either<L,S> bind(Func1<R, Either<L,S>> f,
			final String newWhy) {
		return caseOf(f, new Func1<L, Either<L,S>>() {
			@Override
			public Either<L,S> func(L a) {
				return Either.Left(a, newWhy);
			}
		});
	}
	
	public <T> T caseOf(Func1<R, T> f, Func1<L, T> g) {
		if (isRight()) {
			return f.func(right());
		} else {
			return g.func(left());
		}
	}
}
