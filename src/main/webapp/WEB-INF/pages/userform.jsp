<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="userProfile.title"/>"/>


<script src="<c:url value="/js/jquery-2.1.0.min.js"/>"></script>
</head>

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
				<h1><fmt:message key="userProfile.heading"/></h1>
				<hr>
			</div>
		</div>
	</div>
	
	
	<div class="row">
		<div class="col-md-6 col-md-offset-3 text-center padding">
			<ul class="nav nav-tabs responsive" id="tabs">
			  <li role="presentation" class="active"><a data-toggle="tab" href="#info">Info Generali</a></li>
			  <li role="presentation"><a data-toggle="tab" href="#offriservizio"><fmt:message key="userProfile.ruoliServizi"/></a></li>
			</ul>
			<div class="tab-content responsive">
			
				<div role="tabpanel" class="tab-pane active" id="info">
				    <form:form commandName="user" method="post" action="userform" id="userForm" autocomplete="off" onsubmit="return validateUser(this)">
				        <form:hidden path="id"/>
				        
				        <spring:bind path="user.username">
				        	<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
				        </spring:bind>
				            <label><fmt:message key="user.username"/></label>
				            <form:input  path="username" id="username"/>
				            <form:errors path="username" cssClass="help-block"/>
			            	<c:if test="${pageContext.request.remoteUser == user.username}">
				                <span class="help-block">
				                    <a href="<c:url value="/updatePassword" />"><fmt:message key='updatePassword.changePasswordLink'/></a>
				                </span>
			            	</c:if>
				        </div>

			            <spring:bind path="user.firstName">
			            	<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
			            </spring:bind>
			                <label><fmt:message key="user.firstName"/></label>
			                <form:input  path="firstName" id="firstName" maxlength="50"/>
			                <form:errors path="firstName" cssClass="help-block"/>
			            </div>

			            <spring:bind path="user.lastName">
			            	<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
			            </spring:bind>
			                <label><fmt:message key="user.lastName"/></label>
			                <form:input  path="lastName" id="lastName" maxlength="50"/>
			                <form:errors path="lastName" cssClass="help-block"/>
			            </div>
				            
			            <spring:bind path="user.email">
			            	<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top${(not empty status.errorMessage) ? ' has-error' : ''}">
			            </spring:bind>
							<label><fmt:message key="user.email"/></label>
			                <form:input  path="email" id="email"/>
			                <form:errors path="email" cssClass="help-block"/>
				        </div>

				
				  
						<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
						
							<a class="btn btn-green" name="save" onclick="validazioneSubmit( userForm );">
								<i class="fa fa-check"> </i> <fmt:message key="button.save"/></a>
							
							<a href="<c:url value='/home-utente'/>" class="btn btn-default" name="cancel" >
								<i class="fa fa-times"></i> <fmt:message key="button.cancel"/></a>
							
							<a class="btn btn-red" name="delete" onclick="eliminaAccountConferma( document.getElementById('userForm') );">
		                  		<i class="fa fa-ban"></i> <fmt:message key="button.deleteUtente"/> </a>
		                  		<input type="hidden" name="delete">
						</div>

				    </form:form>
			    </div> <!-- fine primo tab -->
			    
			    
			    <div role="tabpanel" class="tab-pane" id="offriservizio">
					<%@ include file="userformOffriServizi.jsp"%>
			    </div>


			</div>
		</div>
	</div>
</section>


<script>
	function validazioneSubmit(formId){
		if ( validateUser(formId) ){
			formId.submit();
		}
	}

	function eliminaAccountConferma(obj){
		var msgDelConfirm = "<fmt:message key="delete.confirmAccount"></fmt:message>";
		var conf = confirm(msgDelConfirm);
		if (conf == true) {
			obj.elements["delete"].value = 'ok';
			obj.submit();
		} else {

		}
	}
	
	function validazioneSubmit(formId){
		if ( validateUser(formId) ){
			formId.submit();
		}
	}

	$(document).ready(function(){
		// questo è il nuovo
		$('#tabs a').click(function (e) {
		  e.preventDefault()
		  $(this).tab('show')
		});
		
		var url = document.location.toString();
		if (url.match('#')) {
		    $('.nav-tabs a[href=#'+url.split('#')[1]+']').tab('show') ;
		}
	});	 

	//questo il vecchio non funziona.....
	/*
	$("#tabs").tabs({
	    activate: function (event, ui) {
	        var active = $('#tabs').tabs('option', 'active');
	        //$("#tabid").html('the tab id is ' + $("#tabs ul>li a").eq(active).attr("href"));
	    }
	});
	*/
</script>
	

	
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