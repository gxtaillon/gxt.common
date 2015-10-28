package gxt.common.fp.wip;

import gxt.common.Func1;
import gxt.common.fp.Either;

public class CaseOf {

	public static void main(String[] args) {
		Either<Character, Integer> e = Either.<Character, Integer>Right(42, "test");
		Boolean result = e.caseOf(Either.<Character, Integer, Boolean>newCaseOf()
		  .caseRight(new Func1<Integer, Boolean>() {
			
			@Override
			public Boolean func(Integer a) {
				// TODO Auto-generated method stub
				return null;
			}
		}).caseLeft(new Func1<Character, Boolean>() {
			
			@Override
			public Boolean func(Character a) {
				// TODO Auto-generated method stub
				return null;
			}
		}));

	}

}
