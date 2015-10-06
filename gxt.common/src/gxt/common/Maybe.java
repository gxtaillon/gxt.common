package gxt.common;

import java.io.Serializable;

public class Maybe<R> implements Functor<R>, MaybeBase, Monad<R, MaybeBase>,
		MonadWhy<R, MaybeBase>, Serializable {

	private static final long serialVersionUID = -2148548805452617600L;
	protected R just;
	protected String why;
	protected Boolean isJust;

	public R just() {
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

	@Override
	public <Tb> Functor<Tb> fmap(Func1<R, Tb> f) {
		if (isJust()) {
			return Just(f.func(just()), why());
		} else {
			return Nothing(why());
		}
	}

	@Override
	public Maybe<R> unit(R u) {
		return Just(u, "returned");
	}

	@Override
	public <Tb, Tmb extends Monad<Tb, MaybeBase>> Tmb bind(Func1<R, Tmb> f) {
		return bind(f, why());
	}

	@Override
	public String why() {
		return why;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Tb, Tmb extends Monad<Tb, MaybeBase>> Tmb bind(Func1<R, Tmb> f,
			String newWhy) {
		if (isJust()) {
			return f.func(just());
		} else {
			return (Tmb) Maybe.<Tb> Nothing(newWhy);
		}
	}
}
