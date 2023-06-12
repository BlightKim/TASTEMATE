$(document).ready(function() {
  $('#summernote').summernote({
    placeholder: '본문을 입력해주세요',
    tabsize: 6,
    height: 300
  });

  $('#cancel_btn').on('click', function() {
    location.href = '/board';
  });
})