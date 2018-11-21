function changeQType()
{
	var x = document.getElementsByName("options");
	for(var i=0;i<x.length;i++)
     {
     	x[i].checked = false;
     } 

	var y = document.getElementsByClassName("options");
	for(var i=0;i<y.length;i++)
     {
     	y[i].value = "";
     } 
	
	var y1 = document.getElementsByClassName("SingleAnswerOptions");
	for(var i=0;i<y1.length;i++)
     {
     	y1[i].value = "";
     } 
	
	var type=document.getElementById("qType").value;
	
	if(type=="1")
	{
		var element = document.getElementById("multipleChoiceQuestions");
		element.style.display='none';
		var element = document.getElementById("link");
		element.style.display='none';
		var element = document.getElementById("MAQ");
		element.style.display='none';
		
		var element = document.getElementById("TandF");
		element.style.display='block';
		
	}
	else if(type=="2")
	{
		var element = document.getElementById("TandF");
		element.style.display='none';
		var element = document.getElementById("MAQ");
		element.style.display='none';
		
		var element = document.getElementById("multipleChoiceQuestions");
		element.style.display='block';
		var element = document.getElementById("link");
		element.style.display='block';
		
	}
	else if(type=="3")
	{
		var element = document.getElementById("MAQ");
		element.style.display='block';
		
		var element = document.getElementById("multipleChoiceQuestions");
		element.style.display='none';
		var element = document.getElementById("link");
		element.style.display='block';
		var element = document.getElementById("TandF");
		element.style.display='none';
		
	}
	
}