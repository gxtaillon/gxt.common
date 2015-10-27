package gxt.common;

import java.io.Serializable;

public class Challenge implements Why, Serializable {
	
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

	public Challenge fmap(Func1<String, String> f) {
		if (isSuccess()) {
			return Success(f.func(why()));
		} else {
			return Failure(why());
		}
	}

	public Challenge unit(String s) {
		return Challenge.Success(s);
	}

	public Challenge bind(Func1<String, Challenge> f) {
		return bind(f, why());
	}
	
	public Challenge bind(Func1<String, Challenge> f,
			final String newWhy) {
		return caseOf(f, new Func1<String, Challenge>() {
			@Override
			public Challenge func(String a) {
				return Challenge.Failure(newWhy);
			}
		});
	}
	
	public <T> T caseOf(Func1<String, T> f, Func1<String, T> g) {
		if (isSuccess()) {
			return f.func(why());
		} else {
			return g.func(why());
		}
	}
}
