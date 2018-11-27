function checkScoreCardSettings()
{
	var settingNumber = document.getElementsByName('scoreCard')[0].selectedIndex;
	
	if(settingNumber == 2)
	{
		document.getElementById('scoreDate').setAttribute('style','');
		document.getElementsByName('scoreDate')[0].setAttribute('required','true');
		document.getElementsByName('scoreTime')[0].setAttribute('required','true');
		
		document.getElementById('br1').setAttribute('style','display:block');
	}
	else if(settingNumber == 1)
	{
		document.getElementById('scoreDate').setAttribute('style','display:none');
		document.getElementsByName('scoreDate')[0].removeAttribute('required');
		document.getElementsByName('scoreTime')[0].removeAttribute('required');
		
		document.getElementById('br1').setAttribute('style','display:none');
	}
}

function checkRankCardSettings()
{
	var settingNumber = document.getElementsByName('rankCard')[0].selectedIndex;
	
	if(settingNumber == 2)
	{
		document.getElementById('rankDate').setAttribute('style','');
		document.getElementsByName('rankDate')[0].setAttribute('required','true');
		document.getElementsByName('rankTime')[0].setAttribute('required','true');
		
		document.getElementById('br3').setAttribute('style','display:block');
	}
	else if(settingNumber == 1)
	{
		document.getElementById('rankDate').setAttribute('style','display:none');
		document.getElementsByName('rankDate')[0].removeAttribute('required');
		document.getElementsByName('rankTime')[0].removeAttribute('required');
		
		document.getElementById('br3').setAttribute('style','display:none');
	}
}