package org.kosta.goodmove.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 메타 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class HomeController {
	@RequestMapping("{viewName}.do")
	public String showView(@PathVariable String viewName){
		//System.out.println("@PathVariable:"+viewName);
		return viewName+".tiles";
	}
	@RequestMapping("{dirName}/{viewName}.do")
	public String showView(@PathVariable String dirName,
			@PathVariable String viewName){
		//System.out.println("@PathVariable:"+dirName+"/"+viewName);
		return dirName+"/"+viewName+".tiles";
	}
}
