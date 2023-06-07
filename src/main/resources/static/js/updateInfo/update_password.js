let pwd1Chk = false;
let pwd2Chk = false;

const pwdRegExp = RegExp(
    /([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/);

$('#password1').change(checkPassword);
$('#password1').keyup(checkPassword);

$('#password2').change(checkPassword);
$('#password2').keyup(checkPassword);

$('#password_change_btn').click(() => {
  if (!(pwd1Chk === true && pwd2Chk === true)) {
    alert("비밀번호를 다시 한번 확인해주세요.");
  } else {

    const password = $("#password1").val();

    $.ajax({
      type: 'POST',
      url: '/register/passwordChange',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        'password': password
      }, // 사용자가 입력한 id 데이터를 서버로 전송
      success: (data) => {
        console.log(data);
        if (data === 'Success') {
          alert('비밀번호 변경이 완료되었습니다.\n'
              + '다시 로그인 해주세요.');
          location.href = '/login/controller?type=logOut';
        }
      },
      error: (status, error) => {
        console.log('통신 실패');
        console.log(status, error);
      }
    });
  }
});

function checkPassword() {
  if ($('#password1').val() === '') {
    $('#password1_warning').removeAttr('hidden', 'hidden');
    $('#password1_warning').html(
        '<b style="font-size: 14px; color:red">[비밀번호는 필수 정보입니다.]</b>');
    pwd1Chk = false;
  } else if (!pwdRegExp.test($('#password1').val()) || $(
      '#password1').val().length < 8) {
    $('#password1_warning').removeAttr('hidden');
    $('#password1_warning').html(
        '<b style="font-size: 14px; color:red">[비밀번호는 특수문자 포함 8자 이상입니다.]</b>');
    pwd1Chk = false;
  } else {
    $('#password1_warning').attr('hidden', 'hidden');
    pwd1Chk = true;
  }

  if ($('#password2').val() === '') {
    $('#password2_warning').removeAttr('hidden', 'hidden');
    $('#password2_warning').html(
        '<b style="font-size: 14px; color:red">[비밀번호는 필수 정보입니다.]</b>');
    pwd2Chk = false;
  } else if ($('#password2').val() !== $('#password1').val()) {
    $('#password2_warning').removeAttr('hidden', 'hidden');
    $('#password2_warning').html(
        '<b style="font-size: 14px; color:red">[입력한 비밀번호가 일치하지 않습니다.]</b>');
  } else {
    $('#password2_warning').attr('hidden', 'hidden');
    pwd2Chk = true;
  }
}