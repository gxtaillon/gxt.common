package gxt.common.lispite;

import java.util.ArrayList;

import gxt.common.Func0;
import gxt.common.Func1;
import gxt.common.Maybe;
import gxt.common.extension.ArrayExtension;

public class TokenGroupParser extends Parser {
	TokenGroup group;

	protected TokenGroupParser(String input) {
		super(input);
	}

	public static Maybe<TokenGroup> parseGroup(String input) {
		TokenGroupParser p = new TokenGroupParser(input);
		return p.group();
	}

	protected Maybe<String> butParensWs() {
		return consumeString(new Func1<Character, Boolean>() {
			public Boolean func(Character c) {
				return c != '(' && c != ')' && c != '{' && c != '}'
						&& !Character.isWhitespace(c);
			}
		});
	}

	protected Maybe<String> butBrackets() {
		return consumeString(new Func1<Character, Boolean>() {
			public Boolean func(Character c) {
				return c != '{' && c != '}';
			}
		});
	}

	protected Maybe<TokenGroup> group() {
		whitespace();
		return character('(').bind(new Func1<Character, Maybe<TokenGroup>>() {
			public Maybe<TokenGroup> func(Character c) {
				return butParensWs().bind(
						new Func1<String, Maybe<TokenGroup>>() {
							public Maybe<TokenGroup> func(final String s) {
								final ArrayList<TokenGroup> innerLit = many0(new Func0<Maybe<TokenGroup>>() {
									public Maybe<TokenGroup> func() {
										return literal();
									}
								});
								final ArrayList<TokenGroup> inner = many0(new Func0<Maybe<TokenGroup>>() {
									public Maybe<TokenGroup> func() {
										return group();
									}
								});
								return character(')')
										.bind(new Func1<Character, Maybe<TokenGroup>>() {
											public Maybe<TokenGroup> func(
													Character a) {
												TokenGroup[] g = new TokenGroup[inner
														.size()];
												inner.toArray(g);
												TokenGroup[] h = new TokenGroup[innerLit
														.size()];
												innerLit.toArray(h);
												TokenGroup[] i = new TokenGroup[inner
														.size()
														+ innerLit.size()];
												ArrayExtension.concat(g, h, i);
												if (i.length == 0) {
													return Maybe.<TokenGroup> Just(
															TokenGroup
																	.Single(s),
															"got one");
												} else {
													return Maybe.<TokenGroup> Just(
															TokenGroup.Group(s,
																	i),
															"got group");
												}
											}
										}, "expected closing parenthesis of a token group");
							}
						});
			}
		}, "expected opening parenthesis of a token group");
	}

	protected Maybe<TokenGroup> literal() {
		whitespace();
		return character('{').bind(new Func1<Character, Maybe<TokenGroup>>() {
			public Maybe<TokenGroup> func(Character c) {
				return butBrackets().bind(
						new Func1<String, Maybe<TokenGroup>>() {
							public Maybe<TokenGroup> func(final String s) {
								return character('}')
										.bind(new Func1<Character, Maybe<TokenGroup>>() {
											public Maybe<TokenGroup> func(
													Character a) {
												return Maybe.<TokenGroup> Just(
														TokenGroup.Single(s),
														"got literal");
											}
										}, "expected closing bracket of a literal");
							}
						});
			}
		}, "expected opening brackets of a literal");
	}
}
