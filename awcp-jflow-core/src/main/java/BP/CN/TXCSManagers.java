package BP.CN;

import BP.En.Entities;
import BP.En.Entity;

public class TXCSManagers extends Entities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Entity getGetNewEntity() {
		return new TXCSManager();
	}

}
