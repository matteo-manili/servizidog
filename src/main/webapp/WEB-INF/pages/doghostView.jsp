<%@ include file="/common/taglibs.jsp"%>


<head>
    <title>Pensione per Cani a ${dogHost.address.city}, ${dogHost.address.address}</title>
    <meta name="breadcrumb" content="<fmt:message key="doghost.title"/>"/>
    <meta name="description" content="${descrizioneCorta}" />
	<meta name="keywords" content="${keywordsDogHost}" />
    
    <meta property="og:url" content="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${dogHost.urlProfilo}" />
	<meta property="og:type" content="article" />
	<meta property="og:title" content="${nomeCognome}" />
	<meta property="og:description" content="${dogHost.descrizione}" />
	<c:if test="${not empty imagesShareFacebook}">
		<meta property="og:image" content="<fmt:message key="company.url"/><c:url value="/picture/${imagesShareFacebook.id}"/>" />
	</c:if>
	
    
	<script src="<c:url value="/js/jquery-2.1.0.min.js"/>"></script>
	<link rel="stylesheet" href="<c:url value="/scripts/CLNDR/example/styles/clndr.css"/>">
</head>

<body>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/it_IT/sdk.js#xfbml=1&version=v2.5&appId=547748282047154";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<!-- script type="application/ld+json"> (questo lo stanno ancora sviluppando quelli di google, non è raccomandato)
{
	"@context": "http://schema.org",
	"@type": "Person",
	"name": "${nomeCognome}",
	"description": "${descrizioneCorta}",
	"image": "<c:url value="/img/logo_x_ricerca.png"/>",
	"brand": "<fmt:message key="doghost.title"/>",
	"telephone": "${dogHost.telefono}",
	"email": "${indirizzoMail}",
	"address": {
	"@type": "PostalAddress",
	"addressLocality": "${dogHost.address.city}",
	"addressRegion": "${dogHost.address.province}",
	"postalCode": "${dogHost.address.postalCode}",
	"streetAddress": "${dogHost.address.address}"
	},
	"makesOffer": {
	  "@type": "Offer",
	  "priceSpecification": {
		  "price": "${dogHost.tariffa}",
		  "priceCurrency": "EUR"
	  }
	}
}
</script -->


<div itemscope itemtype="http://schema.org/Organization">

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><span itemprop="brand"><fmt:message key="doghost.title"/></span></h1>
				<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-center">
			<table class="table table-hover">
				<tbody>
					<!-- <tr>
					  <th scope="row"><fmt:message key="user.firstName"/></th>
					  <td>${dogHost.titolo}</td>
					</tr>  -->
					<tr>
					  <!-- <th scope="row"><fmt:message key="doghost.descrizioneCorta"/></th> -->
					  <td><span itemprop="description">${dogHost.descrizione}</span>
					  <br><div class="fb-share-button" data-href="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${dogHost.urlProfilo}" 
				  		data-layout="button"></div>
				  		
				  	</td>
					</tr>
					<tr>
					  <!-- <th scope="row">Nome</th> -->
					  <td><span itemprop="name">${nomeCognome}</span></td>
					</tr>
					<tr>
					  <!-- <th scope="row">indirizzo</th> -->
					  <td>
					  <div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
					  	<span itemprop="streetAddress">${dogHost.address.address}</span>, <span itemprop="addressLocality">${dogHost.address.city}</span>, <span itemprop="postalCode">${dogHost.address.postalCode}</span>, <span itemprop="addressRegion">${dogHost.address.province}</span>
					  </div>
					  </td>
					</tr>
					<c:if test="${not empty dogHost.telefono}">
					<tr>
					  <!-- <th scope="row">Telefono</th> -->
					  <td>tel. <span itemprop="telephone">${dogHost.telefono}</span></td>
					</tr>
					</c:if>
					<tr>
					  <!-- <th scope="row">Email</th> -->
					  <td><a href="mailto:${indirizzoMail}" itemprop="email">${indirizzoMail}</a></td>
					</tr>
					
					
					<c:if test="${not empty dogHost.metriQuadrati}">
					<tr>
						<!-- <th scope="row">Info Alloggio</th> -->
						<td><strong>metri quadrati:</strong> ${dogHost.metriQuadrati} 
						
							&nbsp;<strong>terrazza:</strong> <c:choose>
							    <c:when test="${dogHost.terrazza}">presente</c:when> <c:otherwise>non presente</c:otherwise> </c:choose>
				
							&nbsp;<strong>giardino:</strong> <c:choose>
							    <c:when test="${dogHost.giardino}">presente</c:when> <c:otherwise>non presente</c:otherwise> </c:choose>
								
							&nbsp;<strong>animali presenti:</strong> <c:choose> 
								<c:when test="${dogHost.animaliPresenti}">si</c:when> <c:otherwise>no</c:otherwise> </c:choose> </td>
					</tr>
					</c:if>
					
					
					<c:if test="${not empty dogHost.tariffa}">
					<tr>
					<td>
					  <!-- <th scope="row"><fmt:message key="doghost.tariffagiornaliera"/></th> -->
						<div itemprop="makesOffer" itemscope itemtype="http://schema.org/Offer">
						<meta itemprop="priceCurrency" content="EUR" />
						<meta itemprop="price" content="${dogHost.tariffa}" />${dogHost.tariffa} &euro; notte
						</div>
					</td>
					</tr>
					</c:if>
					
					<fmt:formatDate value="${dogHost.dataFineDisponib}" pattern="dd-yyyy" var="fineDisp" />
					<fmt:formatDate value="${now}" pattern="dd-yyyy" var="adesso" />
					
					<c:if test="${not empty dogHost.dataInizioDisponib && not empty dogHost.dataFineDisponib && 
							dogHost.dataInizioDisponib ne dogHost.dataFineDisponib && fineDisp ge adesso}">
					<tr>
					  <!-- <th scope="row"><fmt:message key="doghost.disponibilitaservizio"/></th> -->
					  <td><div class="cal1" style="width: 500px" ></div></td>
					</tr>
					</c:if>
					
					
					<tr>
					  <!-- <th scope="row"><fmt:message key="doghost.mappaView"/></th> -->
					  <td><div id="mappa" style="width: 100%px; min-width: 280px; height:100%; min-height:300px;"></div></td>
					</tr>
					<c:if test="${not empty images}">
					<tr>
					<td colspan="2">
						  <c:forEach items="${images}" var="item">
						  	<img src="<c:url value="/picture/${item.id}"/>" itemprop="image" alt="" width="200" >
						  </c:forEach>
					  	
					  </td>
					</tr>
					</c:if>
					
					<tr>
					  <td>
					  <h1>
					  <i class="fa fa-th-list" style="color:#FF9227"></i>
					  <a href="<c:url value='/doghost'/>"><fmt:message key="doghost.lista"/></a>
					  </h1>
					  </td>
					</tr>
					
					<tr>
					  <td>
					  <!-- banner -->
						<%@ include file="/common/bannerRegistrati.jsp"  %>
					  </td>
					</tr>
					
					
				</tbody>
			</table>
		</div>
	</div>
