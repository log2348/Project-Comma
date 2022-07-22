let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

let index = {

	init: function() {
		$("#btn-reply").bind("click", () => {
			this.addReply();
		});

		$("#edit-btn").bind("click", () => {
			this.editText();
		});

		$("#btn-delete").bind("click", () => {
			this.deleteReply();
		});

	},

	addReply: function() {
		let reviewId = $("#review-id").text();
		let houseId = $("#house-id").text();

		console.log("houseId : " + houseId);

		let data = {
			content: $("#content").val()
		}

		if (data.content == "" || data.content.trim() === "") {
			alert("내용을 입력하세요.");
		} else {
			$.ajax({
				beforeSend: function(xhr) {
					console.log("xhr: " + xhr)
					xhr.setRequestHeader(header, token)
				},

				type: "POST",
				url: "/review/reply/" + reviewId,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(response) {
				if (response.status == 200) {
					appendReply(response.data);
					alert("댓글이 등록되었습니다.");
					location.href = "/review/management/" + houseId
				}
			}).fail(function(error) {
				alert("댓글이 등록되지 않았습니다.");
				console.log(error);
			});
		}
	},

	updateReply: function() {
		let replyId = $("#reply-id").val();

		let data = {
			content: $("#content").val()
		}

		if (data.content == "" || data.content.trim() === "") {
			alert("내용을 입력하세요.")
		} else {
			$.ajax({
				beforeSend: function(xhr) {
					console.log("xhr: " + xhr)
					xhr.setRequestHeader(header, token)
				},

				type: "PUT",
				url: "/review/reply/" + replyId,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(response) {
				if (response.status == 200) {
					alert("댓글이 수정되었습니다.")
				}

			}).fail(function(error) {
				alert("댓글이 수정되지 않았습니다.");
				console.log(error);
			});

		}

	},

	deleteReply: function() {
		let replyId = $("#reply-id").val();
		let houseId = $("#house-id").val();
		let deleteCheck = confirm("삭제하시겠습니까?");

		if (deleteCheck) {
			$.ajax({
				beforeSend: function(xhr) {
					console.log("xhr: " + xhr)
					xhr.setRequestHeader(header, token)
				},

				type: "DELETE",
				url: "/review/reply/" + replyId,
			}).done(function(response) {
				if (response.status == 200) {
					alert("댓글이 삭제되었습니다.");
					location.href = "/review/management/" + houseId;
				} else {
					alert("댓글이 삭제되지 않았습니다.")
				}
			}).fail(function(error) {
				alert("댓글이 삭제되지 않았습니다.");
				console.log(error);
			});
		}
	}
}

function editText() {

	console.log("수정 버튼 클릭");

	//let editForm = '<textarea class="form-control" rows="3" id="reply-edit-box" placeholder="게스트의 리뷰에 대한 답글을 남겨주세요!"></textarea>';

	//$('#reply-edit-box').replaceWith(editForm);
	//$('#reply-edit-box').focus();

}


function appendReply(reply) {

	let childElement = `<div class=" d-flex">
																		<div class="ms-3">
																			<p>${reply.content}</p>
																			<button
																				class="btn btn-outline-danger btn-sm float-right"
																				style="margin-left: 10px;" id="btn-delete">삭제</button>
																			<a class="btn btn-outline-primary btn-sm float-right"
																				id="btn-update" href="">수정</a>
																		</div>
																	</div>
																	<br>`;

	$("#reply--box").append(childElement);
	$("#content").val("");
}

index.init();
