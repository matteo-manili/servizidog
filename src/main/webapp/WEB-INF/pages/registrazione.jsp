<%@ include file="/common/taglibs.jsp" %>

	<head>
	    <title><fmt:message key="signup.title"/></title>
	    <meta name="breadcrumb" content="<fmt:message key="signup.title"/>"/>
	    <meta name="description" content="<fmt:message key="meta.description.signup"/>" />
	    <meta name="keywords" content="${keywordsGenerica}" />
	    
	    <script src="<c:url value="/scripts/loginFacebook.js"/>"></script>
	    
	</head>
	
<body>

<%@ include file="/common/messages.jsp" %>	
<%@ include file="/common/facebookForm.jsp" %>

	
	<spring:bind path="user.*">
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
				<h1>REGISTRAZIONE</h1>
				<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3 text-center padding">
	    	<form:form commandName="user" method="post" action="registrazione" id="registrazioneForm" autocomplete="off" onsubmit="return validateRegistrazione(this)">
	
				<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
					<fb:login-button scope="public_profile,email" data-size="large" onlogin="checkLoginState();">
					</fb:login-button>
					<div id="status"></div>
				</div>

				<spring:bind path="user.username">
					<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		        </spring:bind>
					<appfuse:label styleClass="control-label" key="user.username"/>
					<form:input path="username" id="username" autofocus="true"/>
					<form:errors path="username" cssClass="help-block"/>
				</div>
		             
		       <spring:bind path="user.password">
					<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		       </spring:bind>
					<appfuse:label styleClass="control-label" key="user.password"/>
					<form:password  path="password" id="password" showPassword="true"/>
					<form:errors path="password" cssClass="help-block"/>
				</div>
		             
				<spring:bind path="user.confirmPassword">
					<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		       	</spring:bind>
					<appfuse:label styleClass="control-label" key="user.confirmPassword"/>
					<form:password  path="confirmPassword" id="confirmPassword" showPassword="true"/>
					<form:errors path="confirmPassword" cssClass="help-block"/>
				</div>
		
				<spring:bind path="user.firstName">
					<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		        </spring:bind>
					<appfuse:label styleClass="control-label" key="user.firstName"/>
					<form:input  path="firstName" id="firstName" maxlength="50"/>
		               <form:errors path="firstName" cssClass="help-block"/>
				</div>
		
				<spring:bind path="user.lastName">
					<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		        </spring:bind>
					<appfuse:label styleClass="control-label" key="user.lastName"/>
					<form:input  path="lastName" id="lastName" maxlength="50"/>
		            <form:errors path="lastName" cssClass="help-block"/>
				</div>
		
				<spring:bind path="user.email">
					<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		        </spring:bind>
					<appfuse:label styleClass="control-label" key="user.email"/>
					<form:input  path="email" id="email"/>
		            <form:errors path="email" cssClass="help-block"/>
				</div>
		
		        <!-- Matteo selezionare scelta ruolo servizio dog 
				<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
				<label><fmt:message key="userProfile.ruoliServizi"/></label>
					<br><br>
					<form:checkbox path="ruoliServizi.dogsitter" id="dogsitter"/>
		                <fmt:message key="user.dogsitter"/> <br>
					<form:checkbox path="ruoliServizi.dogHost" id="dogHost"/>
		                <fmt:message key="user.doghost"/> <br>
					<form:checkbox path="ruoliServizi.adozione" id="adozione"/>
		                <fmt:message key="user.adozione"/> <br>
					<form:checkbox path="ruoliServizi.associazione" id="associazione"/>
		                <fmt:message key="user.associazione"/> <br>
				</div> -->
				
				<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
					<a class="btn btn-green" name="save" onclick="validazioneSubmit( registrazioneForm )">
						<i class="fa fa-check"></i> <fmt:message key="button.register"/></a>
						
					<a href="" class="btn btn-red" name="cancel" >
						<i class="fa fa-times"></i> <fmt:message key="button.cancel"/></a>
				</div>

			</form:form>
			
				<!-- username e passowrd dimenticate? -->
				<div class="col-md-6 col-md-offset-3 col-sm-12 margin-top">
					<p>
					    <fmt:message key="recoverPass.link">
					        <fmt:param><c:url value="/recoverpass"/></fmt:param>
					    </fmt:message>
					</p>
				</div>
	
		</div>
	</div>
</section>
        

    
    
	<script>
		function validazioneSubmit(formId){
			if ( validateRegistrazione(formId) ){
				formId.submit();
				
				var email = formId.elements["email"].value;
				alert("Conferma la Registrazione: abbiamo inviato una mail al tuo indirizzo di posta: "+email);
				
			}
		}
	</script>
        
	<c:set var="scripts" scope="request">
		<v:javascript formName="registrazione" staticJavascript="false"/>
		<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
	</c:set>
	
</body>