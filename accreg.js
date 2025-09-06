let btton = document.querySelector(".btn");

btton.addEventListener("click", function () {
  let otp = prompt("Please enter the OTP received on SMS:");
  if (otp) {
    alert("You entered OTP: " + otp);
  } else {
    alert("No OTP entered!");
  }
});
