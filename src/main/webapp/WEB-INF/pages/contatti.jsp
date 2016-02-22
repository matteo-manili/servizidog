<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="index.contatti"/></title>
	<meta name="breadcrumb" content="<fmt:message key="index.contatti"/>"/>
	<meta name="description" content="<fmt:message key="meta.description.contatti"/>" />
	<meta name="keywords" content="${keywordsGenerica}" />
	
</head>
<body> 

<%@ include file="/common/messages.jsp" %>

		<!-- CONTACT -->
		
		
		
	<spring:bind path="contatti.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="alert alert-danger alert-dismissable">
			    <a href="#" data-dismiss="alert" class="close">&times;</a>
			    <c:forEach var="error" items="${status.errorMessages}">
			<c:out value="${error}" escapeXml="false"/><br/>
			</c:forEach>
			</div>
		</c:if>
	</spring:bind>



<section id="contact">
	<div class="container">
	
		
	
	
		<div class="row">
			<div class="col-md-12 col-sm-12 text-center">
				<h1>Contatti</h1>
				<hr>
				<!-- <p>Inviaci un messaggio, ti risponderemo al più presto.</p>  -->
			</div>
		</div>


<!-- DONAZIONE -->
		<div class="row text-right">
		
			<div class="col-md-3 margin-top col-sm-3$">

			</div>
			<div class="col-md-7 margin-top col-sm-7$">
				<b>Aiutaci a portare avanti il nostro progetto partecipando alle spese di manutenzione del Sito.
				servizidog.it è una organizzazione no-profit.</b>
			</div>
			
			<div class="col-md-2 margin-top col-sm-2$">
			
			<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
				<input type="hidden" name="cmd" value="_s-xclick">
				<input type="hidden" name="hosted_button_id" value="N9JKDGW4GKLAQ">
				<input type="image" src="https://www.paypalobjects.com/it_IT/IT/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal è il metodo rapido e sicuro per pagare e farsi pagare online.">
				<img alt="" border="0" src="https://www.paypalobjects.com/it_IT/i/scr/pixel.gif" width="1" height="1">
			</form>
			
			
			
			</div>
		</div>
		<!-- FINE DONAZIONE -->
		

		<form:form commandName="contatti" method="post" action="contatti" id="contattiform" autocomplete="off" onsubmit="return validateContatti(this)">
		
			
		
			<div class="row text-center">
				<spring:bind path="contatti.nome">
			    	<div class="col-md-6 margin-top col-sm-6${(not empty status.errorMessage) ? ' has-error' : ''}">
			    </spring:bind>
			        <form:input path="nome" id="nome" placeholder="Nome" maxlength="64" />
					<form:errors path="nome" cssClass="help-block"/>
				</div>
				
				<spring:bind path="contatti.email">
		    		<div class="col-md-6 margin-top col-sm-6${(not empty status.errorMessage) ? ' has-error' : ''}">
		    	</spring:bind>
			        <form:input path="email" id="email" placeholder="Email" maxlength="64" />
					<form:errors path="email" cssClass="help-block"/>
				</div>
			</div>
				
				
			<div class="row text-center">
				<spring:bind path="contatti.messaggio">
			    	<div class="col-md-12 col-sm-12"${(not empty status.errorMessage) ? ' has-error' : ''}">
			    </spring:bind>
			        <form:textarea path="messaggio" id="messaggio" placeholder="Messaggio..." maxlength="1000" />
					<form:errors path="messaggio" cssClass="help-block"/>
				</div>
			</div>


			<div class="row text-center">
				<div class="col-md-12 col-sm-12">
					<a class="btn btn-lg btn-green" onclick="validazioneSubmit( contattiform );">
					<i class="fa fa-envelope"></i> Invia Messaggio</a>
				</div>
			</div>
			
		</form:form>


		<br><br>
		<!-- banner -->
		<%@ include file="/common/bannerRegistrati.jsp"  %>
		
		
		
		<!--  
		<br><br>
		<div class="row text-center">
				<div class="col-md-2 col-sm-2">
		
		
		<a href="http://www.ilblogpeloso.com/"> 
			<img alt="" width="100" height="50" src="http://www.ilblogpeloso.com/images/banda-home.jpg"></a>

		

		<a href="http://www.ilblogpeloso.com/"> 
			<img alt="" width="100" height="50" src="http://www.ilblogpeloso.com/images/banda-home.jpg"></a>
			
			</div>
		</div>
			-->
		
		
		
		
		
		
	</div> <!-- fine div container -->
</section>

	<script>
	function validazioneSubmit(formId){
		if ( validateContatti(formId) ){
			formId.submit();
		}
	}
	</script>

	
	<v:javascript formName="contatti" staticJavascript="false"/>
	<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</body>