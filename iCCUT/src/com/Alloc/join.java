package com.Alloc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class join extends HttpServlet{
	/**
	 * Constructor of the object.
	 */
	public join() {
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
		
		// ������Ӧ����
		response.setCharacterEncoding("UTF-8");
		
		// ��ȡ�������
//		Integer index = Integer.parseInt(request.getParameter("index"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//���ȱ��һЩ��Ҫ��������ô�ͷ��ش������Ϣ
		if (username == null || password == null) {
			JSONObject object = getErrorJSONObject();
			PrintWriter out = response.getWriter();
			out.print(object);
			return;
		}
		
		// �������ݿ�
		// �������ݵ�����

		String str = new String();
		
		DBHelper dbHelper;
		String sql = " SELECT * FROM users where username='"+username+"'"+"and password='"+password+"'";
	
		dbHelper = new DBHelper(sql);
		
		try {
			ResultSet resultSet = dbHelper.pst.executeQuery();
			while (resultSet.next()) {
				// ���ÿһ����¼
				
				str = resultSet.getString("token");
				
			}
			resultSet.close();
			dbHelper.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		// ���
		JSONObject obect;
		if (str.length() == 0) {
			//Ϊ��
			obect = this.getBaseJsonObject(str,"û�и���", false);
		}else {
			obect = this.getBaseJsonObject(str,"�ɹ���ѯ", true);
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

	
	private JSONObject getBaseJsonObject(String str,String msg,Boolean success) {
		
		JSONObject baseObject = new JSONObject();
		baseObject.put("token", str);
		baseObject.put("msg", msg);
		baseObject.put("success", success);
		return baseObject;
	}
	
	private JSONObject getErrorJSONObject() {
		JSONObject baseObject = new JSONObject();
		baseObject.put("token", "");
		baseObject.put("msg", "fail");
		baseObject.put("success", false);
		return baseObject;
	}
}
