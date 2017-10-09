//package BP.Web;
//
//import BP.En.*;
//import BP.DA.*;
//import BP.Sys.*;
//
///** 
// PageBase ��ժҪ˵����
// 
//*/
//public class PageBase extends System.Web.UI.Page
//{
//	/** 
//	 �رմ���
//	 
//	*/
//	protected final void WinCloseWithMsg(String mess)
//	{
//		//this.ResponseWriteRedMsg(mess);
//		//return;
//		mess = mess.replace("'", "��");
//
//		mess = mess.replace("\"", "��");
//
//		mess = mess.replace(";", "��");
//		mess = mess.replace(")", "��");
//		mess = mess.replace("(", "��");
//
//		mess = mess.replace(",", "��");
//		mess = mess.replace(":", "��");
//
//
//		mess = mess.replace("<", "��");
//		mess = mess.replace(">", "��");
//
//		mess = mess.replace("[", "��");
//		mess = mess.replace("]", "��");
//
//
//		mess = mess.replace("@", "\\n@");
//
//		mess = mess.replace("\r\n", "");
//
//		this.Response.Write("<script language='JavaScript'>alert('" + mess + "'); window.close()</script>");
//	}
//	public final String getRefEnKey()
//	{
//		String str = this.Request.QueryString["No"];
//		if (str == null)
//		{
//			str = this.Request.QueryString["OID"];
//		}
//
//		if (str == null)
//		{
//			str = this.Request.QueryString["MyPK"];
//		}
//
//		if (str == null)
//		{
//			str = this.Request.QueryString["PK"];
//		}
//
//
//		return str;
//	}
//	public final String getMyPK()
//	{
//		return this.Request.QueryString["MyPK"];
//	}
//	public final int getRefOID()
//	{
//		String s = this.Request.QueryString["RefOID"];
//		if (s == null)
//		{
//			s = this.Request.QueryString["OID"];
//		}
//		if (s == null)
//		{
//			return 0;
//		}
//		return Integer.parseInt(s);
//	}
//	public final String GenerTableStr(DataTable dt)
//	{
//		String str = "<Table id='tb' border=1 >";
//		// ����
//		str += "<TR>";
//		for (DataColumn dc : dt.Columns)
//		{
//			str += "<TD class='DGCellOfHeader" + BP.Web.WebUser.getStyle() + "' >" + dc.ColumnName + "</TD>";
//		}
//		str += "</TR>";
//
//		//����
//		for (DataRow dr : dt.Rows)
//		{
//			str += "<TR>";
//
//			for (DataColumn dc : dt.Columns)
//			{
//				str += "<TD >" + dr[dc.ColumnName] + "</TD>";
//			}
//			str += "</TR>";
//		}
//		str += "</Table>";
//		return str;
//	}
//	public final String GenerTablePage(DataTable dt, String title)
//	{
//		return PubClass.GenerTablePage(dt, title);
//	}
//	public final String GenerLabelStr(String title)
//	{
//		return PubClass.GenerLabelStr(title);
//		//return str;
//	}
//	public final Control GenerLabel(String title)
//	{
//		String path = this.Request.ApplicationPath;
//		String str = "";
//		str += "<TABLE style='font-size:14px' cellpadding='0' cellspacing='0' background='" + SystemConfig.getCCFlowWebPath() + "/WF/Img/DG_bgright.gif'>";
//		str += "<TR>";
//		str += "<TD>";
//		str += "<IMG src='" + SystemConfig.getCCFlowWebPath() + "/WF/Img/DG_Title_Left.gif' border='0' width='30' height='25'></TD>";
//
//		str += "<TD  valign=bottom noWrap background='" + SystemConfig.getCCFlowWebPath() + "/WF/Img/DG_Title_BG.gif'   height='25' border=0>&nbsp;";
//		str += " &nbsp;<b>" + title + "</b>&nbsp;&nbsp;";
//		str += "</TD>";
//		str += "<TD >";
//		str += "<IMG src='" + SystemConfig.getCCFlowWebPath() + "/WF/Img/DG_Title_Right.gif' border='0' width='25' height='25'></TD>";
//		str += "</TR>";
//		str += "</TABLE>";
//		return this.ParseControl(str);
//	}
//	public final Control GenerLabel_bak(String title)
//	{
//		// return this.ParseControl(title);
//
//		String path = SystemConfig.getCCFlowWebPath(); //this.Request.ApplicationPath;
//		String str = "";
//
//		str += "<TABLE style='font-size:14px'  cellpadding='0' cellspacing='0' background='" + path + "/Images/DG_bgright.gif'>";
//		str += "<TBODY>";
//		str += "<TR>";
//		str += "<TD>";
//		str += "<IMG src='" + path + "/Images/DG_Title_Left.gif' border='0' width='30' height='20'></TD>";
//		str += "<TD  class=TD  vAlign='center' noWrap background='" + path + "/Images/DG_Title_BG.gif'>&nbsp;";
//		str += " &nbsp;" + title + "&nbsp;&nbsp;";
//		str += "</TD>";
//		str += "<TD>";
//		str += "<IMG src='" + path + "/WF/Img/DG_Title_Right.gif' border='0' width='25' height='20'></TD>";
//		str += "</TR>";
//		str += "</TBODY>";
//		str += "</TABLE>";
//		return this.ParseControl(str);
//		//return str;
//	}
//	public final void GenerLabel(Label lab, Entity en, String msg)
//	{
//		lab.Controls.Clear();
//		lab.Controls.Add(this.GenerLabel("<img src='" + en.getEnMap().Icon + "' border=0 />" + msg));
//	}
//	public final void GenerLabel(Label lab, String msg)
//	{
//		lab.Controls.Clear();
//		lab.Controls.Add(this.GenerLabel(msg));
//	}
//	//public void GenerLabel(Label lab, Entity en)
//	//{
//	//    this.GenerLabel(lab, en.EnDesc + en.EnMap.TitleExt);
//	//    return;
//
//	//    lab.Controls.Clear();
//	//    if (en.EnMap.Icon == null)
//	//        lab.Controls.Add(this.GenerLabel(en.EnMap.EnDesc));
//	//    else
//	//        lab.Controls.Add(this.GenerLabel("<img src='" + en.EnMap.Icon + "' border=0 />" + en.EnMap.EnDesc + en.EnMap.TitleExt));
//	//}
//	public final String GenerCaption(String title)
//	{
//		if (BP.Web.WebUser.getStyle().equals("2"))
//		{
//			return "<div class=Table_Title ><span>" + title + "</span></div>";
//		}
//
//		return "<b>" + title + "</b>";
//	}
//	@Override
//	protected void OnLoad(EventArgs e)
//	{
//		//if (Web.WebUser.No == null)
//		//    this.ToSignInPage();
//		super.OnLoad(e);
//	}
//	/** 
//	 ������һ��excel,�ļ����ڣ���ݵ��롣
//	 
//	 @param attr
//	 @param sheetName
//	 @return 
//	*/
//	protected final void ExportEnToExcelModel_OpenWin(Attrs attrs, String sheetName)
//	{
//		String filename = sheetName + ".xls";
//		String file = filename;
//		//SystemConfig.PathOfTemp
//		String filepath = SystemConfig.getCCFlowWebPath() + "\\Temp\\";
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region �����������
//		//����Ŀ¼û�н���������.
//		if (!Directory.Exists(filepath) )
//		{
//			Directory.CreateDirectory(filepath);
//		}
//
//		filename = filepath + filename;
//		FileStream objFileStream = new FileStream(filename, FileMode.OpenOrCreate, FileAccess.Write);
//		StreamWriter objStreamWriter = new StreamWriter(objFileStream, System.Text.Encoding.Unicode);
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region ��ɵ����ļ�
//		String strLine = "";
//		for (Attr attr : attrs)
//		{
//			strLine += attr.getDesc() + Convert.ToChar(9);
//		}
//
//		objStreamWriter.WriteLine(strLine);
//		objStreamWriter.Close();
//		objFileStream.Close();
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
//		//this.WinOpen(Request.ApplicationPath+"/Temp/" + file,"sss", 500,800);
//
//		this.Write_Javascript(" window.open('" + SystemConfig.getCCFlowWebPath() + "/Temp/" + file + "'); ");
//	}
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region �û��ķ���Ȩ��
//	/** 
//	 ˭��ʹ�����ҳ��,���Ǳ����ɵ��ִ���
//	 such as ,admin,jww,002, 
//	 if return value is null, It's mean all emps can visit it . 
//	 
//	*/
//	protected String WhoCanUseIt()
//	{
//		return null;
//	}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
//	private void RP(String msg)
//	{
//		this.Response.Write(msg);
//	}
//	private void RPBR(String msg)
//	{
//		this.Response.Write(msg + "<br>");
//	}
//	public final void TableShow(DataTable dt, String title)
//	{
//
//		this.RPBR(title);
//		this.RPBR("<table border='1' width='100%'>");
//
//	}
//	public final String GenerCreateTableSQL(String className)
//	{
//		java.util.ArrayList als = ClassFactory.GetObjects(className);
//		int u = 0;
//		String sql = "";
//		for (Object obj : als)
//		{
//			u++;
//			try
//			{
//				Entity en = (Entity)obj;
//				switch (en.getEnMap().getEnDBUrl().getDBType())
//				{
//					case Oracle:
//						sql += SqlBuilder.GenerCreateTableSQLOfOra_OK(en) + " \n GO \n";
//						break;
//					case Informix:
//						sql += SqlBuilder.GenerCreateTableSQLOfInfoMix(en) + " \n GO \n";
//						break;
//					default:
//						sql += SqlBuilder.GenerCreateTableSQLOfMS(en) + "\n GO \n";
//						break;
//				}
//			}
//			catch (java.lang.Exception e)
//			{
//				continue;
//			}
//			//Map map=en.EnMap;
//			//objStreamWriter.WriteLine(Convert.ToChar(9)+"No:"+u.ToString()+Convert.ToChar(9) +map.EnDesc +Convert.ToChar(9) +map.PhysicsTable+Convert.ToChar(9) +map.EnType);
//		}
//		Log.DefaultLogWriteLineInfo(sql);
//		return sql;
//	}
//
//
//	public final void ExportEntityToExcel(String classbaseName)
//	{
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region �ļ�
//		String filename = "DatabaseDesign.xls";
//		String file = filename;
//		//bool flag = true;
//		String filepath = Request.PhysicalApplicationPath + "\\Temp\\";
//
//		//����Ŀ¼û�н���������.
//		if (!Directory.Exists(filepath) )
//		{
//			Directory.CreateDirectory(filepath);
//		}
//
//		filename = filepath + filename;
//		FileStream objFileStream = new FileStream(filename, FileMode.OpenOrCreate, FileAccess.Write);
//		StreamWriter objStreamWriter = new StreamWriter(objFileStream, System.Text.Encoding.Unicode);
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
//		//string str="";
//		java.util.ArrayList als = ClassFactory.GetObjects(classbaseName);
//		int i = 0;
//		objStreamWriter.WriteLine();
//		objStreamWriter.WriteLine(Convert.ToChar(9) + "ϵͳʵ��[" + classbaseName + "]" + Convert.ToChar(9));
//		objStreamWriter.WriteLine();
//		//objStreamWriter.WriteLine(Convert.ToChar(9)+"��лʹ��ϵͳʵ��ṹ�Զ������"+Convert.ToChar(9)+"��������"+Convert.ToChar(9)+DateTime.Now.ToString("yyyy��MM��dd��"));
//		objStreamWriter.WriteLine(Convert.ToChar(9) + "��" + classbaseName + "�̳�������ʵ����[" + als.size() + "]��");
//
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region ����Ŀ¼
//		objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "ϵͳʵ��Ŀ¼");
//		objStreamWriter.WriteLine(Convert.ToChar(9) + "���" + Convert.ToChar(9) + "ʵ�����" + Convert.ToChar(9) + "�����/��ͼ" + Convert.ToChar(9) + "����");
//		int u = 0;
//		for (Object obj : als)
//		{
//			try
//			{
//				u++;
//				Entity en = (Entity)obj;
//				Map map = en.getEnMap();
//				objStreamWriter.WriteLine(Convert.ToChar(9) + "No:" + (new Integer(u)).toString() + Convert.ToChar(9) + map.getEnDesc() + Convert.ToChar(9) + map.getPhysicsTable() + Convert.ToChar(9) + map.getEnType());
//			}
//			catch (java.lang.Exception e)
//			{
//			}
//		}
//		objStreamWriter.WriteLine();
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
//		for (Object obj : als)
//		{
//			try
//			{
//
//				i++;
//				Entity en = (Entity)obj;
//				Map map = en.getEnMap();
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//					///#region ��ɵ����ļ�
//				objStreamWriter.WriteLine("���" + i);
//				objStreamWriter.WriteLine(Convert.ToChar(9) + "ʵ�����" + Convert.ToChar(9) + map.getEnDesc() + Convert.ToChar(9) + "�����/��ͼ" + Convert.ToChar(9) + map.getPhysicsTable() + Convert.ToChar(9) + "ʵ������" + Convert.ToChar(9) + map.getEnType());
//				if (map.getCodeStruct() == null)
//				{
//					objStreamWriter.WriteLine(Convert.ToChar(9) + "����ṹ��Ϣ:��");
//				}
//				else
//				{
//					objStreamWriter.WriteLine(Convert.ToChar(9) + "����ṹ" + Convert.ToChar(9) + map.getCodeStruct() + "�Ƿ����ŵĳ���" + Convert.ToChar(9) + map.getIsCheckNoLength());
//				}
//				//objStreamWriter.WriteLine(Convert.ToChar(9)+"������λ��"+map.EnDBUrl+Convert.ToChar(9)+"ʵ���ڴ���λ��"+Convert.ToChar(9)+map.DepositaryOfEntity+Convert.ToChar(9)+"Map �ڴ���λ��"+Convert.ToChar(9)+map.DepositaryOfMap);
//				objStreamWriter.WriteLine(Convert.ToChar(9) + "������λ��" + map.getEnDBUrl() + Convert.ToChar(9) + "Map �ڴ���λ��" + Convert.ToChar(9) + map.getDepositaryOfMap());
//				objStreamWriter.WriteLine(Convert.ToChar(9) + "����Ȩ��" + Convert.ToChar(9) + "�Ƿ�鿴" + en.getHisUAC().IsView + Convert.ToChar(9) + "�Ƿ��½�" + en.getHisUAC().IsInsert + Convert.ToChar(9) + "�Ƿ�ɾ��" + en.getHisUAC().IsDelete + "�Ƿ����" + en.getHisUAC().IsUpdate + Convert.ToChar(9) + "�Ƿ񸽼�" + en.getHisUAC().IsAdjunct);
//				if (map.getDtls().size() > 0)
//				{
//					// output dtls 
//					EnDtls dtls = map.getDtls();
//					objStreamWriter.WriteLine(Convert.ToChar(9) + "��ϸ/�ӱ���Ϣ:����" + dtls.size());
//					int ii = 0;
//					for (EnDtl dtl : dtls)
//					{
//						ii++;
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "���:" + ii + "����:" + dtl.getDesc() + "��ϵ����ʵ����" + dtl.getEnsName() + "���" + dtl.getRefKey());
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "�����:" + dtl.getEns().getGetNewEntity().getEnMap().getPhysicsTable());
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "��ע:����" + dtl.getDesc() + "����ϸ����Ϣ,��ο�" + dtl.getEnsName());
//					}
//				}
//				else
//				{
//					objStreamWriter.WriteLine(Convert.ToChar(9) + "��ϸ/�ӱ���Ϣ:��");
//				}
//
//				if (map.getAttrsOfOneVSM().size() > 0)
//				{
//					// output dtls 
//					AttrsOfOneVSM dtls = map.getAttrsOfOneVSM();
//					objStreamWriter.WriteLine(Convert.ToChar(9) + "��Զ��ϵ:����" + dtls.size());
//					int ii = 0;
//					for (AttrOfOneVSM dtl : dtls)
//					{
//						ii++;
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "���:" + ii + "����:" + dtl.getDesc());
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "��Զ�ʵ����" + dtl.getEnsOfMM().toString() + "���" + dtl.getAttrOfOneInMM());
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "��ʵ������������" + dtl.getAttrOfOneInMM());
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "��ʵ����" + dtl.getEnsOfMM().toString() + "���" + dtl.getAttrOfMValue());
//					}
//				}
//				else
//				{
//					objStreamWriter.WriteLine(Convert.ToChar(9) + "��Զ��ϵ:��");
//				}
//
//				objStreamWriter.WriteLine(Convert.ToChar(9) + "��/��ͼ�ṹ");
//				int iii = 0;
//				objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + "�������" + Convert.ToChar(9) + "��������" + Convert.ToChar(9) + "����" + Convert.ToChar(9) + "�����ֶ�" + Convert.ToChar(9) + "�������" + Convert.ToChar(9) + "Ĭ��ֵ" + Convert.ToChar(9) + "��ϵ����" + Convert.ToChar(9) + "��ע");
//
//				for (Attr attr : map.getAttrs())
//				{
//					iii++;
//					if (attr.getMyFieldType() == FieldType.Enum)
//					{
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + iii + Convert.ToChar(9) + attr.getDesc() + Convert.ToChar(9) + attr.getKey() + Convert.ToChar(9) + attr.getField() + Convert.ToChar(9) + attr.getMyDataTypeStr() + Convert.ToChar(9) + attr.getDefaultVal() + Convert.ToChar(9) + "ö��" + Convert.ToChar(9) + "ö��Key" + attr.getUIBindKey() + " ����ö�ٵ���Ϣ�뵽Sys_Enum�����ҵ�����ϸ����Ϣ.");
//						continue;
//					}
//					if (attr.getMyFieldType() == FieldType.PKEnum)
//					{
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + iii + Convert.ToChar(9) + attr.getDesc() + Convert.ToChar(9) + attr.getKey() + Convert.ToChar(9) + attr.getField() + Convert.ToChar(9) + attr.getMyDataTypeStr() + Convert.ToChar(9) + attr.getDefaultVal() + Convert.ToChar(9) + "����ö��" + Convert.ToChar(9) + "ö��Key" + attr.getUIBindKey() + " ����ö�ٵ���Ϣ�뵽Sys_Enum�����ҵ�����ϸ����Ϣ.");
//
//						//objStreamWriter.WriteLine(Convert.ToChar(9)+" "+Convert.ToChar(9)+"No:"+iii+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Desc+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Key+Convert.ToChar(9)+"����Ĭ��ֵ"+Convert.ToChar(9)+attr.DefaultVal+Convert.ToChar(9)+"�����ֶ�"+Convert.ToChar(9)+attr.Field+Convert.ToChar(9)+"�ֶι�ϵ����"+Convert.ToChar(9)+"ö������"+Convert.ToChar(9)+"�ֶ�������� "+Convert.ToChar(9)+attr.MyDataTypeStr+"");
//						continue;
//					}
//					if (attr.getMyFieldType() == FieldType.FK)
//					{
//						Entity tmp = attr.getHisFKEn();
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + iii + Convert.ToChar(9) + attr.getDesc() + Convert.ToChar(9) + attr.getKey() + Convert.ToChar(9) + attr.getField() + Convert.ToChar(9) + attr.getMyDataTypeStr() + Convert.ToChar(9) + attr.getDefaultVal() + Convert.ToChar(9) + "���" + Convert.ToChar(9) + "������ʵ��:" + tmp.getEnDesc() + "�����:" + tmp.getEnMap().getPhysicsTable() + " ����" + tmp.getEnDesc() + "��Ϣ�뵽��ʵ����Ϣ����ȥ��.");
//
//						//objStreamWriter.WriteLine(Convert.ToChar(9)+" "+Convert.ToChar(9)+"No:"+iii+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Desc+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Key+Convert.ToChar(9)+"����Ĭ��ֵ"+Convert.ToChar(9)+attr.DefaultVal+Convert.ToChar(9)+"�����ֶ�"+Convert.ToChar(9)+attr.Field+Convert.ToChar(9)+"�ֶι�ϵ����"+Convert.ToChar(9)+"���"+Convert.ToChar(9)+"�ֶ�������� "+Convert.ToChar(9)+attr.MyDataTypeStr+""+"��ϵ����ʵ�����"+Convert.ToChar(9)+tmp.EnDesc+"�����"+Convert.ToChar(9)+tmp.EnMap.PhysicsTable+Convert.ToChar(9)+"����ϸ����Ϣ��ο�"+Convert.ToChar(9));
//						continue;
//					}
//					if (attr.getMyFieldType() == FieldType.PKFK)
//					{
//						Entity tmp = attr.getHisFKEn();
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + iii + Convert.ToChar(9) + attr.getDesc() + Convert.ToChar(9) + attr.getKey() + Convert.ToChar(9) + attr.getField() + Convert.ToChar(9) + attr.getMyDataTypeStr() + Convert.ToChar(9) + attr.getDefaultVal() + Convert.ToChar(9) + "������" + Convert.ToChar(9) + "������ʵ��:" + tmp.getEnDesc() + "�����:" + tmp.getEnMap().getPhysicsTable() + " ����" + tmp.getEnDesc() + "��Ϣ�뵽��ʵ����Ϣ����ȥ��.");
//						continue;
//					}
//
//					//��������.
//					if (attr.getMyFieldType() == FieldType.Normal || attr.getMyFieldType() == FieldType.PK)
//					{
//						objStreamWriter.WriteLine(Convert.ToChar(9) + " " + Convert.ToChar(9) + iii + Convert.ToChar(9) + attr.getDesc() + Convert.ToChar(9) + attr.getKey() + Convert.ToChar(9) + attr.getField() + Convert.ToChar(9) + attr.getMyDataTypeStr() + Convert.ToChar(9) + attr.getDefaultVal() + Convert.ToChar(9) + "��ͨ" + Convert.ToChar(9) + attr.getEnterDesc());
//						//objStreamWriter.WriteLine(Convert.ToChar(9)+" "+Convert.ToChar(9)+"No:"+iii+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Desc+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Key+Convert.ToChar(9)+"����Ĭ��ֵ"+Convert.ToChar(9)+attr.DefaultVal+Convert.ToChar(9)+"�����ֶ�"+Convert.ToChar(9)+attr.Field+Convert.ToChar(9)+"�ֶι�ϵ����"+Convert.ToChar(9)+"�ַ�"+Convert.ToChar(9)+"�ֶ��������"+Convert.ToChar(9)+attr.MyDataTypeStr+""+Convert.ToChar(9)+"����Ҫ��"+Convert.ToChar(9)+attr.EnterDesc);
//						continue;
//					}
//					//objStreamWriter.WriteLine("�������:"+iii+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Desc+Convert.ToChar(9)+"����"+Convert.ToChar(9)+attr.Key+Convert.ToChar(9)+"����Ĭ��ֵ"+Convert.ToChar(9)+attr.DefaultVal+Convert.ToChar(9)+"�����ֶ�"+Convert.ToChar(9)+attr.Field+"�ֶι�ϵ����"+Convert.ToChar(9)+"�ַ�"+Convert.ToChar(9)+"�ֶ��������"+Convert.ToChar(9)+attr.MyDataTypeStr+Convert.ToChar(9)+""+"����Ҫ��"+Convert.ToChar(9)+attr.EnterDesc+Convert.ToChar(9));
//				}
//			}
//			catch (java.lang.Exception e2)
//			{
//			}
//		}
//		objStreamWriter.WriteLine();
//		objStreamWriter.WriteLine(Convert.ToChar(9) + Convert.ToChar(9) + " " + Convert.ToChar(9) + Convert.ToChar(9) + " �Ʊ��ˣ�" + Convert.ToChar(9) + WebUser.getName() + Convert.ToChar(9) + "���ڣ�" + Convert.ToChar(9) + new java.util.Date().ToShortDateString());
//
//		objStreamWriter.Close();
//		objFileStream.Close();
//
//		this.Write_Javascript(" window.open('" + SystemConfig.getCCFlowWebPath() + "/Temp/" + file + "'); ");
//
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//					///#endregion
//
//
//
//	}
//	public final void Helper(String htmlFile)
//	{
//		this.WinOpen(htmlFile);
//	}
//
//	public final void Helper()
//	{
//		this.WinOpen(SystemConfig.getCCFlowWebPath() + "/" + SystemConfig.getAppSettings()["PageOfHelper"]);
//	}
//	/** 
//	 ȡ������by key.
//	 
//	 @param key
//	 @return 
//	*/
//	public final String GetRequestStrByKey(String key)
//	{
//		return this.Request.QueryString[key];
//	}
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region ��������
//	/** 
//	 showmodaldialog 
//	 
//	 @param url
//	 @param title
//	 @param Height
//	 @param Width
//	*/
//	protected final void ShowModalDialog(String url, String title, int Height, int Width)
//	{
//		String script = "<script language='JavaScript'>window.showModalDialog('" + url + "','','dialogHeight: " + (new Integer(Height)).toString() + "px; dialogWidth: " + (new Integer(Width)).toString() + "px; dialogTop: 100px; dialogLeft: 100px; center: no; help: no'); </script> ";
//
//		//this.RegisterStartupScript("key1s",script); // old .
//		ClientScript.RegisterStartupScript(this.getClass(), "K1", script); // new
//
//		//this.Response.Write( script );
//		//this.RegisterClientScriptBlock("Dia",script);
//	}
//	/** 
//	 �رմ���
//	 
//	*/
//	protected final void WinClose()
//	{
//		this.Response.Write("<script language='JavaScript'> window.close();</script>");
//	}
//	protected final void WinClose(String val)
//	{
//		//�����Թȸ�,IE����window.top.returnValue ����
//		String clientscript = "<script language='javascript'> if(window.opener != undefined){window.top.returnValue = '" + val + "';} else { window.returnValue = '" + val + "';} window.close(); </script>";
//		//string clientscript = "<script language='javascript'>  window.returnValue = '" + val + "'; window.close(); </script>";
//		this.Page.Response.Write(clientscript);
//	}
//	/** 
//	 ��һ���µĴ���
//	 
//	 @param msg
//	*/
//	protected final void WinOpen(String url)
//	{
//		this.WinOpen(url, "", "msg", 900, 500);
//	}
//	protected final String dealUrl(String url)
//	{
//		if (url.indexOf("?") == -1)
//		{
//			//url=url.Substring(0,url.IndexOf("",""));
//			return url;
//		}
//		else
//		{
//			return url;
//		}
//	}
//	protected final void WinOpen(String url, String title, String winName, int width, int height)
//	{
//		this.WinOpen(url, title, winName, width, height, 0, 0);
//	}
//	protected final void WinOpen(String url, String title, int width, int height)
//	{
//		this.WinOpen(url, title, "ActivePage", width, height, 0, 0);
//	}
//	protected final void WinOpen(String url, String title, String winName, int width, int height, int top, int left)
//	{
//		WinOpen(url, title, winName, width, height, top, left, false, false);
//	}
//	protected final void WinOpen(String url, String title, String winName, int width, int height, int top, int left, boolean _isShowToolBar, boolean _isShowAddress)
//	{
//		url = url.replace("<", "[");
//		url = url.replace(">", "]");
//		url = url.trim();
//		title = title.replace("<", "[");
//		title = title.replace(">", "]");
//		title = title.replace("\"", "��");
//		String isShowAddress = "no", isShowToolBar = "no";
//		if (_isShowAddress)
//		{
//			isShowAddress = "yes";
//		}
//		if (_isShowToolBar)
//		{
//			isShowToolBar = "yes";
//		}
//		// this.Response.Write("<script language='JavaScript'> var newWindow =window.showModelessDialog('" + url + "','" + winName + "','width=" + width + "px,top=" + top + "px,left=" + left + "px,height=" + height + "px,scrollbars=yes,resizable=yes,toolbar=" + isShowToolBar + ",location=" + isShowAddress + "'); newWindow.focus(); </script> ");
//		this.Response.Write("<script language='JavaScript'> var newWindow =window.open('" + url + "','" + winName + "','width=" + width + "px,top=" + top + "px,left=" + left + "px,height=" + height + "px,scrollbars=yes,resizable=yes,toolbar=" + isShowToolBar + ",location=" + isShowAddress + "'); newWindow.focus(); </script> ");
//
//	}
//	//private int MsgFontSize=1;
//	/** 
//	 �����ҳ���Ϻ�ɫ�ľ��档
//	 
//	 @param msg ��Ϣ
//	*/
//	protected final void ResponseWriteRedMsg(String msg)
//	{
//		msg = msg.replace("@", "<BR>@");
//		System.Web.HttpContext.Current.Session["info"] = msg;
//		System.Web.HttpContext.Current.Application["info" + WebUser.getNo()] = msg;
//		String url = "" + SystemConfig.getCCFlowWebPath() + "/WF/Comm/Port/ErrorPage.jsp;
//		this.WinOpen(url, "����", "errmsg", 500, 400, 150, 270);
//	}
//	protected final void ResponseWriteShowModalDialogRedMsg(String msg)
//	{
//		msg = msg.replace("@", "<BR>@");
//		System.Web.HttpContext.Current.Session["info"] = msg;
//		//			string url="/WF/Comm/Port/ErrorPage..jsp;
//		String url = "" + SystemConfig.getCCFlowWebPath() + "/WF/Comm/Port/ErrorPage..jspd=" + new java.util.Date().toString();
//		this.WinOpenShowModalDialog(url, "����", "msg", 500, 400, 120, 270);
//	}
//	protected final void ResponseWriteShowModalDialogBlueMsg(String msg)
//	{
//		msg = msg.replace("@", "<BR>@");
//		System.Web.HttpContext.Current.Session["info"] = msg;
//		String url = "" + SystemConfig.getCCFlowWebPath() + "/WF/Comm/Port/InfoPage..jspd=" + new java.util.Date().toString();
//		this.WinOpenShowModalDialog(url, "��ʾ", "msg", 500, 400, 120, 270);
//	}
//
//	protected final void WinOpenShowModalDialog(String url, String title, String key, int width, int height, int top, int left)
//	{
//		//url=this.Request.ApplicationPath+"Comm/ShowModalDialog.htm?"+url;
//		//this.RegisterStartupScript(key,"<script language='JavaScript'>window.showModalDialog('"+url+"','"+key+"' ,'dialogHeight: 500px; dialogWidth:"+width+"px; dialogTop: "+top+"px; dialogLeft: "+left+"px; center: yes; help: no' ) ;  </script> ");
//
//		this.ClientScript.RegisterStartupScript(this.getClass(), key, "<script language='JavaScript'>window.showModalDialog('" + url + "','" + key + "' ,'dialogHeight: 500px; dialogWidth:" + width + "px; dialogTop: " + top + "px; dialogLeft: " + left + "px; center: yes; help: no' ) ;  </script> ");
//
//	}
//	protected final void WinOpenShowModalDialogResponse(String url, String title, String key, int width, int height, int top, int left)
//	{
//		url = this.Request.ApplicationPath + "Comm/ShowModalDialog.htm?" + url;
//		this.Response.Write("<script language='JavaScript'>window.showModalDialog('" + url + "','" + key + "' ,'dialogHeight: 500px; dialogWidth:" + width + "px; dialogTop: " + top + "px; dialogLeft: " + left + "px; center: yes; help: no' ) ;  </script> ");
//	}
//
//	protected final void ResponseWriteRedMsg(RuntimeException ex)
//	{
//		this.ResponseWriteRedMsg(ex.getMessage());
//	}
//	/** 
//	 �����ҳ������ɫ����Ϣ��
//	 
//	 @param msg ��Ϣ
//	*/
//	protected final void ResponseWriteBlueMsg(String msg)
//	{
//		msg = msg.replace("@", "<br>@");
//		System.Web.HttpContext.Current.Session["info"] = msg;
//		System.Web.HttpContext.Current.Application["info" + WebUser.getNo()] = msg;
//		String url = SystemConfig.getCCFlowWebPath() + "/WF/Comm/Port/InfoPage..jspd=" + new java.util.Date().toString();
//		//   string url = "/WF/Comm/Port/InfoPage..jspd=" + DateTime.Now.ToString();
//		this.WinOpen(url, "��Ϣ", "d" + this.Session.SessionID, 500, 300, 150, 270);
//	}
//
//	protected final void AlertHtmlMsg(String msg)
//	{
//		if (StringHelper.isNullOrEmpty(msg))
//		{
//			return;
//		}
//
//		msg = msg.replace("@", "<br>@");
//		System.Web.HttpContext.Current.Session["info"] = msg;
//		String url = "MsgPage..jspd=" + new java.util.Date().toString();
//		this.WinOpen(url, "��Ϣ", this.Session.SessionID, 500, 400, 150, 270);
//	}
//	/** 
//	 ����ɹ�
//	 
//	*/
//	protected final void ResponseWriteBlueMsg_SaveOK()
//	{
//		Alert("����ɹ���", false);
//	}
//	/** 
//	 ����ɹ�
//	 
//	 @param num ��¼����
//	*/
//	protected final void ResponseWriteBlueMsg_SaveOK(int num)
//	{
//		Alert("����[" + num + "]����¼����ɹ���", false);
//	}
//	/** 
//	 ResponseWriteBlueMsg_DeleteOK
//	 
//	*/
//	protected final void ResponseWriteBlueMsg_DeleteOK()
//	{
//
//		this.Alert("ɾ��ɹ���", false);
//		//
//		//���³ɹ�
//		//			//this.Alert("ɾ��ɹ�!");
//		//			ResponseWriteBlueMsg("ɾ��ɹ�!");
//	}
//	/** 
//	 "����["+delNum+"]����¼ɾ��ɹ���"
//	 
//	 @param delNum delNum
//	*/
//	protected final void ResponseWriteBlueMsg_DeleteOK(int delNum)
//	{
//		//this.Alert("ɾ��ɹ�!");
//		this.Alert("����[" + delNum + "]����¼ɾ��ɹ���", false);
//
//	}
//	/** 
//	 ResponseWriteBlueMsg_UpdataOK
//	 
//	*/
//	protected final void ResponseWriteBlueMsg_UpdataOK()
//	{
//		//this.ResponseWriteBlueMsg("���³ɹ�",false);
//		this.Alert("���³ɹ�!");
//		// ResponseWriteBlueMsg("���³ɹ�!");
//	}
//	protected final void ToSignInPage()
//	{
//		System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getPageOfLostSession());
//	}
//	protected final void ToWelPage()
//	{
//		System.Web.HttpContext.Current.Response.Redirect(BP.Sys.Glo.getRequest().ApplicationPath + "/Wel..jsp);
//	}
//	protected final void ToErrorPage(RuntimeException mess)
//	{
//		this.ToErrorPage(mess.getMessage());
//	}
//	/** 
//	 �л�����ϢҲ�档
//	 
//	 @param mess
//	*/
//	protected final void ToErrorPage(String mess)
//	{
//		System.Web.HttpContext.Current.Session["info"] = mess;
//		System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getCCFlowWebPath() + "/WF/Comm/Port/ToErrorPage..jspd=" + new java.util.Date().toString(), false);
//	}
//	/** 
//	 �л�����ϢҲ�档
//	 
//	 @param mess
//	*/
//	protected final void ToCommMsgPage(String mess)
//	{
//		mess = mess.replace("@", "<BR>@");
//		mess = mess.replace("~", "@");
//
//		System.Web.HttpContext.Current.Session["info"] = mess;
//		if (SystemConfig.getAppSettings()["PageMsg"] == null)
//		{
//			System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getCCFlowWebPath() + "Comm/Port/InfoPage..jspd=" + new java.util.Date().toString(), false);
//		}
//		else
//		{
//			System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getAppSettings()["PageMsg"] + "?d=" + new java.util.Date().toString(), false);
//		}
//	}
//	/** 
//	 �л�����ϢҲ�档
//	 
//	 @param mess
//	*/
//	protected final void ToWFMsgPage(String mess)
//	{
//		mess = mess.replace("@", "<BR>@");
//		mess = mess.replace("~", "@");
//
//		System.Web.HttpContext.Current.Session["info"] = mess;
//		if (SystemConfig.getAppSettings()["PageMsg"] == null)
//		{
//			System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getCCFlowWebPath() + "WF/Comm/Port/InfoPage..jspd=" + new java.util.Date().toString(), false);
//		}
//		else
//		{
//			System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getAppSettings()["PageMsg"] + "?d=" + new java.util.Date().toString(), false);
//		}
//	}
//	protected final void ToMsgPage_Do(String mess)
//	{
//		System.Web.HttpContext.Current.Session["info"] = mess;
//		System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getCCFlowWebPath() + "/Comm/Port/InfoPage..jspd=" + new java.util.Date().toString(), false);
//	}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
//	/** 
//	ת��һ��ҳ���ϡ� '_top'
//	 
//	 @param mess
//	 @param target '_top'
//	*/
//	protected final void ToErrorPage(String mess, String target)
//	{
//		System.Web.HttpContext.Current.Session["info"] = mess;
//		System.Web.HttpContext.Current.Response.Redirect(SystemConfig.getCCFlowWebPath() + "/WF/Comm/Port/InfoPage..jsptarget='_top'");
//	}
//
//	/** 
//	 ���ڵ�OnInit�¼����Զ���ҳ���ϼ�һ�¼�¼��ǰ�е�Hidden
//	 
//	 @param e
//	*/
//	@Override
//	protected void OnInit(EventArgs e)
//	{
//		//ShowRuning();
//		super.OnInit(e);
//
//		if (this.WhoCanUseIt() != null)
//		{
//			if (this.WhoCanUseIt().equals(WebUser.getNo()))
//			{
//				return;
//			}
//			if (this.WhoCanUseIt().indexOf("," + WebUser.getNo() + ",") == -1)
//			{
//				this.ToErrorPage("��û��Ȩ�޷������ҳ�档");
//			}
//		}
//
//
//
//
//	}
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region ��ؼ��й�ϵ�Ĳ���
//	public final void ShowDataTable(DataTable dt)
//	{
//		this.Response.Write(this.DataTable2Html(dt, true));
//	}
//	/** 
//	 ��ʾDataTable.
//	 
//	*/
//	public final String DataTable2Html(DataTable dt, boolean isShowTitle)
//	{
//		String str = "";
//		if (isShowTitle)
//		{
//			str = dt.TableName + " �ϼ�:" + dt.Rows.size() + "��¼.";
//		}
//		str += "<Table>";
//		str += "<TR>";
//		for (DataColumn dc : dt.Columns)
//		{
//			str += "  <TD warp=false >";
//			str += dc.ColumnName;
//			str += "  </TD>";
//		}
//		str += "</TR>";
//
//
//		for (DataRow dr : dt.Rows)
//		{
//			str += "<TR>";
//
//			for (DataColumn dc : dt.Columns)
//			{
//				str += "  <TD>";
//				str += dr[dc.ColumnName];
//				str += "  </TD>";
//			}
//			str += "</TR>";
//		}
//
//		str += "</Table>";
//		return str;
//
//		//this.ResponseWriteBlueMsg(str);
//
//
//	}
//	/** 
//	 ��ʾ����
//	 
//	*/
//	public final void ShowRuning()
//	{
//		//if (this.IsPostBack==false)
//		//	return ;		
//
//
//		String str = "<script language=javascript><!-- function showRuning() {	sending.style.visibility='visible' } --> </script>";
//
//
//		// if (!this.IsClientScriptBlockRegistered("ClientProxyScript"))
//		//   this.RegisterClientScriptBlock("ClientProxyScript", str);
//
//		if (!this.ClientScript.IsClientScriptBlockRegistered("ClientProxyScript"))
//		{
//			this.ClientScript.RegisterStartupScript(this.getClass(), "ClientProxyScript", str);
//		}
//
//		if (!this.IsPostBack )
//		{
//			str = "<div id='sending' style='position: absolute; top: 126; left: -25; z-index: 10; visibility: hidden; width: 903; height: 74'><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>ϵͳ������Ӧ�������, ���Ժ�...</td></tr></table></td><td width=30%></td></tr></table></div> ";
//			this.Response.Write(str);
//		}
//	}
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region ͼƬ����
//
//	/** 
//	 �Ƿ�Ҫ��鹦��
//	 
//	*/
//	protected final boolean getIsCheckFunc()
//	{
//			//if (this.SubPageMessage==null || this.SubPageTitle==null) 
//			//return false;
//
//		if (ViewState["IsCheckFunc"] != null)
//		{
//			return (boolean)ViewState["IsCheckFunc"];
//		}
//		else
//		{
//			return true;
//		}
//
//	}
//	protected final void setIsCheckFunc(boolean value)
//	{
//		ViewState["IsCheckFunc"] = value;
//	}
//
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region ����session ������
//
//	public static Object GetSessionObjByKey(String key)
//	{
//		Object val = System.Web.HttpContext.Current.Session[key];
//		return val;
//	}
//	public static String GetSessionByKey(String key)
//	{
//		return (String)GetSessionObjByKey(key);
//	}
//	/** 
//	 ȡ�����ַ��е� Key1:val1;Key2:val2;  ֵ. 
//	 
//	 @param key1
//	 @param key2
//	 @return 
//	*/
//	public static String GetSessionByKey(String key1, String key2)
//	{
//		String str = GetSessionByKey(key1);
//		if (str == null)
//		{
//			throw new RuntimeException("û��ȡ��" + key1 + "��ֵ.");
//		}
//
//		String[] strs = str.split("[;]", -1);
//		for (String s : strs)
//		{
//			String[] ss = s.split("[:]", -1);
//			if (key2.equals(ss[0]))
//			{
//				return ss[1];
//			}
//		}
//		return null;
//	}
//	public static void SetSessionByKey(String key, Object obj)
//	{
//		System.Web.HttpContext.Current.Session[key] = obj;
//	}
//	public static void SetSessionByKey(String key1, String key2, Object obj)
//	{
//		String str = GetSessionByKey(key1);
//		String KV = key2 + ":" + obj.toString() + ";";
//		if (str == null)
//		{
//			SetSessionByKey(key1, KV);
//			return;
//		}
//
//
//
//		String[] strs = str.split("[;]", -1);
//		for (String s : strs)
//		{
//			String[] ss = s.split("[:]", -1);
//			if (key2.equals(ss[0]))
//			{
//				SetSessionByKey(key1, str.replace(s + ";", KV));
//				return;
//			}
//		}
//
//		SetSessionByKey(key1, str + KV);
//	}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region ���� ViewState �Ĳ�����
//	/** 
//	 ���� ViewState Value
//	 
//	 @param key
//	 @param val
//	 @param DefaultVal
//	*/
//	public final void SetValueByKey(String key, Object val, Object DefaultVal)
//	{
//		if (val == null)
//		{
//			ViewState[key] = DefaultVal;
//		}
//		else
//		{
//			ViewState[key] = val;
//		}
//	}
//	public final void SetValueByKey(String key, Object val)
//	{
//		ViewState[key] = val;
//	}
//	/** 
//	 ȡ��Val
//	 
//	 @param key
//	 @return 
//	*/
//	public final String GetValueByKey(String key)
//	{
//		try
//		{
//			return ViewState[key].toString();
//		}
//		catch (java.lang.Exception e)
//		{
//			return null;
//		}
//	}
//	public final boolean GetValueByKeyBool(String key)
//	{
//		if (this.GetValueByKey(key).equals("1"))
//		{
//			return true;
//		}
//		return false;
//	}
//	/** 
//	 ss
//	 
//	 @param key ss
//	 @param DefaultVal ss
//	 @return 
//	*/
//	public final String GetValueByKey_del(String key, String DefaultVal)
//	{
//		try
//		{
//			return ViewState[key].toString();
//		}
//		catch (java.lang.Exception e)
//		{
//			return DefaultVal;
//		}
//	}
//	/** 
//	 ����key ȡ����,bool ��ֲ. 
//	 
//	 @param key
//	 @param DefaultValue
//	 @return 
//	*/
//	public final boolean GetBoolValusByKey_del(String key, boolean DefaultValue)
//	{
//		try
//		{
//			return Boolean.parseBoolean(this.GetValueByKey(key));
//		}
//		catch (java.lang.Exception e)
//		{
//			return DefaultValue;
//		}
//	}
//	/** 
//	 ȡ��int valus , ���û�оͷ��� DefaultValue ;
//	 
//	 @param key
//	 @return 
//	*/
//	public final int GetIntValueByKey_del(String key, int DefaultValue)
//	{
//		try
//		{
//			return Integer.parseInt(ViewState[key].toString());
//		}
//		catch (java.lang.Exception e)
//		{
//			return DefaultValue;
//		}
//	}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
//
//
//	/** 
//	 ���table ����������ҳ���ϵ�DataGride. 
//	 
//	*/
//	protected final System.Data.DataTable getTable()
//	{
//			//DataTable dt = (System.Data.DataTable)ViewState["Table"];
//		DataTable dt = (System.Data.DataTable)ViewState["Table"];
//		if (dt == null)
//		{
//			dt = new DataTable();
//		}
//		return dt;
//	}
//	protected final void setTable(System.Data.DataTable value)
//	{
//		ViewState["Table"] = value;
//	}
//	protected final System.Data.DataTable getTable_bak()
//	{
//			//DataTable dt = (System.Data.DataTable)ViewState["Table"];
//		DataTable dt = (DataTable)((this.Session["Table"] instanceof DataTable) ? this.Session["Table"] : null);
//		if (dt == null)
//		{
//			dt = new DataTable();
//		}
//		return dt;
//	}
//	protected final void setTable_bak(System.Data.DataTable value)
//	{
//		this.Session["Table"] = value;
//	}
//	protected final System.Data.DataTable getTable1()
//	{
//
//		DataTable dt = (System.Data.DataTable)ViewState["Table1"];
//		if (dt == null)
//		{
//			dt = new DataTable();
//		}
//		return dt;
//	}
//	protected final void setTable1(System.Data.DataTable value)
//	{
//		ViewState["Table1"] = value;
//	}
//	/** 
//	 Ӧ�ó�������
//	 
//	*/
//	protected final String getPK()
//	{
//		try
//		{
//			return ViewState["PK"].toString();
//		}
//		catch (java.lang.Exception e)
//		{
//			return null;
//		}
//	}
//	protected final void setPK(String value)
//	{
//		ViewState["PK"] = value;
//	}
//	/** 
//	 ��������״̬��
//	 
//	*/
//	protected final boolean getIsNew_del()
//	{
//		try
//		{
//			return (boolean)ViewState["IsNew"];
//		}
//		catch (java.lang.Exception e)
//		{
//			return false;
//		}
//	}
//	protected final void setIsNew_del(boolean value)
//	{
//		ViewState["IsNew"] = value;
//	}
//	/** 
//	 PKOID if is null return 0 
//	 
//	*/
//	protected final int getPKint()
//	{
//		try
//		{
//			return Integer.parseInt(ViewState["PKint"].toString());
//		}
//		catch (java.lang.Exception e)
//		{
//			return 0;
//		}
//	}
//	protected final void setPKint(int value)
//	{
//		ViewState["PKint"] = value;
//	}
//	//		protected void ShowMessage(string msg)
//	//		{
//	//			PubClass.ShowMessage(msg);
//	//		}		
//	//		protected void ShowMessage_SaveOK()
//	//		{
//	//			PubClass.ShowMessageMSG_SaveOK();
//	//		}
//	protected final void ShowMessage_SaveUnsuccessful()
//	{
//		//PubClass.ShowMessage(msg);
//	}
//
//	//		protected void ShowMessage_UpdateSuccessful()
//	//		{
//	//			PubClass.ShowMessage("���³ɹ���");
//	//		}
//	protected final void ShowMessage_UpdateUnsuccessful()
//	{
//		//PubClass.ShowMessage(msg);
//	}
//	protected final void Write_Javascript(String script)
//	{
//		script = script.replace("<", "[");
//		script = script.replace(">", "]");
//		Response.Write("<script language=javascript> " + script + " </script>");
//	}
//	protected final void ShowMessageWin(String url)
//	{
//		this.Response.Write("<script language='JavaScript'> window.open('" + url + "')</script>");
//	}
//	protected final void Alert(String mess)
//	{
//		if (StringHelper.isNullOrEmpty(mess))
//		{
//			return;
//		}
//
//		this.Alert(mess, false);
//	}
//	/** 
//	 ����page ����show message
//	 
//	 @param mess
//	*/
//	protected final void Alert(String mess, boolean isClent)
//	{
//		if (StringHelper.isNullOrEmpty(mess))
//		{
//			return;
//		}
//
//		//this.ResponseWriteRedMsg(mess);
//		//return;
//		mess = mess.replace("'", "��");
//
//		mess = mess.replace("\"", "��");
//
//		mess = mess.replace(";", "��");
//		mess = mess.replace(")", "��");
//		mess = mess.replace("(", "��");
//
//		mess = mess.replace(",", "��");
//		mess = mess.replace(":", "��");
//
//
//		mess = mess.replace("<", "��");
//		mess = mess.replace(">", "��");
//
//		mess = mess.replace("[", "��");
//		mess = mess.replace("]", "��");
//
//
//		mess = mess.replace("@", "\\n@");
//
//		mess = mess.replace("\r\n", "");
//
//		String script = "<script language=JavaScript>alert('" + mess + "');</script>";
//		if (isClent)
//		{
//			System.Web.HttpContext.Current.Response.Write(script);
//		}
//		else
//		{
//			this.ClientScript.RegisterStartupScript(this.getClass(), "kesy", script);
//		}
//		//this.RegisterStartupScript("key1", script);
//	}
//
//	protected final void Alert(RuntimeException ex)
//	{
//		this.Alert(ex.getMessage(), false);
//	}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region �����ķ���
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#region ���?��������
//	/** 
//	 ���DataTable���ݵ�����Excel��  
//	 
//	 @param dt Ҫ�������ݵ�DataTable
//	 @param filepath Ҫ������ļ�·��
//	 @param filename Ҫ������ļ�
//	 @return 
//	*/
//	protected final boolean ExportDataTableToExcel_OpenWin_del(System.Data.DataTable dt, String title)
//	{
//		String filename = "Ep" + this.Session.SessionID + ".xls";
//		String file = filename;
//		boolean flag = true;
//		String filepath = SystemConfig.getPathOfTemp();
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region ���� datatable
//		for (DataColumn dc : dt.Columns)
//		{
////C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
////			switch (dc.ColumnName)
////ORIGINAL LINE: case "No":
//			if (dc.ColumnName.equals("No"))
//			{
//					dc.Caption = "���";
//			}
////ORIGINAL LINE: case "Name":
//			else if (dc.ColumnName.equals("Name"))
//			{
//					dc.Caption = "���";
//			}
////ORIGINAL LINE: case "Total":
//			else if (dc.ColumnName.equals("Total"))
//			{
//					dc.Caption = "�ϼ�";
//			}
////ORIGINAL LINE: case "FK_Dept":
//			else if (dc.ColumnName.equals("FK_Dept"))
//			{
//					dc.Caption = "���ű��";
//			}
////ORIGINAL LINE: case "ZSJGName":
//			else if (dc.ColumnName.equals("ZSJGName"))
//			{
//					dc.Caption = "�������";
//			}
////ORIGINAL LINE: case "IncNo":
//			else if (dc.ColumnName.equals("IncNo"))
//			{
//					dc.Caption = "��˰�˱��";
//			}
////ORIGINAL LINE: case "IncName":
//			else if (dc.ColumnName.equals("IncName"))
//			{
//					dc.Caption = "��˰�����";
//			}
////ORIGINAL LINE: case "TaxpayerNo":
//			else if (dc.ColumnName.equals("TaxpayerNo"))
//			{
//					dc.Caption = "��˰�˱��";
//			}
////ORIGINAL LINE: case "TaxpayerName":
//			else if (dc.ColumnName.equals("TaxpayerName"))
//			{
//					dc.Caption = "��˰�����";
//			}
////ORIGINAL LINE: case "byrk":
//			else if (dc.ColumnName.equals("byrk"))
//			{
//					dc.Caption = "�������";
//			}
////ORIGINAL LINE: case "ljrk":
//			else if (dc.ColumnName.equals("ljrk"))
//			{
//					dc.Caption = "�ۼ����";
//			}
////ORIGINAL LINE: case "qntq":
//			else if (dc.ColumnName.equals("qntq"))
//			{
//					dc.Caption = "ȥ��ͬ��";
//			}
////ORIGINAL LINE: case "jtqzje":
//			else if (dc.ColumnName.equals("jtqzje"))
//			{
//					dc.Caption = "��ȥ��������";
//			}
////ORIGINAL LINE: case "jtqzjl":
//			else if (dc.ColumnName.equals("jtqzjl"))
//			{
//					dc.Caption = "��ȥ��������";
//			}
////ORIGINAL LINE: case "BenYueYiJiao":
//			else if (dc.ColumnName.equals("BenYueYiJiao"))
//			{
//					dc.Caption = "�����ѽ�";
//			}
////ORIGINAL LINE: case "BenYueYingJiao":
//			else if (dc.ColumnName.equals("BenYueYingJiao"))
//			{
//					dc.Caption = "����Ӧ��";
//			}
////ORIGINAL LINE: case "BenYueWeiJiao":
//			else if (dc.ColumnName.equals("BenYueWeiJiao"))
//			{
//					dc.Caption = "����δ��";
//			}
////ORIGINAL LINE: case "LeiJiWeiJiao":
//			else if (dc.ColumnName.equals("LeiJiWeiJiao"))
//			{
//					dc.Caption = "�ۼ�δ��";
//			}
////ORIGINAL LINE: case "QuNianTongQiLeiJiYiJiao":
//			else if (dc.ColumnName.equals("QuNianTongQiLeiJiYiJiao"))
//			{
//					dc.Caption = "ȥ��ͬ��δ��";
//
//			}
////ORIGINAL LINE: case "QianNianTongQiLeiJiYiJiao":
//			else if (dc.ColumnName.equals("QianNianTongQiLeiJiYiJiao"))
//			{
//					dc.Caption = "ǰ��ͬ���ۼ��ѽ�";
//			}
////ORIGINAL LINE: case "QianNianTongQiLeiJiYingJiao":
//			else if (dc.ColumnName.equals("QianNianTongQiLeiJiYingJiao"))
//			{
//					dc.Caption = "ǰ��ͬ���ۼ�Ӧ��";
//
//			}
////ORIGINAL LINE: case "JiaoQuNianTongQiZhengJian":
//			else if (dc.ColumnName.equals("JiaoQuNianTongQiZhengJian"))
//			{
//					dc.Caption = "��ȥ��ͬ������";
//			}
////ORIGINAL LINE: case "JiaoQuNianTongQiZhengJianLv":
//			else if (dc.ColumnName.equals("JiaoQuNianTongQiZhengJianLv"))
//			{
//					dc.Caption = "��ȥ��ͬ��������";
//
//			}
////ORIGINAL LINE: case "JiaoQianNianTongQiZhengJian":
//			else if (dc.ColumnName.equals("JiaoQianNianTongQiZhengJian"))
//			{
//					dc.Caption = "��ȥ��ͬ������";
//			}
////ORIGINAL LINE: case "JiaoQianNianTongQiZhengJianLv":
//			else if (dc.ColumnName.equals("JiaoQianNianTongQiZhengJianLv"))
//			{
//					dc.Caption = "��ǰ��ͬ��������";
//			}
////ORIGINAL LINE: case "LeiJiYiJiao":
//			else if (dc.ColumnName.equals("LeiJiYiJiao"))
//			{
//					dc.Caption = "�ۼ��ѽ�";
//			}
////ORIGINAL LINE: case "LeiJiYingJiao":
//			else if (dc.ColumnName.equals("LeiJiYingJiao"))
//			{
//					dc.Caption = "�ۼ�Ӧ��";
//			}
////ORIGINAL LINE: case "QuNianBenYueYiJiao":
//			else if (dc.ColumnName.equals("QuNianBenYueYiJiao"))
//			{
//					dc.Caption = "ȥ�걾���ѽ�";
//			}
////ORIGINAL LINE: case "QuNianBenYueYingJiao":
//			else if (dc.ColumnName.equals("QuNianBenYueYingJiao"))
//			{
//					dc.Caption = "ȥ�걾��Ӧ��";
//			}
////ORIGINAL LINE: case "QuNianLeiJiYiJiao":
//			else if (dc.ColumnName.equals("QuNianLeiJiYiJiao"))
//			{
//					dc.Caption = "ȥ���ۼ��ѽ�";
//			}
////ORIGINAL LINE: case "QuNianLeiJiYingJiao":
//			else if (dc.ColumnName.equals("QuNianLeiJiYingJiao"))
//			{
//					dc.Caption = "ȥ���ۼ�Ӧ��";
//			}
////ORIGINAL LINE: case "QianNianBenYueYiJiao":
//			else if (dc.ColumnName.equals("QianNianBenYueYiJiao"))
//			{
//					dc.Caption = "ǰ�걾���ѽ�";
//			}
////ORIGINAL LINE: case "QianNianBenYueYingJiao":
//			else if (dc.ColumnName.equals("QianNianBenYueYingJiao"))
//			{
//					dc.Caption = "ǰ�걾��Ӧ��";
//			}
////ORIGINAL LINE: case "QianNianLeiJiYiJiao":
//			else if (dc.ColumnName.equals("QianNianLeiJiYiJiao"))
//			{
//					dc.Caption = "ǰ��ͬ���ۼ��ѽ�";
//			}
////ORIGINAL LINE: case "QianNianLeiJiYingJiao":
//			else if (dc.ColumnName.equals("QianNianLeiJiYingJiao"))
//			{
//					dc.Caption = "ǰ��ͬ���ۼ�Ӧ��";
//			}
////ORIGINAL LINE: case "JiaoQuNianZhengJian":
//			else if (dc.ColumnName.equals("JiaoQuNianZhengJian"))
//			{
//					dc.Caption = "��ȥ��ͬ������";
//			}
////ORIGINAL LINE: case "JiaoQuNianZhengJianLv":
//			else if (dc.ColumnName.equals("JiaoQuNianZhengJianLv"))
//			{
//					dc.Caption = "��ȥ��ͬ��������";
//			}
////ORIGINAL LINE: case "JiaoQianNianZhengJian":
//			else if (dc.ColumnName.equals("JiaoQianNianZhengJian"))
//			{
//					dc.Caption = "��ǰ��ͬ������";
//			}
////ORIGINAL LINE: case "JiaoQianNianZhengJianLv":
//			else if (dc.ColumnName.equals("JiaoQianNianZhengJianLv"))
//			{
//					dc.Caption = "��ǰ��ͬ��������";
//			}
////ORIGINAL LINE: case "level":
//			else if (dc.ColumnName.equals("level"))
//			{
//					dc.Caption = "����";
//			}
//		}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region �����������
//		//����У��
//		if (dt == null || dt.Rows.size() <= 0 || filename == null || filename.equals("") || filepath == null || filepath.equals(""))
//		{
//			return false;
//		}
//
//		//����Ŀ¼û�н���������
//		if (!Directory.Exists(filepath) )
//		{
//			Directory.CreateDirectory(filepath);
//		}
//
//		filename = filepath + filename;
//
//		FileStream objFileStream = new FileStream(filename, FileMode.OpenOrCreate, FileAccess.Write);
//		StreamWriter objStreamWriter = new StreamWriter(objFileStream, System.Text.Encoding.Unicode);
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region ��ɵ����ļ�
//		try
//		{
//			objStreamWriter.WriteLine();
//			objStreamWriter.WriteLine(Convert.ToChar(9) + title + Convert.ToChar(9));
//			objStreamWriter.WriteLine();
//			String strLine = "";
//			//����ļ�����
//			for (int i = 0; i < dt.Columns.size(); i++)
//			{
//				strLine = strLine + dt.Columns[i].Caption + Convert.ToChar(9);
//			}
//
//			objStreamWriter.WriteLine(strLine);
//
//			strLine = "";
//
//			//����ļ�����
//			for (int row = 0; row < dt.Rows.size(); row++)
//			{
//				for (int col = 0; col < dt.Columns.size(); col++)
//				{
//					strLine = strLine + dt.Rows[row][col] + Convert.ToChar(9);
//				}
//				objStreamWriter.WriteLine(strLine);
//				strLine = "";
//			}
//			objStreamWriter.WriteLine();
//			objStreamWriter.WriteLine(Convert.ToChar(9) + "�Ʊ��ˣ�" + Convert.ToChar(9) + WebUser.getName() + Convert.ToChar(9) + "���ڣ�" + Convert.ToChar(9) + new java.util.Date().ToShortDateString());
//
//		}
//		catch (java.lang.Exception e)
//		{
//			flag = false;
//		}
//		finally
//		{
//			objStreamWriter.Close();
//			objFileStream.Close();
//		}
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#region ɾ����ɵ��ļ�
//		DelExportedTempFile(filepath);
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//			///#endregion
//
//
//		if (flag)
//		{
//			this.WinOpen("../Temp/" + file);
//			//this.Write_Javascript(" window.open( ); " );
//		}
//
//		return flag;
//	}
//	/** 
//	 ɾ�������ʱ�������ʱ�ļ� 2002.11.09 create by bluesky 
//	 
//	 @param filepath ��ʱ�ļ�·��
//	 @return 
//	*/
//	public final boolean DelExportedTempFile(String filepath)
//	{
//		boolean flag = true;
//		try
//		{
//			String[] files = Directory.GetFiles(filepath);
//
//			for (int i = 0; i < files.length; i++)
//			{
//				java.util.Date lastTime = File.GetLastWriteTime(files[i]);
//				TimeSpan span = new java.util.Date() - lastTime;
//
//				if (span.Hours >= 1)
//				{
//					File.Delete(files[i]);
//				}
//			}
//		}
//		catch (java.lang.Exception e)
//		{
//			flag = false;
//		}
//
//		return flag;
//	}
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion ���?��
//
//
////C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//		///#endregion
//
//}