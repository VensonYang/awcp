package BP.En;

import BP.Tools.StringHelper;

/** 
 树实体
 
*/
public abstract class EntityTree extends Entity
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final boolean getIsRoot()
	{
		if (this.getParentNo().equals("-1") || this.getParentNo().equals("0"))
		{
			return true;
		}

		if (this.getNo().equals(this.getParentNo()))
		{
			return true;
		}

		return false;
	}
	/** 
	 唯一标示
	 
	*/
	public final String getNo()
	{
		return this.GetValStringByKey(EntityTreeAttr.No);
	}
	public final void setNo(String value)
	{
		this.SetValByKey(EntityTreeAttr.No, value);
	}
	/** 
	 树结构编号
	 
	*/
	public final String getTreeNo()
	{
		return this.GetValStringByKey(EntityTreeAttr.TreeNo);
	}
	public final void setTreeNo(String value)
	{
		this.SetValByKey(EntityTreeAttr.TreeNo, value);
	}
	/** 
	 名称
	 
	*/
	public final String getName()
	{
		return this.GetValStringByKey(EntityTreeAttr.Name);
	}
	public final void setName(String value)
	{
		this.SetValByKey(EntityTreeAttr.Name, value);
	}
	/** 
	 父节点编号
	 
	*/
	public String getParentNo()
	{
		return this.GetValStringByKey(EntityTreeAttr.ParentNo);
	}
	public void setParentNo(String value)
	{
		this.SetValByKey(EntityTreeAttr.ParentNo, value);
	}
	/** 
	 图标
	 
	*/
	public final String getICON()
	{
		return this.GetValStringByKey(EntityTreeAttr.ICON);
	}
	public final void setICON(String value)
	{
		this.SetValByKey(EntityTreeAttr.ICON, value);
	}
	/** 
	 是否是目录
	 
	*/
	public final boolean getIsDir()
	{
		return this.GetValBooleanByKey(EntityTreeAttr.IsDir);
	}
	public final void setIsDir(boolean value)
	{
		this.SetValByKey(EntityTreeAttr.IsDir, value);
	}
	/** 
	 顺序号
	 
	*/
	public final int getIdx()
	{
		return this.GetValIntByKey(EntityTreeAttr.Idx);
	}
	public final void setIdx(int value)
	{
		this.SetValByKey(EntityTreeAttr.Idx, value);
	}
	/** 
	 级别
	 
	*/
	public final int getGrade()
	{
		return this.getTreeNo().length() / 2;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造函数
	/** 
	 主键
	 
	*/
	@Override
	public String getPK()
	{
		return EntityTreeAttr.No;
	}
	/** 
	 树结构编号
	 
	*/
	public EntityTree()
	{
	}
	/** 
	 树结构编号
	 
	 @param no 编号
	*/
	public EntityTree(String no)
	{
		if (StringHelper.isNullOrEmpty(no))
		{
			throw new RuntimeException(this.getEnDesc() + "@对表[" + this.getEnDesc() + "]进行查询前必须指定编号。");
		}

		this.setNo(no);
		if (this.Retrieve() == 0)
		{
			throw new RuntimeException("@没有" + this.get_enMap().getPhysicsTable() + ", No = " + this.getNo() + "的记录。");
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 业务逻辑处理
	/** 
	 重新设置treeNo
	 
	*/
	public final void ResetTreeNo()
	{
	}
	/** 
	 检查名称的问题.
	 
	 @return 
	*/
	@Override
	protected boolean beforeInsert()
	{
		if (!this.getEnMap().getIsAllowRepeatName())
		{
			if (this.getPKCount() == 1)
			{
				try {
					if (this.ExitsValueNum("Name", this.getName()) >= 1)
					{
						throw new RuntimeException("@插入失败[" + this.getEnMap().getEnDesc() + "] 编号[" + this.getNo() + "]名称[" + getName() + "]重复.");
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (StringHelper.isNullOrEmpty(this.getNo()))
		{
			try {
				this.setNo(this.GenerNewNoByKey("No"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.beforeInsert();
	}
	@Override
	protected boolean beforeUpdate()
	{
		if (!this.getEnMap().getIsAllowRepeatName())
		{
			if (this.getPKCount() == 1)
			{
				try {
					if (this.ExitsValueNum("Name", this.getName()) >= 2)
					{
						throw new RuntimeException("@更新失败[" + this.getEnMap().getEnDesc() + "] 编号[" + this.getNo() + "]名称[" + getName() + "]重复.");
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return super.beforeUpdate();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 可让子类调用的方法
	/** 
	 新建同级节点
	 
	 @return 
	*/
	public final EntityTree DoCreateSameLevelNode()
	{
		Entity tempVar = this.CreateInstance();
		EntityTree en = (EntityTree)((tempVar instanceof EntityTree) ? tempVar : null);
		try {
			en.setNo((new Long(BP.DA.DBAccess.GenerOID(this.toString()))).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // en.GenerNewNoByKey(EntityTreeAttr.No);
		en.setName("新建节点" + en.getNo());
		en.setParentNo(this.getParentNo());
		en.setIsDir(false);
		// en.TreeNo=this.GenerNewNoByKey(EntityTreeAttr.TreeNo,EntityTreeAttr.ParentNo,this.ParentNo)
		try {
			en.setTreeNo(this.GenerNewNoByKey(EntityTreeAttr.TreeNo, EntityTreeAttr.ParentNo, this.getParentNo()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		en.Insert();
		return en;
	}
	/** 
	 新建子节点
	 
	 @return 
	*/
	public final EntityTree DoCreateSubNode()
	{
		Entity tempVar = this.CreateInstance();
		EntityTree en = (EntityTree)((tempVar instanceof EntityTree) ? tempVar : null);
		try {
			en.setNo((new Long(BP.DA.DBAccess.GenerOID(this.toString()))).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // en.GenerNewNoByKey(EntityTreeAttr.No);
		en.setName("新建节点" + en.getNo());
		en.setParentNo(this.getNo());
		en.setIsDir(false);
		try {
			en.setTreeNo(this.GenerNewNoByKey(EntityTreeAttr.TreeNo, EntityTreeAttr.ParentNo, this.getNo()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (en.getTreeNo().substring(en.getTreeNo().length() - 2).equals("01"))
		{
			en.setTreeNo(this.getTreeNo() + "01");
		}
		en.Insert();

		// 设置此节点是目录
		if (!this.getIsDir())
		{
			this.Retrieve();
			this.setIsDir(true);
			this.Update();
		}
		return en;
	}
	/** 
	 上移
	 
	 @return 
	*/
	public final String DoUp()
	{
		try {
			this.DoOrderUp(EntityTreeAttr.ParentNo, this.getParentNo(), EntityTreeAttr.Idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/** 
	 下移
	 
	 @return 
	*/
	public final String DoDown()
	{
		try {
			this.DoOrderDown(EntityTreeAttr.ParentNo, this.getParentNo(), EntityTreeAttr.Idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}