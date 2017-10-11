<#macro convertRadio c>
	<#if c.getOptions() ?? >
		<#list c.getOptions() as option >
			<label class='${c.getCss()!"radio-inline"}'>
			<input type='radio' 
			<#if c.getName()?? >
				name='${c.getName()}'
			</#if> 	
			<#if c.getDataItemCode()?? >
				value='${c.getDataItemCode()}'
			</#if> 
			id='${((c.getPageId())!"") + "_" + ((c.getName())!"")}'
			value='${option.getValue()}'
			/>${option.getText()}
			</label>
		</#list>
	</#if>
</#macro>
<@convertRadio c />
