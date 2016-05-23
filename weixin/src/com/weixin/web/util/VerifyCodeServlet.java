package com.weixin.web.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class VerifyCodeServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(VerifyCodeServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("�����ȡ��֤��()");
		/*
		 * 1. ������֤����
		 */
		VerifyCode vc = new VerifyCode();
		/*
		 * 2. �õ���֤��ͼƬ
		 */
		BufferedImage image = vc.getImage();
		/*
		 * 3. ��ͼƬ�ϵ��ı����浽session��
		 */
		request.getSession().setAttribute("session_vcode", vc.getText());
		/*
		 * 4. ��ͼƬ��Ӧ���ͻ���
		 */
		VerifyCode.output(image, response.getOutputStream());
		log.debug("������֤�����():{}",vc.getText());
	}
}
