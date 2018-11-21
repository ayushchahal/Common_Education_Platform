function insertNames()
{
	var n = document.getElementsByClassName("ui-autocomplete-multiselect-item").length;
	
	var ninput = document.createElement('input');
	ninput.setAttribute('name','numberOfTopics');
	ninput.setAttribute('value',n);
	ninput.setAttribute('type','hidden');
	document.getElementById("questionsForm").appendChild(ninput);
	
	for(var i = 0;i< n; i++)
	{
		var text = document.getElementsByClassName("ui-autocomplete-multiselect-item")[i].innerText;
		
		var input = document.createElement('input');
		input.setAttribute('name','topics'+(i+1));
		input.setAttribute('value',text);
		input.setAttribute('type','hidden');
		
		document.getElementById("questionsForm").appendChild(input);
		
	}
	

}