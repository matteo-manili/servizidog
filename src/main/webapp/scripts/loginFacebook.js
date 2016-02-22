		// Load the SDK asynchronously
		  (function(d, s, id) {
			//alert("1111111");
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) return;
			js = d.createElement(s); js.id = id;
			js.src = "//connect.facebook.net/it_IT/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		  }(document, 'script', 'facebook-jssdk'));  
		  
		  // This function is called when someone finishes with the Login
		  // Button.  See the onlogin handler attached to it in the sample
		  // code below.
		  function checkLoginState() {
			  //alert("inizio 1111 checkLoginState");
		    FB.getLoginStatus(function(response) {
		      statusChangeCallback(response);
		    });
		  }

		  window.fbAsyncInit = function() {
			  //alert("inizializzo paramentri facebook 222222222");
			  FB.init({
				appId      : '547748282047154',
				cookie     : true,  // enable cookies to allow the server to access 
									// the session
				xfbml      : true,  // parse social plugins on this page
				version    : 'v2.2' // use version 2.2
			  });
			  

			  // Now that we've initialized the JavaScript SDK, we call 
			  // FB.getLoginStatus().  This function gets the state of the
			  // person visiting this page and can return one of three states to
			  // the callback you provide.  They can be:
			  //
			  // 1. Logged into your app ('connected')
			  // 2. Logged into Facebook, but not your app ('not_authorized')
			  // 3. Not logged into Facebook and can't tell if they are logged into
			  //    your app or not.
			  //
			  // These three cases are handled in the callback function.
			  FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			  });

		  };

		//This is called with the results from from FB.getLoginStatus().
		  function statusChangeCallback(response) {
			//alert("33333333333333333333333 faccio controllo status connessione");
			
		    console.log('statusChangeCallback');
		    console.log(response);
		    // The response object is returned with a status field that lets the
		    // app know the current login status of the person.
		    // Full docs on the response object can be found in the documentation
		    // for FB.getLoginStatus().
		    if (response.status === 'connected') {
		      // Logged into your app and Facebook.
		    	FB.api('/me', {fields: 'id,name,email,first_name,last_name,middle_name,locale,gender,cover,picture'}, function(response) {
		    		
			    	riempiFormFacebook(response);
			    	
			    	if (response.gender == 'male'){

			    		document.getElementById("status").innerHTML = " Benvenuto,  " + response.name + "  " +
			    				"<br><a href='javascript:accediConFacebook();'><b>Entra con Facebook</b></a>";
			    		
			    		
			    	}else{
			    		document.getElementById("status").innerHTML = " Benvenuta,  " + response.name + "  " +
	    				"<br><a href='javascript:accediConFacebook();'><b>Entra con Facebook</b></a>";
			    	}
			    /*
			      console.log('Successful login for: ' + response.name);
			      //alert(JSON.stringify(response));
			      */

			    });
		    	/*
		    	document.getElementById('btnFacebook').style.visibility = 'visible';     // Show
		    	
		    	document.getElementById('status').style.visibility = 'hidden';      // Hide
		    	document.getElementById('buttonFB').style.visibility = 'hidden';      // Hide
		    	*/
		    } else if (response.status === 'not_authorized') {
		      // The person is logged into Facebook, but not your app.
		    	/*
		      document.getElementById('status').innerHTML = 'Please log ' +
		        'into this app.';
		    	*/
		    } else {
		      // The person is not logged into Facebook, so we're not sure if
		      // they are logged into this app or not.
			    document.getElementById('status').innerHTML = 'Effettua il login a Facebook.';
			    /*
			    document.getElementById('buttonFB').style.visibility = 'visible';     // Show
			    
			    document.getElementById('btnFacebook').style.visibility = 'hidden';      // Hide
			    */
		    }
		  }

		  function riempiFormFacebook(response){
			  //alert('weee');
			  //response.id
			  var aa = response.name;
			  //alert(aa);
			$("#idFB").val(response.id);
	    	$("#nameFB").val(response.name);
			$("#emailFB").val(response.email);
	    	$("#firstNameFB").val(response.first_name);
	    	$("#lastNameFB").val(response.last_name);
	    	$("#middleNameFB").val(response.middle_name);
	    	$("#localeFB").val(response.locale);
	    	$("#genderFB").val(response.gender);
	    	$("#coverFB").val(response.cover);
	    	$("#pictureFB").val(response.picture);
	    	//$("#j_username").val(response.name);
		    //top.location.href='/facebookReturn?emailFB='+response.email;
	    	//document.getElementById('facebookForm').submit();
		  }
		  
		  function accediConFacebook(){
			  document.getElementById('facebookForm').submit();
		  }
		  
		  function facebookLogout(){
			  alert("esco");
			  FB.logout(function(response) {
			      // Person is now logged out
			  });
		  }