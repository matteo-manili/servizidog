<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="dogsitter.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="dogsitter.title"/>"/>

	
	<script src="<c:url value="/scripts/vendor/jquery-ui-1.11.4/external/jquery/jquery.js"/>"></script>
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
    <!-- script type="text/javascript" src='http://maps.google.com/maps/api/js?sensor=false&libraries=places'></script -->
    <script src="<c:url value="/scripts/jquery-locationpicker-plugin-master/dist/locationpicker.jquery.js"/>"></script>
    
    
    
    
</head>

<body>

<%@ include file="/common/messages.jsp" %>

<script>
	var visualizzaCivico = '${dogSitter.visualizzaCivico}';
	var cittaSalvata = '${dogSitter.address.city}';
</script>


<spring:bind path="dogSitter.*">
	<c:if test="${not empty status.errorMessages}">
		<div class="alert alert-danger alert-dismissable">
		    <a href="#" data-dismiss="alert" class="close">&times;</a>
		    <c:forEach var="error" items="${status.errorMessages}">
				<c:out value="${error}" escapeXml="false"/><br/>
			</c:forEach>
		</div>
	</c:if>
</spring:bind>




<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><fmt:message key="dogsitter.title"/></h1>
				<hr>
			</div>
		</div>
	</div>




	<div class="row">
		<div class="col-md-6 col-md-offset-3 text-center padding" >
			<ul class="nav nav-tabs responsive" id="tabs">
			  <li role="presentation" class="active"><a data-toggle="tab" href="#info"><fmt:message key="dogsitter.infogenerali"/></a></li>
			  <li role="presentation">               <a data-toggle="tab" href="#foto"><fmt:message key="dogsitter.foto"/></a></li>
			  <li role="presentation">               <a data-toggle="tab" href="#disponiblita"><fmt:message key="dogsitter.disponibilitatariffa"/></a></li>
			</ul>
			<div class="tab-content responsive">


				<div role="tabpanel" class="tab-pane active" id="info">
					<form:form commandName="dogSitter" method="post" action="dogsitterform" id="dogSitterForm" autocomplete="off" onsubmit="return validateDogSitter(this)">
						<form:hidden path="id"/>
						
						<!-- 
						<spring:bind path="dogSitter.titolo">
					    	<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
					    </spring:bind>
							<label><fmt:message key="dogsitter.titolo"/></label>
							<form:input path="titolo" id="titolo" maxlength="64" />
							<form:errors path="titolo" cssClass="help-block"/>
						</div>  -->

					    <spring:bind path="dogSitter.descrizione">
					    	<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
					    </spring:bind>
				        	<label><fmt:message key="dogsitter.descrizione"/></label>
				        	<form:textarea path="descrizione" id="descrizione" maxlength="500" />
					    	<form:errors path="descrizione" cssClass="help-block"/>
           				</div>
           
           				<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
							<label><fmt:message key="dogsitter.telefono"/></label>
						    <form:input path="telefono" id="telefono" />
     					</div>
     						
     						
				     	<spring:bind path="dogSitter.indirizzoCompleto">
					    	<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
					    </spring:bind>
					    	<label><fmt:message key="dogsitter.indirizzo"/></label>
					    	<form:input path="indirizzoCompleto" id="indirizzoCompleto"  />
					    	<form:errors path="indirizzoCompleto" cssClass="help-block"/>

    					    <!-- questa è la mappa -->
						    <label><fmt:message key="dogsitter.mappa"/></label>
						   	<div id="us5" style="border: 2px solid orange; width: 100%px; min-width: 280px; height:100%; min-height:300px;"></div>
   						</div>


						<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
						   	<label><fmt:message key="dogsitter.indirizzoVisualizzato"/></label>
						    <form:input readonly="true" path="address.address" id="us5-street1" />
							<div class="text-right">
								<label>visualizza/nascondi Numero Civico </label>
							    <form:checkbox path="visualizzaCivico" id="checkNumeroCiv"   />
						    </div>
						</div>
						
					    <input type="hidden" id="appostreet">
					    <input type="hidden" id="appostreetCompleto">
    
					    <spring:bind path="address.city">
					    	<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
					    </spring:bind>
					    	<label><fmt:message key="user.address.city"/></label>
							<form:input  path="address.city" id="us5-city"/>
							<form:errors path="address.city" cssClass="help-block"/>
						</div>
						
						
						<!-- <div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top">
							<label><fmt:message key="user.address.province"/></label> 
						   	
   						</div>  --><form:hidden  path="address.province" id="us5-state" />
   	
					   	<!-- <div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top">
						   	<label><fmt:message key="user.address.postalCode"/></label>
						   	
					   	</div>  --><form:hidden  path="address.postalCode" id="us5-zip" />
   	
   	
					   	<!-- <div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top">
						   	<label><fmt:message key="user.address.country"/></label>
						   	
					   	</div> --><form:hidden  path="address.country" id="us5-country" />

				       	<form:hidden path="address.lat" id="us3-lat"/>
				       	<form:hidden path="address.lng" id="us3-lon"/>
       

       					<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
							<a class="btn btn-green" name="save" onclick="validazioneSubmit( dogSitterForm );">
							<i class="fa fa-check"> </i> <fmt:message key="button.save"/></a>
							
							<a href="<c:url value='/home-utente'/>" class="btn btn-red" name="cancel">
							<i class="fa fa-times"></i> <fmt:message key="button.cancel"/></a>
						</div>


						<script>
						function validazioneSubmit(formId){
							if ( validateDogSitter(formId) ){
								formId.submit();
							}
						}

						jQuery.noConflict();
						(function( $ ) {
							
							if ('${dogSitter.visualizzaCivico}' == 'true' ){
								$('#us5-street1').val($('#appostreetCompleto').val());
							}else{
								$('#us5-street1').val($('#appostreet').val());
							}
						
							$('#checkNumeroCiv').change(function() {
						
						        if($(this).is(":checked") ) {
						        	
						        	$('#us5-street1').val($('#appostreetCompleto').val());
						        }else{
						        	
						        	$('#us5-street1').val($('#appostreet').val());
						        }
						    });
							
						   	function updateControls(addressComponents) {
							    //$('#us5-street1').val(addressComponents.addressLine1);
							    $('#us5-city').val(addressComponents.city);
							    $('#us5-state').val(addressComponents.stateOrProvince);
							    $('#us5-zip').val(addressComponents.postalCode);
							    $('#us5-country').val(addressComponents.country);
							    $('#us5-street1').val(addressComponents.addressLine1).val();
							    $('#appostreet').val(addressComponents.addressLine2).val();
							    $('#appostreetCompleto').val(addressComponents.addressLine3).val();    
							}
						   
							$('#us5').locationpicker({
								location: {latitude: '${dogSitter.address.lat}', longitude: '${dogSitter.address.lng}'},
								zoom: 14,
						        radius: 400,
						        enableAutocomplete: true,
						        inputBinding: {
						            latitudeInput: $('#us3-lat'),
						            longitudeInput: $('#us3-lon'),
						            radiusInput: $('#us3-radius'),
						            locationNameInput: $('#indirizzoCompleto')
						        },
							    onchanged: function (currentLocation, radius, isMarkerDropped) {
							    	
							        var addressComponents = $(this).locationpicker('map').location.addressComponents;
							        updateControls(addressComponents);
							    },
							    oninitialized: function(component) {
							        var addressComponents = $(component).locationpicker('map').location.addressComponents;
							        updateControls(addressComponents);
						   		}
							});
						 
							$('#us5-city').autocomplete({
							    minLength:2,
							    source: "${pageContext.request.contextPath}/getComune"
							});
						
						})( jQuery ); //fine jQuery.noConflict();   
						    
						</script>
						

					</form:form>
   				</div> <!-- fine primo tab -->
   
   				
   				<script src="<c:url value="/js/jquery-2.1.0.min.js"/>"></script>
   				
			    <div role="tabpanel" class="tab-pane" id="foto">
					<%@ include file="/common/uploadImage.jsp" %>
			    </div>
			    
			     
			    <div role="tabpanel" class="tab-pane" id="disponiblita">
					<%@ include file="/common/dateRangeTariffaDogSitter.jsp" %>
			    </div>
   
   
			</div>
		</div>
	</div>
</section>    
    

	
	
<script>


$(document).ready(function(){
	
	// questo è il nuovo
	$('#tabs a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});

	var url = document.location.toString();
	if (url.match('#')) {
	    $('.nav-tabs a[href=#'+url.split('#')[1]+']').tab('show') ;
	}
	
	
});	 
		 
</script>


<!-- questa validazione funziona se nel form si usa il pulsante submit e non un link come sto usando -->
<c:set var="scripts" scope="request">
	<script type="text/javascript">
	// This is here so we can exclude the selectAll call when roles is hidden
	function onFormSubmit(theForm) {
		alert("validateDogSitter");
	    return validateDogSitter(theForm);
	}
	</script>
</c:set>

<v:javascript formName="dogSitter" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

</body>