<%@ include file="/common/taglibs.jsp"%>


<head>
	<title>Dog Sitter a ${dogSitter.address.city}, ${dogSitter.address.address}</title>
	<meta name="breadcrumb" content="<fmt:message key="dogsitter.title"/>"/>
	<meta name="description" content="${descrizioneCorta}" />
	<meta name="keywords" content="${keywordsDogSitter}" />

	<meta property="og:url" content="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${dogSitter.urlProfilo}" />
	<meta property="og:type" content="article" />
	<meta property="og:title" content="${nomeCognome}" />
	<meta property="og:description" content="${dogSitter.descrizione}" />
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


<div itemscope itemtype="http://schema.org/Person">

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><span itemprop="brand"><fmt:message key="dogsitter.title"/></span></h1>

				<hr>
			</div>
		</div>
	</div>

	<div class="row">

	<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-center">
	
	
		<table class="table table-hover">
			<tbody>

				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.descrizioneCorta"/></th> -->
				  <td><span itemprop="description">${dogSitter.descrizione}</span>
				  <br><div class="fb-share-button" data-href="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${dogSitter.urlProfilo}" 
				  		data-layout="button"></div>
				</td>
				</tr>
				<tr>
				  <!-- <th scope="row"><fmt:message key="user.firstName"/></th> -->
				  <td><span itemprop="name">${nomeCognome}</span>
				  </td>
				</tr>
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.indirizzocorto"/></th> -->
				  <td>
				  <div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
				  	<span itemprop="streetAddress">${dogSitter.address.address}</span>, <span itemprop="addressLocality">${dogSitter.address.city}</span>, <span itemprop="postalCode">${dogSitter.address.postalCode}</span>, <span itemprop="addressRegion">${dogSitter.address.province}</span>
				  </div>
				  </td>
				</tr>

				<c:if test="${not empty dogSitter.telefono}">
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.telefono"/></th> -->
				  <td>tel.<span itemprop="telephone">${dogSitter.telefono}</span></td>
				</tr>
				</c:if>
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.email"/></th> -->
				  <td><a href="mailto:${indirizzoMail}" itemprop="email">${indirizzoMail}</a></td>
				</tr>

				<c:if test="${not empty dogSitter.tariffa}">
				<tr>
				<!-- <th scope="row"><fmt:message key="dogsitter.tariffaoraria"/></th> -->
				<td>
				<div itemprop="makesOffer" itemscope itemtype="http://schema.org/Offer">
					<meta itemprop="priceCurrency" content="EUR" />
					<meta itemprop="price" content="${dogSitter.tariffa}" />${dogSitter.tariffa} &euro; ora
				</div>
				</td>
				</tr>
				</c:if>

				
				
				<fmt:formatDate value="${dogSitter.dataFineDisponib}" pattern="dd-yyyy" var="fineDisp" />
				<fmt:formatDate value="${now}" pattern="dd-yyyy" var="adesso" />
				
				<c:if test="${not empty dogSitter.dataInizioDisponib && not empty dogSitter.dataFineDisponib && 
						dogSitter.dataInizioDisponib ne dogSitter.dataFineDisponib && fineDisp ge adesso}">
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.disponibilitaservizio"/></th> -->
				  <td><div class="cal1" style="width: 100%px; min-width: 280px; height:100%; min-height:300px;" ></div></td>
				</tr>
				</c:if>
				
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.mappaView"/></th> -->
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
				  <a href="<c:url value='/dogsitter'/>"><fmt:message key="dogsitter.lista"/></a>
				  </h1>
				  </td>
				</tr>
				
				<tr>
				  <td>
				  <!-- banner -->
					<%@ include file="/common/bannerRegistrati.jsp"  %>
				  </td>
				</tr>
				
				<!-- <tr>
				  <td>  -->
					<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
					<!-- adsense_matteo_1 
					<ins class="adsbygoogle"
					     style="display:block"
					     data-ad-client="ca-pub-3883726305395812"
					     data-ad-slot="5757935484"
					     data-ad-format="auto"></ins> -->
					<script>
						//(adsbygoogle = window.adsbygoogle || []).push({});
					</script>
				  <!-- </td>
				</tr>  -->
				
			</tbody>
		</table>
	
		
		
	</div> <!-- fine div col -->
		
		
		
		
		
		
	</div>
</section>
</div><!-- fine http://schema.org/Person -->
	



<script>
var local = '${pageContext.request.locale.language}';
var inizioDay = '${dogSitter.dataInizioDisponib}';
var fineDay = '${dogSitter.dataFineDisponib}';
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

	var coord = { 'lat': ${dogSitter.address.lat}, 'lng': ${dogSitter.address.lng} };
	
	function initMap() {
  	var map = new google.maps.Map(document.getElementById('mappa'), {
	    zoom: 14,
	    center: coord,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
  	});
	  	var circle = new google.maps.Circle({
		strokeColor: '#2a00ff',
	 	strokeOpacity: 0.8,
	  	strokeWeight: 2,
	  	fillColor: '#2a00ff',
	  	fillOpacity: 0.35,
	  	map: map,
	  	center: coord,
	  	radius: 400
	  })
	};

	

	
    </script>


	<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
	<script src= "<c:url value="/scripts/CLNDR/example/moment-2.8.3.js"/>"></script>
	<script src="<c:url value="/scripts/CLNDR/src/clndr.js"/>"></script>

	
	<script async defer
	        src="https://maps.googleapis.com/maps/api/js?signed_in=true&callback=initMap"></script>
	

</body>