package BP.CN;

import BP.En.Entities;
import BP.En.Entity;

public class JieDManagers extends Entities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Entity getGetNewEntity() {
		// TODO Auto-generated method stub
		return new JieDManager();
	}
	
}
