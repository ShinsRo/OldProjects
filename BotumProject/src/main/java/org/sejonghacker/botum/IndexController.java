package org.sejonghacker.botum;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 2017.12.16. 세종대학교 해커톤 프로젝트
 * Team Botum
 *
 * @author siotMan
 */
@Controller
public class IndexController {
	@Resource
	private TestService testService;
	//private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 
	 * @return 메인 인덱스 페이지
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model m) {
		TestVO testVO = testService.getOne(1);
		System.out.println(testVO);
		m.addAttribute("test", testVO);
		return "index";
	}
	
}
