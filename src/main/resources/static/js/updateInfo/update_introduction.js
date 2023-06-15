let introductionChk = false;

$('#introduction').keyup(check_introduction);

$('#introduction_change_btn').click(() => {
  if(!introductionChk === true) {
    alert("자기소개를 다시 한번 확인해주세요.");
  } else {

    let introduction = $("#introduction").val();

    introduction = introduction.replace(/(?:\r\n|\r|\n)/g, '<br />');
    console.log(introduction);

    $.ajax({
      type: 'POST',
      url: '/register/introductionChange',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        'introduction': introduction
      }, // 사용자가 입력한 id 데이터를 서버로 전송
      success: (data) => {
        console.log(data);
        if (data.result === 'success')
        {
          alert('자기 소개 변경이 완료되었습니다.');
          $('#show_introduction').val((data.introduction).replaceAll("<br />", "\r\n"));
          $('#introduction').val('');
        }
      },
      error: (status, error) => {
        console.log('통신 실패');
        console.log(status, error);
      }
    });
  }
});
function check_introduction() {
  if ($('#introduction').val() === '') {
    $('#introduction_warning').removeAttr('hidden', 'hidden');
    $('#introduction_warning').html('<b style="font-size: 14px; color:red">[자기소개를 입력해주세요]</b>');
    introductionChk = false;
  } else {
    $('#introduction_warning').attr('hidden', 'hidden');
    introductionChk = true;
  }
}
