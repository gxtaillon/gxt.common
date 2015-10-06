package gxt.common.lispite.wip;

import gxt.common.Maybe;
import gxt.common.lispite.Command;

public class EchoCommand implements Command {
	private String msg;

	public EchoCommand(String msg) {
		this.msg = msg;
	}

	public Maybe<Object> func() {
		System.out.println(msg);
		return Maybe.<Object> Just(this, "got this");
	}

}
