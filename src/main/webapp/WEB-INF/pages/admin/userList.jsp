<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="userList.title"/>"/>
</head>
<body>

<c:if test="${not empty searchError}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <c:out value="${searchError}"/>
    </div>
</c:if>


<section>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
			<div class="content">
				<h1><fmt:message key="userList.title"/></h1>
				<hr>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-left">

		    <form method="get" action="${ctx}/admin/users" id="searchForm" class="form-inline">
				<div class="col-md-3 col-sm-3 col-xs-12 text-left margin-top">
					<input type="text" size="20" name="q" id="query" value="${param.q}" placeholder="<fmt:message key="search.enterTerms"/>" >
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12 text-left margin-top">
					<a class="btn btn-blue start" name="save" onclick="document.getElementById('searchForm').submit();">
					<i class="fa fa-search"></i> <fmt:message key="button.search"/></a>
				</div>
		    </form>
		
		    <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
		        <a class="btn btn-green" href="<c:url value='/admin/userformAdmin?method=Add&from=list'/>">
		        	<i class="fa fa-plus"></i></i> <fmt:message key="button.add"/></a>
		
		        <a class="btn btn-default" href="<c:url value='/home-utente'/>">
		       	<i class="fa fa-check-square"></i><fmt:message key="button.done"/></a>
		    </div>
		    
			<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			    <display:table name="userList" cellspacing="0" cellpadding="0" requestURI=""
			                   defaultsort="1" id="users" pagesize="25" class="table table-condensed table-striped table-hover" export="true">
			                   
                  	<display:column property="id" sortable="true" titleKey="userList.user" style="width: 5%" media="html"/>
			        <display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" style="width: 25%"
			                        url="/admin/userformAdmin?from=list" paramId="id" paramProperty="id"/>
			        <display:column property="fullName" escapeXml="true" sortable="true" titleKey="activeUsers.fullName"
			                        style="width: 34%"/>
			        <display:column property="email" sortable="true" titleKey="user.email" style="width: 25%" autolink="true"
			                        media="html"/>
			        <display:column property="email" titleKey="user.email" media="csv xml excel pdf"/>
			        <display:column sortProperty="enabled" sortable="true" titleKey="user.enabled"
			                        style="width: 16%; padding-left: 15px" media="html">
			            <input type="checkbox" disabled="disabled" <c:if test="${users.enabled}">checked="checked"</c:if>/>
			        </display:column>
			        <display:column property="enabled" titleKey="user.enabled" media="csv xml excel pdf"/>
			        
					<display:column property="confirmDate" titleKey="user.confermaRegistrazione" format="{0,date,dd-MM-yyyy HH:mm}" sortable="true" media="html"/>
			                        
					<display:column titleKey="menu.form.servizi">
						<ul>
						<c:forEach var="servizio"  items="${users.tipoRuoli}" varStatus="status">
							  <li>${servizio.description}</li>
                		</c:forEach>
                		</ul>
					</display:column>


			        <display:setProperty name="paging.banner.item_name"><fmt:message key="userList.user"/></display:setProperty>
			        <display:setProperty name="paging.banner.items_name"><fmt:message key="userList.users"/></display:setProperty>
			
			        <display:setProperty name="export.excel.filename" value="User List.xls"/>
			        <display:setProperty name="export.csv.filename" value="User List.csv"/>
			        <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
			    </display:table>
			    
			    

			    
			</div>
		</div>
	</div>
</section>
</body>
