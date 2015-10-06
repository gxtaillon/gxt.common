package gxt.common;

public interface Functor<Ta> {
	public <Tb> Functor<Tb> fmap(Func1<Ta, Tb> f);
}
