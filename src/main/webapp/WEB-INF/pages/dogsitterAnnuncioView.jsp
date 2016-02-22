<%@ include file="/common/taglibs.jsp"%>


<head>
	<title>Dog Sitter a ${dogSitterAnnuncio.luogo}, ${dogSitterAnnuncio.nomeUtente}</title>
	<meta name="breadcrumb" content="<fmt:message key="dogsitter.title"/>"/>
	<meta name="description" content="${descrizioneCorta}" />
	<meta name="keywords" content="${keywordsDogSitter}" />

	<meta property="og:url" content="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${dogSitterAnnuncio.urlProfilo}" />
	<meta property="og:type" content="article" />
	<meta property="og:title" content="${dogSitterAnnuncio.nomeUtente} - ${dogSitterAnnuncio.luogo}" />
	<meta property="og:description" content="${dogSitterAnnuncio.descrizione}" />
	<c:if test="${not empty dogSitterAnnuncio.immagine1}">
		<meta property="og:image" content="<fmt:message key="company.url"/><c:url value="/immagineGrandeAnnuncioDogSitter/1/${dogSitterAnnuncio.idAnnuncio}"/>" />
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
				  <td><span itemprop="description">${dogSitterAnnuncio.descrizione}</span>
				  <br><div class="fb-share-button" data-href="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${dogSitterAnnuncio.urlProfilo}" 
				  		data-layout="button"></div>
				  </td>
				</tr>
				<tr>
				  <!-- <th scope="row"><fmt:message key="user.firstName"/></th> -->
				  <td><span itemprop="name">${dogSitterAnnuncio.nomeUtente}</span>
				  </td>
				</tr>
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.indirizzocorto"/></th> -->
				  <td>
				  <div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
				  	<span itemprop="addressLocality">${dogSitterAnnuncio.luogo}</span>
				  </div>
				  </td>
				</tr>

				<c:if test="${not empty dogSitterAnnuncio.telefono}">
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.telefono"/></th> -->
				  <td>tel.<span itemprop="telephone">${dogSitterAnnuncio.telefono}</span></td>
				</tr>
				</c:if>

				<c:if test="${not empty dogSitterAnnuncio.tariffa}">
				<tr>
				<!-- <th scope="row"><fmt:message key="dogsitter.tariffaoraria"/></th> -->
				<td>
				<div itemprop="makesOffer" itemscope itemtype="http://schema.org/Offer">
					<meta itemprop="priceCurrency" content="EUR" />
					<meta itemprop="price" content="${dogSitterAnnuncio.tariffa}" />${dogSitterAnnuncio.tariffa} &euro; ora
					<meta itemprop="validFrom" content="<fmt:formatDate value="${dogSitterAnnuncio.dataPubblicazione}" pattern="yyyy-mm-dd hh:mm:ss"/>" />
				</div>	
				</td>
				</tr>
				</c:if>

				<tr>
				  <td>pubblicato il <fmt:formatDate value="${dogSitterAnnuncio.dataPubblicazione}" pattern="dd MMMM yyyy"/></td>
				</tr>
				
				<c:if test="${not empty dogSitterAnnuncio.immagine1}">
				<tr>
				<td colspan="2">
					  	<img src="<c:url value="/immagineGrandeAnnuncioDogSitter/1/${dogSitterAnnuncio.idAnnuncio}"/>" itemprop="image" alt="${nomeCognome}" width="200">
					  	
					  	<c:if test="${not empty dogSitterAnnuncio.immagine2}">
					  		<img src="<c:url value="/immagineGrandeAnnuncioDogSitter/2/${dogSitterAnnuncio.idAnnuncio}"/>" alt="${nomeCognome}" width="200">
					  	</c:if>
					  	
					  	<c:if test="${not empty dogSitterAnnuncio.immagine3}">
					  		<img src="<c:url value="/immagineGrandeAnnuncioDogSitter/3/${dogSitterAnnuncio.idAnnuncio}"/>" alt="${nomeCognome}" width="200">
					  	</c:if>
				  </td>
				</tr>
				</c:if>
				
				
				
				<c:if test="${dogSitterAnnuncio.lat != 0 || dogSitterAnnuncio.lng != 0 }">
				<tr>
				  <!-- <th scope="row"><fmt:message key="dogsitter.mappaView"/></th> -->
				  <td><div id="mappa" style="width: 100%px; min-width: 280px; height:100%; min-height:300px;"></div></td>
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

				
			</tbody>
		</table>
	
		
		
	</div> <!-- fine div col -->
		
		
		
		
		
		
	</div>
</section>
</div><!-- fine http://schema.org/Person -->
	



<script>


	var coord = { 'lat': ${dogSitterAnnuncio.lat}, 'lng': ${dogSitterAnnuncio.lng} };
	
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