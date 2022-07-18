package com.JMThouseWeb.JMThouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.JMThouseWeb.JMThouse.model.User;
import com.JMThouseWeb.JMThouse.service.LikeHouseService;
import com.JMThouseWeb.JMThouse.service.ReviewService;
import com.JMThouseWeb.JMThouse.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private LikeHouseService likeHouseService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping({ "", "/" })
	public String home() {
		return "home";
	}

	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}

	@GetMapping("/auth/join_form")
	public String joinForm(User user) {
		return "user/join_form";
	}

	@GetMapping("/auth/update_form")
	public String updateForm() {
		return "user/update_user_form";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/";
	}

	@PostMapping("/auth/joinProc")
	public String saveUser(User user) {
		userService.saveUser(user);
		return "redirect:/";
	}

	@GetMapping("/reservation-history/{guestId}")
	public String reservationHistory(@PathVariable int guestId) {
		return "user/reservation_history";
	}

	// test
	@GetMapping("/test")
	public String test() {
		return "user/my_review_list_form";
	}

	// 위시리스트 페이지 호출
	@GetMapping("/wish-list/{guestId}")
	public String getWishList(@PathVariable int guestId, Model model) {
		model.addAttribute("wishList", likeHouseService.getWishListById(guestId));
		return "user/wish_list_form";
	}

	// 나의 리뷰 관리 페이지 호출
	@GetMapping("/my-review/{guestId}")
	public String getReviewList(@PathVariable int guestId, Model model) {
		model.addAttribute("reviews", reviewService.getReviewListByGuestId(guestId));
		return "user/my_review_list_form";
	}
}
