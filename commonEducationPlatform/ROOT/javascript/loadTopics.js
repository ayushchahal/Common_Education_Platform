function loadTopics()
{
	var n = document.getElementById("alreadyPresentLabels").value;
	if(n != 0)
	{
		var numberOfLabels = n.split(',').length;
		var array = n.split(',');
		for(var i=0;i<numberOfLabels;i++)
		{
			var span = document.createElement("span");
			span.setAttribute('class','ui-icon ui-icon-close');
			
			var div = document.createElement("div");
			div.setAttribute('class','ui-autocomplete-multiselect-item');
			var t = document.createTextNode(array[i]);
			div.appendChild(t);  
			div.appendChild(span);
			
			
			var topicField = document.getElementById("topic");
			var parent1 = document.getElementsByClassName("ui-autocomplete-multiselect ui-state-default ui-widget");
		
			parent1[0].insertBefore(div,topicField);
		}					
	}
}