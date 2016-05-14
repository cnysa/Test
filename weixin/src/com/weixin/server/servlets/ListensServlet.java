package com.weixin.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.servlets.service.CoreService;
import com.weixin.server.util.SignUtil;

/**
 * 
 * @ClassName: ListensServlet
 * @Description: TODO(����΢����Ϣ) 
 * @author: zhanggd
 * @date 2016��3��2������6:00:15
 */
public class ListensServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(ListensServlet.class);

	/**
	 * 
	 * @Title: doGet 
	 * @Description: TODO()
	 * @author: zhanggd
	 * @date: 2016��3��2������5:59:35
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("==GET����==");
		// ΢�ż���ǩ��
		String signature = request.getParameter("signature");
		// ʱ���
		String timestamp = request.getParameter("timestamp");
		// �����
		String nonce = request.getParameter("nonce");
		// ����ַ���
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 
	 * @Title: doPost 
	 * @Description: TODO()
	 * @author: zhanggd
	 * @date: 2016��3��2������5:59:18
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("==POST����==");
		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// ���ú���ҵ���������Ϣ��������Ϣ
		String respMessage = CoreService.processRequest(request);
		
		// ��Ӧ��Ϣ
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

}
