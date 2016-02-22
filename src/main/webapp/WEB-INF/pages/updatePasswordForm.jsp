<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="updatePassword.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="updatePassword.title"/>"/>

</head>
<!-- 
<body id="updatePassword">  -->

<body>

<%@ include file="/common/messages.jsp" %>

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><fmt:message key="updatePassword.heading"/></h1>
				<hr>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-6 col-md-offset-3 text-center padding">

				<form method="post" id="updatePassword" action="<c:url value='/updatePassword'/>" autocomplete="off">
				
				<!-- 
					<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
					    <c:choose>
					        <c:when test="${not empty token}">
					            <p><fmt:message key="updatePassword.passwordReset.message"/></p>
					        </c:when>
					        <c:otherwise>
					        	<p><fmt:message key="updatePassword.changePassword.message"/></p>
					        </c:otherwise>
					    </c:choose>
					</div>
				 -->
				
				
				

			        <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			            <label class="control-label"><fmt:message key="user.username"/></label>
			            <input type="text" name="username"  id="username" value="<c:out value="${username}" escapeXml="true"/>" required>
			        </div>
			
				    <c:choose>
				    	<c:when test="${not empty token}">
						    <input type="hidden" name="token" value="<c:out value="${token}" escapeXml="true" />"/>
				    	</c:when>
				    	<c:otherwise>
					        <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
					        	<label class="control-label"><fmt:message key="updatePassword.currentPassword.label"/></label>
			                    <input type="password"  name="currentPassword" id="currentPassword" required autofocus>
					        </div>
				    	</c:otherwise>
				    </c:choose>
			
			        <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			        	<label class="control-label"><fmt:message key="updatePassword.newPassword.label"/></label>
			            <input type="password"  name="password" id="password" required>
					</div>
					
					<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
					    <button type="submit" class="btn btn-large btn-primary">
					        <i class="icon-ok icon-white"></i> <fmt:message key='updatePassword.changePasswordButton'/>
					    </button>
				    </div>
				    
				    
				</form>

			</div>
		</div>
	</div>
</section>  
</body>