<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
-U {
	text-decoration: none;
}

TABLE TD {
	padding-left: 15px;
}

.main {
	color: black;
	font-family: Arial, Helvetica, sans-serif;
	width: 95%;
}

.main a {
	color: blue;
	text-decoration: none;
}

.main a:hover {
	text-decoration: underline;
}

.main H2 {
	color: red;
}

.main input,.main select {
	border: 2px solid #CCC;
	background-color: white;
	padding: 3px;
	color: black;
	font-weight: bold;
}

.main select {
	cursor: pointer;
}

.main #submit {
	border: none;
	cursor: pointer;
}
</style>
<meta charset="utf-8" />
<title>DataCash Response page</title>
<script type="text/javascript">
	// Get the query string contents from the page URL used by removing the "?"
	var query = location.search.substr(1);

	var search_pattern = '\\+';
	var search_flags = 'g';
	var search_reg_exp = new RegExp(search_pattern, search_flags);

	function populate(formName) {
		if (query) { // Was there a query string?
			var params = query.split("&"); // Yes!  Split them up
			var theForm = findForm(formName); // Locate the form with the correct name/ID
			if (theForm != null) { // Did we find the form?
				for (q = 0; q < params.length; q++) {
					xy = params[q].split("="); // Split the command into name/value pair
					paramName = xy[0];
					newValue = unescape(xy[1].replace(search_reg_exp, ' ')); // translate query string

					if (theForm.elements[paramName]) { // does named element exist?

						// alert(paramName + ' ' + theForm.elements[paramName] + ' ' + theForm.elements[paramName].type);

						switch (theForm.elements[paramName].type) {
						case 'text': // type = "text"
						case 'hidden': // type = "hidden"
						case 'textarea': // <textarea>
						case 'email': // type = "email"
						case 'search': // type = "search"
						case 'url': // type = "url"
						case 'number': // type = "number"
						case 'range': // type = "range"
							theForm.elements[paramName].value = newValue;
							break;

						case 'select-one': // <select>
							theOption = theForm.elements[paramName];
							max = theOption.length;
							for (i = 0; i < max; i++) {
								if (theOption.options[i].value == newValue) {
									theOption.options.selectedIndex = i;
								}
							} // end for i
							break;

						case undefined: // type = "radio" (really 'nodeList')
							if (theForm.elements[paramName]) { // double-check this element exists
								theOption = theForm.elements[paramName];
								max = theOption.length;
								for (i = 0; i < max; i++) {
									if (theOption[i].value == newValue) {
										theOption[i].checked = true;
									}
								} // end for i
							} // endif exists
							break;

						case 'checkbox': // type="checkbox"
							theForm.elements[paramName].checked = true;
							break;

						} // end switch
					} // endif theForm.elements[paramName]
				} // end for q
			} // endif {theForm)
		} // endif (query)
	} // end populate()

	function findForm(theForm) {
		if (document.getElementById(theForm)) { // try ID first
			formElement = document.getElementById(theForm);
		} else {
			for (i = 0; i < document.forms.length; i++) {
				if (document.forms[i].name == theForm) {
					formElement = document.forms[i];
					break;
				}
			} // endFor
		} // endif document.getElementById
		return formElement;
	} // end findForm
	
function closeMe(){	
var win = window.open("","_self"); /* url = "" or "about:blank"; target="_self" */
win.close();
}
	
</script>
</head>

<style>
design {
	border-style: solid;
	border-width: 5px;
	color: black;
	size: 30;
	width: 900px;
	margin: 0px auto;
	background-color: White;
	margin-top: 0;
	margin-left: 0;
	margin-right: 0;
	font-style: italic;
	font-size: 14px;
	font-family: "Times New Roman";
	font-weight: bold;
}
</style>

<style>
table {
	border-collapse: collapse;
	width: 100%;
	border-collapse: collapse;
}

th {
	text-align: left;
	height: 50 px;
	background-color: Grey;
	color: white;
}
</style>


<body onload="populate('DisplayForm')">

	<img src="IMG/mhe.png" style="height: 76px; width: 91px;">
	<br>

	<label
		style='font-style: italic; font-size: 14px; font-family: "Times New Roman", Serif; background-color: black; color: #FFFFFF'>ERP
		ONLINE:&nbsp;</label>
	<form name="DisplayForm">
		<table>
			<tr>
				<p design>
				<th><label>ORDERID: </label></th>
				<th><label><input type="text" disabled name="orderId"
						id="orderId"
						style="width: 200px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=orderId%&gt;">
				</label></th>
				<th><label>TOKEN : </label></th>
				<th><label><input type="text" disabled name="token"
						id="token"
						style="width: 200px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New Roman";
    font-weight:bold;" value="&lt;%=token%&gt;">
				</label></th>
				</p>
			</tr>
			<tr>
				<p design>
				<th><label>AUTHCODE: </label></th>
				<th><label><input type="text" disabled name="authCode"
						id="authCode"
						style="width: 200px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=authCode%&gt;">
				</label></th>
				<th><label>DATACASH REFERENCE: </label></th>
				<th><label><input type="text" disabled
						name="dataCashRef" id="dataCashRef"
						style="width: 400px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=dataCashRef%&gt;">
				</label></th>
				</p>
			</tr>
			<tr>
				<p design>
				<th><label>CARD TYPE : </label></th>
				<th><label><input type="text" disabled name="cardType"
						id="cardType"
						style="width: 200px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=cardType%&gt;">
				</label></th>
				<th><label>CARD(4) DIGIT : </label></th>
				<th><label><input type="text" disabled
						name="cardLastFourDigits" id="cardLastFourDigits"
						style="width: 200px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=cardLastFourDigits%&gt;">
				</label></th>
				</p>
			</tr>
			<tr>
				<p design>
				
				<th><label>RESPONSE CODE : </label></th>
				<th><label><input type="text" disabled
						name="finalStatusCode" id="finalStatusCode"
						style="width: 400px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=finalStatusCode%&gt;">
				</label></th>
				
				<th><label>RESPONSE STATUS : </label></th>
				<th><label><input type="text" disabled
						name="finalStatus" id="finalStatus"
						style="width: 400px; color: black; font-style: italic; font-size: 14px; font-family:"
						Times New
						Roman";
    font-weight:bold;" value="&lt;%=finalStatus%&gt;">
				</label></th>
				</p>
			</tr>
			<tr>
				<p design></p>
			</tr>
		</table>
		
			<p design>
				<th><input type="button" value="CLOSE" name="CLOSE"
					style='font-size: 16px; margin-left: auto; margin-right: auto; display: block; margin-top: 22'
					onclick="closeMe()"></th>
			</p>
		
	</form>

</body>
</html>