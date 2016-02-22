<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="canismarriti.title"/></title>
    <meta name="menu" content="CaniSmarriti"/>
</head>
<script src="<c:url value="/scripts/vendor/jquery-ui-1.11.4/external/jquery/jquery.js"/>"></script>

<script src="<c:url value="/scripts/vendor/jquery-ui-1.11.4/autocomplete.js"/>"></script>
<script src="<c:url value="/scripts/vendor/jquery-ui-1.11.4/datepicker.js"/>"></script>
<script src="<c:url value="/scripts/vendor/jquery-ui-1.11.4/i18n/datepicker-it.js"/>"></script>
<script src="<c:url value="/scripts/vendor/jquery-ui-1.11.4/i18n/datepicker-es.js"/>"></script>


<body>
	<div class="row">
		<div class="col-md-6 col-md-offset-3 text-center padding">
		
		<h3>PROVE JQUERY UI</h3><br>
		
		prova autocomplete comuni: 
		<input id="comuni" size="40">
		
		<br><br>
		prova autocomplete persone: 
		<input id="group_id">
		 
		<script>
		$('#comuni').autocomplete({
		    minLength:2,
		    source: "${pageContext.request.contextPath}/getComune"
		   
		});
		
		$('#group_id').autocomplete({
		    source: "${pageContext.request.contextPath}/getTags"
		});
		
		
		

		</script>
		<br><br>
		
		<br><br>
		
		<input type="text" name="date" id="date">
		<script type="text/javascript">
		$( "#date" ).datepicker($.d${request.getLocale()}atepicker.regional['${pageContext.request.locale.language}']);
		</script>
		
		
		<br><br>
		 <a id="mioLink" href="http://www.html.it">link</a>
		<script type="text/javascript">
		$("#mioLink").text("Nuovo testo").css("color","green");
		</script>
		
		</div>
		</div>
				</body>
