package cn.itcast.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		//1.获取参数
		String currentPage = req.getParameter("currentPage");//当前页码
		String rows = req.getParameter("rows");//每页显示条数
		
		if(currentPage == null || "".equals(currentPage)) {
			currentPage = "1";
		}if(rows == null || "".equals(rows)) {
			rows = "5";
		}
		//获取条件查询参数
		Map<String, String[]> condition = req.getParameterMap();
		
		//2.调用Service查询
		UserService service = new UserServiceImpl();
		PageBean<User> pb = service.findUserByPage(currentPage,rows,condition);
//		System.out.println(pb);
		//3.将PageBean存入request
		req.setAttribute("pb", pb);
		req.setAttribute("condition", condition);	//将查询条件存入request
		//4.转发list.jsp展示
		req.getRequestDispatcher("/list.jsp").forward(req, resp);
		
	}

}