package gxt.common.lispite.wip;

import gxt.common.Maybe;
import gxt.common.lispite.Command;
import gxt.common.lispite.CommandFactory;
import gxt.common.lispite.TokenGroup;

public class EchoCommandFactory implements CommandFactory {

	@Override
	public Maybe<Command> make(TokenGroup group) {
		if (group.getGroup().length == 1) {
			return Maybe.<Command> Just(
					new EchoCommand(group.getGroup()[0].getName()),
					"made echo command");
		} else {
			return Maybe
					.<Command> Nothing("echo command has only one argument");
		}
	}
}
