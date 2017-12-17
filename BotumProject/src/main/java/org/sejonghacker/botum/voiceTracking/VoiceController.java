package org.sejonghacker.botum.voiceTracking;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VoiceController {
	
	@RequestMapping(value = "/tt.do", method = RequestMethod.POST)
	public String testing(String kim, HttpServletRequest req){
		String contextPath = req.getSession().getServletContext().getRealPath("/scripts/");
		System.out.println("Source : " + kim);
		
		System.out.println(contextPath);
		VoiceFileRecoder vfr = new VoiceFileRecoder(contextPath, "me", "1");
		
		System.out.println("리딩 : "+vfr.recodeAndRead(kim).toString());

		return "t2";
	}
}
