<%@ include file="/common/taglibs.jsp"%>


<script src="<c:url value="/scripts/vendor/validationjquery-1.14.0/dist/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/scripts/vendor/validationjquery-1.14.0/dist/localization/messages_${pageContext.request.locale.language}.js"/>"></script>

	<script>
		var idDogHost = '${dogHost.id}';
	</script>






	<form id="myform01">
	
	
		<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top">
			<label>Metri quadrati</label>
			<input type="text" id="metriQuadrati" name="metriQuadrati"  value="${dogHost.metriQuadrati}" /> </br> 
		</div>
		
		<div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			<label>Terrazza</label>
	    	<input type="checkbox" id="terrazza" name="checkInfoCasa[]" value="terrazza" <c:if test="${dogHost.terrazza}">checked</c:if> />

	    	<label>Giardino</label>
	    	<input type="checkbox" id="giardino" name="checkInfoCasa[]" value="giardino" <c:if test="${dogHost.giardino}">checked</c:if> />

	    	<label>Animali Presenti</label>
	    	<input type="checkbox" id="animaliPresenti" name="checkInfoCasa[]" value="animaliPresenti" <c:if test="${dogHost.animaliPresenti}">checked</c:if> />   
	    </div>
	    
	    <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			<span style="color:green; font-weight: bold;" id="spanEsito"></span>
		</div>
		
	    <div class="col-md-12 col-sm-12 col-xs-12 text-left margin-top">
			<a href="#" class="btn btn-green" id="btnSave">
			<i class="fa fa-check"> </i> <fmt:message key="button.save"/></a>
		</div>
		
		
	</form>
	


        <script>
        jQuery.noConflict();
		(function( $ ) {
        
        
        $(document).ready(function () {

        	$('#myform01').validate({
                rules: {
                	metriQuadrati: {
                        required: true,
                        number: true
                    }
                } // se ci fosse il bottone submint.....
                // ,submitHandler: function (form) { // for demo 
                //}
            });
        	

            // Testa tutto il form....
            $('#btnSave').on('click', function () {
            	if($('#myform01').valid()){

    				$.ajax({
           				  type: 'POST',
           				  url: '${pageContext.request.contextPath}/impostaInfoCasaDogHost?idDogHost='+idDogHost,
           				  data : {
           					metriQuadrati : $('#metriQuadrati').val(),
           					checkInfoCasa : $('[name="checkInfoCasa[]"]').serialize()
							//checkInfoCasa: $("#checkInfoCasa:checked").serialize()

           		            },
           		            dataType: 'text',
           		            success: function( data ) {
           				        //alert(data);
           				        if (data!=''){
           				        	$('#spanEsito').text(data);
           				        }else{
           				        	$('#spanEsito').text('');
           				        }
           				    },
           		            error: function (data) {
           		            	$('#spanEsito').text('');
           		            }
           				});
            	}else{
            		//alert('validazione errata');
            	}

                
            });

            // testa un solo elemnto.
            /*
            $('#btnControllaMetriQuadrati').on('click', function () {
                $('metriQuadrati').valid();
            });
			*/

            
        });      
        
        
		})( jQuery ); //fine jQuery.noConflict();  
        </script>
        
        