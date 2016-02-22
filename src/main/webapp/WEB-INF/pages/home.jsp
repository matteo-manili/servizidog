<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
	<meta name="breadcrumb" content="<fmt:message key="index.title"/>"/>
	<meta name="description" content="<fmt:message key="meta.description.index"/>" />
	<meta name="keywords" content="${keywordsGenerica}" />
	
</head>
<body>

		<!-- HOME -->

		<header id="home">

			<div id="owl-demo">

				<div class="item" style="background:url('<c:url value='/img/slider.png'/>'); background-size:cover;">

					<div class="header-text">
						<h1 class="title wow fadeInDown" data-wow-duration="0.5s" data-wow-delay="0.25s"><img src="<c:url value='/img/servizidog.png'/>" alt="<fmt:message key="webapp.name"/>" /></h1>
						<a href="#about" class="btn btn-lg btn-primary page-scroll">Scopri</a>
					</div>

				</div>

				<div class="item" style="background:url('<c:url value='/img/slider2.png'/>'); background-size:cover;">

					<div class="header-text">
					</div>

				</div>

				<div class="item" style="background:url('<c:url value='/img/slider3.png'/>'); background-size:cover;">

					<div class="header-text">
					</div>

				</div>

			</div>

		</header>

		<!-- ABOUT -->

		<div id="about">
			<div class="row">
				<div class="col-md-4 col-sm-4 text-center wow fadeIn section-1" data-wow-duration="0.5s">
					<div class="fa fa-3x fa-lock"></div>
					<h4>SICUREZZA</h4>
					<p>Le tua privacy è assicurata.</p>
				</div>
				<div class="col-md-4 col-sm-4 text-center wow fadeIn section-2" data-wow-duration="1s">
					<div class="fa fa-3x fa-paw"></div>
					<h4>AFFIDABILITA'</h4>
					<p>Se decidi di Registrarti o Cancellarti dal servizio l'operazione avverrà immediatamente.</p>
				</div>
				<div class="col-md-4 col-sm-4 text-center wow fadeIn section-3" data-wow-duration="1.5s">
					<div class="fa fa-3x fa-leaf"></div>
					<h4>COMODITA'</h4>
					<p>Servizi veloci, intuitivi e centralizzati in tutta Italia.</p>
				</div>
			</div>
		</div>

		<section>
			<div class="container">
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center section-content padding wow fadeInRight" data-wow-duration="1s">
						<div class="content">
							<h1>Che Cos'è Servizi Dog?</h1>
							<hr>
							<p>Servizi Dog si pone come obiettivo primario quello di agevolare la convivenza tra le due specie: <strong>Cane</strong> e <strong>Uomo.</strong> I nostri <strong>Servizi Gratuiti</strong> volgono ad aiutare i cani in difficoltà e fornire comodità a chi già possiede un cane.</p>
							<br>
							<a href="#servizi" class="page-scroll btn btn-primary">SCOPRI</a>
						</div>
					</div>
				</div>
			</div>
		</section>

		<section class="section-darker">
			<div class="row padding-left padding-right">
				<div class="col-md-6 col-sm-6 text-left wow fadeInDown" data-wow-duration="1s">
					<h4>Cosa facciamo</h4>
					<p>Hai bisogno di un <strong>Dog Sitter</strong> o cerchi un <strong>Impiego come Dog Sitter</strong>? Cerchi una <strong>Pensione per Cani</strong> o vuoi <strong>Ospitare Cani</strong>?
					Vuoi <strong>Adottare</strong> o fare adottare un cane? Sei una <strong>Associazione</strong>? Bene! allora troverai nel sito ciò di cui hai bisogno.</p>
						
					<br>
					<a href="#servizi" class="page-scroll btn btn-primary">SERVIZI</a>
				</div>
				<div class="col-md-6 col-sm-6">
				</div>
			</div>
			<div class="row margin-top padding-left padding-right">
				<div class="col-md-6 col-sm-6">
				</div>
				<div class="col-md-6 col-sm-6 text-right wow fadeInDown" data-wow-duration="1s">
					<h4>Perché sceglierci</h4>
					<p>I nostri servizi sono <strong>Gratuiti</strong> e immediati. Pronti a fornirti <strong>Contatti</strong>, <strong>Disponbilità</strong> e <strong>Tariffe</strong> su tutto il terrinorio Nazionale.</p>
					<br>
					<a href="<c:url value='/contatti'/>" class="page-scroll btn btn-primary">CONTATTI</a>
				</div>
			</div>
		</section>

		<!-- SERVICES -->

		<section id="servizi">
			<div class="container">
				<div class="row">
					<div class="col-md-12 col-sm-12 text-center">
						<h1>Servizi</h1>
						<hr>
						<p><strong>Dog Sitter</strong> - <strong>Pensione per Cani</strong> - <strong>Adozione o fare Adottare</strong> - <strong>Associazioni di Volontariato</strong></p>
					</div>
				</div>

				<div class="row margin-top wow fadeInLeft" data-wow-duration="1s">
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 text-left service-item">
						<a href="<c:url value='/dogsitter'/>">
						<div class="fa fa-3x fa-paw"></div>
						<h4><fmt:message key="index.dogsitter"/></h4></a>
						<p>Per chi ha bisogno di un Dog Sitter e per chi cerca un impiego come Dog Sitter.</p>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 text-right service-item">
						<a href="<c:url value='/doghost'/>">
						<div class="fa fa-3x fa-home"></div>
						<h4><fmt:message key="doghost.title"/></h4></a>
						<p>Chi ha bisogno di una Alloggio temporaneo per il suo cane e per chi mette a disposizione un Alloggio.</p>
					</div>
				</div>
				<div class="row margin-top wow fadeInRight" data-wow-duration="1s" data-wow-delay="0.5s">
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 text-left service-item">
						<a href="<c:url value='/adozione'/>">
						<div class="fa fa-3x fa-heart"></div>
						<h4><fmt:message key="index.adozione"/></h4></a>
						<p>Per chi cerca un cane da adottare e per chi vuole farne adottare.</p>
					</div>
					
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 text-right service-item">
						<a href="<c:url value='/associazione'/>">
						<div class="fa fa-3x fa-group"></div>
						<h4><fmt:message key="index.associazionivolontariato"/></h4></a>
						<p>Le associazioni private e pubbliche possono utilizzare il servizio per farsi conoscere, dire cosa fanno e di cosa si occupano, di quali esigenze hanno bisongno e far adottare cani.</p>
					</div>
				</div>
			</div>
		</section>

		<section class="call-to-action full-width-parallax">
			<div class="trasparence">
				<div class="container">
					<div class="row">
						<div class="col-md-3 col-sm-6 col-xs-12 padding-tb text-center">
							<a href="<c:url value='/dogsitter'/>">
							<div class="fa fa-3x fa-paw wow fadeInDown" data-wow-duration="1s"></div></a>
							<h3><fmt:message key="index.dogsitter"/></h3>
							<hr>
							<h2 class="wow fadeInUp" data-wow-duration="1s">${dogSitterTot}</h2>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12 padding-tb text-center">
							<a href="<c:url value='/doghost'/>">
							<div class="fa fa-3x fa-home wow fadeInDown" data-wow-duration="1s" data-wow-delay="0.25s"></div></a>
							<h3><fmt:message key="index.doghost"/></h3>
							<hr>
							<h2 class="wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.2s">${dogHostTot}</h2>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12 padding-tb text-center">
							<a href="<c:url value='/adozione'/>">
							<div class="fa fa-3x fa-heart wow fadeInDown" data-wow-duration="1s" data-wow-delay="0.5s"></div></a>
							<h3><fmt:message key="index.adozione"/></h3>
							<hr>
							<h2 class="wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.5s">${caniTotAdozione}</h2>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-12 padding-tb text-center">
							<a href="<c:url value='/associazione'/>">
							<div class="fa fa-3x fa-group wow fadeInDown" data-wow-duration="1s" data-wow-delay="0.75s"></div></a>
							<h3><fmt:message key="index.associazione"/></h3>
							<hr>
							<h2 class="wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.75s">${associazioneTot}</h2>
						</div>
					</div>
				</div>
			</div>
		</section>

		<div id="testimonials">
			<div class="container">
				<div class="row">
					<div class="col-md-12 col-sm-12 text-center">
						<div id="owl-testimonials">
							<div class="item">
								<p>"Nella pacatezza dello sguardo degli animali parla ancora la saggezza della natura; perché in essi la volontà e l'intelletto non si sono ancora distaccati abbastanza l'uno dall'altro per potersi, al loro reincontrarsi, stupirsi l'uno dell'altra."</p>
								<br>
								<i>Arthur Schopenhauer</i>
							</div>
							<div class="item">
								<p>"Puoi conoscere il cuore di un uomo già dal modo in cui egli tratta gli animali."</p>
								<br>
								<i>Immanuel Kant</i>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<section class="darker">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-sm-8 text-left wow fadeInLeft" data-wow-duration="1s">
						<h3>Non sei ancora registrato?</h3>
						<p>Registrati per poter usufruire gratuitamente dei nostri Servizi.</p>
					</div>
					<div class="col-md-4 col-sm-4 text-center wow fadeInRight" data-wow-duration="1s">
						<a href="<c:url value='/registrazione'/>" class="btn btn-lg btn-primary"><fmt:message key="menu.registrati"/></a>
					</div>
				</div>
			</div>
		</section>
		
</body>