<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>Data Access Error</title>
</head>


<body>

<%@ include file="/common/messages.jsp" %>

		<section>
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-left">


					<p>
					    <h2>Data Access Failure</h2>
					    <c:out value="${requestScope.exception.message}"/>
					</p>
					
					<!--
					<% 
					((Exception) request.getAttribute("exception")).printStackTrace(new java.io.PrintWriter(out));  
					%>
					-->
					
					<a href="home" onclick="history.back();return false">&#171; Back</a>


			</div>
		</div>
	</section>
</body>