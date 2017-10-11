package cn.org.awcp.formdesigner.convert;

import org.apache.commons.lang3.StringEscapeUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MutiStringConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return (String.class).equals(clazz);
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if(object != null) {
			String s = (String) object;
			StringBuilder sb = new StringBuilder();
			sb.append("<![CDATA[").append(StringEscapeUtils.unescapeHtml4(s)).append("]]>");
			writer.setValue(sb.toString());
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0,
			UnmarshallingContext arg1) {
		return null;
	}

}
