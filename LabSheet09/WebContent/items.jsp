
<%
	//Initialize
session.setAttribute("statusMsg", "");
System.out.println("Trying to process..");

//Save---------------------------------
if (request.getParameter("itemCode") != null) {
	Item itemObj = new Item();
	String stsMsg = "";
	//Insert--------------------------
	if (request.getParameter("hidItemIDSave") == "") {
		stsMsg = itemObj.insertItem(request.getParameter("itemCode"), request.getParameter("itemName"),
		request.getParameter("itemPrice"), request.getParameter("itemDesc"));
	} else//Update----------------------
	{
		stsMsg = itemObj.updateItem(request.getParameter("hidItemIDSave"), 
				request.getParameter("itemCode"),
				request.getParameter("itemName"), 
				request.getParameter("itemPrice"), 
				request.getParameter("itemDesc"));
	}
	session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) {
	Item itemObj = new Item();
	String stsMsg = itemObj.deleteItem(request.getParameter("hidItemIDDelete"));
	session.setAttribute("statusMsg", stsMsg);
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.Item"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="Components/item.js"></script>
<link rel="stylesheet" href="./View/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Add Items</h1>

				<form name="formItem" id="formItem" method="post" action="items.jsp">
					ItemCode: <input type="text" id="itemCode" name="itemCode"
						class="form-control border border-primary form-control-sm"><br>
					Item Name: <input type="text" id="itemName" name="itemName"
						class="form-control border border-primary form-control-sm"><br>
					Item Price: <input type="text" id="itemPrice" name="itemPrice"
						class="form-control border border-primary form-control-sm"><br>
					Item Description: <input type="text" id="itemDesc" name="itemDesc"
						class="form-control border border-primary form-control-sm"><br>

					<div id="alertSuccess" class="alert alert-success">
						<%
						out.print(session.getAttribute("statusMsg"));
						%>
					</div>
					<div id="alertError" class="alert alert-danger"></div>

					<input type="button" id="btnSave" name="btnSave" value="Save"
						class="btn btn-outline-primary"><br> <input
						type="hidden" name="hidItemIDSave" id="hidItemIDSave" value="">

				</form>
				<br>
				<%
					Item item = new Item();
					out.print(item.readItems());
				%>
			</div>
		</div>
	</div>


</body>
</html>