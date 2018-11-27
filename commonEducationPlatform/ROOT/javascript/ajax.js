function checkFraud()
{
	 var xhttp = new XMLHttpRequest();
	 xhttp.open("POST", "/PopupErrors", true);
	 xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	 var testID = document.getElementById("testID").value;
	 var questionID = document.getElementById("questionID").value;
	 var studentID = document.getElementById("studentID").value;
	 
	 xhttp.send("testID="+testID+"&questionID="+questionID+"&studentID="+studentID);	
	 
	 xhttp.onreadystatechange = function() {
	        if (this.readyState == 4 && this.status == 200) {
	            {
	            	var r = this.responseText+"";
	            	if(r == "Fraud")
	            	{ 
	            		alert("It seems you pressed browser's back button to read this question. Please avoid doing that.");
		            	
	            	}
	            }
	       }
	    };
}