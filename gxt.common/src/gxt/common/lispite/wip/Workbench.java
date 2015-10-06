package gxt.common.lispite.wip;

import gxt.common.Func1;
import gxt.common.Maybe;
import gxt.common.Tup0;
import gxt.common.lispite.Command;
import gxt.common.lispite.InputDispatcher;
import gxt.common.lispite.TokenGroup;
import gxt.common.lispite.TokenGroupParser;

public class Workbench {

	public static void main(String[] args) {
		Maybe<TokenGroup> t;
		t = TokenGroupParser.parseGroup("(asd (asdasd (askndsalkjdbki123)) (asd324))");
		System.out.println(t);
		InputDispatcher id = new InputDispatcher();
		id.addFactory("exit", new ExitCommandFactory());
		id.addFactory("exit2", new ExitCommandFactory());
		Maybe<Command> c;
		c = id.dispatch("(exit2)");
		System.out.println(c);
		c.fmap(new Func1<Command, Tup0>() {
			public Tup0 func(Command cmd) {
				cmd.func();
				return Tup0.Tup();
			}
		});

		System.out.println("bye");
	}

}
