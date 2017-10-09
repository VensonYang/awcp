<#macro convertDatetime c>
	<input type='text' class='${c.getCss()!"form-control"}'
	<#if c.getPlaceHolder()?? >
		placeholder='${c.getPlaceHolder()}'
	</#if>
	<#if c.getName()?? >
		name='${c.getName()}'
	</#if> 			
	<#if c.getDataItemCode()?? >
		value='${ "${" +  c.getDataItemCode() + "}" }' 
	</#if> 
	
	id='${((c.getPageId())!"") + "_" + ((c.getName())!"")}'
	<#if c.getDescription()?? >
		title='${c.getDescription()}'
	</#if> 	
	/>
</#macro>
<@convertDatetime c />