<script src="<c:url value="/js/jquery-2.1.0.min.js"/>"></script>
<script src="<c:url value="/scripts/vendor/validationjquery-1.14.0/dist/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/scripts/vendor/validationjquery-1.14.0/dist/localization/messages_${pageContext.request.locale.language}.js"/>"></script>
<script src="<c:url value="/scripts/dynamic_list_helper.js"/>"></script>
<style type="text/css">
    .Table
    {
        display: table;
    }
	.Title
    {
        display: table-caption;
        text-align: center;
        font-weight: bold;
        font-size: larger;
    }
    .Heading
    {
        display: table-row;
        font-weight: bold;
        text-align: center;
    }
    .Row
    {
        display: table-row;
    }
    .Cell
    {
        display: table-cell;
    }
</style>



            
            
            

<form:form action="adozioneCaneform" commandName="caneListContainer" modelAttribute="caneListContainer" method="post" id="caneListForm" >
	<br>
     <table>
         <tbody id="caneListContainer">
         <c:forEach items="${caneListContainer.caneList}" var="Cane" varStatus="i" begin="0">
            <tr class="cane">
			<td>
		
				<div class="Table">
					<div class="Title"> Scheda cane </div>
				    <div class="Row">
				        <div class="Cell"> Nome &nbsp;</div>
						<div class="Cell"> <form:input class="required" path="caneList[${i.index}].nomeCane" id="nomeCane${i.index}" size="18" /> </div>
				    </div>
				    <div class="Row">
						<div class="Cell"> Anni &nbsp;</div>
						<div class="Cell"> <form:input class="required number" path="caneList[${i.index}].eta" id="eta${i.index}" size="3"  /> </div>
						
						<div class="Cell"> Sesso &nbsp;</div>
						<div class="Cell"> 
							<form:select path="caneList[${i.index}].sesso" class="required">
								<form:options items="${sessoList}" />
							</form:select>
					 	</div>
				    </div>
					<div class="Row">
						<div class="Cell"> Taglia &nbsp;</div>
						<div class="Cell"> 
							<form:select path="caneList[${i.index}].taglia" class="required">
								<form:options items="${tagliaList}" />
							</form:select>
					 	</div>
					 	<div class="Cell"> Pelo &nbsp;</div>
					 	<div class="Cell"> 
							<form:select path="caneList[${i.index}].pelo" class="required">
								<form:options items="${peloList}" />
							</form:select>
					 	</div>
				    </div>
	
					<div class="Row">
				        <div class="Cell"> Carattere &nbsp;</div>
						<div class="Cell"> <form:textarea class="required" path="caneList[${i.index}].carattere" id="carattere${i.index}" size="14" /> </div>
						
				        <div class="Cell"> Info &nbsp;</div>
						<div class="Cell"> <form:textarea path="caneList[${i.index}].storiadelcane" id="storiadelcane${i.index}" /> </div>
						
												    
					</div>
					<div class="Row">
						<div class="Cell"> &nbsp; </div>
						<div class="Cell"> &nbsp; </div>
						<div class="Cell"> 
						<form:hidden path="caneList[${i.index}].id" id="id${i.index}" /> </div>
						
						<div class="Cell" align="right" > 
							<div id="linkRemove">
								<a href="#" style="color:red" class="removeCane" id="${Cane.id}">rimuovi cane</a>
							</div> 
						</div>
					</div>	
				
			    </div>
			    
			</td>
            </tr>
			</c:forEach>
			
			<%-- 
	            IMPORTANT 
	            There must always be one row.
	            This is to allow the JavaScript to clone the row.
	            If there is no row at all, it cannot possibly add a new row.
	        
	            If this 'default row' is undesirable 
	                remove it by adding the class 'defaultRow' to the row
	            I.e. in this case, class="person" represents the row.
	            Add the class 'defaultRow' to have the row removed.
	            This may seem weird but it's necessary because 
	            a row (at least one) must exist in order for the JS to be able clone it.
	            <tr class="person"> : The row will be present
	            <tr class="person defaultRow"> : The row will not be present
	        --%>
			<c:if test="${caneListContainer.caneList.size() == 0}">
				<tr class="cane defaultRow">    
				<td>
				
					<div class="Table">
						<div class="Title"> Scheda cane </div>
					    <div class="Row">
					        <div class="Cell"> Nome &nbsp;</div>
							<div class="Cell"> <input type="text" class="required" id="nomeCane" name="caneList[].nomeCane" value="" size="18" /> </div>
					    </div>
						<div class="Row">
							<div class="Cell"> Anni &nbsp;</div>
							<div class="Cell"> <input type="text" class="required number" name="caneList[].eta" value="" size="3" /> </div>
							<div class="Cell"> Sesso &nbsp;</div>
							<div class="Cell"> 
								<select name="caneList[].sesso" class="required">
								<c:forEach items="${sessoList}" var="sesso">
									<option value="${sesso.key}">${sesso.value}</option>
								</c:forEach>
								</select>
						 	</div>
					    </div>
						<div class="Row">
							<div class="Cell"> Taglia &nbsp;</div>
							<div class="Cell"> 
								<select name="caneList[].taglia" class="required">
									<c:forEach items="${tagliaList}" var="taglia">
										<option value="${taglia.key}">${taglia.value}</option>
									</c:forEach>
								</select>
						 	</div>
						 	<div class="Cell"> Pelo &nbsp;</div>
						 	<div class="Cell"> 
								<select name="caneList[].pelo" class="required">
									<c:forEach items="${peloList}" var="pelo">
										<option value="${pelo.key}">${pelo.value}</option>
									</c:forEach>
								</select>
								
						 	</div>
				    	</div>
				    	<div class="Row">
					        <div class="Cell"> Carattere &nbsp;</div>
							<div class="Cell"> <textarea name="caneList[].carattere" value="" class="required"> </textarea> </div>
							
					        <div class="Cell"> Info &nbsp;</div>
							<div class="Cell"> <textarea name="caneList[].storiadelcane" value=""> </textarea> </div>				    
						</div>
						
						<div class="Row">
							<div class="Cell"> &nbsp; </div>
							<div class="Cell"> &nbsp; </div>
							<div class="Cell"> &nbsp; </div>
							<div class="Cell" align="right" > 
								<div id="linkRemove">
									<a href="#" style="color:red" class="removeCane" id="">rimuovi cane</a>
								</div> 
							</div>
						</div>	
					</div>
					
				</td>
				</tr>
			</c:if>
		</tbody>
    </table> <br>


		<div class="col-md-12 col-sm-12 col-xs-12 text-right margin-top">
		
		<div class="col-md-6 col-sm-6 col-xs-12 text-left margin-top">
		
		<!-- 
			<a class="btn btn-green" id="linkSub" name="save">
			<i class="fa fa-check"></i> <fmt:message key="button.save"/></a>  -->
			<a href="#" class="btn btn-blue start" name="save" id="addCane" >
			<i class="fa fa-plus"></i> Aggiungi Cane</a>
			
			<input type="submit" class="btn btn-green" value="<fmt:message key="button.save"/>" id="submit" />

			</div>
		</div>

    
    
    
            
	</form:form>



        <script>

		jQuery.noConflict();
            (function( $ ) {

            	
            	
            	

            	
            	
       	$(document).ready(function () {

       	
			$("#linkSub").click(function() {
   			  //alert( "Valid: " + form.valid() );
   			  var form = $( "#caneListForm" );
   			  //form.valid();
   			  alert("inzio klik su link");
   			  
   			  form.submit();
   			  
   			});
         	
         	
     		
			$('#caneListForm').validate({
				rules: {
                         required: true,
                         number: true
                 } // se ci fosse il bottone submint.....
                  ,submitHandler: function (form) { // for demo
                 	 
                 	return true;

                 }
            });
            
	        
	        $("#linkRemove a").click(function() {
	        	var idCane = $(this).attr('id');
	        	//alert("inizio idCane= "+idCane);
	    			$.ajax({
	   				  type: 'POST',
	   				  url: '${pageContext.request.contextPath}/eliminaCaneAdozione?idCane='+idCane,
	   						    
	   				  data : {
	   					  //tariffa : $('#tariffa').val(),
	   		            },
	   		            dataType: 'text',
	   		            success: function( data ) {
	
	   				    },
	   		            error: function (data) {
	
	   		            }
	    			});
	            });
	
	        
	        
	            function rowAdded(rowElement) {
	            	//alert("ROW ADDED");
	                //clear the imput fields for the row
	                $(rowElement).find("input").val('');
	                $(rowElement).find("textarea").val('');
	                $(rowElement).find("select").val('');           
	                $(rowElement).find("label").text('');
	                //$(rowElement).find("label").attr('for').text('');
	
	                //$(rowElement).find("label[class='error']").text('');
	                //$(rowElement).find("label").find( ":contains('error')" ).text('');
	                
	                //$("div.error span").html(message);
	                //$(rowElement).find("div").id('');
	                //may want to reset <select> options etc
	                //in fact you may want to submit the form
	                saveNeeded();
	            }
	            
	            function rowRemoved(rowElement) {
	            	//alert( $(rowElement).html() );
	                saveNeeded();
	            }
	
	            function saveNeeded() {
	            	
	            	/*
	                $('#submit').css('color','red');
	                $('#submit').css('font-weight','bold');
	                if( $('#submit').val().indexOf('!') != 0 ) {
	                    $('#submit').val( '!' + $('#submit').val() );
	                    //$('#submit').val($('#submit').val() );
	                    //alert("zero righe!");
	                }
	                //alert( $(rowElement).html() );
	                */
	            }
	
	            function beforeSubmit() {
	            	return true;
	            }
	
	            
	            	
	            var config = {
	                rowClass : 'cane',
	                addRowId : 'addCane',
	                removeRowClass : 'removeCane',
	                formId : 'caneListForm', 
	                rowContainerId : 'caneListContainer',
	                indexedPropertyName : 'caneList',
	                indexedPropertyMemberNames : 'nomeCane,carattere,sesso,storiadelcane,eta,taglia,pelo,id',
	                rowAddedListener : rowAdded,
	                rowRemovedListener : rowRemoved,
	                beforeSubmit : beforeSubmit
	            };
	            new DynamicListHelper(config);

            
            
            
       		});    
        	
        	
        	
        })( jQuery ); //fine jQuery.noConflict();  
        </script>