<#macro convertTextarea c >
	<textarea   class='${c.getCss()!"form-control"}'
	<#if c.getStyle()?? >
		style='${c.getStyle()}'
	</#if> 	
	<#if c.getName()?? >
		name='${c.getName()}'
	</#if> 
	<#if c.getDataItemCode()?? >
		value='${ "${" +  c.getDataItemCode() + "}" }'
	</#if> 
	<#if c.getRowCount()?? >
		rows='${c.getRowCount()}'
	</#if> 
	id='${((c.getPageId())!"") + "_" + ((c.getName())!"")}'	
	<#if c.getDescription()?? >
		title='${c.getDescription()}'
	</#if> 
	></textarea>
</#macro>
<@convertTextarea c/>