$(document).ready(function () {
  $('')
  $('.search_btn_color').on('click', function () {
    let keyword = $('input[name="keyword"]').val();
    let option = $('select[name="option"]').val();
    if(keyword == "" || keyword == null) {
      Swal.fire("검색어를 입력해주세요.", "", "warning");
      return false;
    }

    location.href = "/board?keyword=" + keyword + "&option=" + option;
  });
});