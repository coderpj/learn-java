package learn.pengj.java.learn_java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author pengj
 * @date 2017年11月30日
 *
 */
public class JDBCDemo {

	/*
	 * 1、加载数据库驱动
	 * 2、创建数据库连接对象
	 * 3、创建preparedstatement对象
	 * 4、调用preparedstatement的方法执行SQL
	 * 5、关闭数据库连接
	 */
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/ssmdemo?useUnicode=true&characterEncoding=UTF-8";
		String username = "root";
		String password = "root";
		String sql = "SELECT * FROM user u WHERE u.name=?";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "张三");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String city = resultSet.getString("city");
				System.out.println(id+","+name+","+age+","+gender+","+city);
			}				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if(preparedStatement != null) {
					preparedStatement.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
