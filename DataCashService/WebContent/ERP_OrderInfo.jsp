<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="sun.misc.BASE64Encoder" %>
    
<html>
<head>

<body style="position: absolute; height: inside; visibility: visible; width: inside; overflow: visible">
	<img src="IMG/mhe.png" style="height: 76px; width: 91px; "><br>
	<!-- <label style='font-style: italic; font-size: 14px; font-family: "Times New Roman", Serif; background-color: #000000; color: #FFFFFF'>ERP ONLINE:&nbsp;</label>-->
	<br><br><br>


	<form name="form1" method="get" action="https://172.26.6.142:8002/DataCashService/tokenization/DataCash/SessionSetUp">
					<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;Merchant Reference Number :&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="merchantref" id="merchantref" value="mref_001"
				style="height: 27px;">
				
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;First Name :&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="firstname" id="firstname" value="FirstName"
				style="height: 27px;">
				
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;Last Name :&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="lastname" id="lastname" value="LastName"
				style="height: 27px;">
				
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;E-mail :&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="email" id="email" value="Firstname.Lastname@mheducation.com"
				style="height: 27px;">
				
				<!-- <label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;City :&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="city" id="city" value="NewYork"
				style="height: 27px;">
				
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;ZipCode :&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="zipcode" id="zipcode" value="10001"
				style="height: 27px;">-->
				
				
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;Country:&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="countrycode" id="countrycode" value="CA"
				style="height: 27px;">				
				
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;Currency:&nbsp;&nbsp;</label><input TYPE="text" size="30"
				name="currency" id="currency" value="CAD"
				style="height: 27px;">			
								
				<label>&nbsp;&nbsp;<br><br>&nbsp;&nbsp;Payment Amount:&nbsp;</label><input
				TYPE="text" name="amount" id="amount" size="10" value="12.00"
				style="height: 27px;">
				
				<p><input type="submit" class="myButton" name="submit" value="Submit" style="clip: rect(auto, auto, auto, auto); background-position: center; bottom: auto; left: auto; right: auto; top: auto"/></p>

</form>
</body>
</html>