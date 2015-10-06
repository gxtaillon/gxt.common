package gxt.common;

// This is better than allowing any type of Monad but it is not ideal as it allow any type of concrete base (ex: any Monad<*,EitherBase>).
public interface Monad<Ta, Tm extends MonadBase<Tm>> {
	public Monad<Ta, Tm> unit(Ta a); // static?

	public <Tb, Tmb extends Monad<Tb, Tm>> Tmb bind(Func1<Ta, Tmb> f);
}
