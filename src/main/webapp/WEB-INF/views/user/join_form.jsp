<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<title>회원가입 페이지</title>
<link rel="stylesheet" href="/fonts/material-icon/css/material-design-iconic-font.min.css" />
<link rel="stylesheet" href="/css/user/style.css" />
<div class="main">
	<section class="signup">
		<div class="container-signup">
			<div class="signup-content">
				<div class="signup-form needs-validation">
					<h2 class="form-title">회원가입</h2>
					<form action="/auth/joinProc" method="post" onsubmit="return joinCheck()" class="register-form" id="register-form">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
						<p id="usernameCheckResult"></p>
						<div class="form-group">
							<label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label> <input type="text" name="username" id="username" placeholder="Username" required />
						</div>
						<button type="button" id="btn-checkUsername" class="custom-sm-btn">중복확인</button>
						<br />

						<p id="passwordCheck" style="color: rgb(203, 0, 0)"></p>
						<div class="form-group" data-validate="Password is required">
							<label for="password"><i class="zmdi zmdi-lock-outline"></i></label> <input type="password" name="password" id="password" placeholder="Password" required />
						</div>
						<div class="form-group">
							<label for="re-password"><i class="zmdi zmdi-lock-outline"></i></label> <input type="password" name="re-password" id="re-password" placeholder="Repeat your password" required />
						</div>

						<div class="form-group">
							<label for="email"><i class="zmdi zmdi-email"></i></label> <input type="email" name="email" id="email" placeholder="Your Email" />
						</div>

						<p id="phoneNumberCheck"></p>
						<div class="form-group">
							<label for="email"><i class="zmdi zmdi-phone"></i></label> <input type="text" name="phoneNumber" id="phoneNumber" placeholder="Your Phone Number" />
						</div>
						<button type="submit" class="custom-btn">회원가입</button>
					</form>
				</div>

				<div class="signup-image">
					<figure>
						<img src="/assets/images/img-01.png" style="margin-top: 50px;" />
					</figure>
					<br /> <br /> <a href="/auth/login_form" class="signup-image-link">이미 회원이신가요?</a>
				</div>
			</div>
		</div>
	</section>
</div>


<script>
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");

	let usernameCheck = false;
	$("#btn-checkUsername").bind("click", function() {
		let data = {
			username : $("#username").val(),
		};
		console.log(data);
		$.ajax({

			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token)
			},
			type : "POST",
			url : "/auth/username-check",
			data : JSON.stringify(data),
			contentType : "application/json; charset=utf-8",
			dataType : "json",
		}).done(function(response) {
			console.log(response);
			if (response.data.username != null) {
				document.getElementById('usernameCheckResult').style.color = "red";
				$("#usernameCheckResult").text("이미 사용 중인 아이디입니다.");
			} else {
				usernameCheck = true;
				document.getElementById('usernameCheckResult').style.color = "blue";
				$("#usernameCheckResult").text("사용 가능한 아이디입니다.");
			}
		}).fail(function(error) {
			console.log(error);
		});
	});

	$(document)
			.ready(
					function() {
						// keyup
						// 사용자가 키보드의 키를 눌렀다가 떼었을 때 발생
						$("#password, #re-password")
								.keyup(
										function() {
											let password = $("#password").val();
											let passwordCheck = $(
													"#re-password").val();
											if (password.trim() != ""
													&& passwordCheck.trim() != "") {
												if (password != passwordCheck) {
													$("#passwordCheck").text(
															"비밀번호가 일치하지 않습니다.");
													document
															.getElementById('passwordCheck').style.color = "red";
													return false;
												} else if (password == passwordCheck) {

													if (password.length < 8 || password.length > 16) {
														$("#passwordCheck")
																.text(
																		"비밀번호는 8~16자여야 합니다.");
														document
														.getElementById('passwordCheck').style.color = "red";
														return false;
													} else {
														$("#passwordCheck").text(
														"");
													}

												}
											}

										});
					});

	function joinCheck() {
		let password = $("#password").val();
		let passwordCheck = $("#re-password").val();
		let phoneNumber = $("#phoneNumber").val();

		if (usernameCheck == false) {
			document.getElementById('usernameCheckResult').style.color = "red";
			$("#usernameCheckResult").text("아이디 중복 체크를 해주세요.");
			return false;
		}

		if (phoneNumber.indexOf("-") != -1) {
			document.getElementById('phoneNumberCheck').style.color = "red";
			$("#phoneNumberCheck").text("하이픈(-)을 제외한 번호를 입력해주세요.");
			return false;
		}

		if (phoneNumber.length != 11) {
			document.getElementById('phoneNumberCheck').style.color = "red";
			$("#phoneNumberCheck").text("휴대폰 번호 11자리를 입력해주세요.");
			return false;
		}

		if (phoneNumber.charAt(0) != 0 || phoneNumber.charAt(1) != 1
				|| phoneNumber.charAt(2) != 0) {
			document.getElementById('phoneNumberCheck').style.color = "red";
			$("#phoneNumberCheck").text("휴대폰 번호의 형식은 010으로 시작해야 합니다.");
			return false;
		}

	}
</script>

<%@ include file="../layout/footer.jsp"%>