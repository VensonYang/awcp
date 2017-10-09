package org.szcloud.framework.formdesigner.core.parse.html;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.szcloud.framework.formdesigner.core.constants.FormDesignerGlobal;
import org.szcloud.framework.formdesigner.core.domain.design.context.act.PageAct;

public class PageActHtmlWorker {
	
	public static String convertDialog(PageAct pageAct){
		StringBuilder sb = new StringBuilder();
		sb.append("var dialogId = '").append(FormDesignerGlobal.PAGEACT_DIALOG_ID_PREFIX)
			.append(pageAct.getPageId()).append("';\n");
		sb.append("var mydialog = top.dialog.get(dialogId);\n");
		sb.append("if(typeof(mydialog) == \"undefined\"){\n");
		sb.append("mydialog = dialog({\n");
		sb.append("id : dialogId,\n");
		sb.append("title : '").append(pageAct.getTittle()).append("',\n");
		
		if("1".equals(pageAct.getContentType())){
			//自定义内容
			sb.append("content : '").append(pageAct.getContent()).append("',\n");
		} else if("2".equals(pageAct.getContentType())){
			//静态页面
			sb.append("url : '").append(pageAct.getContent()).append("',\n");
		}  else if("3".equals(pageAct.getContentType())){
			//动态页面
			String docId = pageAct.getContent().split(",")[0];
			String pageId = pageAct.getContent().split(",")[1];
			sb.append("url : '").append(FormDesignerGlobal.DOCUMENT_URL).append("?docId=").append(docId)
				.append("&pageId=").append(pageId).append("',\n");
		}
		
		String ok = pageAct.getButtons().split(",")[0];
		String cancel = pageAct.getButtons().split(",")[1];
		if(ok.equalsIgnoreCase("1")){
			sb.append("okValue : '确定',\n");
			sb.append("ok: function () {\n");
			if(StringUtils.isNotBlank(pageAct.getClientScript())){
				sb.append(StringEscapeUtils.unescapeHtml4(pageAct.getClientScript()));
			} else {
				sb.append("actClick(this);\n");
			}
			sb.append("return true;\n");
			sb.append("},\n");
		}
		if(cancel.equalsIgnoreCase("0")){
			sb.append("cancelValue: '取消',\n");
			sb.append("cancel: true,\n");
		}
		//sb.append("align : '").append(pageAct.getLocation()).append("',\n");
		sb.append("height : ").append(pageAct.getHeight()).append(",\n");
		sb.append("width : ").append(pageAct.getWidth()).append("\n");
		sb.append("});\n");
		sb.append("}\n");
		sb.append("if(!mydialog.open){\n");
		sb.append("mydialog.show();\n");
		sb.append("} else {\n");
		sb.append("mydialog.close();\n");
		sb.append("}\n");
		return sb.toString();
	}
}
