package com.seven.team01.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seven.team01.service.ReplyService;
import com.seven.team01.vo.ReplyVO;
import com.seven.team01.vo.UserVO;


@Controller
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping(value="/re_reply.do",method = RequestMethod.POST)
	public String insertReReply(@RequestParam int rGCode, ReplyVO reply,  Model model, HttpSession session) throws Exception {
			
			UserVO user = (UserVO)session.getAttribute("user"); 
			System.out.println("ReplyController_re_reply");
			// 10/31 기능수정(유나)			
			if(user == null){
				System.out.println("아이디가 없다!");
				return "redirect:/goodDetail?gCode=" + rGCode;
			}
			
			else { 
				reply.setrUId(user.getuId());
				//줄바꿈 처리
				String content = reply.getrCont().replace("\r\n", "<br>");
				reply.setrCont(content);
				replyService.insertReReply(reply);
			}
			return "redirect:/goodDetail?gCode="+reply.getrGCode();
		}
		
	
}
