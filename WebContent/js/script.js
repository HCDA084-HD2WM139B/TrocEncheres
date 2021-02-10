window.addEventListener("load",function() { 
    var IdAchats = document.getElementById("inp_achats");
    var IdVentes = document.getElementById("vente");
    var CheckBox = document.querySelector("input[name=inp_radio]");
    
    if (CheckBox.checked == false) {
  	  document.getElementById('en_open').disabled = true;
	  document.getElementById('en_encours').disabled = true;
	  document.getElementById('en_val').disabled = true;
	  document.getElementById('ve_encours').disabled = true;
	  document.getElementById('ve_null').disabled = true;
	  document.getElementById('ve_over').disabled = true;
    }
    
    IdAchats.addEventListener("change", function() {
        if (IdAchats.checked) {
      	  document.getElementById('en_open').disabled = false;
      	  document.getElementById('en_encours').disabled = false;
      	  document.getElementById('en_val').disabled = false;
    	  document.getElementById('ve_encours').disabled = true;
    	  document.getElementById('ve_null').disabled = true;
    	  document.getElementById('ve_over').disabled = true;
        }
      }, false); 
    
    IdVentes.addEventListener("change", function() {
        if (IdVentes.checked) {
      	  document.getElementById('en_open').disabled = true;
      	  document.getElementById('en_encours').disabled = true;
      	  document.getElementById('en_val').disabled = true;
    	  document.getElementById('ve_encours').disabled = false;
    	  document.getElementById('ve_null').disabled = false;
    	  document.getElementById('ve_over').disabled = false;
        }
      }, false); 
    
    

})