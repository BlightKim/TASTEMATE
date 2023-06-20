$(document).ready(function () {
  var socket = null;
  var serverURL = 'ws://localhost:8080/ws/echo'; // 백엔드의 엔드포인트 경로와 동일하게 설정해야 합니다.

  socket = new WebSocket(serverURL);

  socket.onopen = function() {
    console.log("WebSocket connection opened");
  };
  socket.onmessage = function(event) {
    console.log("WebSocket message received: ", event.data);
  };
  socket.onclose = function() {
    console.log("WebSocket connection closed");
  };
  socket.onerror = function(error) {
    console.log("WebSocket error occurred: ", error);
  };


  $(".chat-btn").on("click", function () {
    console.log("chat-btn 클릭");
    let sender = $(this).data("sender");
    let receiver = $(this).data("receiver");
    let roomName = `${sender}With${receiver}`;
    let content = `${sender}님이 ${receiver}님과 대화를 하고 싶습니다.`;
    let url = "http://192.168.1.13:8080/chat/createRoom?roomName=" + roomName;
    let data = {
      sender: sender,
      receiver: receiver,
      content: content,
      url: url
    };
    socket.send(JSON.stringify(data));
    window.open(
        url,
        "_blank",
        "width=500, height=500"
    );

  });

  function fillWidth(elem, timer, limit) {
    if (!timer) {
      timer = 3000;
    }
    if (!limit) {
      limit = 100;
    }
    var width = 1;
    var id = setInterval(frame, timer / 100);

    function frame() {
      if (width >= limit) {
        clearInterval(id);
      } else {
        width++;
        elem.style.width = width + "%";
      }
    }
  }

  function onMessage(evt) {
    var data = evt.data;
    var timer = 7000;
    var $elem = $(
      `<div class="toastWrap"><span class="toast">${data}<b></b><div class="timerWrap"><div class="timer"></div></div></span></div>`
    );
    $("#toast").append($elem);
    $elem.slideToggle(100, function () {
      $(".timerWrap", this)
        .first()
        .outerWidth($elem.find(".toast").outerWidth() - 10);

      fillWidth($elem.find(".timer").first()[0], timer);
      setTimeout(function () {
        $elem.fadeOut(100, function () {
          $(this).remove();
        });
      }, timer);
    });
  }
});