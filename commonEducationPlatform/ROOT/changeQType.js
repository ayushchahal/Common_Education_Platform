function changeQType()
{
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