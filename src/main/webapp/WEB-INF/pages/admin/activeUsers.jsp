<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="activeUsers.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="activeUsers.title"/>"/>
    
</head>
<!-- 
<body id="activeUsers">  -->
<body>

<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><fmt:message key="activeUsers.title"/></h1>
				<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-left">

		    <display:table name="applicationScope.userNames" id="user" cellspacing="0" cellpadding="0"
		                   defaultsort="1" class="table table-condensed table-striped table-hover" pagesize="50" requestURI="">
		        <display:column property="username" escapeXml="true" style="width: 30%" titleKey="user.username"
		                        sortable="true"/>
		        <display:column titleKey="activeUsers.fullName" sortable="true">
		            <c:out value="${user.firstName} ${user.lastName}" escapeXml="true"/>
		            <c:if test="${not empty user.email}">
		                <a href="mailto:<c:out value="${user.email}"/>">
		                    <img src="<c:url value="/img/iconEmail.gif"/>"
		                         alt="<fmt:message key="icon.email"/>" class="icon"/></a>
		            </c:if>
		        </display:column>
		
		        <display:setProperty name="paging.banner.item_name" value="user"/>
		        <display:setProperty name="paging.banner.items_name" value="users"/>
	    	</display:table>
    
    
    
		</div>
	</div>
</section>
</body>
