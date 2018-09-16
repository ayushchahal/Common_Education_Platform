setTimeout(function() {
var startDateTimeInSeconds = document.getElementById("timer1").value;
var startTime = Math.floor(startDateTimeInSeconds/1000);
var timeInSeconds = document.getElementById("timer").value;
var timeInSeconds1 = parseInt(timeInSeconds);
var targetTime = startTime + timeInSeconds1;
var i = 0;

var x = setInterval(function() 
		{
			i = i+1;
			if(i % 5 == 0)
			{
				//AJAX to ask server to check the time difference at server side.
				//If time difference is more then server will ask Ajax to execute 
			}
			var currentTime = Math.floor(Date.now()/1000);
			var differnce = targetTime - currentTime;
			
			var minutes = Math.floor(differnce/60);
			var seconds = Math.floor(differnce % 60);
			
			if(differnce < 0)
			{
				document.getElementById("timeleft1").innerHTML = "EXPIRED";
				document.getElementById("next").click();
				clearInterval(x);
			}
			else
			{
				if(minutes == 0)
				{
					if(seconds < 15)
					{
						document.getElementById("badge").className = "badge badge-danger";
					}
					document.getElementById("timeleft1").innerHTML = seconds + " seconds";
				}
				else
				{
					document.getElementById("timeleft1").innerHTML = minutes + " minutes " + seconds + " seconds";
				}
			}
		},1000);
}, 300);
	