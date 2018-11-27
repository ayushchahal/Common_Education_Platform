function clientSideValidations()
{
	var qTypeID = document.getElementById("qType").selectedIndex;
	
	if(qTypeID == 1)
	{
		var e = document.getElementsByClassName('SingleAnswerOptions');
		var n = e.length;
		
		for(var i = 0; i <n; i++)
		{
			document.getElementById('checkOptions').setAttribute('style','display:none;color:red');
		}		
	}
	if(qTypeID == 2)
	{
		var e = document.getElementsByClassName('options');
		var n = e.length;
		
		for(var i = 0; i <n; i++)
		{
			document.getElementById('checkOptions').setAttribute('style','display:none;color:red');
		}		
	}
	document.getElementById('checkMRQOptions').setAttribute('style','display:none');
	document.getElementById('validationQText').setAttribute('style','display:none');
	document.getElementById('validationPMarks').setAttribute('style','display:none');
	document.getElementById('validationNMarks').setAttribute('style','display:none');
	document.getElementById('validationTopics').setAttribute('style','display:none');
	
	var counter = 0;
	var	questionText = nicEditors.findEditor(document.getElementById('question')).getContent();
	var pmarks = document.getElementById('pmarks').value;
	var nmarks = document.getElementById('nmarks').value;
	var topics = document.getElementsByClassName('ui-autocomplete-multiselect-item')[0];
	var answer1 = document.getElementsByClassName("radio");
	var answer2 = document.getElementsByClassName("checkbox");
	
	
	if(questionText == "" || questionText == null || questionText == "<br>")
	{
		document.getElementById('validationQText').setAttribute('style','display:block');
		document.getElementsByClassName(' nicEdit-main ')[0].focus();
		counter = 1;
	}
	if(pmarks == "" || pmarks == null)
	{
		document.getElementById('validationPMarks').setAttribute('style','display:block');
		document.getElementById('pmarks').focus();
		counter = 1;
	}
	if(nmarks == "" || nmarks == null)
	{
		document.getElementById('validationNMarks').setAttribute('style','display:block');
		document.getElementById('nmarks').focus();
		counter = 1;
	}
	if(topics == "" || topics == null)
	{
		document.getElementById('validationTopics').setAttribute('style','display:block');
		document.getElementById('validationTopics').focus();
		counter = 1;
	}
	
	if(qTypeID == 1)
	{
		var e = document.getElementsByClassName('SingleAnswerOptions');
		var n = e.length;
		
		for(var i = 0; i <n; i++)
		{
			if(e[i].value == null || e[i].value == "")
			{
				document.getElementById('checkOptions').setAttribute('style','display:block;color:red');
				document.getElementById('checkOptions').focus();
				counter = 1;
			}
		}		
	}
	else if(qTypeID == 2)
	{
		var e = document.getElementsByClassName('options');
		var n = e.length;
		
		for(var i = 0; i <n; i++)
		{
			if(e[i].value == null || e[i].value == "")
			{
				document.getElementById('checkOptions').setAttribute('style','display:block;color:red');
				document.getElementById('checkOptions').focus();
				counter = 1;
			}
		}		
	}
	
	if(qTypeID == 0 || qTypeID == 1)
	{
		var l = answer1.length;
		var c = 0;
		for(var i = 0; i < l; i++)
		{
			if(answer1[i].checked)
			{
				c = 1;
			}	
		}
		
		if(c == 0)
		{
			document.getElementById('checkMRQOptions').setAttribute('style','display:block;color:red');
			document.getElementById('checkMRQOptions').focus();
			counter = 1;
		}	
	}
	else if(qTypeID == 2)
	{
		var l = answer2.length;
		var c = 0;
		for(var i = 0; i < l; i++)
		{
			if(answer2[i].checked)
			{
				c = 1;
			}	
		}
		
		if(c == 0)
		{
			document.getElementById('checkMRQOptions').setAttribute('style','display:block;color:red');
			document.getElementById('checkMRQOptions').focus();
			counter = 1;
		}	
	}
	
	if(counter != 0)
	{
		return false;
	}
	else
	{
		return true;
	}
}