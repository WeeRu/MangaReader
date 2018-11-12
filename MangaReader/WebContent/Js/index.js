var div1 = document.getElementById("div1");
var div2 = document.getElementById("div2");

function $(id) {
  return document.getElementById(id);
}


function reg(){
    div1.style.display ="none";
    div2.style.display ="block";
}

function log(){
    
    div1.style.display ="block";
    div2.style.display ="none";
}
function createAccount(){
	var data = {
            Clave: $('Clave').value,
			Usuario: $('Usuario').value,
			Nombre: $('Nombre').value,
			Email: 	$('Email').value,
    };          
	console.log(data)
	let config = {
		method: 'POST',
		body: JSON.stringify(data),
        };
	fetch("./Registro", config)
		.then(function(response){
			return response.json();
		})
		.then(function(data){
			console.log(data);
		});
	}
/*
function validateEmail(email) {
	var re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	return re.test(email);
}

function validate() {
	var result = document.getElementById("result");
	var email = document.getElementById("email").value;
	if (validateEmail(email)) {
		result.innerHTML = "Valid Email.";
		document.getElementById("button3").disabled = false;
	} else {
	    result.innerHTML = "Invalid Email.";
	    document.getElementById("button3").disabled = true;
	}
	return false;
}
*/
document.getElementById("button3").addEventListener("click", createAccount);
/*
function SwitchURL(){
	history.replaceState({},"Register", "Re.k8j2vdk.g.jkzvz84kl1.is.af7223.t.kjkkabofajs.er.Reggakbster.html");
	document.getElementById("button3").disabled = true;
}

function forceLower(strInput) {
	strInput.value=strInput.value.toLowerCase();
}*/