<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="breadcrumb" content="<fmt:message key="home.title"/>"/>
</head>

<body>

<%@ include file="/common/messages.jsp" %>

		<section>
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center padding">
					<div class="content">
						<h1><fmt:message key="home.heading"/>, &nbsp;${fullName}</h1>
						<hr>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 col-md-12 padding text-left">
					
					<c:if test="${ empty dogSitter && empty dogHost && empty adozione && empty associazione }"> 
						<h4>Per attivare o disattivare un servizio vai su <strong>IMPOSTAZIONI</strong> sulla scheda <a href="<c:url value='/userform#offriservizio'/>">GESTIONE SERVIZI</a>.</h4>
					</c:if>
					<ul>
					<!-- The main application script -->
					<c:choose>
					    <c:when test="${not empty dogSitter}">
					    <li>
						    <h3>
						    <font style="font-weight:bold; color: green;"><fmt:message key="dogsitter.title"/></font>
							<c:choose> 
								<c:when test="${dogSitter.informazioniGeneraliInserite() != true }">
									<font style="font-style: italic; font-weight:bold;"> (profilo non ancora Pubblicato) </font>
								</c:when> 
								<c:otherwise>
									<a href="<c:url value="/${dogSitter.urlProfilo}"/>" style="font-style: italic; font-weight:bold; text-decoration: underline;" >(Ecco come appare il tuo profilo al pubblico)</a>
								</c:otherwise> 
							</c:choose> 
							</h3>
							<ul>
							<h4>
							<c:if test="${not dogSitter.infoGenerali()}">
								<li>info Generali <a href="<c:url value="/dogsitterform"/>">(inserisci informazioni) </a> </li>
								
							</c:if>
				
							<c:if test="${empty dogSitter.tariffa}">
								<li>Tariffa oraria <a href="<c:url value="/dogsitterform#disponiblita"/>"> (inserisci informazioni)</a></li>
								
							</c:if>
							
							<c:if test="${empty dogSitter.dataInizioDisponib}">
								<li>Disponibilità <a href="<c:url value="/dogsitterform#disponiblita"/>"> (inserisci informazioni)</a></li>
								
							</c:if>
							
							<c:if test="${not imageDogSitterPref}">
								<li>Carica foto (opzionale) <a href="<c:url value="/dogsitterform#foto"/>"> (inserisci)</a></li>
								
							</c:if>
							</h4>
						</ul>
				 		</li>
						</c:when>
					</c:choose>
				
					<c:choose>
					    <c:when test="${not empty dogHost}">
						<li>
							<h3>
						    <font style="font-weight:bold; color: green;"><fmt:message key="doghost.title"/></font>
							<c:choose> 
								<c:when test="${dogHost.informazioniGeneraliInserite() != true }">
									<font style="font-style: italic; font-weight:bold;"> (profilo non ancora Pubblicato) </font>
								</c:when> 
								<c:otherwise>
									<a href="<c:url value="/${dogHost.urlProfilo}"/>" style="font-style: italic; font-weight:bold; text-decoration: underline;" >(Ecco come appare il tuo profilo al pubblico)</a>
								</c:otherwise> 
							</c:choose> 
							</h3>
							<ul>
								<h4>
								<c:if test="${not dogHost.infoGenerali()}">
									<li>info Generali <a href="<c:url value="/doghostform"/>">(inserisci informazioni)</a></li>
									
								</c:if>	
										
								<c:if test="${empty dogHost.metriQuadrati}">
									<li>info Alloggio <a href="<c:url value="/doghostform#infoAlloggio"/>">(inserisci informazioni)</a></li>
									
								</c:if>			
								
								<c:if test="${empty dogHost.tariffa}">
									<li>Tariffa giornaliera <a href="<c:url value="/doghostform#dataTariffa"/>">(inserisci informazioni)</a></li>
									
								</c:if>
								
								<c:if test="${empty dogHost.dataInizioDisponib}">
									<li>Disponibilità <a href="<c:url value="/doghostform#dataTariffa"/>">(inserisci informazioni)</a></li>
									
								</c:if>								
								
								<c:if test="${not imageDogHostPref}">
									<li>Carica foto (opzionale) <a href="<c:url value="/doghostform#foto"/>">(inserisci)</a></li>
								</c:if>
								</h4>
							</ul>
				 		</li>
					    </c:when>
					</c:choose>
					
					
					
					<c:choose>
						<c:when test="${not empty adozione}">
							<li>
							<h3>
						    <font style="font-weight:bold; color: green;"><fmt:message key="adozione.title"/></font>
							<c:choose> 
								<c:when test="${adozione.informazioniGeneraliInserite() != true || caneAdozione != true}">
									<font style="font-style: italic; font-weight:bold;"> (profilo non ancora Pubblicato) </font>
								</c:when> 
								<c:otherwise>
									<a href="<c:url value="/${adozione.urlProfilo}"/>" style="font-style: italic; font-weight:bold; text-decoration: underline;" >(Ecco come appare il tuo profilo al pubblico)</a>
								</c:otherwise> 
							</c:choose> 
							</h3>
							<ul>
								<h4>
								<c:if test="${not adozione.infoGenerali()}">
									<li>info Generali <a href="<c:url value="/adozioneform"/>">(inserisci informazioni)</a></li>
								</c:if>	
								
								<c:if test="${not caneAdozione}">
									<li>cane <a href="<c:url value="/adozioneform#inserisciCane"/>">(inserisci informazioni)</a></li>
								</c:if>
								
								<c:if test="${not imageAdozionePref}">
									<li>Carica foto (opzionale) <a href="<c:url value="/adozioneform#foto"/>">(inserisci)</a></li>
								</c:if>
								</h4>
							</ul>
					 		</li>
						</c:when>
					</c:choose>
					
					
					<c:choose>
						<c:when test="${not empty associazione}">
							<li>
							<h3>
						    <font style="font-weight:bold; color: green;"><fmt:message key="associazione.title"/></font>
							<c:choose> 
								<c:when test="${associazione.informazioniGeneraliInserite() != true}">
									<font style="font-style: italic; font-weight:bold;"> (profilo non ancora Pubblicato) </font>
								</c:when> 
								<c:otherwise>
									<a href="<c:url value="/${associazione.urlProfilo}"/>" style="font-style: italic; font-weight:bold; text-decoration: underline;" >(Ecco come appare il tuo profilo al pubblico)</a>
								</c:otherwise> 
							</c:choose> 
							</h3>
							<ul>
								<h4>
								<c:if test="${not associazione.infoGenerali()}">
									<li>info Generali <a href="<c:url value="/associazioneform"/>">(inserisci informazioni)</a></li>
								</c:if>	
					
								<c:if test="${not caneAssociazione}">
									<li>cane <a href="<c:url value="/associazioneform#inserisciCane"/>">(inserisci informazioni)</a></li>
								</c:if>
								
								<c:if test="${not imageAssociazionePref}">
									<li>Carica foto (opzionale) <a href="<c:url value="/associazioneform#foto"/>">(inserisci)</a></li>
								</c:if>
								</h4>
							</ul>
					 		</li>
						</c:when>
					</c:choose>
					
					
					
					
					</ul>
					
					

				</div>
			</div>
		</section>


</body>
