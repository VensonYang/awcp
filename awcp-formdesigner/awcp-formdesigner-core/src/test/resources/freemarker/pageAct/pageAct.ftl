<#macro convertCheckbox c>
	<#if (!(c.getOptions()??) || c.getOptions()?size == 0)  >
		<label class='${c.getCss()!"checkbox-inline"}'>
			<input type='checkbox'
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
			/>s
			<#if c.getTitle() ?? >
				${c.getTitle()}
			</#if>
		</label>
	<#else>
		<label  class='${c.getCss()!"checkbox-inline"}'>
			<#if c.getTitle() ?? >
				${c.getTitle()}
			</#if>
		</label>
		<#if c.getOptions() ??>
			<#list c.getOptions() as option >
				<label class='${c.getCss()!"checkbox-inline"}'>
				<input type='checkbox' 
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
	</#if>		
</#macro>
<@convertCheckbox c />
