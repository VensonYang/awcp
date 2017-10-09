<#macro convertSelect c >
	<select class='${c.getCss()!"form-control"}'
	<#if c.getStyle()?? >
		style='${c.getStyle()}'
	</#if>	
	<#if c.getName()?? >
		name='${c.getName()}'
	</#if> 	
	<#if c.getDataItemCode()?? >
		value='${c.getDataItemCode()}'
	</#if> 	
	id='${((c.getPageId())!"") + "_" + ((c.getName())!"")}'
	<#if c.getDescription()?? >
		title='${c.getDescription()}'
	</#if> 
	>
	<#if c.getOptions() ?? >
		<#list c.getOptions() as option >
				<option value='${option.getValue()}' 			
					<#if option.isDef()>
						selected='selected' 
					</#if>
					>${option.getText()}
				</option>
		</#list>
	</#if>
	</select>
</#macro>
<@convertSelect c/>