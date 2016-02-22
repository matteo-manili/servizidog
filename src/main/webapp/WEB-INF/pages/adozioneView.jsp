
<%@ include file="/common/taglibs.jsp"%>


<head>
    <title>Adotta un Cane a ${adozione.address.city}, ${adozione.address.address}</title>
    <meta name="breadcrumb" content="<fmt:message key="adozione.title"/>"/>
    <meta name="description" content="${descrizioneCorta}" />
	<meta name="keywords" content="${keywordsAdozione}" />

	<meta property="og:url" content="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${adozione.urlProfilo}" />
	<meta property="og:type" content="article" />
	<meta property="og:title" content="${nomeCognome}" />
	<meta property="og:description" content="${adozione.descrizione}" />
	<c:if test="${not empty imagesShareFacebook}">
		<meta property="og:image" content="<fmt:message key="company.url"/><c:url value="/picture/${imagesShareFacebook.id}"/>" />
	</c:if>

	<style type="text/css">
	    .Table
	    {
	        display: table;
	    }
		.Title
	    {
	        display: table-caption;
	        text-align: center;
	        font-weight: bold;
	        font-size: larger;
	    }
	    .Heading
	    {
	        display: table-row;
	        font-weight: bold;
	        text-align: center;
	    }
	    .Row
	    {
	        display: table-row;
	    }
	    .Cell
	    {
	        display: table-cell;
	    }
	</style>

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
	"jobTitle": "<fmt:message key="adozione.title"/>",
	"telephone": "${adozione.telefono}",
	"email": "${indirizzoMail}",
	"address": {
	"@type": "PostalAddress",
	"addressLocality": "${adozione.address.city}",
	"addressRegion": "${adozione.address.province}",
	"postalCode": "${adozione.address.postalCode}",
	"streetAddress": "${adozione.address.address}"
	}
}
</script -->

