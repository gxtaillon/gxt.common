package gxt.common;

public interface MonadWhy<Ta, Tmw extends MonadWhyBase<Tmw> & MonadBase<Tmw>>
		extends Monad<Ta, Tmw>, Why {

	public <Tb, Tmb extends Monad<Tb, Tmw>> Tmb bind(Func1<Ta, Tmb> f,
			String newWhy);

}
