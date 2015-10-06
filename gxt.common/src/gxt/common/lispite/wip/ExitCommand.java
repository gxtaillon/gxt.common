package gxt.common.lispite.wip;

import gxt.common.Maybe;
import gxt.common.lispite.Command;

public class ExitCommand implements Command {
	public Maybe<Object> func() {
		System.exit(0);
		return Maybe.<Object> Just(this, "got this");
	}

}
