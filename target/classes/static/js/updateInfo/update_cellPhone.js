const cellPhoneRegExp = RegExp(/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/);

let cellPhoneChk = false;

$('#cellPhone').keyup(check_cellphone);
$('#cellPhone_change_btn').click(() => {
  if(!cellPhoneChk === true) {
    alert("전화번호를 다시 한번 확인해주세요.");
  } else {

    const cellPhone = $("#cellPhone").val();

    $.ajax({
      type: 'POST',
      url: '/register/cellPhoneChange',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        'cellPhone': cellPhone
      }, // 사용자가 입력한 id 데이터를 서버로 전송
      success: (data) => {
        console.log(data);
        if (data.result === 'success')
        {
          alert('전화번호 변경이 완료되었습니다.');
          $('#show_cellPhone').val(data.cellPhone);
          $('#cellPhone').val('');
        }
      },
      error: (status, error) => {
        console.log('통신 실패');
        console.log(status, error);
      }
    });
  }
});

function check_cellphone() {
  if ($('#cellPhone').val() === '') {
    $('#cellPhone_warning').removeAttr('hidden', 'hidden');
    $('#cellPhone_warning').html('<b style="font-size: 14px; color:red">[휴대전화는 필수 정보입니다.]</b>');
    cellPhoneChk = false;
    $('.sendSms').attr('hidden', 'hidden');
  } else if (!cellPhoneRegExp.test($("#cellPhone").val())) {
    $('#cellPhone_warning').removeAttr('hidden', 'hidden');
    $('#cellPhone_warning').html(
        '<b style="font-size: 14px; color: red">[올바른 형식의 휴대폰 번호를 입력해주세요.]</b>')
    let text = $('#cellPhone').val();
    text = text.replace(/[^0-9]/g, '').replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g,
        "$1-$2-$3").replace(/(\-{1,2})$/g, "");
    $('#cellPhone').val(text);
    cellPhoneChk = false;
    // $('.sendSms').attr('hidden', 'hidden');
  } else {
    $('#cellPhone_warning').attr('hidden', 'hidden');
    cellPhoneChk = true;
  }
}