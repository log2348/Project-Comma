package com.JMThouseWeb.JMThouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.JMThouseWeb.JMThouse.dto.RequestPostDto;
import com.JMThouseWeb.JMThouse.model.House;
import com.JMThouseWeb.JMThouse.service.HouseService;

@Controller
@RequestMapping("/house")
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	
	// 숙소 리스트 페이지 호출
	@GetMapping("/list")
	public String getHouseList(Model model) {
		List<House> houseList = houseService.getHouseList();
		model.addAttribute("houseList", houseList);
		return "house/list_form";
	}
	
	// 숙소 상세정보 페이지 호출
	@GetMapping("/detail/{houseId}")
	public String getHouseDetail(@PathVariable int houseId, Model model) {
		model.addAttribute("house", houseService.getHouseDetail(houseId));
		return "house/detail_form";
	}
	
	// 숙소 등록 페이지 호출
	@GetMapping("/post_form")
	public String getPostingForm() {
		return "house/post_form";
	}

	// 숙소 글 수정 페이지 호출
	@GetMapping("/update_form")
	public String getUpdateForm() {
		return "house/update_form";
	}
	
	@PostMapping("/image/upload")
	public String postHouse(RequestPostDto requestPostDto) {
		houseService.postHouse(requestPostDto);
		return "redirect:/house/list";
	}
	
}
