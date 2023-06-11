$(document).ready(function() {
  let originalParent = $(".comment-reply-container-wrap")
  let currentIdx = 0;
  let boardIdx = $("#boardIdx").val();
  let commentLevel = 1;
  let container = $('.comment-reply-container')

  $("#summernote").summernote({
    placeholder: "Hello Bootstrap 4",
    tabsize: 4,
    height: 100,
    width: 400,
    toolbar: [
      ['style', ['style']],
      ['font', ['bold', 'italic', 'underline', 'clear']],
      ['height', ['height']],
    ],
    lang: "ko-KR"
  });

  $("#like-button").on("click", () => {
    if (isLiked) {
      // 이미 추천한 상태이므로 'unlike' 요청
      $.ajax({
        url: "/board/unlike/" + boardIdx, // 수정 필요: 실제 'unlike' 요청을 처리하는 URL로 변경
        type: "POST",
        data: {
          boardIdx: boardIdx,
        },
        success: function() {
          isLiked = false;
          // 버튼 텍스트를 '추천'으로 변경
          $("#like-button").text("추천");
        },
      });
    } else {
      // 추천하지 않은 상태이므로 'like' 요청
      $.ajax({
        url: "/board/like/" + boardIdx, // 수정 필요: 실제 'like' 요청을 처리하는 URL로 변경
        type: "POST",
        data: {
          boardIdx: boardIdx,
        },
        success: function() {
          isLiked = true;
          // 버튼 텍스트를 '추천 취소'로 변경
          $("#like-button").text("추천 취소");
        },
      });
    }
  });

  $("#edit_btn").on("click", () => {
    location.href = "/board/update/" + $("#boardIdx").val();
  });

  $("#del_btn").on("click", () => {
    $.ajax({
      url: "/board/delete/" + $("#boardIdx").val(),
      type: "POST",
      success: function() {
        alert("삭제되었습니다.");
        location.href = "/board";
      },
    });
  });

  $("#comment_del_btn").on("click", () => {
    $.ajax({
      url: "/comments/delete/" + $("#commentIdx").val() + "?boardIdx=" + $("#boardIdx").val(),
      type: "delete",
      success: function() {
        Swal.fire({
          icon: "success",
          title: "댓글 삭제 완료",
          text: "댓글 삭제가 완료되었습니다",
        });
        location.reload();
      }
    });
  });

  $(document).on("click", ".reply_comment_btn", function() {
    let replyContainer = $(".comment-reply-container");
    if ($(this).data("clicked")) {
      replyContainer.appendTo(originalParent);
      $(this).data("clicked", false);
      currentIdx = 0;
      commentLevel = 1;
      console.log(currentIdx);
    } else {
      let target = $(this).parent().parent().siblings('.comment-area');
      target.append(replyContainer);
      $(this).data("clicked", true);

      currentIdx = $(this).data("idx");
      commentLevel = $(this).data("cl") + 1;

      console.log("commentLevel : " + commentLevel);
      console.log("currentIdx : " + currentIdx);
    }
  });

/*  $(".reply_comment_btn").click(function() {
    // 현재 클릭한 버튼의 부모 요소 다음에 있는 댓글 입력창을 찾습니다.
    var commentInput = $(this).parent().next(".comment-input");

    // 만약 댓글 입력창이 이미 존재한다면,
    if (commentInput.length > 0) {
      // 댓글 입력창을 제거합니다.
      commentInput.remove();
    } else {
      // 그렇지 않다면, 새로운 댓글 입력창을 복제하고 추가합니다.
      commentInput = $(".comment-input").first().clone();
      commentInput.show();  // 복제된 댓글 입력창을 보이게 합니다.
      $(this).parent().after(commentInput);  // 현재 클릭한 버튼의 부모 요소 다음에 댓글 입력창을 추가합니다.
    }
  });*/

  $("#comment_write_btn").on("click", () => {
    // i want to find a reply_comment_btn near this button
    $("#reply_comment_area").appendTo(originalParent);

    $.ajax({
      type: "post",
      url: "/comments/write",
      async: true,
      headers: {
        "Content-Type": "application/json",
        "X-HTTP-Method-Override": "POST",
      },
      dataType: "json",
      data: JSON.stringify({
        commentLevel: commentLevel,
        parentCommentIdx: currentIdx,
        boardIdx: boardIdx,
        commentContent: $("#summernote").summernote("code"),
        commentStatus: "등록",
      }),
      error: function(request, status, error) {},
      success: function(comment) {
        let closest_comment_btn = $(this).find("#reply_comment_area");

        // console.log(comment.parentCommentIdx);
        appendComment(comment, currentUser);
      },
    });
  });

  function appendComment(commentList, currentUser) {
    let commentContent = "";
    commentList.forEach((comment) => {
      let commenter = comment.commenter;
      let comment_content = comment.commentContent;
      let commentClass =
          comment.commentLevel === 1 ?
              "col-12 p-4 main-comments" :
              comment.commentLevel === 2 ?
                  "col-12 p-4 sub-comments" :
                  "col-12 p-4 sub-sub-comments";

      let editDeleteButtons = "";
      console.log(currentUser);
      if (`${commenter}` === currentUser) {
        editDeleteButtons = `<button
                                type="button"
                                class="btn btn-success ml-2"
                                id="comment_edit_btn"
                              >
                                <svg
                                  xmlns="http://www.w3.org/2000/svg"
                                  width="16"
                                  height="16"
                                  fill="currentColor"
                                  class="bi bi-pencil-fill"
                                  viewBox="0 0 16 16"
                                >
                                  <path
                                    d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10Z"
                                  ></path>
                                </svg>
                                수정
                              </button>
                              <button
                                type="button"
                                class="btn btn-danger ml-2"
                                data-toggle="modal"
                                data-target="#commentDeleteModal"
                              >
                                <svg
                                  xmlns="http://www.w3.org/2000/svg"
                                  width="16"
                                  height="16"
                                  fill="currentColor"
                                  class="bi bi-trash-fill"
                                  viewBox="0 0 16 16"
                                >
                                  <path
                                    d="M5.5 5.5a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0v-6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0v-6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0v-6a.5.5 0 0 1 .5-.5z"
                                  ></path>
                                  <path
                                    fill-rule="evenodd"
                                    d="M1 2a1 1 0 0 1 1-1h10a1 1 0 0 1 1 1v1a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2zm14 3a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h13z"
                                  ></path>
                                </svg>
                                삭제
                              </button>
        `;
      }

      commentContent += `
      <div class="${commentClass}" style="width: 80%; height: auto; text-align: justify; position: relative; background-color: #f9f9f9; padding: 10px; border-radius: 8px;" data-parentIdx="${
          comment.parentCommentIdx
      }">
        <div class="row align-items-center">
          <div class="col-auto">
           
          </div>
          <div class="col">
            <span style="font-weight: bold;">${comment.commenter}</span>
          </div>
          <div class="col-auto">
            <i class="bi bi-clock"></i>
            &nbsp; &nbsp; ${formatDate(comment.regDate)}
          </div>
          <div class="col-auto ms-auto">
           <button
           type="button"                              
           class="btn btn-outline-secondary reply_comment_btn"
           data-idx="${comment.commentIdx}"
           data-cl="${comment.commentLevel}"
           >
           <svg
           xmlns="http://www.w3.org/2000/svg"
           width="16"
           height="16"
           fill="currentColor"
           class="bi bi-reply"
           viewBox="0 0 16 16"
           >
           <path
           d="M6.598 5.013a.144.144 0 0 1 .202.134V6.3a.5.5 0 0 0 .5.5c.667 0 2.013.005 3.3.822.984.624 1.99 1.76 2.595 3.876-1.02-.983-2.185-1.516-3.205-1.799a8.74 8.74 0 0 0-1.921-.306 7.404 7.404 0 0 0-.798.008h-.013l-.005.001h-.001L7.3 9.9l-.05-.498a.5.5 0 0 0-.45.498v1.153c0 .108-.11.176-.202.134L2.614 8.254a.503.503 0 0 0-.042-.028.147.147 0 0 1 0-.252.499.499 0 0 0 .042-.028l3.984-2.933zM7.8 10.386c.068 0 .143.003.223.006.434.02 1.034.086 1.7.271 1.326.368 2.896 1.202 3.94 3.08a.5.5 0 0 0 .933-.305c-.464-3.71-1.886-5.662-3.46-6.66-1.245-.79-2.527-.942-3.336-.971v-.66a1.144 1.144 0 0 0-1.767-.96l-3.994 2.94a1.147 1.147 0 0 0 0 1.946l3.994 2.94a1.144 1.144 0 0 0 1.767-.960v-.667z"
           ></path>
           </svg>
           <span class="visually-hidden">댓글 달기</span>
           </button>
            ${editDeleteButtons}
          </div>
        </div>
        <div class="row no-border">
          <div class="col-12 p-4 comment-content" style="width: 100%; height: auto; text-align: justify; margin-top: 10px; background-color: #f7f7f7; border: none;">
            ${comment_content}
          </div>
        </div>
        <div id="comment_${comment.commentIdx}_reply_area"></div>
      </div>
    `;
    });
    $("#collapseExample").html(commentContent);
  }

  function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");

    return `${year}-${month}-${day} ${hours}:${minutes}`;
  }

  function escapeHtml(unsafe) {
    return unsafe
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
  }
});