package gxt.common.fp;

import gxt.common.Func1;
import gxt.common.Tup0;
import gxt.common.Why;

import java.io.Serializable;

public final class Either<L, R> implements Why, Serializable {

	private static final long serialVersionUID = -3328798946605402708L;
	private L left;
	private R right;
	private String why;
	private Boolean isRight;

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
	
	public static <L, R, T> CaseOf<L, R, Tup0, Tup0, T> newCaseOf() {
		return new CaseOf<L, R, Tup0, Tup0, T>();
	}
	
    public <T> T caseOf(CaseOf<L, R, Func1<L, T>, Func1<R, T>, T> caseOf) {
    	if (isRight()) {
    		return caseOf.matchRight.func(this.right());
    	} else {
    		return caseOf.matchLeft.func(this.left());
    	}
    }
	
	public static final class CaseOf<L, R, PL, PR, T> {
		private Func1<R, T> matchRight; 
		private Func1<L, T> matchLeft;
		private CaseOf() {}
		@SuppressWarnings("unchecked")
		public CaseOf<L, R, PL, Func1<R, T>, T> caseRight(Func1<R, T> f) {
			matchRight = f;
			return (CaseOf<L, R, PL, Func1<R, T>, T>)this;
		}
		@SuppressWarnings("unchecked")
		public CaseOf<L, R, Func1<L, T>, PR, T> caseLeft(Func1<L, T> f) {
			matchLeft = f;
			return (CaseOf<L, R, Func1<L, T>, PR, T>)this;
		}
	}
}
