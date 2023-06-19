$('#withDrawl_change_btn').click(() => {
  $.ajax({
    type: 'POST',
    url: '/register/withDrawl',
    header: {
      'Content-Type': 'application/json'
    },
    success: (data) => {
      console.log(data);
      if (data === 'Success') {
        alert('탈퇴가 완료되었습니다.\n'
            + '다시 로그인 해주세요.');
        location.href = '/login/controller?type=logOut';
      }
    },
    error: (status, error) => {
      console.log('통신 실패');
      console.log(status, error);
    }
  });
});



