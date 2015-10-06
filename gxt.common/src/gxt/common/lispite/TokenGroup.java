package gxt.common.lispite;

public class TokenGroup {
	String name;
	TokenGroup[] group;

	private TokenGroup() {
	}

	public String getName() {
		return name;
	}

	public TokenGroup[] getGroup() {
		return group;
	}

	public static TokenGroup Single(String name) {
		TokenGroup r = new TokenGroup();
		r.name = name;
		r.group = new TokenGroup[0];
		return r;
	}

	public static TokenGroup Group(String name, TokenGroup[] group) {
		TokenGroup r = new TokenGroup();
		r.name = name;
		r.group = group;
		return r;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(name);
		for (TokenGroup g : group) {
			sb.append(" ");
			sb.append(g.toString());
		}
		sb.append(")");
		return sb.toString();
	}
}
