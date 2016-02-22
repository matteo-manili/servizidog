<%@page import="com.dogsitter.model.FacebookUser"%>
<%@page import="com.dogsitter.webapp.util.FBConnection"%>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="login.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="login.title"/>"/>
    <meta name="description" content="<fmt:message key="meta.description.login"/>" />
    <meta name="keywords" content="${keywordsGenerica}" />
    
    <script src="<c:url value="/scripts/loginFacebook.js"/>"></script>
    
    <% FBConnection fbConnection = new FBConnection(); %>
	    
 
</head>


<body>
	<%@ include file="/common/facebookForm.jsp" %>
	
	<!-- LOGIN -->
	<%@ include file="/common/messages.jsp" %>
		
		
		
	<section id="login">
		<div class="container">
		
			<div class="row">
				<div class="col-md-12 col-sm-12 text-center">
					<h1><fmt:message key="login.heading"/></h1>
					<hr>
				</div>
			</div>
			<!--
			<div id="status"></div>
						<a class="btn btn-facebook" name="buttonFb" onclick="checkLoginState();">
			<i class="fa fa-facebook-square"> </i> accedi con facebook </a>
			<h3>
			<a href="" name="save" onclick="facebookLogout();"> logout facebook </a>
			</h3>
			<br><br><br>
			<br>
			<h3>
				<a href="<%=fbConnection.getFBAuthUrl()%>&display=popup"> facebook 2 </a>
				<br><br>
				<a href="" onclick="facebookLogin();"> facebook 3 </a>
				
			</h3>
			  -->
			<form method="post" id="loginForm" action="<c:url value='/j_security_check'/>" 
				onsubmit="saveUsername(this); return validateForm(this)" autocomplete="off">
			
			<c:if test="${param.error != null}">
			    <div class="alert alert-danger alert-dismissable">
			        <fmt:message key="errors.password.mismatch"/>
			    </div>
			</c:if>
			
				<div class="row text-right">
					<div class="col-md-6 col-md-offset-3 col-sm-12 text-right margin-top">
						<fb:login-button scope="public_profile,email" data-size="large" onlogin="checkLoginState();">
						</fb:login-button>
						<div id="status"></div>
					</div>
				</div>
					
				<div class="row text-center">
					<div class="col-md-6 col-md-offset-3 col-sm-12 margin-top">
						<input type="text" name="j_username" id="j_username"  
							placeholder="<fmt:message key="label.username"/>" required tabindex="1">
					</div>
					<div class="col-md-6 col-md-offset-3 col-sm-12 margin-top">
						<input type="password" name="j_password" id="j_password" 
	         						placeholder="<fmt:message key="label.password"/>" required tabindex="2">
					</div>
				</div>
				
				<div class="row text-center">
					<div class="col-md-6 col-md-offset-3 col-sm-12 margin-top">
						
				   		<c:if test="${appConfig['rememberMeEnabled']}">
				   		<label for="rememberMe">
				        	<input type="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3"/>
				        	<fmt:message key="login.rememberMe"/>
				        </label>
						</c:if>
					</div>
				</div>
				<div class="row text-left">
					<div class="col-md-6 col-md-offset-3 col-sm-12 margin-top">
						<p>
						    <fmt:message key="login.signup">
						        <fmt:param><c:url value="/registrazione"/></fmt:param>
						    </fmt:message>
						</p>
						<p>
						    <fmt:message key="recoverPass.link">
						        <fmt:param><c:url value="/recoverpass"/></fmt:param>
						    </fmt:message>
						</p>
						<!-- 
							<p><fmt:message key="updatePassword.requestRecoveryTokenLink"/></p>
						 -->
						<!-- <p><fmt:message key="login.passwordHint"/></p> SUGGERIMENTO PASSWORD NON LO USO  --> 
					</div>
				</div>
				
				<div class="row text-center margin-top">
				
					<div class="col-md-12 col-sm-12">
						<a class="btn btn-orange" name="login" onclick="validazioneSubmit()" tabindex="4">
						<i class="fa fa-lock"></i> <fmt:message key='button.login'/></a>
					</div> 
					
				</div>
	
			</form>
		</div>
	</section>


	    




<script>
function validazioneSubmit(){
	if (validateForm(loginForm)){
		document.getElementById('loginForm').submit();	
	} 
}
</script>



<c:set var="scripts" scope="request">
	<%@ include file="/scripts/login.js"%>
</c:set>


</body>
