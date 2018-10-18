function questionAnalysis(questionID,questionNumber)
{
	 var xhttp = new XMLHttpRequest();
	 xhttp.open("POST", "/QuestionAnalysis", true);
	 xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	 var testID = document.getElementById("testID").value;
	 var studentID = document.getElementById("studentID").value;
	 
	 
	 xhttp.send("testID="+testID+"&questionID="+questionID+"&studentID="+studentID+"&questionNumber="+questionNumber);	
	 
	 xhttp.onreadystatechange = function() {
	        if (this.readyState == 4 && this.status == 200) {
	            {
	            	document.getElementById("analysis").innerHTML = this.responseText;
	            }
	       }
	    };
}