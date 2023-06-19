/*
	By Mushfiq Shishir, hello@mrshishir.me, www.mrshishir.me
*/

"use strict";

(function (document, window, index) {
  var inputs = document.querySelectorAll(".inputfile");
  Array.prototype.forEach.call(inputs, function (input) {
    var label = input.nextElementSibling,
      labelVal = label.innerHTML;

    input.addEventListener("change", function (e) {

      if (isExistingFile()) {
        return false;
      }
      var fileName = "";
      if (this.files && this.files.length > 1)
        fileName = (this.getAttribute("data-multiple-caption") || "").replace(
          "{count}",
          this.files.length
        );
      else fileName = e.target.value.split("\\").pop();

      if (fileName) label.querySelector("span").innerHTML = fileName;
      else label.innerHTML = labelVal;
    });

    // Firefox bug fix
    input.addEventListener("focus", function () {
      input.classList.add("has-focus");
    });
    input.addEventListener("blur", function () {
      input.classList.remove("has-focus");
    });
    function isExistingFile() {
      let del_fileArea = document.getElementById("del_file_area");
      console.log(del_fileArea);
      if (del_fileArea == null) {
        return false;
      } else {
        return true;
      }
    }
  });
})(document, window, 0);
