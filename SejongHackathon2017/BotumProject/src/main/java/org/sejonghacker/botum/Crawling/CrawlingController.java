package org.sejonghacker.botum.Crawling;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CrawlingController {
	
	@RequestMapping(value = "crawl.do")
	public String showScripts(Model model) {
		Document doc = null;
		try {
			doc = Jsoup.connect("http://sports.media.daum.net/sports/record/epl").get();
			System.out.println("연결됨");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		System.out.println(doc.html());
		System.out.println("긁어오기 시작");
		
		Elements teams = doc.getElementsByTag("div");
		Element team = teams.get(0);
		String txt = team.text();
		System.out.println(txt);
		System.out.println(teams.size());
		
		for (Element element : teams) {
			String str = element.html();
			System.out.println(str);
		}
		
		/*
		for (Element element : teams) {
			String date = element.text();
			System.out.println(date);
		}
		*/
		
		model.addAttribute("txt", txt);
		
		return "crawl";
	}
}
