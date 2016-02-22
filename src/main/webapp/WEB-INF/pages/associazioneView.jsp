<%@ include file="/common/taglibs.jsp"%>


<head>
    <title>Associazione ${associazione.nome} a ${associazione.address.address}, ${associazione.address.city}</title>
    <meta name="breadcrumb" content="<fmt:message key="associazione.title"/>"/>
    <meta name="description" content="${descrizioneCorta}" />
    <meta name="keywords" content="${keywordsAssociazione}" />
    
    <meta property="og:url" content="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${associazione.urlProfilo}" />
	<meta property="og:type" content="article" />
	<meta property="og:title" content="${associazione.nome}" />
	<meta property="og:description" content="${associazione.descrizione}" />
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
	"@type": "Organization",
	"brand": "${associazione.nome}",
	"description": "${descrizioneCorta}",
	"image": "<c:url value="/img/logo_x_ricerca.png"/>",
	"jobTitle": "<fmt:message key="associazione.title"/>",
	"telephone": "${associazione.telefono}",
	"email": "${indirizzoMail}",
	"url": "associazione.website"
	"address": {
	"@type": "PostalAddress",
	"addressLocality": "${associazione.address.city}",
	"addressRegion": "${associazione.address.province}",
	"postalCode": "${associazione.address.postalCode}",
	"streetAddress": "${associazione.address.address}"
	}
}
</script -->

<div itemscope itemtype="http://schema.org/Organization">

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><span itemprop="brand"><fmt:message key="associazione.title"/></span></h1>
				<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-center">
			<table class="table table-hover">
				<tbody>
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.nomeAssociazione"/></th> -->
					  <td><span itemprop="name">${associazione.nome}</span>
					  <br><div class="fb-share-button" data-href="<fmt:message key="company.url"/>${pageContext.request.contextPath}/${associazione.urlProfilo}" 
				  		data-layout="button"></div>
				  			
					  </td>
					</tr>
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.descrizione"/></th> -->
					  <td><span itemprop="description">${associazione.descrizione}</span></td>
					</tr>
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.nome"/></th> -->
					  <td><span itemprop="member">${nomeCognome}</span></td>
					</tr>
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.indirizzocorto"/></th> -->
					  <td>
					  <div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
					  	<span itemprop="streetAddress">${associazione.address.address}</span>, <span itemprop="addressLocality">${associazione.address.city}</span>, <span itemprop="postalCode">${associazione.address.postalCode}</span>, <span itemprop="addressRegion">${associazione.address.province}</span>
					  </div>
					  </td>
					</tr>
					<c:if test="${not empty associazione.telefono}">
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.telefono"/></th> -->
					  <td>tel. <span itemprop="telephone">${associazione.telefono}</span></td>
					</tr>
					</c:if>
					<c:if test="${not empty associazione.email}">
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.email"/></th> -->
					  <td><a href="mailto:${associazione.email}" itemprop="email">${associazione.email}</a></td>
					</tr>
					</c:if>
					<c:if test="${not empty associazione.website}">
					<tr>
					  <!-- <th scope="row"><fmt:message key="associazione.website"/></th> -->
					  <td><a href="http://${associazione.website}" itemprop="url" target="_blank" >${associazione.website}</a></td>
					</tr>
					</c:if>
				<!-- ------------------------------------------------------------------ -->
				<!-- ------------------------------------------------------------------ -->
				
					<c:forEach items="${caneListContainer.caneList}" var="Cane">
					<tr>
					<!-- <th scope="row"><fmt:message key="cane.caneInAdozione"/></th> -->
					<td>
						<div class="Table text-left">
							<!-- <div class="Title"><fmt:message key="cane.schedacane"/> </div> -->
						    <div class="Row">
						        <div class="Cell" style="font-weight: bold;"><fmt:message key="cane.nome"/> &nbsp;</div> <div class="Cell"> ${Cane.nomeCane} </div>
						    </div>
						    <div class="Row">
								<div class="Cell" style="font-weight: bold;"><fmt:message key="cane.anni"/> &nbsp;</div> <div class="Cell"> ${Cane.eta} </div>
								
								<div class="Cell" style="font-weight: bold;"> &nbsp; <fmt:message key="cane.sesso"/> &nbsp;</div> <div class="Cell"><fmt:message key="${Cane.sesso}"/></div>
						    </div>
							<div class="Row">
								<div class="Cell" style="font-weight: bold;"> <fmt:message key="cane.taglia"/> &nbsp;</div> <div class="Cell"><fmt:message key="${Cane.taglia}"/></div> 
								
								<div class="Cell" style="font-weight: bold;"> &nbsp;<fmt:message key="cane.pelo"/> &nbsp;</div> <div class="Cell"><fmt:message key="${Cane.pelo}"/></div> 
							</div>
							<div class="Row">
						        <div class="Cell" style="font-weight: bold;"><fmt:message key="cane.carattere"/> &nbsp;</div> <div class="Cell">${Cane.carattere}</div>
								
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
				  <a href="<c:url value='/associazione'/>"><fmt:message key="associazione.lista"/></a>
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
	var coord = { 'lat': ${associazione.address.lat}, 'lng': ${associazione.address.lng} };
	
	function initMap() {
  	var map = new google.maps.Map(document.getElementById('mappa'), {
	    zoom: 14,
	    center: coord,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
  	});

  	  var image = '${pageContext.request.contextPath}/scripts/activmap/images/icons/marker_associazioni.png';
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