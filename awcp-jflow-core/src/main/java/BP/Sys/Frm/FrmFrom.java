package BP.Sys.Frm;


public enum FrmFrom
{
	Flow,
	Node,
	Dtl;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FrmFrom forValue(int value)
	{
		return values()[value];
	}
}