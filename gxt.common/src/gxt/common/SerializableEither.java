package gxt.common;

import java.io.Serializable;

public class SerializableEither <L extends Serializable, R extends Serializable> 
		extends Either<L,R> implements Serializable {
	private static final long serialVersionUID = 1L;

	protected SerializableEither() { }
	
	L left_;
	R right_;
	Boolean isRight_;
	String why_;
	
	public static <L extends Serializable, R extends Serializable> 
			SerializableEither<L,R> fromEither(Either<L, R> ei) {
		SerializableEither<L, R> r = new SerializableEither<L, R>();
		r.isRight_ = ei.isRight;
		r.left_ = ei.left;
		r.right_ = ei.right;
		r.why_ = ei.why;
		return r;
	}
	
	public Either<L,R> updateToEither() {
		if (isRight == null) {
			isRight = isRight_;
			left = left_;
			right = right_;
			why = why_;
		}
		return this;
	}
}
