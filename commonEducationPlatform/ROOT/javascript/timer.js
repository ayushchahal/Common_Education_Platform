setTimeout(function() {
	var distance1 = document.getElementById("timer").value;

	var distance = distance1*1000;


	// Update the count down every 1 second
	var x = setInterval(function() {

	   
	    // Time calculations for days, hours, minutes and seconds
	    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
	    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
	    
	    // Output the result in an element with id="demo"
	    document.getElementById("timeleft1").innerHTML =  hours + "h "
	    + minutes + "m " + seconds + "s ";
	    
	    // If the count down is over, write some text 
	    if (distance < 0) {
	        clearInterval(x);
	        document.getElementById("timeleft1").innerHTML = "EXPIRED";
	    }
	    
	    distance = distance-1000;
	}, 1000);
}, 300);
