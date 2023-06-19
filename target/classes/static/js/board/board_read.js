$(document).ready(function () {
  let originalParent = $(".comment-reply-container-wrap");
  let currentIdx = 0;
  let boardIdx = $("#boardIdx").val();
  let commentLevel = 1;
  let container = $(".comment-reply-container");

  $("#summernote").summernote({
    placeholder: "댓글을 입력해주세요.",
    tabsize: 4,
    height: 100,
    width: 400,
    toolbar: [
      ["style", ["style"]],
      ["font", ["bold", "italic", "underline", "clear"]],
      ["height", ["height"]],
    ],
    lang: "ko-KR",
  });

  $(document).on("click", ".comment-del-btn", function () {
    let commentIndex = $(this).data("comment-idx");

    Swal.fire({
      title: "댓글을 삭제하시겠습니까?",
      text: "삭제된 댓글은 복구할 수 없습니다.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "삭제",
      cancelButtonText: "취소",
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          url: "/comments/delete/" + commentIndex + "?boardIdx=" + boardIdx,
          type: "POST",
          data: {
            commentIdx: commentIndex,
            boardIdx: boardIdx,
          },
          success: function (data) {
            Swal.fire("삭제 완료!", "댓글이 삭제되었습니다.", "success").then(
              () => {
                location.reload();
              }
            );
          },
          error: function (e) {
            Swal.fire("삭제 실패!", "댓글 삭제에 실패하였습니다.", "error");
          },
        });
      }
    });
  });

  $(document).on("click", "#unlike-button", () => {
    $.ajax({
      url: "/board/unlike/" + boardIdx,
      type: "POST",
      data: {
        boardIdx: boardIdx,
      },
      success: function (data) {
        // 'like-button'을 삭제하고 'unlike-button'을 생성
        let likeButton = `
        <button type="button" class="btn btn-primary" id="like-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
            <path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"></path>
          </svg>
          추천
        </button>
      `;

        $("#unlike-button").replaceWith(likeButton);
      },
    });
  });

  $(document).on("click", "#del_btn", function () {
    Swal.fire({
      title: "게시글을 삭제하시겠습니까?",
      text: "삭제된 게시글은 복구할 수 없습니다.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "삭제",
      cancelButtonText: "취소",
    }).then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          url: "/board/delete/" + boardIdx,
          type: "POST",
          data: {
            boardIdx: boardIdx,
          },
          success: function (data) {
            if (data === 'success') {
              Swal.fire("삭제 완료!", "게시글이 삭제되었습니다.", "success").then(
                  () => {
                    location.href = "/board";
                  }
              );
            }
          },
          error: function (e) {
            Swal.fire("삭제 실패!", "게시글 삭제에 실패하였습니다.", "error");
          },
        });
      }
    });
  });

  $(document).on("click", "#like-button", () => {
    $.ajax({
      url: "/board/like/" + boardIdx,
      type: "POST",
      data: {
        boardIdx: boardIdx,
      },
      success: function (data) {
        // 'unlike-button'을 삭제하고 'like-button'을 생성
        let unlikeButton = `
        <button type="button" class="btn btn-outline-danger" id="unlike-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-down-fill" viewBox="0 0 16 16">
            <path d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"></path>
          </svg>
          추천 취소
        </button>
      `;
        $("#like-button").replaceWith(unlikeButton);
      },
    });
  });

  $("#edit_btn").on("click", () => {
    location.href = "/board/update/" + $("#boardIdx").val();
  });

  $(document).on("click", ".reply_comment_btn", function () {
    let replyContainer = $(".comment-reply-container");
    if ($(this).data("clicked")) {
      replyContainer.appendTo(originalParent);
      $(this).data("clicked", false);
      currentIdx = 0;
      commentLevel = 1;
      console.log(currentIdx);
    } else {
      let target = $(this).parent().parent().siblings(".comment-area");
      console.log(target);
      target.append(replyContainer);
      $(this).data("clicked", true);

      currentIdx = $(this).data("comment-idx");
      commentLevel = $(this).data("cl") + 1;

      console.log("commentLevel : " + commentLevel);
      console.log("currentIdx : " + currentIdx);
    }
  });

  $(document).on("click", ".comment-edit-btn", function () {
    let commentContent = $(this).parents().siblings(".comment-content").html();
    Swal.fire({
      title: "<strong>댓글 수정</strong>",
      icon: "info",
      html:
          '<div class="summernote-edit" id="summernote-edit" style="height: 400px;">' +
          commentContent +
          "</div>",
      showCloseButton: true,
      showCancelButton: true,
      focusConfirm: false,
      confirmButtonText: "수정",
      cancelButtonText: "취소",
    }).then((result) => {
      if (result.isConfirmed) {
        let editedComment = $("#summernote-edit").summernote("code");

        $.ajax({
          type: "POST",
          url: "/comments/update/" + $(this).data("comment-idx"),
          async: true,
          headers: {
            "Content-Type": "application/json",
          },
          dataType: "text",
          data: JSON.stringify({
            commentIdx: $(this).data("comment-idx"),
            commentContent: editedComment,
            boardIdx : boardIdx,

          }),
          error: function (request, status, error) {},
          success: function (comment) {
            console.log(comment);
            originalParent.append($(".comment-reply-container"));
            $("#comment-container").replaceWith(comment);
            $("#summernote-edit").summernote();
          },
        });
      }
    });
    $("#summernote-edit").summernote();
  });

  $("#comment_write_btn").on("click", () => {
    // i want to find a reply_comment_btn near this button
    $(".comment-reply-container").appendTo(originalParent);
    $.ajax({
      type: "post",
      url: "/comments/write",
      async: true,
      headers: {
        "Content-Type": "application/json",
        "X-HTTP-Method-Override": "POST",
      },
      dataType: "text",
      data: JSON.stringify({
        commentLevel: commentLevel,
        parentCommentIdx: currentIdx,
        boardIdx: boardIdx,
        commentContent: $("#summernote").summernote("code"),
        commentStatus: "등록",
      }),
      error: function (request, status, error) {},
      success: function (comment) {
        console.log(comment);
        $("#comment-container").replaceWith(comment);
        $("#summernote").summernote("code", "");
      },
    });
  });

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
