<%@ include file="/common/taglibs.jsp"%>

  <head>
	<title><fmt:message key="dogsitter.lista"/></title>
    <meta name="breadcrumb" content="<fmt:message key="dogsitter.lista"/>"/>
    <meta name="description" content="<fmt:message key="meta.description.dogSitterList"/>" />
	<meta name="keywords" content="${keywordsDogSitter}" />


	<link href="<c:url value="/scripts/filter.js-master/examples/assets/css/jquery-ui-1.10.2.custom.min.css"/>" media="screen" rel="stylesheet" type="text/css">
	<script src="<c:url value="/scripts/filter.js-master/examples/assets/js/jquery-1.11.3.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/scripts/filter.js-master/examples/assets/js/jquery-ui-1.10.2.custom.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/scripts/filter.js-master/dist/filter.js"/>" type="text/javascript"></script>
<script>

var urlContent = '${pageContext.request.contextPath}';
var serviziTot = ${DogSitterTot};
var serviziJson = ${DogSitterJson};


jQuery.noConflict();
(function( $ ) {

$(document).ready(function(){

  initSliders();
  
  //NOTE: To append in different container
  var appendToContainer = function(htmlele, record){
    console.log(record)
  };

  var FJS_Servizi = FilterJS(serviziJson, '#ServiziRecord', {
    template: '#template-Servizi',
    search: {ele: '#searchbox_Servizi'},

    callbacks: {
      afterFilter: function(result){
        $('#total_Servizi').text(result.length);
      }
    }
    //appendToContainer: appendToContainer
  });

  FJS_Servizi.addCallback('beforeAddRecords', function(){
    if(this.recordsCount >= serviziTot){
      this.stopStreaming();
    }
  });

  
  
	FJS_Servizi.addCriteria({field: 'tariffa', ele: '#tariffa_filter_Servizi', type: 'range'});
	
 	window.FJS_Servizi = FJS_Servizi;
 
});

function initSliders(){


  $("#tariffa_slider_Servizi").slider({
    min: 0,
    max: 40,
    values:[0, 40],
    step: 1,
    range:true,
    slide: function( event, ui ) {
      $("#tariffa_range_label_Servizi" ).html(ui.values[ 0 ] + ' - ' + ui.values[ 1 ] +' &#8364;');
      $('#tariffa_filter_Servizi').val(ui.values[0] + '-' + ui.values[1]).trigger('change');
    }
  });
  
  
  
}

})( jQuery ); //fine jQuery.noConflict();
</script>

</head>


<body>

<div class="container">

  <div class="sidebar col-md-3">
    <div>
      <legend align="left"><fmt:message key="dogsitter.title"/></legend>
      <input type="text" class="form-control" id="searchbox_Servizi" placeholder="<fmt:message key="ricerca.placeholder"/>">
    </div>
    <br>
    
    <div class="well">
      <fieldset id="tariffa_criteria_Servizi">
        <legend><fmt:message key="dogsitter.tariffaoraria"/> </legend> <span id="tariffa_range_label_Servizi" class="slider-label">0 - 40 &#8364;</span>
        <div id="tariffa_slider_Servizi" class="slider">
        </div>
        <input type="hidden" id="tariffa_filter_Servizi" value="0-40">
      </fieldset>
    </div>

  </div>  


  <div class="col-md-9">
    <div class="movies content row" id="ServiziRecord"> </div>
  </div>
  
  
</div>	


<script id="template-Servizi" type="text/html">

  <div  class="col-md-4 movie">
    <div class="thumbnail">
      <span class="label label-success rating">{{= tariffa }} <i>&#8364;</i></span>
		<span class="label label-success rating">{{= servizio }} </span>
      <div class="caption">
        <h4>{{= nome }}</h4>
        <div class="outline">
          {{= descrizione }}
        </div>
        <div class="detail">
          <dl>
            <dt>Indirizzo:</dt>
            <dd>{{= indirizzo }}</dd>
            <dt>Telefono:</dt>
            <dd>{{= telefono }}</dt>
            <dd><a href="{{= urlProfilo }}" target="_blank">vedi profilo</a></dd>
          </dl>
        </div>
      </div>
		<div>
			<img src="{{= img }}" />
		</div>
    </div>
  </div>
</script>

</body>
