package gxt.common;

import java.io.Serializable;

public class Maybe<A> implements Why, Serializable {

	private static final long serialVersionUID = -2148548805452617600L;
	protected A just;
	protected String why;
	protected Boolean isJust;

	public A just() {
		if (!isJust) {
			throw new RuntimeException(
					"Invalid operation: called just on a nothing maybe.");
		}
		return just;
	}

	public Boolean isJust() {
		return isJust;
	}

	protected Maybe() {
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isJust()) {
			sb.append("Just (");
			sb.append(String.valueOf(just()));
			sb.append(")");
		} else {
			sb.append("Nothing");
		}
		sb.append(": ");
		sb.append(String.valueOf(why()));
		return sb.toString();
	}

	public static <R> Maybe<R> Nothing(String why) {
		Maybe<R> ew = new Maybe<R>();
		ew.isJust = false;
		ew.why = why;
		return ew;
	}

	public static <R> Maybe<R> Just(R right, String why) {
		Maybe<R> ew = new Maybe<R>();
		ew.just = right;
		ew.isJust = true;
		ew.why = why;
		return ew;
	}

	public static <Ta> Maybe<Ta> Challenge(Challenge challenge, Func1<String, Maybe<Ta>> f) {
		if (challenge.isSuccess()) {
			return f.func(challenge.why());
		} else {
			return Maybe.<Ta>Nothing(challenge.why());
		}
	}

	@Override
	public String why() {
		return why;
	}
	
	public <B> Maybe<B> fmap(Func1<A, B> f) {
		if (isJust()) {
			return Just(f.func(just()), why());
		} else {
			return Nothing(why());
		}
	}

	public <B> Maybe<B> unit(B b) {
		return Just(b, "unit'd");
	}

	public <B> Maybe<B> bind(Func1<A, Maybe<B>> f) {
		return bind(f, why());
	}
	
	public <B> Maybe<B> bind(Func1<A, Maybe<B>> f,
			final String newWhy) {
		return caseOf(f, new Func1<String, Maybe<B>>() {
			@Override
			public Maybe<B> func(String why) {
				return Nothing(newWhy);
			}
		});
	}
	
	public <T> T caseOf(Func1<A, T> f, Func1<String, T> g) {
		if (isJust()) {
			return f.func(just());
		} else {
			return g.func(why());
		}
	}
}
