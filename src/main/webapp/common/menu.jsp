<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter" >

	<menu:displayMenu name="Ricerca"/>
	<menu:displayMenu name="Mappa"/>

	<!-- menu:displayMenu name="Ricerche"/ -->

	<c:if test="${empty pageContext.request.remoteUser}">
        <menu:displayMenu name="Login"/>
   		<menu:displayMenu name="Registrazione"/>
   		<menu:displayMenu name="Contatti"/>
    </c:if>
	
	<menu:displayMenu name="Home"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="AdminMenu"/>
	 
	<c:choose>
		<c:when test="${not empty ruoliServiziUtente}">
		<!-- </li>  -->
			<li class="dropdown">
				<a title="<fmt:message key="menu.form.servizi"/>" class="page-scroll dropdown-toggle" data-toggle="dropdown">
					<fmt:message key="menu.form.servizi"/></a>
				<ul class="dropdown-menu">
				    <c:forEach items="${ruoliServiziUtente}" var="ruoliServizi">
						<li>
							<a href="<c:url value="/${ruoliServizi.label}form"/>" title="<fmt:message key="menu.${ruoliServizi.label}"/>" ><fmt:message key="menu.${ruoliServizi.label}"/></a>
							
						</li>
					</c:forEach>
				</ul>
			</li>
	    </c:when>
	</c:choose>

   	<menu:displayMenu name="Logout"/>

</menu:useMenuDisplayer>
