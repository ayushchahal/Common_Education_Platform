function clearAnswerFields()
{
	var ele = document.getElementsByName("options");
	for(var i=0;i<ele.length;i++)
	      ele[i].checked = false;
}