<div itemscope itemtype="http://schema.org/Person">

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><span itemprop="brand"><fmt:message key="adozione.adozionecane.title"/></span></h1>
				<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-center">
			<table class="table table-hover">
				<tbody>
					<tr>
					  <!-- <th scope="row"><fmt:message key="adozione.descrizionecorta"/></th> -->
					  <td><span itemprop="description">${adozione.descrizione}</span>
					  <br><div class="fb-share-button" data-href="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${adozione.urlProfilo}" 
				  		data-layout="button"></div>
				  		
				  	</td>
					</tr>
					<tr>
					  <!-- <th scope="row"><fmt:message key="adozione.nome"/></th> -->
					  <td><span itemprop="name">${nomeCognome}</span></td>
					</tr>
					<tr>
					  <!-- <th scope="row">Data Annuncio</th> -->
					  <td>Data Annuncio: <fmt:formatDate value="${adozione.ultimaModifica}" pattern="dd-MM-yyyy"/></td>
					</tr>
					<tr>
					  <!-- <th scope="row"><fmt:message key="adozione.indirizzocorto"/></th> -->
					  <td>
					  <div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
					  	<span itemprop="streetAddress">${adozione.address.address}</span>, <span itemprop="addressLocality">${adozione.address.city}</span>, <span itemprop="postalCode">${adozione.address.postalCode}</span>, <span itemprop="addressRegion">${adozione.address.province}</span>
					  </div>
					  </td>
					</tr>
					<c:if test="${not empty adozione.telefono}">
					<tr>
					  <!-- <th scope="row"><fmt:message key="adozione.telefono"/></th> -->
					  <td>tel. <span itemprop="telephone">${adozione.telefono}</span></td>
					</tr>
					</c:if>
					<tr>
					  <!-- <th scope="row"><fmt:message key="adozione.email"/></th> -->
					  <td><a href="mailto:${indirizzoMail}" itemprop="email">${indirizzoMail}</a></td>
					</tr>
					

				<!-- ------------------------------------------------------------------ -->
				<!-- ------------------------------------------------------------------ -->
				
					<c:forEach items="${caneListContainer.caneList}" var="Cane">
					<tr>
					<!-- <th scope="row"><fmt:message key="cane.caneInAdozione"/></th> -->
					<td>
						<div class="Table text-left">
							<!-- <div class="Title"><fmt:message key="cane.schedacane"/> </div> -->
							
						    <div class="Row">
						        <div class="Cell" style="font-weight: bold;"><fmt:message key="cane.nome"/> &nbsp;</div> <div class="Cell">${Cane.nomeCane}</div>
						        
						    </div>
						    
						    <div class="Row">
								<div class="Cell" style="font-weight: bold;"><fmt:message key="cane.anni"/> &nbsp;</div> <div class="Cell">${Cane.eta}</div>
								
								<div class="Cell" style="font-weight: bold;"> &nbsp; <fmt:message key="cane.sesso"/> &nbsp;</div> <div class="Cell"> <fmt:message key="${Cane.sesso}"/></div>
						    </div>
						    
							<div class="Row">
								<div class="Cell" style="font-weight: bold;"> <fmt:message key="cane.taglia"/> &nbsp;</div> <div class="Cell"><fmt:message key="${Cane.taglia}"/></div> 
								
								<div class="Cell" style="font-weight: bold;"> &nbsp;<fmt:message key="cane.pelo"/> &nbsp;</div> <div class="Cell"><fmt:message key="${Cane.pelo}"/></div> 
							</div>
					
							<div class="Row">
						        <div class="Cell" style="font-weight: bold;"> <fmt:message key="cane.carattere"/> &nbsp;</div> <div class="Cell">${Cane.carattere}</div>
								
						        <div class="Cell" style="font-weight: bold;"> &nbsp; <fmt:message key="cane.info"/> &nbsp;</div> <div class="Cell">${Cane.storiadelcane}</div>	    
							</div>
					
					    </div>
					    
					</td>
					</tr>
					</c:forEach>
		
				<!-- -------------------------------------------------------- -->
				<!-- -------------------------------------------------------- -->
					<c:if test="${not empty images}">
					<tr>
					<td colspan="2">
						  <c:forEach items="${images}" var="item">
						  	<img src="<c:url value="/picture/${item.id}"/>" itemprop="image" alt="" width="200">
						  </c:forEach>
					  	
					  </td>
					</tr>
					</c:if>

					<tr>
					  <!-- <th scope="row"><fmt:message key="adozione.mappa"/></th> -->
					  <td><div id="mappa" style="width: 100%px; min-width: 280px; height:100%; min-height:300px;"></div></td>
					</tr>
				</tbody>
				
				<tr>
				  <td>
				  <h1>
				  <i class="fa fa-th-list" style="color:#FF9227"></i>
				  <a href="<c:url value='/adozione'/>"><fmt:message key="adozione.lista"/></a>
				  </h1>
				  </td>
				</tr>

				
				<tr>
				  <td>
				  <!-- banner -->
					<%@ include file="/common/bannerRegistrati.jsp"  %>
				  </td>
				</tr>
				

				
				
				
			</table>
		</div>
	</div>
</section>

</div>

<script>
var local = '${pageContext.request.locale.language}';

//----mappa
	var coord = { 'lat': ${adozione.address.lat}, 'lng': ${adozione.address.lng} };
	
	function initMap() {
  	var map = new google.maps.Map(document.getElementById('mappa'), {
	    zoom: 14,
	    center: coord,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
  	});
  	/*
	  	var circle = new google.maps.Circle({
		strokeColor: '#2a00ff',
	 	strokeOpacity: 0.8,
	  	strokeWeight: 2,
	  	fillColor: '#2a00ff',
	  	fillOpacity: 0.35,
	  	map: map,
	  	center: coord,
	  	radius: 400*/
	  	  var image = '${pageContext.request.contextPath}/scripts/activmap/images/icons/marker_adozione.png';
	  	  var beachMarker = new google.maps.Marker({
	  	    position: coord,
	  	    map: map,
	  	    icon: image
	  	
	  })
	};

	

	
    </script>


	<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>

	
	<script async defer
	        src="https://maps.googleapis.com/maps/api/js?signed_in=true&callback=initMap"></script>
	

</body>