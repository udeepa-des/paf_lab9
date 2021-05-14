package com;

import java.sql.*;


public class Item {
	//Database Connection
	private Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_project", "root", "");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Read database values
	public String readItems()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Item Code</th><th>Item Name</th><th>Item Price</th><th>Item Description</th><th>Update</th><th>Remove</th></tr>";
					String query = "select * from item";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					// iterate through the rows in the result set
					while (rs.next())
					{
						String itemID = Integer.toString(rs.getInt("itemID"));
						String itemCode = rs.getString("itemCode");
						String itemName = rs.getString("itemName");
						String itemPrice = Double.toString(rs.getDouble("itemPrice"));
						String itemDesc = rs.getString("itemDesc");
						// Add into the html table
						output += "<tr><td><input id=\"hidItemIDUpdate\" name=\"hidItemIDUpdate\" type=\"hidden\" value=\"" 
										+ itemID + "\">"
										+ itemCode + "</td>";
										output += "<td>" + itemName + "</td>";
										output += "<td>" + itemPrice + "</td>";
										output += "<td>" + itemDesc + "</td>";
										// buttons
										output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btnUpdate btn btn-secondary\"></td> <td><form method=\"post\" action=\"items.jsp\"> <input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\"> <input name=\"hidItemIDDelete\" type=\"hidden\" value=\"" + itemID + "\">" + "</form></td></tr>";
					}
					con.close();
					// Complete the html table
					output += "</table>";
		}
		
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//insert
	public String insertItem(String code,String name,String price,String description) {
		String output="";
		
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			//create a prepared statement
			String query = "insert into item (itemCode,itemName,itemPrice,itemDesc) values(?,?,?,?)";
			PreparedStatement pdsm = con.prepareCall(query);
			
			//binding values
			pdsm.setString(1, code);
			pdsm.setString(2, name);
			pdsm.setString(3, price);
			pdsm.setString(4, description);
			
			//execute the stateent
			pdsm.executeUpdate();
			con.setAutoCommit(false);
			con.commit();
			output="inserted successfully.";
			
			
		}catch(Exception e) {
			output = "Error while inserting data.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	//update
	public String updateItem(String id,String code,String name,String price,String description) {
		String output = "";
		try {
			Connection con = connect();
			
			//check database connection.
			if(con == null) {
				return "Error while connecting to the databse for updating.";
			}
			
			//create a prepared statement
			String query = "update item set itemCode=? itemName=?,itemPrice=?,itemDesc=? where itemID=? ";
			PreparedStatement pdmst = con.prepareStatement(query);
			
			//binding values
			pdmst.setString(1, code);
			pdmst.setString(2, name);
			pdmst.setString(3, price);
			pdmst.setString(4, description);
			pdmst.setInt(5, Integer.parseInt(id));
			
			//execute the statement
			pdmst.executeUpdate();
			con.setAutoCommit(false);
			con.commit();
			output = "Updated successfully";
			
		}catch(Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	//delete
	public String deleteItem(String id) {
		String output="";
		try {
			Connection con = connect();
			
			//check database connection
			if(con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			//create a prepared statement
			String query = "delete from item where itemID=?";
			PreparedStatement pdms = con.prepareStatement(query);
			
			//bind values
			pdms.setInt(1, Integer.parseInt(id));
			
			//execute the statement
			pdms.executeUpdate();
			con.setAutoCommit(false);
			con.commit();
			output="Deleted Successfully.";
			
			
		}catch(Exception e) {
			output="Error while deleteing items";
			System.err.println(e.getMessage());
		}
		
		
		return output;
	}

	
}
