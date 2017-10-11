package cn.org.awcp.formdesigner.core.engine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.stereotype.Service;

@Service
public class FormEngine {
	private ScriptEngineManager manager = null;
	private ScriptEngine engine = null;
	private FreeMarkers markers = null;
	
	public FormEngine(){
		manager = new ScriptEngineManager();
		setEngine(manager.getEngineByName("JavaScript"));
		markers = new FreeMarkers();
	}
	
	public FreeMarkers getMarkers() {
		return markers;
	}
	
	public void setMarkers(FreeMarkers markers) {
		this.markers = markers;
	}

	public ScriptEngine getEngine() {
		return engine;
	}
	
	public void setEngine(ScriptEngine engine) {
		this.engine = engine;
	}

}
