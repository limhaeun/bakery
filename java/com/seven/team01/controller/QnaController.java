package com.seven.team01.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seven.team01.service.QnaService;

import com.seven.team01.vo.Pagination;
import com.seven.team01.vo.QnaReplyVO;
import com.seven.team01.vo.QnaVO;

@Controller
public class QnaController {

	@Resource(name="uploadPath")
	private String uploadPath;
	private int pAGE_SCALE = 10;
	private int bLOCK_SCALE = 5;
	
	@Autowired
	QnaService qnaSerivce;
	

	//문의게시판//////////////////////////////////////////
	@RequestMapping(value="/qna.do")
	public String selectQna(Model model, @RequestParam(defaultValue="1") int curPage){
		//qnaList.jsp에서 보이는 게시글 페이징 !
		int count = qnaSerivce.countForQna();
		

		Pagination.setBLOCK_SCALE(bLOCK_SCALE);
		Pagination.setPAGE_SCALE(pAGE_SCALE);
		Pagination paging = new Pagination(count, curPage);
		
		
		int start = paging.getPageBegin();
		int end = paging.getPageEnd();
		
		List<QnaVO> list = qnaSerivce.selectQna(start, end);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("paging",paging);
		return "qna/qnaList";
	}
	//////////////////////////////////////////////////////
	
	@RequestMapping(value="insertQna.do")
	public String insertqna() {
		return "qna/insertQna";
	}
	
	//qna insert
	@RequestMapping(value = "QnaWrite.do",method =RequestMethod.POST)
	public String qnawrite(HttpServletRequest request,@Valid @ModelAttribute("qna") QnaVO qnaVO,@RequestParam MultipartFile qFile ,Model model, Errors errors) {
		
		if(errors.hasErrors()){
			System.out.println("에러지롱");
			return "qna/qnaList";
		}	
		
		try {
			qFile = qnaVO.getqFile();
			System.out.println("qfile=" + qFile); //파일미선택도 객체 생성 (NULL)
			
			
			//1. 업된 파일처리 (0바이트 파일은 비저장 => 0바이트 파일을 업로드해도 리스트에 노출 안 됨) 
			if (qFile != null && qFile.getSize() != 0) { 
				String fileName = qFile.getOriginalFilename();// 업된 파일명
				
				// 파일명을 qnaVO의 setqImg에  설정
				qnaVO.setqImg(fileName);
				System.out.println(">>>>> 파일명을 VO에  설정");
				
				// upload폴더의 물리적인 폴더 절대경로
				String upath = uploadPath + "/" + "qnafile";
				System.out.println(upath);
				File file = new File(upath + "/" + fileName); // upload폴더 File 객체 생성
				qFile.transferTo(file); // 파일을  upload폴더로 복사
				System.out.println(fileName + " upath" + "에 저장");
				System.out.println("파일크기=" + qFile.getSize() + "바이트");
			}
			//2. Service 메소드 호출
			
			//문의 내용 줄바꿈 처리
			String content = qnaVO.getqContent().replace("\r\n", "<br>");
			qnaVO.setqContent(content);
			
			qnaSerivce.insertQna(qnaVO);
			System.out.println(">>>>> Service 메소드 호출");
			
			
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage().toString());
			return "qna/qnaList";
		}

