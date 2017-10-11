package BP.CN;

import BP.En.Entities;
import BP.En.Entity;

public class TXManagers extends Entities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Entity getGetNewEntity() {
		return new TXManager();
	}
	
}
