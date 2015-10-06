package gxt.common.lispite.wip;

import gxt.common.Maybe;
import gxt.common.lispite.Command;
import gxt.common.lispite.CommandFactory;
import gxt.common.lispite.TokenGroup;

public class ExitCommandFactory implements CommandFactory {
	@Override
	public Maybe<Command> make(TokenGroup group) {
		if (group.getGroup().length == 0) {
			return Maybe.<Command> Just(new ExitCommand(), "made exit command");
		} else {
			return Maybe
					.<Command> Nothing("exit command does not have any argument");
		}
	}
}
