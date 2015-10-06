package gxt.common.lispite;

import gxt.common.Maybe;

public interface CommandFactory {
	public Maybe<Command> make(TokenGroup group);
}
