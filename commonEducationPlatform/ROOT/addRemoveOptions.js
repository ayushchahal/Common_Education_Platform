function removeOption() {
    // Removes an element from the document
	document.getElementById("optionsValidator").innerHTML = "";
	var qType = document.getElementById("qType").value;
	if(qType==2)
	{
		var element = document.getElementById("multipleChoiceQuestions");
		var numberOfChildren = element.getElementsByClassName("radio").length;
		if(numberOfChildren > 2)
		{
			var element1 = document.getElementById("option-"+numberOfChildren);
		    element1.parentNode.removeChild(element1);
		}
		else
		{
			document.getElementById("optionsValidator").innerHTML = "You cannot have less than 2 options.";
		}
	}
	else if(qType==3)
	{
		var element = document.getElementById("MAQ");
		var numberOfChildren = element.getElementsByClassName("checkbox").length;
		if(numberOfChildren > 2)
		{
			var element1 = document.getElementById("MAQoption-"+numberOfChildren);
		    element1.parentNode.removeChild(element1);
		}
		else
		{
			document.getElementById("optionsValidator").innerHTML = "You cannot have less than 2 options.";
		}
	}
	
}

function addOption() {
	document.getElementById("optionsValidator").innerHTML = "";
	var qType = document.getElementById("qType").value;
	if(qType==2)
	{
		var element = document.getElementById("multipleChoiceQuestions");
		var numberOfChildren = element.getElementsByClassName("radio").length;
		if(numberOfChildren<6)
		{
			var div = document.createElement('div');
			div.id = 'option-'+(numberOfChildren+1);
			
			//div.innerHTML = '<br><input type="radio" name="options" value="'+(numberOfChildren+1)+'" class="radio"><textarea  onclick="clearParagraph()" class="options" name='+div.id+'></textarea>';
			div.innerHTML = 
				'<label style="width: 90%">' + 
					'<div class="input-group input-group-lg" style="cursor:pointer">' + 
						'<div class="input-group-prepend" style="width:100%;height:9%">' +
							'<div class="input-group-text">' +
								'<input type="radio" name="options" value="'+ (numberOfChildren+1) +'" class="radio">' +
							'</div>' +
							'<textarea onclick="clearParagraph()" name="'+div.id+'" class="options" style="width:100%;height:100%"></textarea>' +
						'</div>' +
					'</div>' + 
				'</label>';
			document.getElementsByClassName('option')[0].appendChild(div);
		}
		else
		{
			document.getElementById("optionsValidator").innerHTML = "You cannot have more than 6 options.";
		}
	}
	else if(qType==3)
	{
		var element = document.getElementById("MAQ");
		var numberOfChildren = element.getElementsByClassName("checkbox").length;
		if(numberOfChildren<6)
		{
			var div = document.createElement('div');
			div.id = 'MAQoption-'+(numberOfChildren+1);
			
			//div.innerHTML = '<br><input type="checkbox" name="options" value="'+(numberOfChildren+1)+'" class="checkbox"><textarea  onclick="clearParagraph()" class="options" name='+div.id+'></textarea>';
			div.innerHTML = 
				'<label style="width: 90%">' + 
					'<div class="input-group input-group-lg" style="cursor:pointer">' + 
						'<div class="input-group-prepend" style="width:100%;height:9%">' +
							'<div class="input-group-text">' +
								'<input type="checkbox" name="options" value="'+ (numberOfChildren+1) +'" class="checkbox">' +
							'</div>' +
							'<textarea onclick="clearParagraph()" name="'+div.id+'" class="options" style="width:100%;height:100%"></textarea>' +
						'</div>' +
					'</div>' + 
				'</label>';
			document.getElementsByClassName('MAQoption')[0].appendChild(div);
		}
		else
		{
			document.getElementById("optionsValidator").innerHTML = "You cannot have more than 6 options.";
		}
	}
	
   
}

function clearParagraph()
{
	document.getElementById("optionsValidator").innerHTML = "";
}

function dropdownTestType()
{
	 var x = document.getElementById('multipleChoiceQuestions');
	    if (x.style.display === 'none') {
	        x.style.display = 'block';
	    } else {
	        x.style.display = 'none';
	    }
}
