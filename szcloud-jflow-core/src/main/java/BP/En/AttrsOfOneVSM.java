package BP.En;

import java.util.ArrayList;

import BP.En.Entities;

/** 
 AttrsOfOneVSM 集合
 
*/
public class AttrsOfOneVSM extends ArrayList<AttrOfOneVSM>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttrsOfOneVSM()
	{
	}
	public final AttrOfOneVSM getItem(int index)
	{
		return (AttrOfOneVSM)this.get(index);
		/*
		 * warning return (AttrOfOneVSM)this.InnerList[index];*/
	}
	/** 
	 增加一个SearchKey .
	 
	 @param r SearchKey
	*/
	public final void Add(AttrOfOneVSM attr)
	{
		if (this.IsExits(attr))
		{
			return;
		}
		this.add(attr);
		/*
		 * warning this.InnerList.Add(attr);*/
	}

	/** 
	 是不是存在集合里面
	 
	 @param en 要检查的EnDtl
	 @return true/false
	*/
	public final boolean IsExits(AttrOfOneVSM en)
	{
		for (AttrOfOneVSM attr : this)
		{
			if (attr.getEnsOfMM() == en.getEnsOfMM())
			{
				return true;
			}
		}
		return false;
	}

	/** 
	 增加一个属性
	 
	 @param _ensOfMM 多对多的实体
	 @param _ensOfM 多实体
	 @param AttrOfOneInMM 点实体,在MM中的属性
	 @param AttrOfMInMM 多实体主键在MM中的属性
	 @param AttrOfMText
	 @param AttrOfMValue
	 @param desc 描述
	*/
	public final void Add(Entities _ensOfMM, Entities _ensOfM, String AttrOfOneInMM, String AttrOfMInMM, String AttrOfMText, String AttrOfMValue, String desc)
	{
		AttrOfOneVSM en = new AttrOfOneVSM(_ensOfMM, _ensOfM, AttrOfOneInMM, AttrOfMInMM, AttrOfMText, AttrOfMValue, desc);

		this.Add(en);
	}

}