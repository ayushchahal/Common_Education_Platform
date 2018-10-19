setTimeout(function() {
var time = document.getElementById("timer1").value;
var remainingTime = parseInt(time)


var x = setInterval(function() 
		{
			var minutes = Math.floor(remainingTime/60);
			var seconds = Math.floor(remainingTime % 60);
			
			if(remainingTime < 0)
			{
				document.getElementById("timeleft1").innerHTML = "EXPIRED";
				document.getElementById("subm").click();
				swal({
					  title: "Attention!",
					  text: "Time Over",
					  icon: "warning",
					  showCancelButton: false,
					  confirmButtonClass: "btn-success",
					  confirmButtonText: "Submit Test",
					  closeOnConfirm: false,
					  closeOnClickOutside: false}).then(function() {
						  document.getElementById("subm").click(); // <--- submit form programmatically
				        });
//					},
//					function(){
//					  swal("Deleted!", "Your imaginary file has been deleted.", "success");
//					});

				clearInterval(x);
			}
			else{
			if(minutes == 0)
			{
				if(seconds < 55)
				{
					document.getElementById("badge").className = "badge badge-danger";
				}
				document.getElementById("timeleft1").innerHTML = seconds + " seconds ";
			}
			else
			{
				document.getElementById("timeleft1").innerHTML = minutes + " minutes " + seconds + " seconds ";
			}
			remainingTime = remainingTime - 1;
		}
	},1000);
}, 300);
	