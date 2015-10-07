package gxt.common;

import java.io.Serializable;

public class Challenge implements Monad<String, ChallengeBase>, 
		MonadWhy<String, ChallengeBase>, Serializable {
	
	private static final long serialVersionUID = -5527645162705193712L;
	protected String why;
	protected boolean success;
	
	protected Challenge() { }

	public static Challenge Success(String why) {
		Challenge r = new Challenge();
		r.success = true;
		r.why = why;
		return r;
	}
	
	public static Challenge Failure(String why) {
		Challenge r = new Challenge();
		r.success = false;
		r.why = why;
		return r;
	}
	
	public static <Ta> Challenge Maybe(Maybe<Ta> maybe, Func1<Ta, Challenge> f) {
		if (maybe.isJust()) {
			return f.func(maybe.just());
		} else {
			return Failure(maybe.why());
		}
	}
	
	public boolean isSuccess() {
		return success;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isSuccess()) {
			sb.append("Success: ");
		} else {
			sb.append("Failure: ");
		}
		sb.append(String.valueOf(why()));
		return sb.toString();
	}
	
	@Override
	public String why() {
		return why;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Tb, Tmb extends Monad<Tb, ChallengeBase>> Tmb bind(
			Func1<String, Tmb> f, String newWhy) {
		if (success) {
			return f.func(why);
		} else {
			return (Tmb) Failure(newWhy);
		}
	}

	@Override
	public Monad<String, ChallengeBase> unit(String a) {
		return Success(a);
	}

	@Override
	public <Tb, Tmb extends Monad<Tb, ChallengeBase>> Tmb bind(
			Func1<String, Tmb> f) {
		return bind(f, why());
	}

}
