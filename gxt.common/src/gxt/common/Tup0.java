package gxt.common;

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
