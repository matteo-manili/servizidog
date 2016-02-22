<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="userProfile.title"/>"/>
</head>

<script src="<c:url value="/js/jquery-2.1.0.min.js"/>"></script>

<c:set var="delObject" scope="request"><fmt:message key="userList.user"/></c:set>
<script type="text/javascript">var msgDelConfirm =
   "<fmt:message key="delete.confirm"><fmt:param value="${delObject}"/></fmt:message>";
</script>



<body>

<%@ include file="/common/messages.jsp" %>

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
				<h1><fmt:message key="userProfileAdmin.heading"/></h1>
			<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-left">


		    <form:form commandName="user" method="post" action="userformAdmin" id="userForm" autocomplete="off" onsubmit="return validateUser(this)">
		        <form:hidden path="id"/>
		        <form:hidden path="version"/>
		        <input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>
		
		
		
		        <spring:bind path="user.username">
		        	<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
		        </spring:bind>
		            <appfuse:label styleClass="control-label" key="user.username"/>
		            <form:input  path="username" id="username"/>
		            <form:errors path="username" cssClass="help-block"/>
		            <c:if test="${pageContext.request.remoteUser == user.username}">
		                <span class="help-block">
		                    <a href="<c:url value="/updatePassword" />"><fmt:message key='updatePassword.changePasswordLink'/></a>
		                </span>
		            </c:if>
				</div>
				
				
	
	
	            <spring:bind path="user.firstName">
	            <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
	            </spring:bind>
	                <appfuse:label styleClass="control-label" key="user.firstName"/>
	                <form:input  path="firstName" id="firstName" maxlength="50"/>
	                <form:errors path="firstName" cssClass="help-block"/>
				</div>
	
	            <spring:bind path="user.lastName">
	            <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
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
	
				<c:choose>
				    <c:when test="${param.from == 'list' or param.method == 'Add'}">
				        <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
				            <label class="control-label"><fmt:message key="userProfile.accountSettings"/></label>
				            <label class="checkbox-inline">
				                <form:checkbox path="enabled" id="enabled"/>
				                <fmt:message key="user.enabled"/>
				            </label>
				
				            <label class="checkbox-inline">
				                <form:checkbox path="accountExpired" id="accountExpired"/>
				                <fmt:message key="user.accountExpired"/>
				            </label>
				
				            <label class="checkbox-inline">
				                <form:checkbox path="accountLocked" id="accountLocked"/>
				                <fmt:message key="user.accountLocked"/>
				            </label>
				
				            <label class="checkbox-inline">
				                <form:checkbox path="credentialsExpired" id="credentialsExpired"/>
				                <fmt:message key="user.credentialsExpired"/>
				            </label>
				        </div>
				        <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
				            <label for="userRoles" class="control-label"><fmt:message key="userProfile.assignRoles"/></label>
				            <select id="userRoles" name="userRoles" multiple="true" class="form-control">
				                <c:forEach items="${availableRoles}" var="role">
				                	<option value="${role.value}" ${fn:contains(user.roles, role.label) ? 'selected' : ''}>${role.label}</option>
				                </c:forEach>
				            </select>
				        </div>
				    </c:when>
				    <c:when test="${not empty user.username}">
				
				            <label class="control-label"><fmt:message key="user.roles"/>:</label>
				            <div class="readonly">
				                <c:forEach var="role" items="${user.roleList}" varStatus="status">
				                    <c:out value="${role.label}"/><c:if test="${!status.last}">,</c:if>
				                    <input type="hidden" name="userRoles" value="<c:out value="${role.label}"/>"/>
				                </c:forEach>
				            </div>
				            <form:hidden path="enabled"/>
				            <form:hidden path="accountExpired"/>
				            <form:hidden path="accountLocked"/>
				            <form:hidden path="credentialsExpired"/>
				
				    </c:when>
				</c:choose>
	
	
	
	
		        <div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
		            <button type="submit" class="btn btn-green" name="save" onclick="bCancel=false">
		                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
		            </button>
		
		            <c:if test="${param.from == 'list' and param.method != 'Add'}">
		              <button type="submit" class="btn btn-red" name="delete" onclick="bCancel=true;return confirmMessage(msgDelConfirm)">
		                  <i class="icon-trash"></i> <fmt:message key="button.delete"/>
		              </button>
		            </c:if>
		
		            <button type="submit" class="btn btn-default" name="cancel" onclick="bCancel=true">
		                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
		            </button>
		        </div>
		    </form:form>
		</div>	    
	</div>
</section>

<c:set var="scripts" scope="request">
<script type="text/javascript">
// This is here so we can exclude the selectAll call when roles is hidden
function onFormSubmit(theForm) {
    return validateUser(theForm);
}
</script>
</c:set>

<v:javascript formName="user" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

</body>