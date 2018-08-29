$( document ).ready(function(){
	$('.button-collapse').sideNav({
		closeOnClick: true
	}
	);
});
var validateLoginid=function(){
	if($("#loginid").val().toString()=="")
		$("#errLoginid").show();
	else
		$("#errLoginid").hide();
}
var validatePassword=function(){
	if($("#password").val().toString()=="")
		$("#errPassword").show();
	else
		$("#errPassword").hide();
}
var validateForm = function(){
	if($("#loginid").val().toString()=="")
	{
		$("#errLoginid").show();
		if($("#password").val().toString()=="")
			$("#errPassword").show();
		return false;
	}
	if($("#password").val().toString()=="")
	{
		$("#errPassword").show();
		if($("#loginid").val().toString()=="")
			$("#errLoginid").show();
		return false;
	}
}