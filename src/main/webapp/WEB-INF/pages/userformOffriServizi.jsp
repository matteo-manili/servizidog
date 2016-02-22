<%@ include file="/common/taglibs.jsp"%>


	<form:form commandName="user" method="post" action="userform" id="userFormCheckServ" autocomplete="off" onsubmit="return validateUser(this)">
		<form:hidden path="id"/>
		<input type="hidden" name="ckRuoliServiziOk">
	
		<div class="col-md-6 col-sm-6 col-xs-6 text-left margin-top">
		<label><h5>
		<font style="font-weight:bold; color: green;">Attiva il servizio che vuoi offrire:</h5></font>
		 </label>
		 
		 
		<br><!-- checkbox Ruoli Servizi Dog  -->
		<c:forEach items="${availableRuoliServiziDog}" var="ruoliServizi">
			<input type="checkbox" name="ckRuoliServizi"  value="${ruoliServizi.value}" 
			${fn:contains(user.tipoRuoli, ruoliServizi.value) ? 'checked' : ''}/> ${ruoliServizi.label} <br>
		</c:forEach>
	       	
	       	
		<!-- Matteo selezionare scelta ruolo servizio dog 
		<form:checkbox path="ruoliServizi.dogsitter" id="dogsitter" />
               <fmt:message key="user.dogsitter"/> <br>
		<form:checkbox path="ruoliServizi.dogHost" id="dogHost"/>
               <fmt:message key="user.doghost"/> <br>
		<form:checkbox path="ruoliServizi.adozione" id="adozione"/>
               <fmt:message key="user.adozione"/> <br>
		<form:checkbox path="ruoliServizi.associazione" id="associazione"/>
               <fmt:message key="user.associazione"/> <br> -->

	       	
	       	
	       	
	       </div>
	
		<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
			<a class="btn btn-green" name="save" onclick="document.getElementById('userFormCheckServ').submit();">
			<i class="fa fa-check"> </i> <fmt:message key="button.save"/></a>
			
			<a href="<c:url value='/home-utente'/>" class="btn btn-red" name="cancel" >
			<i class="fa fa-times"></i> <fmt:message key="button.cancel"/></a>
		</div>
	   </form:form>
	    
