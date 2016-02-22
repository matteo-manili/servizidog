<script src="<c:url value="/scripts/loginFacebook.js"/>"></script>
<%@ include file="/common/facebookForm.jsp" %>

<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
<h3>Registrati e Pubblica il tuo annuncio Gratis</h3>
</div>
<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top">
<h5><strong>Cerchi lavoro come Dog Sitter o vuoi Ospitare cani?</strong></h5>
<h5><strong>Vuoi dare in Adozione Cani o sei una Associazione?</strong></h5>
</div>
<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top" data-wow-duration="1s">
	<a href="<c:url value='/registrazione'/>" class="btn btn-lg btn-primary"><fmt:message key="menu.registrati"/></a>
	<a id="btnFacebook" class="btn btn-facebook" name="loginFacebook" onclick="accediConFacebook()" tabindex="4">
		<i class="fa fa-facebook-square"></i><fmt:message key="login.facebook"/></a>
</div>