</section>
	
</div>

<script>
var local = '${pageContext.request.locale.language}';
var inizioDay = '${dogHost.dataInizioDisponib}';
var fineDay = '${dogHost.dataFineDisponib}';
//call this from the developer console and you can control both instances


jQuery.noConflict();
(function( $ ) {
	
	var calendars = {};

$(document).ready( function() {

  var thisMonth = moment().format('YYYY-MM');
  var eventArray = [
    { startDate: inizioDay, endDate: fineDay, title: 'Disponibilita' }
  ];


  calendars.clndr1 = $('.cal1').clndr({
    events: eventArray,

    clickEvents: {
      click: function(target) {
        console.log(target);
      },
      nextMonth: function() {
        console.log('next month.');
      },
      previousMonth: function() {
        console.log('previous month.');
      },
      onMonthChange: function() {
        console.log('month changed.');
      },
      nextYear: function() {
        console.log('next year.');
      },
      previousYear: function() {
        console.log('previous year.');
      },
      onYearChange: function() {
        console.log('year changed.');
      }
    },
    multiDayEvents: {
      startDate: 'startDate',
      endDate: 'endDate',
      singleDay: 'date'
    },
    showAdjacentMonths: true,
    adjacentDaysChangeMonth: false
  });


  // bind both clndrs to the left and right arrow keys
  $(document).keydown( function(e) {
    if(e.keyCode == 37) {
      // left arrow
      calendars.clndr1.back();
      calendars.clndr2.back();
    }
    if(e.keyCode == 39) {
      // right arrow
      calendars.clndr1.forward();
      calendars.clndr2.forward();
    }
  });

});


})( jQuery ); //fine jQuery.noConflict();


//----------------fine calendario

	var coord = { 'lat': ${dogHost.address.lat}, 'lng': ${dogHost.address.lng} };
	
	function initMap() {
  	var map = new google.maps.Map(document.getElementById('mappa'), {
	    zoom: 14,
	    center: coord,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
  	});
	  	/* var circle = new google.maps.Circle({
		strokeColor: '#2a00ff',
	 	strokeOpacity: 0.8,
	  	strokeWeight: 2,
	  	fillColor: '#2a00ff',
	  	fillOpacity: 0.35,
	  	map: map,
	  	center: coord,
	  	radius: 400 */
	  	
	  	  var image = '${pageContext.request.contextPath}/scripts/activmap/images/icons/marker_doghost.png';
	  	  var beachMarker = new google.maps.Marker({
	  	    position: coord,
	  	    map: map,
	  	    icon: image
	  	
	  })
	};

	

	
    </script>


	<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
	<script src= "<c:url value="/scripts/CLNDR/example/moment-2.8.3.js"/>"></script>
	<script src="<c:url value="/scripts/CLNDR/src/clndr.js"/>"></script>

	
	<script async defer
	        src="https://maps.googleapis.com/maps/api/js?signed_in=true&callback=initMap"></script>
	

</body>