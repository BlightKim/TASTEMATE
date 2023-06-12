let eMailChk = false;

const eMailRegExp = RegExp(/^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/);

$('#eMail').change(check_email);
$('#eMail').keyup(check_email);
$('#eMail_change_btn').click(() => {
  if (!eMailChk === true) {
    alert("이메일을 다시 한번 확인해주세요.");
  } else {

    const eMail = $("#eMail").val();

    $.ajax({
      type: 'POST',
      url: '/register/addressChange',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        'eMail': eMail
      }, // 사용자가 입력한 id 데이터를 서버로 전송
      success: (data) => {
        console.log(data);
        if (data.result === 'success') {
          alert('이메일 변경이 완료되었습니다.');
          $('#show_eMail').val(data.eMail);
          $('#eMail').val('');
        }
      },
      error: (status, error) => {
        console.log('통신 실패');
        console.log(status, error);
      }
    });
  }
});

function check_email() {
  if ($('#eMail').val() === '') {
    $('#eMail_warning').removeAttr('hidden', 'hidden');
    $('#eMail_warning').html(
        '<b style="font-size: 14px; color:red">[이메일은 필수 정보입니다.]</b>');
    eMailChk = false;
  } else if (!eMailRegExp.test($("#eMail").val())) {
    $('#eMail_warning').removeAttr('hidden', 'hidden');
    $('#eMail_warning').html(
        '<b style="font-size: 14px; color: red">[올바른 형식의 이메일을 입력해주세요.]</b>')
    eMailChk = false;
  } else {
    $('#eMail_warning').attr('hidden', 'hidden');
    eMailChk = true;
  }
}