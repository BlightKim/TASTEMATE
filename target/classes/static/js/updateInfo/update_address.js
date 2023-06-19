let addressChk = false;
$('#member_post, #member_addr').click(findArr);
$('#member_post, #member_addr, #detailed_address').change(checkAddr);
$('#address_change_btn').click(update_address);

function checkAddr() {
  $('#address_warning').removeAttr('hidden');
  if ($('#member_post').val() === '') {
    $('#address_warning').html(
        '<b style="font-size: 14px; color:red">[주소는 필수항목입니다.]</b>');
    addressChk = false;
  } else if ($('#member_addr').val() === '') {
    $('#address_warning').html(
        '<b style="font-size: 14px; color:red">[주소는 필수항목입니다.]</b>');
    addressChk = false;
  } else if ($('#detailed_address').val() === '') {
    $('#address_warning').html(
        '<b style="font-size: 14px; color:red">[주소는 필수항목입니다.]</b>');
    addressChk = false;
  } else {
    $('#address_warning').attr('hidden', 'hidden');
    addressChk = true;
  }
}

function findArr() {
  new daum.Postcode({
    oncomplete: function (data) {
      console.log(data);
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
      // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var roadAddr = data.roadAddress; // 도로명 주소 변수
      var jibunAddr = data.jibunAddress; // 지번 주소 변수
      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('member_post').value = data.zonecode;
      if (roadAddr !== '') {
        document.getElementById("member_addr").value = roadAddr;
      } else if (jibunAddr !== '') {
        document.getElementById("member_addr").value = jibunAddr;
      }
    }
  }).open();
}

function update_address() {

  if (!addressChk === true) {
    alert("이메일을 다시 한번 확인해주세요.");
  } else {
    let addressList = new Array();

    $("input[name=address]").each(function (index, item) {
      addressList.push($(item).val());
    });

    let fullAddress = addressList.join('/');

    $.ajax({
      type: 'POST',
      url: '/register/addressChange',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        'address': fullAddress
      }, // 사용자가 입력한 id 데이터를 서버로 전송
      success: (data) => {
        let address = data.address.split("/");
        console.log(address);
        if (data.result === 'success') {
          alert('주소 변경이 완료되었습니다.');

          for (let i = 0; i < address.length; i++) {
            $('#address' + i).val(address[i]);
          }
        }
      },
      error: (status, error) => {
        console.log('통신 실패');
        console.log(status, error);
      }
    });
  }
}