function changeInputType()
{
	var passwordButtonText = document.getElementById("pwdButton").value;
	if(passwordButtonText == "Show Password")
	{
		document.getElementById("inputType").setAttribute("type","text");
		document.getElementById("pwdButton").value = "Hide Password";
	}
	else if(passwordButtonText == "Hide Password")
	{
		document.getElementById("inputType").setAttribute("type","password");
		document.getElementById("pwdButton").value = "Show Password";
	}
}