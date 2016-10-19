package com.Alloc;

import java.io.IOException;
import java.awt.List;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

//import com.sun.tools.javac.util.List;
public class MediaList extends HttpServlet {
	

	/**
	 * Constructor of the object.
	 */
	public MediaList() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 设置响应编码
		response.setCharacterEncoding("UTF-8");
		
		// 获取请求参数
//		Integer index = Integer.parseInt(request.getParameter("index"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//如果缺少一些必要参数，那么就返回错误的信息
		if (username == null || password == null) {
			JSONObject object = getErrorJSONObject();
			PrintWriter out = response.getWriter();
			out.print(object);
			return;
		}
		
		// 连接数据库
		// 返回内容的容器
		JSONArray resultList = new JSONArray();
		
		DBHelper dbHelper;
		String sql = " SELECT * FROM users where username='"+username+"'"+"and password='"+password+"'";
	
		dbHelper = new DBHelper(sql);
		
		try {
			ResultSet resultSet = dbHelper.pst.executeQuery();
			while (resultSet.next()) {
				// 添加每一条记录
				JSONObject object = new JSONObject();
				object.put("username", resultSet.getString("username"));
				object.put("password", resultSet.getString("password"));
				resultList.put(object);
			}
			resultSet.close();
			dbHelper.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		// 输出
		JSONObject obect;
		if (resultList.length() == 0) {
			//为空
			obect = this.getBaseJsonObject(resultList,"没有更多", false);
		}else {
			obect = this.getBaseJsonObject(resultList,"成功查询", true);
		}
		PrintWriter out = response.getWriter();
		out.print(obect);

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	
	private JSONObject getBaseJsonObject(Object object,String msg,Boolean success) {
		
		JSONObject baseObject = new JSONObject();
		baseObject.put("datas", object);
		baseObject.put("msg", msg);
		baseObject.put("success", success);
		return baseObject;
	}
	
	private JSONObject getErrorJSONObject() {
		JSONObject baseObject = new JSONObject();
		baseObject.put("datas", "");
		baseObject.put("msg", "fail");
		baseObject.put("success", false);
		return baseObject;
	}
}