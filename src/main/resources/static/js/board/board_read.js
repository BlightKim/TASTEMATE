$(document).ready(function () {
  let wholeComment = $("#collapseExample").children();
  console.log(wholeComment);
  let originalParent = $("#reply_comment_area").parent();
  let currentIdx = 0;
  let boardIdx = $("#boardIdx").val();
  let commentLevel = 1;

  $("#summernote").summernote({
    placeholder: "Hello Bootstrap 4",
    tabsize: 4,
    height: 200,
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
        success: function () {
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
        success: function () {
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
      success: function () {
        alert("삭제되었습니다.");
        location.href = "/board/list";
      },
    });
  });

  $(document).on("click", ".reply_comment_btn", function () {
    let commentArea = $("#reply_comment_area");
    if ($(this).data("clicked")) {
      commentArea.appendTo(originalParent);
      $(this).data("clicked", false);
      currentIdx = 0;
      commentLevel = 1;
      console.log(currentIdx);
    } else {
      currentIdx = $(this).data("idx");
      commentLevel = $(this).data("cl") + 1;

      commentArea.appendTo(`#comment_${currentIdx}_reply_area`);
      // commentArea.insertAfter($(this).closest('.main-comments'));
      $(".reply_comment_btn").data("clicked", false);
      $(this).data("clicked", true);

      console.log("commentLevel : " + commentLevel);
      console.log("currentIdx : " + currentIdx);
    }
  });

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
      error: function (request, status, error) {},
      success: function (comment) {
        let closest_comment_btn = $(this).find("#reply_comment_area");

        // console.log(comment.parentCommentIdx);
        appendComment(comment);
      },
    });
  });

  function appendComment(commentList) {
    let commentContent = "";
    commentList.forEach((comment) => {
      let comment_content = comment.commentContent;
      let commentClass =
        comment.commentLevel === 1
          ? "col-12 p-4 main-comments"
          : comment.commentLevel === 2
          ? "col-12 p-4 sub-comments"
          : "col-12 p-4 sub-sub-comments";
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
            <button type="button" class="btn btn-outline-secondary reply_comment_btn" data-idx="${
              comment.commentIdx
            }" data-cl="${comment.commentLevel}">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-reply" viewBox="0 0 16 16">
                <path d="M6.598 5.013a.144.144 0 0 1 .202.134V6.3a.5.5 0 0 0 .5.5c.667 0 2.013.005 3.3.822.984.624 1.99 1.76 2.595 3.876-1.02-.983-2.185-1.516-3.205-1.799a8.74 8.74 0 0 0-1.921-.306 7.404 7.404 0 0 0-.798.008h-.013l-.005.001h-.001L7.3 9.9l-.05-.498a.5.5 0 0 0-.45.498v1.153c0 .108-.11.176-.202.134L2.614 8.254a.503.503 0 0 0-.042-.028.147.147 0 0 1 0-.252.499.499 0 0 0 .042-.028l3.984-2.933zM7.8 10.386c.068 0 .143.003.223.006.434.02 1.034.086 1.7.271 1.326.368 2.896 1.202 3.94 3.08a.5.5 0 0 0 .933-.305c-.464-3.71-1.886-5.662-3.46-6.66-1.245-.79-2.527-.942-3.336-.971v-.66a1.144 1.144 0 0 0-1.767-.96l-3.994 2.94a1.147 1.147 0 0 0 0 1.946l3.994 2.94a1.144 1.144 0 0 0 1.767-.96v-.667z"></path>
              </svg>
              <span class="visually-hidden">댓글 달기</span>
            </button>
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
