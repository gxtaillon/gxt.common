package gxt.common.lispite;

import gxt.common.Func1;
import gxt.common.Maybe;
import gxt.common.proxy.HashMapProxy;

import java.util.HashMap;

public class InputDispatcher {
	HashMapProxy<String, CommandFactory> facs;

	public InputDispatcher() {
		facs = HashMapProxy
				.<String, CommandFactory> Map(new HashMap<String, CommandFactory>());
	}

	public void addFactory(String name, CommandFactory cmd) {
		facs.put(name, cmd);
	}

	public Maybe<Command> dispatch(String input) {
		return TokenGroupParser.parseGroup(input).bind(
				new Func1<TokenGroup, Maybe<Command>>() {
					public Maybe<Command> func(final TokenGroup group) {
						return facs.get(group.name).bind(
								new Func1<CommandFactory, Maybe<Command>>() {
									public Maybe<Command> func(
											CommandFactory fac) {
										return fac.make(group);
									}
								});
					}
				});
	}
}
