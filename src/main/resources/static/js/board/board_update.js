$(document).ready(() => {
  $("#summernote").summernote({
    placeholder: "Hello Bootstrap 4",
    tabsize: 4,
    height: 300,
  });

  $("#del_file_btn").on("click", () => {
    deleteFile();
  });

  $("#back-btn").on("click", () => {
    history.back();
  });
  //파일 변경 체크
  $("input[type='file']").change(() => {
    let formData = new FormData();

    if (isExistingFile()) {
      Swal.fire({
        icon: "warning",
        title: "이미 파일이 존재하고 있습니다.",
        text: "파일 업로드를 원하시려면 기존 파일을 삭제한 후 다시 업로드 해주세요.",
      });
      $("input[type='file']").val("");
      return false;
    }

  });

  $('#update_btn').on('click', () => {
    $('#updateForm').submit();
  });
  function deleteFile() {
    let del_fileArea = document.getElementById("del_file_area");
    let parentNode = del_fileArea.parentNode;
    parentNode.removeChild(del_fileArea);
  }

  function isExistingFile() {
    let del_fileArea = document.getElementById("del_file_area");
    console.log(del_fileArea);
    if (del_fileArea == null) {
      return false;
    } else {
      return true;
    }
  }
});
