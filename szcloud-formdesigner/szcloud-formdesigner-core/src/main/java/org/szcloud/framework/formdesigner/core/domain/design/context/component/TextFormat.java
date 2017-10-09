package org.szcloud.framework.formdesigner.core.domain.design.context.component;

import java.util.Map;

public class TextFormat extends SimpleComponent {

	private static final long serialVersionUID = -5394811149123704691L;

	@Override
	public String getKeyString() {
		return null;
	}
	
	/**
	 * 文本
	 */
	protected String text;
	/**
	 * 键盘文本
	 */
	private String kbd;
	/**
	 * 加重语气
	 */
	private String em;
	/**
	 * 更强的加重语气
	 */
	private String strong;
	/**
	 * 定义项目
	 */
	private String dfn;
	
	/** 
	 * 计算机代码文本
	 */
	private String code;
	/**
	 * 定义引用
	 */
	private String cite;
	/**
	 * 样本文本
	 */
	private String samp;
	/**
	 * 定义变量 与pre code 配合使用
	 */
	private String var;
	/**
	 * 短引用
	 */
	private String q;
	/**
	 * 打字机文本
	 */
	private String tt;
	/**
	 * 斜体文字
	 */
	private String i;
	
	/**
	 * 粗体文字
	 */
	private String b;
	
	/**
	 * 大号字体
	 */
	private String big;
	/**
	 * 小号字体
	 */
	private String small;
	
	/** 
	 * 下标字体
	 */
	private String sub;
	/**
	 * 上标字体
	 */
	private String sup;
	
	/**
	 * 加重语气
	 */
	private String address;
	/**
	 * 定义删除文字
	 * key: cite
	 * 		datetime
	 */
	private Map<String,Object> del;
	/**
	 * 超链接
	 * key:charset	coordinates	href
	 * 		rel  rev shape target
	 */
	private Map<String,Object> a;
	
	/**
	 * 长引用
	 * key:cite
	 */
	private Map<String,Object> blockQuote;
	
	/**
	 * 浏览器禁用 或不支持script时的替代文字
	 */
	private String noScript;
	
	/**
	 * 插入文字
	 * key:cite datetime
	 */
	private Map<String,Object> ins;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKbd() {
		return kbd;
	}

	public void setKbd(String kbd) {
		this.kbd = kbd;
	}

	public String getEm() {
		return em;
	}

	public void setEm(String em) {
		this.em = em;
	}

	public String getStrong() {
		return strong;
	}

	public void setStrong(String strong) {
		this.strong = strong;
	}

	public String getDfn() {
		return dfn;
	}

	public void setDfn(String dfn) {
		this.dfn = dfn;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCite() {
		return cite;
	}

	public void setCite(String cite) {
		this.cite = cite;
	}

	public String getSamp() {
		return samp;
	}

	public void setSamp(String samp) {
		this.samp = samp;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getBig() {
		return big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getSup() {
		return sup;
	}

	public void setSup(String sup) {
		this.sup = sup;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Map<String, Object> getDel() {
		return del;
	}

	public void setDel(Map<String, Object> del) {
		this.del = del;
	}

	public Map<String, Object> getA() {
		return a;
	}

	public void setA(Map<String, Object> a) {
		this.a = a;
	}

	public Map<String, Object> getBlockQuote() {
		return blockQuote;
	}

	public void setBlockQuote(Map<String, Object> blockQuote) {
		this.blockQuote = blockQuote;
	}

	public String getNoScript() {
		return noScript;
	}

	public void setNoScript(String noScript) {
		this.noScript = noScript;
	}

	public Map<String, Object> getIns() {
		return ins;
	}

	public void setIns(Map<String, Object> ins) {
		this.ins = ins;
	}
	
}
