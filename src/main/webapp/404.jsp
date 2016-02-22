<%@ include file="/common/taglibs.jsp"%>

<page:applyDecorator name="default">

<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
    <meta name="breadcrumb" content="<fmt:message key="404.title"/>"/>
</head>

	<section>
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-left">
				<p><h3>
				    <fmt:message key="404.message">
				        <fmt:param><c:url value="/home"/></fmt:param>
				    </fmt:message></h3>
				</p>
				<p style="text-align: center">
				      <img src="http://www.servizidog.it/img/paco_prato.png" width="568" height="426" 
				      alt="Paco" style="margin: 20px; border: 1px solid black">
				</p>
				
			</div>
		</div>
	</section>
</page:applyDecorator>