		return "redirect:/qna.do";
	}
	
	@RequestMapping(value="qnaDetail.do")
	public String qnaDetail(@RequestParam("qNum")  int qNum, Model model) throws Exception {
		QnaVO qna = qnaSerivce.qnaDetail(qNum);
		
		List<QnaReplyVO> qnaReplyVO = qnaSerivce.selectQnaReply(qNum);//댓글 list 보기
		model.addAttribute("QnaReplyVO",qnaReplyVO);
		model.addAttribute("qnaVO", qna);
		return "qna/qnaDetail";
	}

	@RequestMapping(value="qnaDelete.do")
	public String qnaDelete(@RequestParam("qNum")  int qNum, Model model) throws Exception {
		qnaSerivce.qnaDelete(qNum);
		return "redirect:/qna.do";
	}
	
	
	@RequestMapping(value="qnaReplyDelete.do",method =RequestMethod.GET)
	public String qnaReplyDelete(@RequestParam("qrNum")  int qRNum, @RequestParam("qNum")  int qNum,Model model) throws Exception {
		qnaSerivce.qnaReplyDelete(qRNum);

		qnaSerivce.qnaReplyUpdateDelete(qNum);//댓글삭제되면상태변화
		
		System.out.println(qRNum);
		return "redirect:/qnaDetail.do?qNum="+qNum;
	}
	
	


	@RequestMapping(value="replyQna.do")
	public String qnaReplyInsert(@RequestParam("qNum") int qNum,Model model,@RequestParam("rCont") String rCont,
				@Valid @ModelAttribute("QnaReplyVO") QnaReplyVO qnaReplyVO,QnaVO qnaVO) {
		System.out.println("qna 댓글 controller");
		qnaReplyVO.setqNum(qNum);
		qnaReplyVO.setQrContent(rCont);
		model.addAttribute("QnaReplyVO", qnaReplyVO);
		//댓글insert
		qnaSerivce.ReplyInsert(qnaReplyVO);
		qnaVO.setqNum(qNum);
		//댓글상태변경
		qnaSerivce.updateState(qNum);
			return "redirect:/qnaDetail.do?qNum="+qNum;
	}
	
	//글 수정 페이지 이동
	@RequestMapping(value="qnaUpdate.do")
	public String qnaUpdate(@RequestParam("qNum") int qNum,Model model) {
		QnaVO qna = qnaSerivce.qnaDetail(qNum);
		String content1 = qna.getqContent().replace("<br>", "\r\n");
		qna.setqContent(content1);
		model.addAttribute("qna", qna);	
		return "qna/updateQna";
	}
	//글 수정 동작
	@RequestMapping(value = "QnaUpdate.do",method =RequestMethod.POST)
	public String QnaUpdate(HttpServletRequest request,@RequestParam("qNum") int qNum,@Valid @ModelAttribute("qna") QnaVO qnaVO,@RequestParam MultipartFile qFile ,Model model, Errors errors) {
		
		if(errors.hasErrors()){
			System.out.println("에러지롱");
			return "qna/qnaList";
		}	
		
		try {
			qFile = qnaVO.getqFile();
			System.out.println("qfile=" + qFile); //파일미선택도 객체 생성 (NULL)
			
			
			//1. 업된 파일처리 (0바이트 파일은 비저장 => 0바이트 파일을 업로드해도 리스트에 노출 안 됨) 
			if (qFile != null && qFile.getSize() != 0) { 
				String fileName = qFile.getOriginalFilename();// 업된 파일명
				
				// 파일명을 qnaVO의 setqImg에  설정
				qnaVO.setqImg(fileName);
				System.out.println(">>>>> 파일명을 VO에  설정");
				
				// upload폴더의 물리적인 폴더 절대경로
				String upath = uploadPath + "/" + "qnafile";
				System.out.println(upath);
				File file = new File(upath + "/" + fileName); // upload폴더 File 객체 생성
				qFile.transferTo(file); // 파일을  upload폴더로 복사
				System.out.println(fileName + " upath" + "에 저장");
				System.out.println("파일크기=" + qFile.getSize() + "바이트");
			}
			qnaVO.setqNum(qNum);
			//문의 내용 줄바꿈 처리
			String content2 = qnaVO.getqContent().replace("\r\n", "<br>");
			qnaVO.setqContent(content2);
			qnaSerivce.updateQna(qnaVO);
			
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage().toString());
			return "qna/qnaList";
		}

		return "redirect:/qnaDetail.do?qNum="+qNum;
	}
	
	
	
}
