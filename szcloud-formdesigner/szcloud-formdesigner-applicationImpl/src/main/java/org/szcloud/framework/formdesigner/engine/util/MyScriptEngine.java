package org.szcloud.framework.formdesigner.engine.util;

import java.io.Reader;
import java.util.HashSet;
import java.util.List;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.szcloud.framework.core.domain.BaseExample;
import org.szcloud.framework.core.domain.Criteria;
import org.szcloud.framework.core.utils.Springfactory;
import org.szcloud.framework.formdesigner.application.service.StoreService;
import org.szcloud.framework.formdesigner.application.vo.StoreVO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MyScriptEngine implements ScriptEngine {
	private Logger logger = LoggerFactory.getLogger(MyScriptEngine.class);
	private HashSet<String> libs = new HashSet<String>();
	private ScriptEngine engine;
	private Long systemId;

	public MyScriptEngine(ScriptEngine scriptEngine, Long systemId){
		this.engine = scriptEngine;
		this.systemId = systemId;
	}

	@Override
	public Object eval(String script, ScriptContext context) throws ScriptException {
		return this.engine.eval(script, context);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context) throws ScriptException {
		return this.engine.eval(reader, context);
	}

	@Override
	public Object eval(String script) throws ScriptException {
		logger.debug("eval start and script is {}", script);
		if(script != null && !script.equalsIgnoreCase("")){
			logger.debug("script is not blank");
			long s = System.currentTimeMillis();
			if (script.indexOf("#include") != -1) {
				logger.debug("script contains #include");
				String Content = script;
				while (Content.indexOf("#include") != -1) {
					String libname = null;
					int k = 0, t = 0, e = 0;

					k = Content.indexOf("#include");
					t = Content.indexOf("\"", k + 8);
					e = Content.indexOf("\"", t + 1);
					libname = Content.substring(t + 1, e);
					logger.debug("script contains lib {}", libname);

					String threadId = Thread.currentThread().getName() + "";
					StringBuilder key = new StringBuilder().append(libname).append("@").append(this.systemId).append("@").append(threadId);
					if (!libs.contains(key.toString())) {
						StoreService storeService = Springfactory.getBean("storeServiceImpl");
						BaseExample baseExample = new BaseExample();
						Criteria criteria = baseExample.createCriteria();
						criteria.andEqualTo("name", libname).andLike("code", StoreService.FUNC_CODE + "%").andEqualTo("system_id", this.systemId);
						List<StoreVO> vos = storeService.selectPagedByExample(baseExample, 1, 2, null);
						if(vos != null && vos.size() > 0) {
							StoreVO vo = vos.get(0);
							String content = vo.getContent();
							JSONObject o = JSON.parseObject(content);
							if(o != null) {
								String js = StringEscapeUtils.unescapeHtml4(o.getString("script"));
								logger.debug("js is {}", js);
								if(js != null && js.trim().length() > 0) {
									Compilable compilable = (Compilable) engine;
									CompiledScript JSFunction = compilable.compile(js); //解析编译脚本函数
									JSFunction.eval(engine.getBindings(ScriptContext.ENGINE_SCOPE));
									libs.add(key.toString());
								}
							}
						}
					}
					String st = Content.substring(0, k);
					String ed = Content.substring(e + 1, Content.length());
					Content = st + ed;
				}
				logger.debug("eval script with time {}", (System.currentTimeMillis() -s));
				return this.engine.eval(Content);
			}
			logger.debug("eval script with time {}", (System.currentTimeMillis() -s));
			logger.debug(script);
			return this.engine.eval(script);
		}
		return null;
	}

	@Override
	public Object eval(Reader reader) throws ScriptException {
		return this.engine.eval(reader);
	}

	@Override
	public Object eval(String script, Bindings n) throws ScriptException {
		return this.engine.eval(script, n);
	}

	@Override
	public Object eval(Reader reader, Bindings n) throws ScriptException {
		return this.engine.eval(reader, n);
	}

	@Override
	public void put(String key, Object value) {
		this.engine.put(key, value);
	}

	@Override
	public Object get(String key) {
		return this.engine.get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		return this.engine.getBindings(scope);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		this.engine.setBindings(bindings, scope);
	}

	@Override
	public Bindings createBindings() {
		return this.engine.createBindings();
	}

	@Override
	public ScriptContext getContext() {
		return this.engine.getContext();
	}

	@Override
	public void setContext(ScriptContext context) {
		this.engine.setContext(context);
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return this.engine.getFactory();
	}

}
