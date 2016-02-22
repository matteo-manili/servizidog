<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="recoverPass.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="recoverPass.title"/>"/>
	<meta name="description" content="username o password dimenticata" />
</head>


<body>

<%@ include file="/common/messages.jsp" %>

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h3><fmt:message key="recoverPass.heading"/></h3>
				<hr>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-6 col-md-offset-3 text-center padding">

				<form method="post" id="recoverPass" action="<c:url value='/requestRecoveryTokenUsernameAndPassword'/>">
				
			        <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			            <label class="control-label"><fmt:message key="recoverPass.emailLabel"/></label> 
			            <input type="text" name="email" id="email" placeholder="<fmt:message key="user.email"/>">
			        </div>
					
					<div class="col-md-12 col-sm-12 col-xs-12 text-center margin-top">
					    <a class="btn btn-lg btn-green" onclick="controlEmail( document.getElementById('recoverPass') );">
							<i class="fa fa-envelope"></i> <fmt:message key="recoverPass.inviaEmail"/></a> 
				    </div>
				    
				    
				</form>

		</div>
	</div>
	
</section>  


<script>

function controlEmail(obj) {
    if ($("#email").val().length == 0) {
        alert("<fmt:message key="errors.required"><fmt:param><fmt:message key="user.email"/></fmt:param></fmt:message>");
        $("#email").focus();
    } else {
        //location.href="<c:url value="/requestRecoveryTokenUsernameAndPassword"/>?email=" + $("#email").val();
        obj.submit();
    }
}

</script>

</body>




