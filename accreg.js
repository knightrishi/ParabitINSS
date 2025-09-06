let btton = document.querySelector(".btn");
console.log(btton);
btton.addEventListener("click", function () {
  let otp = prompt("Please enter the OTP received on SMS:");
  if (otp) {
    alert("You entered OTP: " + otp);
  } else {
    alert("No OTP entered!");
  }
});
