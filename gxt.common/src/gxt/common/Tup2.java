package gxt.common;

public class Tup2<Ta, Tb> {
	Ta a;
	Tb b;

	public Ta getA() {
		return a;
	}

	public void setA(Ta a) {
		this.a = a;
	}

	public Tb getB() {
		return b;
	}

	public void setB(Tb b) {
		this.b = b;
	}

	public Tup2(Ta a, Tb b) {
		super();
		this.a = a;
		this.b = b;
	}

}
