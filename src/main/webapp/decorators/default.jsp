<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="it">
<head>
<meta charset="utf-8">
	<title><decorator:title/> | <fmt:message key="webapp.name"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="x-ua-compatible" content="ie=edge,chrome=1">
	<meta name="robots" content="index,follow" />

	<meta name="google-site-verification" content="cet-g7DtqNNjsD1cQsVg3wtFbtcgBJ-TBGKnXS6ND_A" />
	<meta name="msvalidate.01" content="E95ADFF454F4AFFCA5AD17D4BEAF4CD0" /> <!-- Verifica BING motore di ricerca -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-title" content="<fmt:message key="webapp.name"/>">
	<meta property="fb:app_id" content="547748282047154" />
	<decorator:head/>
	<t:assets type="css"/>
	
	<link rel="apple-touch-icon" href="apple-touch-icon.png">
	<link rel="alternate" hreflang="en" href="${pageContext.request.requestURI}?locale=en" />
	<link rel="alternate" hreflang="es" href="${pageContext.request.requestURI}?locale=es" />
	
	<!-- Place favicon.ico in the root directory -->
	<link rel="icon" href="<c:url value="/img/favicon.ico"/>"/>
	
	<!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-65884370-1', 'auto');
	  ga('send', 'pageview');
	</script>
</head>


<body>
	<!-- Google Tag Manager -->
	<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-TN4ZC9"
	height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-TN4ZC9');</script>
	<!-- End Google Tag Manager -->

	<!-- role="navigation" -->
	<nav class="navbar navbar-default navbar-fixed-top" >
			<div class="container">
		
			<!-- PRIMA PARTE DEL MENU -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<i class="fa fa-paw list toggle-menu"></i>
					</button>
				<a class="navbar-brand" href="<fmt:message key="company.url"/>"><img src="<c:url value='/img/servizidog.png'/>" alt="<fmt:message key="webapp.name"/>" /></a>
			</div>
			<!-- SECONDA PARTE DEL MENU -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right"> 
					<%@ include file="/common/menu.jsp" %>
				</ul>
			</div>
			
		</div>
	</nav>
	<!-- fine MENU nuova grafica -->
	
	<c:set var="page"><decorator:getProperty property="meta.breadcrumb"/></c:set>
	
	<c:if test="${pageContext.request.remoteUser != null && page != 'Index'}">
		<div class="breadcrumb">
			<div class="container">
				<li><a href="<c:url value='/home'/>"> <fmt:message key="home.title"/> </a> /</li>
				<li class="active"> <decorator:getProperty property="meta.breadcrumb"/> </li>
			</div>
		</div>
	</c:if>
	
	
	<c:if test="${pageContext.request.remoteUser == null && page != 'Index'}">
		<div class="breadcrumb">
			<div class="container">
				<li><a href="<c:url value='/'/>"> <fmt:message key="home.title"/> </a> /</li>
				<li class="active"> <decorator:getProperty property="meta.breadcrumb"/> </li>
			</div>
		</div>
	</c:if>

		
	<!-- BODY PAGE -->
	<decorator:body/>
	

	<!-- FOOTER -->
	<footer>
		<div class="container">
			<p></p>
			<div class="row">
				<div class="col-md-12 col-sm-12 text-center"> 
					<a href="<c:url value='/'/>"> <fmt:message key="home.title"/> </a> |
					<!-- <a href="">Servizi</a> | -->
					<a href="<c:url value='/contatti'/>"><fmt:message key="index.contatti"/></a> 
					<!--  <a href="">Regolamento</a> | -->
					<!--  <a href="">Privacy Policy</a> -->
				</div>
			</div>
			<div class="row margin-top">
				<div class="col-md-12 col-sm-12 text-center">
					<a href="https://www.facebook.com/servizidog" target="_blank"><i class="fa fa-2x fa-facebook"></i></a>
					<!-- <a href=""><i class="fa fa-2x fa-twitter"></i></a>
					<a href=""><i class="fa fa-2x fa-google-plus"></i></a>
					<a href=""><i class="fa fa-2x fa-linkedin"></i></a>
					<a href=""><i class="fa fa-2x fa-youtube"></i></a>  -->
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-sm-12 text-center">  
					<a href="<c:url value='/'/>"><img src="<c:url value='/img/servizidog.png'/>" alt="<fmt:message key="webapp.name"/>" /></a>
					<!-- language -->
					<div class="col-md-12 col-sm-12 text-center"> 
						<a href="${pageContext.request.requestURI}?locale=it"> <fmt:message key="index.italiano"/> </a> |
						<a href="${pageContext.request.requestURI}?locale=en"> <fmt:message key="index.english"/> </a> |
						<a href="${pageContext.request.requestURI}?locale=es"> <fmt:message key="index.espanyol"/> </a>
					</div>
					<br><br>
					<p>© Copyright 2015. Servizi Dog. Tutti i diritti riservati.</p>
					<p>Realizzazione Grafica a cura di <a href="http://www.riccardoborchi.it/">RB Web Design di Borchi Riccardo</a></p>
				</div>
			</div>
		</div>
	</footer>
  
  
	<t:assets type="js"/>    
	<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>

	<script>
		//serve l'animazione della index.jsp
		new WOW().init();
	</script>
	


</body>
</html>
