<link rel="stylesheet" href="<c:url value="/scripts/jquery-date-range-picker-master/daterangepicker.css"/>">

<script src="<c:url value="/scripts/jquery-date-range-picker-master/moment.min.js"/>"></script>
<script src="<c:url value="/scripts/jquery-date-range-picker-master/jquery.daterangepicker.js"/>"></script>
<script src="<c:url value="/scripts/vendor/jquery-validation/dist/jquery.validate.js"/>"></script>

<script>
	var idDogSitter = '${dogSitter.id}';
</script>


	<div class="col-md-6 col-sm-6 col-xs-6 text-left margin-top">
		<label>Dal</label>
		<input type="text" id="dataRangeInizio" size="6" value="<fmt:formatDate value="${dogSitter.dataInizioDisponib}" pattern="dd-MM-yyyy"/>" name="dataRangeInizio">
	</div>
	<div class="col-md-6 col-sm-6 col-xs-6 text-left margin-top">
		<label>Al</label>
		<input type="text" id="dataRangeFine" size="6" value="<fmt:formatDate value="${dogSitter.dataFineDisponib}" pattern="dd-MM-yyyy"/>" name="dataRangeFine">
	</div>

	<span style="color:green; font-weight: bold;" id="spanEsitoDateDisp"></span>

	<div class="col-md-6 col-sm-6 col-xs-6 text-left margin-top">
		<label>Tariffa Oraria (di default 8,00 euro)</label>
		<input type="text" id="tariffa" size="5" value="${dogSitter.tariffa}">
		
		<span style="color:green; font-weight: bold;" id="spanEsitoTariffa"></span>
	</div>
		
		

	

<script>
	jQuery.noConflict();
	(function( $ ) {

	$(document).ready(function () {

		var local = '${pageContext.request.locale.language}';
		var toVar = '';
		if (local == 'it' ){ toVar = 'al' }else{ toVar = 'to' }

		jQuery('#tariffa').on('change',function() {

			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/impostaTariffaDogSitter?idDogSitter='+idDogSitter,
						    
				  data : {
					  tariffa : $('#tariffa').val(),
		            },
		            dataType: 'text',
		            success: function( data ) {
				        //alert(data);
				        if (data!=''){
				        	$('#spanEsitoTariffa').text(data);

				        }else{
				        	$('#spanEsitoTariffa').text('');
				        }
				    },
		            error: function (data) {
		            	$('#spanEsitoTariffa').text('');
		            }
				});
		});

		$('#dataRangeInizio').dateRangePicker({
			format: 'DD-MM-YYYY',
			startOfWeek: 'monday',
			separator : toVar,
			language: 'auto',
			getValue: function()
			{
				if ($('#dataRangeInizio').val() && $('#dataRangeFine').val() )
					return $('#dataRangeInizio').val() + toVar + $('#dataRangeFine').val();
				else
					return '';
			},
			setValue: function(s,s1,s2)
			{
				$('#dataRangeInizio').val(s1);
				$('#dataRangeFine').val(s2);
			}
			
		}).bind('datepicker-close',function() {
			
			//Dog sitter

			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/impostaDateRangeDisponibilitaDogSitter?idDogSitter='+idDogSitter,
						    
				  data : {
					  dataRangeInizio : $('#dataRangeInizio').val(),
					  dataRangeFine : $('#dataRangeFine').val()
		            },
		            dataType: 'text',
		            success: function( data ) {
				        //alert(data);
				        if (data!=''){
				        	$('#spanEsitoDateDisp').text(data);

				        }else{
				        	$('#spanEsitoDateDisp').text('');
				        }
				    },
		            error: function (data) {
		            	$('#spanEsitoDateDisp').text('');
		            }
				});
			
		});

	});
	
	})( jQuery ); //fine jQuery.noConflict();
</script>

