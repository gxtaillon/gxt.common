package gxt.common.lispite;

import gxt.common.Func1;
import gxt.common.Maybe;
import gxt.common.extension.HashMapExtension;

import java.util.HashMap;

public class InputDispatcher {
	HashMap<String, CommandFactory> facs;

	public InputDispatcher() {
		facs = new HashMap<String, CommandFactory>();
	}

	public void addFactory(String name, CommandFactory cmd) {
		facs.put(name, cmd);
	}

	public Maybe<Command> dispatch(String input) {
		return TokenGroupParser.parseGroup(input).bind(
				new Func1<TokenGroup, Maybe<Command>>() {
					public Maybe<Command> func(final TokenGroup group) {
						return HashMapExtension.get(facs, group.name).bind(
								new Func1<CommandFactory, Maybe<Command>>() {
									public Maybe<Command> func(
											CommandFactory fac) {
										return fac.make(group);
									}
								}, "command `" + group.name + "` not found");
					}
				});
	}
}
