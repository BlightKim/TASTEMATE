$(document).ready(function () {
  let stompClient = null;
  const url = new URL(location.href).searchParams;
  const roomId = $("#roomId").val();

  function onConnected() {

    stompClient.subscribe(`http://192.168.1.13:8080/sub/chat/room/${roomId}`, onMessageReceived);

    stompClient.send(`http://192.168.1.13:8080/pub/chat/enterUser`, {}, JSON.stringify({
      "roomId": roomId,
      sender: userName,
      type: 'ENTER'
    })
    );


  }
});