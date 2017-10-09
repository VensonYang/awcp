<#macro convertPassword c >
	<input type='password' class='
	<#if c.getCss()?? >
		${c.getCss()}
	<#else>
		${"form-control"}
	</#if> 
	'
	<#if c.getStyle()?? >
		style='${c.getStyle()}'
	</#if> 
	
	<#if c.getName()?? >
		name='${c.getName()}'
	</#if> 
	
	<#if c.getDataItemCode()?? >
		value='${ "${" +  c.getDataItemCode() + "}" }' 
	</#if> 
	
	<#if c.getPlaceHolder()?? >
		placeHolder='${c.getPlaceHolder()}'
	</#if> 
	
	id='${((c.getPageId())!"") + "_" + ((c.getName())!"")}'
	
	<#if c.getDescription()?? >
		title='${c.getDescription()}'
	</#if> 
	/>
</#macro>
<@convertPassword c/>