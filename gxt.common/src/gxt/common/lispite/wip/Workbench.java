package gxt.common.lispite.wip;

import java.util.Scanner;

import gxt.common.Func1;
import gxt.common.Maybe;
import gxt.common.Tup0;
import gxt.common.lispite.Command;
import gxt.common.lispite.InputDispatcher;

public class Workbench {

	public static void main(String[] args) {
		InputDispatcher id = new InputDispatcher();
		id.addFactory("exit", new ExitCommandFactory());
		id.addFactory("echo", new EchoCommandFactory());
		Maybe<Command> c;
		Scanner sc = new Scanner(System.in);
		try {
			do {
				System.out.print(" >");
				String line = sc.nextLine();
				c = id.dispatch(line);
				System.out.println(c.toString());
				c.fmap(new Func1<Command, Tup0>() {
					public Tup0 func(Command cmd) {
						System.out.println(cmd.func().toString());
						return Tup0.Tup();
					}
				});
			} while (true);
		} finally {
			sc.close();
			System.out.println("bye");
		}
	}

}
