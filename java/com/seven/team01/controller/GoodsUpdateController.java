package com.seven.team01.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.seven.team01.service.GoodsService;
import com.seven.team01.vo.GoodsVO;

@Controller
@SessionAttributes("goodVO") // 세션에서 boardVO 검색 가져오기
public class GoodsUpdateController { // 수정폼으로 이동
	@Resource(name = "uploadPath")
	private String uploadPath;

	@Autowired
	private GoodsService goodService;


	@RequestMapping("/update.do") //requestMapping 메소드
	public String handle(@Valid@ModelAttribute("goodVO") GoodsVO vo, SessionStatus status) {
		
		try {
			MultipartFile gFile = vo.getgFile();
			System.out.println("gFile=" + gFile); //파일미선택도 객체 생성
			
			
		if (gFile != null && gFile.getSize() != 0) { 
			String fileName = gFile.getOriginalFilename();// 업된 파일명
			// 파일명을 VO UploadPath에  설정
			System.out.println(">>>>> 파일명을 VO에  설정");
			// upload폴더의 물리적인 폴더 절대경로
			String upath = uploadPath+"/"+"Uploadfile";
			File file = new File(upath + "/" + fileName); // upload폴더 File 객체 생성
			gFile.transferTo(file); // 파일을  upload폴더로 복사
			System.out.println(file + " upath" + "에 저장");
			String showimgpath = "resources/Uploadfile/";
			vo.setgImg( showimgpath + gFile.getOriginalFilename());
			System.out.println("파일크기=" + gFile.getSize() + "바이트");
		}
		    System.out.println(vo.getgImg());
			goodService.updateService(vo);
			status.setComplete();
			return "redirect:/goodList.do";
		}catch(Exception e) {
			System.out.println("error:" + e.getMessage().toString());
			return "goods/updateForm";
		}		

	}
	
	@RequestMapping(value = "/goodUpdateView.do", method = RequestMethod.GET)
	public String goodUpdateView(@RequestParam int gCode, Model model) {
	
		GoodsVO vo = goodService.selectAfterUpdate(gCode);
		model.addAttribute("goodVO", vo); //boarVO가 session에 저장되도록 지정
		System.out.println(model);
		return "goods/updateForm";
	}

}
