//This is v3
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
				var headerText = document.getElementById("questionHeader").innerHTML;
				var arr = headerText.split(" ");
				var currentQuestion = arr[1];
				
				var pallete = document.getElementsByName("pallete");
				var palleteCount = pallete.length;
				var ids = new Array();
				for(var i=0; i<palleteCount; i++)
				{
					ids[i] = pallete[i].getAttribute("id");
				}
				var counter = 0;
				for(var i = currentQuestion; i < ids.length; i++)
				{
					var palleteStatus = document.getElementById(ids[i]).getAttribute("disabled");
					if(palleteStatus == null)
					{
						counter = 1;
						var nextQuestionNumber = +i+1;
						if(currentQuestion != nextQuestionNumber)
						{
							swal({
								  title: "Information!",
								  text: "Time over for this question. Automatically moving to question number "+nextQuestionNumber,
								  icon: "warning",
								  showCancelButton: false,
								  confirmButtonClass: "btn-success",
								  timer: 8000,
								  confirmButtonText: "Click here to move to Question Number "+ nextQuestionNumber,
								  closeOnConfirm: false,
								  closeOnClickOutside: false}).then(function() {
									  document.getElementById(ids[i]).click(); // <--- submit form programmatically
							     });
						}
						break;
					}
				}
				if(counter == 0)
				{
					for(var i = 0; i < currentQuestion-1; i++)
					{
						var palleteStatus = document.getElementById(ids[i]).getAttribute("disabled");
						if(palleteStatus == null)
						{
							counter = 1;
							var nextQuestionNumber = +i+1;
							if(currentQuestion != nextQuestionNumber)
							{
								swal({
									  title: "Information!",
									  text: "Time over for this question. Automatically moving to question number "+nextQuestionNumber,
									  icon: "warning",
									  showCancelButton: false,
									  confirmButtonClass: "btn-success",
									  timer: 8000,
									  confirmButtonText: "Click here to move to Question Number "+ nextQuestionNumber,
									  closeOnConfirm: false,
									  closeOnClickOutside: false}).then(function() {
										  document.getElementById(ids[i]).click(); // <--- submit form programmatically
								        });
							}
							//document.getElementById(ids[i]).click();
							break;
						}
					}
					if(counter == 0)
					{
						swal({
							  title: "Attention!",
							  text: "Time Over",
							  icon: "warning",
							  showCancelButton: false,
							  timer: 8000,
							  confirmButtonClass: "btn-success",
							  confirmButtonText: "Submit Test",
							  closeOnConfirm: false,
							  closeOnClickOutside: false}).then(function() {
								  document.getElementById("subm").click(); // <--- submit form programmatically
						        });
					}
				}
				
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



	