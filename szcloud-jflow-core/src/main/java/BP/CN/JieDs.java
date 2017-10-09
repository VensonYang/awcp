package BP.CN;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

public class JieDs extends EntitiesNoName{
	@Override
	public Entity getGetNewEntity()
	{
		return new JieD();
	}
	
	public static ArrayList<SF> convertSFs(Object obj) {
		return (ArrayList<SF>) obj;
	}
	/** 
	 省份s
	 
	*/
	public JieDs()
	{
	}
}
