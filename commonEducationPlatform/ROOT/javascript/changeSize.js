function changeSize()
{
	document.getElementsByClassName(" nicEdit-panelContain")[0].parentNode.setAttribute("style","width:100%");
	document.getElementsByClassName(" nicEdit-main     ")[0].parentNode.setAttribute("style","width: 100%; border-width: 0px 1px 1px; border-top-style: initial; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: initial; border-right-color: rgb(204, 204, 204); border-bottom-color: rgb(204, 204, 204); border-left-color: rgb(204, 204, 204); border-image: initial; overflow: hidden auto;");
	document.getElementsByClassName(" nicEdit-main     ")[0].setAttribute("style","width: 98%; margin: 4px; min-height: 130px; overflow: hidden;");
	
}
