package gxt.common;

/**
 * The Void, Nil, Null, E or whatever you want to call it type.
 */
public class Tup0 {
	protected Tup0() {
	}

	private static Tup0 me;

	public static Tup0 Tup() {
		if (me == null) {
			me = new Tup0();
		}
		return me;
	}
}
