// Proste funkcje JavaScript, głównie dotyczące AJAX'a

// Funkcja przechodzi do URL, podanego jako parametr 'link', po potwierdzeniu przez użytkownika.
// (wyświetla zapytanie podane jako parametr 'message')
function confirmLink(link,message) {
	if(confirm(message)) {
		window.location.href=link;
	}
}

function consumeInnerHtmlOrRedirect(xmlHttp,id_to_reload){
	if (xmlHttp.getResponseHeader("Ajax_insert_param") == "NEW_PAGE" || xmlHttp.getResponseHeader("Ajax_insert_param") == null ){
		//document.body.innerHTML = xmlHttp.responseText;
		console.log(xmlHttp);
		console.log();
		window.location.href = xmlHttp.getResponseHeader("Ajax_redirection");
		//document.getElementById(id_to_reload).innerHTML = xmlHttp.responseText;

	}
	else if(xmlHttp.getResponseHeader("Ajax_insert_param") == "INSERT_PAGE") {
		document.getElementById(id_to_reload).innerHTML = xmlHttp.responseText;
	}
}

function ajaxPostFormPagination(id_form,id_to_reload)
{

    var form = document.getElementById(id_form);
    var formData = new FormData(form); 

	var action  = form.getAttribute("action");
	console.log(action);

    var submitButton = event.submitter;
    if (submitButton) {
      var filterValue = submitButton.value;
      if(filterValue==null)
      {
          filterValue=0;
      }
      formData.append('page', filterValue);
    }
    
    var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {

		if (xmlHttp.readyState == 4 ){
			consumeInnerHtmlOrRedirect(xmlHttp,id_to_reload);
		}
	}
    xmlHttp.open("POST", url, true); 
    xmlHttp.send(formData); 
}



function ajaxPostForm(id_form,url,id_to_reload)
{
    
    var form = document.getElementById(id_form);
    var formData = new FormData(form); 
    
    console.log(url);
    var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 ){
			consumeInnerHtmlOrRedirect(xmlHttp,id_to_reload);
		}

	}
    xmlHttp.open("POST", url, true); 
    xmlHttp.send(formData); 
}

function  ajaxPostFormWithPathParam(id_form,url,id_to_reload)
{

	var form = document.getElementById(id_form);
	var formData = new FormData(form);
	var pathParam =  form.getAttribute("pathParam");
	console.log(pathParam);
	console.log(url.concat(pathParam));
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 ){
			console.log("faza1");
			console.log(xmlHttp.status);
			consumeInnerHtmlOrRedirect(xmlHttp,id_to_reload);
		}

	}

	url=url.concat(pathParam);
	xmlHttp.open("POST", url, true);
	xmlHttp.send(formData);
}


// Funkcja wysyłająca dane formularza identyfkowanego przez 'id_form', do podanego adresu 'url'.
// Po otrzymaniu odpowiedzi wywoływana jest funkcja użytkownika podana jako 'user_function'.
function ajaxPostFormEx(id_form,url,user_function)
{
    var form = document.getElementById(id_form);
    var formData = new FormData(form); 
    var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 ){
			if (xmlHttp.status == 500 || xmlHttp.status == 201){
				document.body.innerHTML = xmlHttp.responseText;
			}
			if(xmlHttp.status == 200) {
				user_function();
			}
		}

	}
    xmlHttp.open("POST", url, true); 
    xmlHttp.send(formData); 
}
 
// Funkcja odświeżająca zawartość elementu o identyfikatorze 'id_element'.
// Zawartość do odświeżenia jest otrzymana w odpowiedzi na żądanie wysłane do podanego adresu 'url'.
// Jeśli podano parametr 'interval' >0 (sekundy), to po otrzymaniu odpowiedzi po upływie podanego
// interwału odświeżanie zostanie automatycznie ponowione (tzw. AJAX pooling).

function ajaxReloadElement(id_element,url,interval=0) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById(id_element).innerHTML = this.responseText;
			if (interval > 0) 
				setTimeout( function(){ ajaxReloadElement(id_element,url,interval) }, interval);
		}
	};
	xhttp.open("GET", url, true);
	xhttp.send();
}