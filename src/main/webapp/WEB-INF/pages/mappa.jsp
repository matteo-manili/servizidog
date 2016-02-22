<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mappa.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="mappa.title"/>"/>
    <meta name="description" content="<fmt:message key="meta.description.mappa"/>" />
    <meta name="keywords" content="${keywordsGenerica}" />
	<meta name="google" content="nositelinkssearchbox" />
	
	<!-- CSS E JS DI PRETTY CHECKABLE -->
	<!--  link rel="stylesheet" href="<c:url value="/scripts/prettyCheckable-master/dist/prettyCheckable.css"/>" -->
	<!--  script src="<c:url value="/scripts/prettyCheckable-master/dist/prettyCheckable.min.js"/>"></script -->
	<script src="<c:url value="/js/jquery-2.1.0.min.js"/>"></script>
	
	<!-- Google Maps API V3 -->
	 <script type="text/javascript" src="//maps.googleapis.com/maps/api/js?sensor=false&language=it&libraries=places"></script>
	 	
	<!-- Activ'Map plugin -->
	<link rel="stylesheet" href="<c:url value="/scripts/activmap/jquery-activmap/css/jquery-activmap.css"/>">
	<script src="<c:url value="/scripts/activmap/jquery-activmap/js/jquery-activmap.js"/>"></script>
	<script src="<c:url value="/scripts/activmap/jquery-activmap/js/markercluster.min.js"/>"></script>
	
	<!-- Fonts -->
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

    <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

	<script>
	jQuery.noConflict();
	(function( $ ) {
		$(document).ready(function () {
		
		$(function(){
			var local = '${pageContext.request.locale.language}';
			var localRes = local.replace("en", "uk");
		    //Activ'Map plugin init
		    $('#activmap-wrapper').activmap({
		        places : '${pageContext.request.contextPath}/getActiveMap.json',
		        //aggiungo un paramentro (sovrascrive quello in jquery-activmap.js)
	            country: localRes,
	            showPanel: true
		    });
		});
	    
		
		});
	})( jQuery ); //fine jQuery.noConflict();
	</script>

</head>

<body>

<!-- 
<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><fmt:message key="mappa.ricerca"/></h1>
				<hr>
			</div>
		</div>
	</div>
 -->
	<div class="row">
		<!--  div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-center" -->
		<div class="col-md-10 col-md-offset-1 text-center padding">
			<!-- inzio mappa -->
			<!-- input ricerca -->
			<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
	
	            <input id="activmap-location" size="40" type="text" name="location" value="" placeholder="<fmt:message key="ricerca.placeholder"/>">
	
			</div>
	            
	        <!-- scegli servizio -->
			<div class="col-md-3 col-sm-3 col-xs-12 text-left margin-top">
				<!-- Activ'Map categories and tags -->
	            <!-- div class="panel-group" id="activmap-accordion" -->
				<h4 class="panel-title">
					<!-- a class="collapsed" data-toggle="collapse" href="#collapse-tourism" -->
					<fmt:message key="index.presentazione.menu"/>
				</h4>
			</div>
			
			<!-- checkBox -->
			<div class="col-md-3 col-sm-3 col-xs-12 text-left margin-top">
		    	<c:forEach items="${availableRuoliServiziDog}" var="servizi">
					<input type="checkbox" name="marker_type[]" value="${servizi.value}"> ${servizi.label}<br>
				</c:forEach>
			</div>
			
			<!-- bottoni -->
			<div class="col-md-6 col-sm-6 col-xs-12 text-right margin-top">
				<a id="activmap-geolocate" class="btn btn-green"></i>Localizza Posizione</a>
				<a id="activmap-reset" class="btn btn-red"><i class="fa fa-times"></i>Reset</a>
			</div>
			
	
			<!-- mappa -->
		    <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-auto">
	
		            <div style="border: 2px solid orange;" id="activmap-wrapper">
		                <!-- Places panel (auto removable) -->
		                <div  id="activmap-places" class="hidden-xs">
		                    <div id="activmap-results-num"></div>
		                </div>
		                <!-- Map wrapper -->
		                <div  id="activmap-canvas"></div>
		            </div>  
	
			</div>
				
			<!-- fine mappa -->

			
			<!-- banner -->
			<%@ include file="/common/bannerRegistrati.jsp"  %>

		</div>
	</div>
	
	<br>
	<!-- </section>  -->

	
	<br>
	<br>

</body>