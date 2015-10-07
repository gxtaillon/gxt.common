package gxt.common.lispite;

import gxt.common.Func0;
import gxt.common.Func1;
import gxt.common.Maybe;
import gxt.common.extension.ArrayListExtension;

import java.util.ArrayList;
import java.util.List;

public class Parser {

	protected ArrayList<Character> chars;
	protected int charIndex;

	protected Parser(String input) {
		super();
		chars = new ArrayList<Character>(input.length());
		for (char c : input.toCharArray()) {
			chars.add(c);
		}
		charIndex = 0;
	}

	protected Maybe<String> word() {
		return consumeString(new Func1<Character, Boolean>() {
			public Boolean func(Character c) {
				return Character.isLetterOrDigit(c);
			}
		});
	}

	protected Maybe<String> whitespace() {
		return consumeString(new Func1<Character, Boolean>() {
			public Boolean func(Character c) {
				return Character.isWhitespace(c);
			}
		});
	}

	protected Maybe<Integer> positiveInteger() {
		return consumeString(new Func1<Character, Boolean>() {
			public Boolean func(Character c) {
				return Character.isDigit(c);
			}
		}).bind(new Func1<String, Maybe<Integer>>() {
			public Maybe<Integer> func(String i) {
				return Maybe.<Integer> Just(Integer.parseInt(i),
						"parsed a positive integer");
			}
		});
	}

	protected Maybe<Integer> integer() {
		final Maybe<Character> minus = lookahead(new Func0<Maybe<Character>>() {
			public Maybe<Character> func() {
				return character('-');
			}
		});
		return positiveInteger().bind(new Func1<Integer, Maybe<Integer>>() {
			public Maybe<Integer> func(Integer i) {
				return Maybe.<Integer> Just((minus.isJust()) ? -i : i,
						"parsed an integer");
			}
		});
	}

	protected Maybe<Double> float64() {
		return integer().bind(new Func1<Integer, Maybe<Double>>() {
			public Maybe<Double> func(final Integer i) {
				return character('.').bind(
						new Func1<Character, Maybe<Double>>() {
							public Maybe<Double> func(Character dot) {
								return positiveInteger().bind(
										new Func1<Integer, Maybe<Double>>() {
											public Maybe<Double> func(Integer f) {
												// not great
												return Maybe.<Double> Just(
														Double.parseDouble(i
																+ "." + f),
														"parsed an integer");
											}
										}, "expected the fractionnal part of a double");
							}
						}, "expected `.` to split the integral and fractionnal part of a double");
			}
		}, "expected the integral part of a double");
	}

	protected Maybe<Character> character(final char c) {
		return consumeChar(new Func1<Character, Boolean>() {
			public Boolean func(Character d) {
				return c == d;
			}
		});
	}

	protected Maybe<String> string(final String s) {
		final int len = s.length();
		return ArrayListExtension.subList(chars, charIndex, charIndex + len).bind(
				new Func1<List<Character>, Maybe<String>>() {
					public Maybe<String> func(List<Character> cs) {
						StringBuilder sb = new StringBuilder(chars.size());
						for (Character c : cs)
							sb.append(c);
						String p = sb.toString();
						charIndex += len;
						if (p.equals(s)) {
							return Maybe.<String> Just(s, "parsed string");
						} else {
							return Maybe
									.<String> Nothing("expected string `" + s + "` not found");
						}
					}
				}, "expected string `" + s + "` but end of input reached");
	}

	protected <Ta> Maybe<Ta> lookahead(final Func0<Maybe<Ta>> pred) {
		int oldCharIndex = charIndex;
		Maybe<Ta> a = pred.func();
		if (a.isJust()) {
			return a;
		} else {
			charIndex = oldCharIndex;
			return Maybe.<Ta> Nothing("predicate failed");
		}
	}

	protected Maybe<Character> consumeChar(final Func1<Character, Boolean> pred) {
		return ArrayListExtension.get(chars, charIndex).bind(
				new Func1<Character, Maybe<Character>>() {
					public Maybe<Character> func(Character c) {
						if (pred.func(c)) {
							++charIndex;
							return Maybe.<Character> Just(c, "consumed");
						} else {
							return Maybe
									.<Character> Nothing("predicate failed");
						}
					}
				});
	}

	protected Maybe<String> consumeString(Func1<Character, Boolean> pred) {
		StringBuilder sb = new StringBuilder();
		Maybe<Character> c;
		for (;;) {
			c = consumeChar(pred);
			if (c.isJust()) {
				sb.append(c.just());
				continue;
			} else {
				break;
			}
		}
		if (sb.length() > 0) {
			return Maybe.<String> Just(sb.toString(), "consumed string");
		} else {
			return Maybe.<String> Nothing(c.why());
		}
	}

	protected <Ta> ArrayList<Ta> many0(Func0<Maybe<Ta>> pred) {
		ArrayList<Ta> sb = new ArrayList<Ta>();
		Maybe<Ta> c;
		for (;;) {
			c = pred.func();
			if (c.isJust()) {
				sb.add(c.just());
				continue;
			} else {
				break;
			}
		}
		return sb;
	}

}