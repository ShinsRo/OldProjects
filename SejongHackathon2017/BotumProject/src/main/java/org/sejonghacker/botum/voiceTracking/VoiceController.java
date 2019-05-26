package org.sejonghacker.botum.voiceTracking;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VoiceController {

	@RequestMapping(value = "recode.do", method = RequestMethod.POST)
	public void recode(String msg, String name, String number, Model m, HttpServletRequest req){
		String contextPath = req.getSession().getServletContext().getRealPath("/scripts/");
		System.out.println("Source : " + msg + "name : " + name + "number : " + number);

		System.out.println(contextPath);
		VoiceFileRecoder vfr = new VoiceFileRecoder(contextPath, number, name);
		vfr.recode(msg);
		
	}
	
	@RequestMapping(value ="showScripts.do")
	public String showScripts(String name, String number, Model m, HttpServletRequest req){
		m.addAttribute("name", name);
		m.addAttribute("number", number);
		return "striptsUI";
	}
	
	@RequestMapping(value ="getScripts.do")
	@ResponseBody
	public JSONObject getScripts(String name, String number, HttpServletRequest req) {
		String contextPath = req.getSession().getServletContext().getRealPath("/scripts/");
		String res = "";
		
		VoiceFileRecoder vfr = new VoiceFileRecoder(contextPath, number, name);
		res = vfr.read(name, number);
		System.out.println(res);
		Map<String, String> jMap = new HashMap<String, String>();
		jMap.put("content", res);
		JSONObject jsonObject = new JSONObject(jMap);
		System.out.println("obj : " + jsonObject.toString());
		return jsonObject;
	}
}
