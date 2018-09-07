package com.ahmed.utils.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 	CREATE TABLE  `technicalkeeda`.`trn_imgs` (
		  `img_id` int(10) unsigned NOT NULL auto_increment,
		  `img_title` varchar(45) collate latin1_general_ci NOT NULL,
		  `img_data` blob NOT NULL,
		  PRIMARY KEY  (`img_id`)
	);
*/

public class InsertImage {

	public static void main(String[] args) throws SQLException {
		InsertImage imageTest = new InsertImage();
		imageTest.insertImage();
		System.out.println("Done");
	}

	public Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "mysql");
		} catch (Exception e) {
			System.out.println("Error Occured While Getting the Connection: - " + e);
		}
		return connection;
	}

	public void insertImage() {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;

		try {
			File image = new File("ishaq.jpg");
			inputStream = new FileInputStream(image);

			connection = getConnection();
			statement = connection.prepareStatement("insert into trn_imgs(img_title, img_data) " + "values(?,?)");
			statement.setString(1, "Ishaq Ahmed");
			statement.setBinaryStream(2, (InputStream) inputStream, (int) (image.length()));

			statement.executeUpdate();

		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: - " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: - " + e);
		} finally {

			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("SQLException Finally: - " + e);
			}

		}

	}
}