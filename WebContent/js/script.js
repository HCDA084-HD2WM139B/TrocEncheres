window.addEventListener("load", function() {
	var IdAchats = document.getElementById("inp_achats");
	var IdVentes = document.getElementById("vente");
	var CheckBox = document.querySelector("input[name=inp_radio]");

	// si rien n'est sélectionner on désative tout
	if (CheckBox.checked == false) {
		document.getElementById('en_open').disabled = true;
		document.getElementById('en_encours').disabled = true;
		document.getElementById('en_val').disabled = true;
		document.getElementById('ve_encours').disabled = true;
		document.getElementById('ve_null').disabled = true;
		document.getElementById('ve_over').disabled = true;
	}

	// initialisation des états en fonction si achat ou vente sont selectionnés
	if (IdAchats.checked) {
		document.getElementById('en_open').disabled = false;
		document.getElementById('en_encours').disabled = false;
		document.getElementById('en_val').disabled = false;
		document.getElementById('ve_encours').disabled = true;
		document.getElementById('ve_encours').checked = false;
		document.getElementById('ve_null').disabled = true;
		document.getElementById('ve_null').checked = false;
		document.getElementById('ve_over').disabled = true;
		document.getElementById('ve_over').checked = false;
	} else if (IdVentes.checked) {
		document.getElementById('en_open').disabled = true;
		document.getElementById('en_open').checked = false;
		document.getElementById('en_encours').disabled = true;
		document.getElementById('en_encours').checked = false;
		document.getElementById('en_val').disabled = true;
		document.getElementById('en_val').checked = false;
		document.getElementById('ve_encours').disabled = false;
		document.getElementById('ve_null').disabled = false;
		document.getElementById('ve_over').disabled = false;
	}

	// En cas de changement sur achat
	IdAchats.addEventListener("change", function() {
		if (IdAchats.checked) {
			getEtatsAchat()
		}
	}, false);

	// En cas de changement sur vente
	IdVentes.addEventListener("change", function() {
		if (IdVentes.checked) {
			getEtatsVente()
		}
	}, false);
})




function getEtatsAchat() {
	document.getElementById('en_open').disabled = false;
	document.getElementById('en_open').checked = true;
	document.getElementById('en_encours').disabled = false;
	document.getElementById('en_val').disabled = false;
	document.getElementById('ve_encours').disabled = true;
	document.getElementById('ve_encours').checked = false;
	document.getElementById('ve_null').disabled = true;
	document.getElementById('ve_null').checked = false;
	document.getElementById('ve_over').disabled = true;
	document.getElementById('ve_over').checked = false;
}

function getEtatsVente() {
	document.getElementById('en_open').disabled = true;
	document.getElementById('en_open').checked = false;
	document.getElementById('en_encours').disabled = true;
	document.getElementById('en_encours').checked = false;
	document.getElementById('en_val').disabled = true;
	document.getElementById('en_val').checked = false;
	document.getElementById('ve_encours').disabled = false;
	document.getElementById('ve_encours').checked = true;
	document.getElementById('ve_null').disabled = false;
	document.getElementById('ve_over').disabled = false;
}