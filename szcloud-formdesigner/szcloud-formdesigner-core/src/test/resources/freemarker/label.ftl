<#macro convertLabel c >
	<label  class='${c.getCss()!"form-control"}'>
		${"${" + c.getDataItemCode() + "}"}
	</label>
</#macro>
<@convertLabel c/>