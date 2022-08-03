let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

let index = {

	init: function() {
		$("#btn-update").bind("click", () => {
			this.updateReview();
		});

		$("#btn-delete").bind("click", () => {
			this.deleteReview();
		});

		$("#btn-report-reply").bind("click", () => {
			this.reportReply();
		});


	},

	updateReview: function() {
		let starScore;
		let starLength = $("#rating").length;
		let reviewId = $("#review-id").val();

		for (let i = 0; i < starLength; i++) {
			if ($("#rating")[i].checked == true) {
				starScore = $("#rating")[i].val();
			}
		}

		let data = {
			content: $("#content").text(),
			starScore
		}
		console.log("콘텐츠 확인" + data.content);
		console.log("별점 확인" + data.starScore);
		console.log("별점 수 확인" + starLength);

		if (data.content == "" || data.content.trim() == "") {
			alert("내용을 입력하세요.")
		} else {
			$.ajax({
				beforeSend: function(xhr) {
					console.log("xhr: " + xhr)
					xhr.setRequestHeader(header, token)
				},

				type: "PUT",
				url: "/review/" + reviewId,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(response) {
				if (response.status == 200) {
					alert("리뷰가 수정되었습니다.")
				} else {
					alert("리뷰가 수정되지 않았습니다.");
				}
			}).fail(function(error) {
				alert("리뷰가 수정되지 않았습니다.");
				console.log(error);
			});
		}

	},

	deleteReview: function(reviewId) {
		let guestId = $("#guest-id").val();
		let deleteCheck = confirm("삭제하시겠습니까?");
		console.log(reviewId);

		if (deleteCheck) {
			$.ajax({

				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token)
				},

				type: "DELETE",
				url: "/review/" + reviewId,
			}).done(function(response) {
				if (response.status == 200) {
					alert("리뷰가 삭제되었습니다.");
					location.href = "/review/my-review-list/" + guestId;
				} else {
					alert("리뷰가 삭제되지 않았습니다.");
				}
			}).fail(function(error) {
				alert("리뷰가 삭제되지 않았습니다.");
				console.log(error);
			});
		}
	},

	reportReply: function() {
		let replyId = $("#reply-id").val();
		console.log(replyId);

		let data = {
			reportType: $("#report-type").val(),
			detailText: $("#detail-text").val()
		}
		console.log("data : " + data.reportType);

		if (data.reportType == "") {
			alert("신고 유형을 선택하셔야 합니다.");
		} else {
			$.ajax({
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token)
				},

				type: "POST",
				url: "/guest/report/" + replyId,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(response) {
				if (response.status == 200) {
					alert("신고가 접수되었습니다.");
					document.getElementById("report-type").value = "";
					document.getElementById("detail-text").value = "";
					document.getElementById("close").click();
				} else {
					alert("신고가 접수되지 않았습니다.");
				}
			}).fail(function(error) {
				alert("신고가 접수되지 않았습니다.");
				console.log(error);
			});
		}
	}

}

index.init();
