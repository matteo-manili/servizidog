/* Activ'Map Plugin
 * Copyright (c) 2015 Pandao
 * Documentation : /doc/index.html
 */
(function($){
    	
    $.activmap = {
        defaults: {
            places: [],            //list of places objects {title, address, phone, tags, lat, lng, img},
            lat: 42.3585439,       //latitude of the center
            lng: 11.8872070,       //longitude of the center
            zoom: 4,              //default zoom level between 0 and 21
            cluster: true,         //enables / disables clustering for large amounts of markers
            mapType: 'roadmap',    //map type : "roadmap", "satellite", "perspective"
            posPanel: 'left',      //position of the removable panel : "left" or "right"
            showPanel: true,       //shows / hides the removable panel
            radius: 0,             //max radius in kilometers
            country: 'uk',         //country limit for location input (ex. "ca": Canada, "us": United States, "fr": France...)
            autogeolocate: true,  //auto geolocation to set the center of the map
            icon: '/activmap/images/icons/marker-hotel.png',
            styles: [{"featureType":"landscape.man_made","elementType":"geometry","stylers":[{"color":"#f7f1df"}]},{"featureType":"landscape.natural","elementType":"geometry","stylers":[{"color":"#d0e3b4"}]},{"featureType":"landscape.natural.terrain","elementType":"geometry","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"poi.business","elementType":"all","stylers":[{"visibility":"off"}]},{"featureType":"poi.medical","elementType":"geometry","stylers":[{"color":"#fbd3da"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#bde6ab"}]},{"featureType":"road","elementType":"geometry.stroke","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.highway","elementType":"geometry.fill","stylers":[{"color":"#ffe15f"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#efd151"}]},{"featureType":"road.arterial","elementType":"geometry.fill","stylers":[{"color":"#ffffff"}]},{"featureType":"road.local","elementType":"geometry.fill","stylers":[{"color":"black"}]},{"featureType":"transit.station.airport","elementType":"geometry.fill","stylers":[{"color":"#cfb2db"}]},{"featureType":"water","elementType":"geometry","stylers":[{"color":"#a2daf2"}]}]
        }
    };
    
    $.fn.extend({
        
        activmap : function(settings){
            
            var s = $.extend({}, $.activmap.defaults, settings);

            //Map init
            var latlng = new google.maps.LatLng(s.lat,s.lng);
            var opendInfoWindow = null;
            var markers = [];
            var infoWindow = [];
            var ids = [];
            var bounds;
            var num_places = 0;
            var old_results = 0;
            var mapTypeId = google.maps.MapTypeId.ROADMAP;
            if(s.mapType == 'satellite' || s.mapType == 'perspective') mapTypeId = google.maps.MapTypeId.HYBRID;
            var map = new google.maps.Map(document.getElementById('activmap-canvas'), {
                zoom: s.zoom,
                center: latlng,
                mapTypeId: mapTypeId,
                styles: s.styles
            });

            //Geolocation
            _geolocate = function(){
                if(navigator.geolocation){
                    browserSupportFlag = true;
                    navigator.geolocation.getCurrentPosition(function(position){
                        initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
                        map.setCenter(initialLocation);
                    }, function(){
                        handleNoGeolocation(browserSupportFlag);
                    });
                }else{
                    browserSupportFlag = false;
                    handleNoGeolocation(browserSupportFlag);
                }
            }

            if(s.autogeolocate == true){
                _geolocate();
            }
            if($('#activmap-geolocate').length){
                $('#activmap-geolocate').click(function(){
                    _geolocate();
                });
            }

            //Geolocation error handler
            function handleNoGeolocation(errorFlag){
                if(errorFlag == true){
                    console.log('Geolocation service failed.');
                    initialLocation = latlng;
                }else{
                    console.log('Your browser doesn\'t support geolocation.');
                    initialLocation = latlng;
                }
                map.setCenter(initialLocation);
            }
            
            if(s.mapType == 'perspective') map.setTilt(45);
            var activmap_canvas = $('#activmap-canvas');
            var activmap_places = $('#activmap-places');
            var map_w = activmap_canvas.width();
            var cont_w = activmap_places.outerWidth();

            //Cluster init
            if(s.cluster){
                var markerCluster = new MarkerClusterer(map, markers, {
                    maxZoom: 12,
                    gridSize: 40
                });
                markerCluster.setIgnoreHidden(true);
            }
            
            if(!s.showPanel) activmap_places.hide();
            else{
                if(s.posPanel == 'left'){
                    activmap_places.css({
                        left: -cont_w,
                        right: 'auto'
                    });
                    activmap_canvas.css({
                        float: 'right'
                    });
                }
                if(s.posPanel == 'right'){
                    activmap_places.css({
                        right: -cont_w,
                        left: 'auto'
                    });
                    activmap_canvas.css({
                        float: 'left'
                    });
                }
            }
            
            //if($('input[name="marker_type[]"]').length) $('input[name="marker_type[]"]').prop('checked',false);
            //if($('input[name="activmap_radius"]').length) $('input[name="activmap_radius"]').prop('checked',false);

            if($('#activmap-location').length){
            	
                var options = {
                   types: ['address'], 
                   componentRestrictions: {country: s.country}
                };
                
                var input = document.getElementById('activmap-location');
                

                //originale
                //autocomplete = new google.maps.places.Autocomplete(input, options);
                
                //modificato.
                //types: ['address'] filtra gli autocomplete alle vie
                //types: ['geocode'] filtra gli autocomplete a comuni zone e vie
                autocomplete = new google.maps.places.Autocomplete(
              	      /** @type {HTMLInputElement} */(document.getElementById('activmap-location')),
              	      { types: ['geocode'],
                          componentRestrictions: {country: s.country} });
                
                
                
                google.maps.event.addListener(autocomplete, 'place_changed', function(){
                    var place = autocomplete.getPlace();
                    if(place.geometry.viewport)
                        map.fitBounds(place.geometry.viewport);
                    else{
                        latlng = place.geometry.location;
                        map.setCenter(place.geometry.location);
                        map.setZoom(10);
                    }
                });
            }

            _init = function(){

                $.each(s.places, function(i, place){
                    place.num_tags = 0;
                    //Marker init
                    var myLatlng = new google.maps.LatLng(place.lat, place.lng);
                    var myIcon = (place.icon != '' && place.icon != undefined) ? place.icon : s.icon;
                    var marker = new google.maps.Marker({
                        map: map,
                        position: myLatlng,
                        icon: myIcon,
                        title: place.title
                    });
                    //Info window content building
                    var mycontent;
                    if(place.img != '') mycontent = '<div class="activmap-brand"><img src="'+place.img+'"></div><h4 class="activmap-title">'+place.title+'</h4>';
                    else mycontent = '<h4 class="activmap-title">'+place.title+'</h4>';
                    if(place.address != '') mycontent += place.address+'<br>';
                    if(place.phone != '') mycontent += place.phone+'<br>';
                    if(place.url != '') mycontent += '<a href="'+place.url+'" target="_blank">'+place.urlTitle+'</a><br>';

                    //Info window initvis
                    infoWindow[i] = new google.maps.InfoWindow({
                        content  : mycontent,
                        position : myLatlng
                    });
                    
                    //Marker click event
                    google.maps.event.addListener(marker, 'click', function(){
                        if(opendInfoWindow != null) opendInfoWindow.close();
                        infoWindow[i].open(map, marker);
                        opendInfoWindow = infoWindow[i];
                        $('.activmap-place').removeClass('active');
                        $('#activmap-place_'+i).addClass('active');
                        activmap_places.scrollTop(activmap_places.scrollTop()+$('#activmap-place_'+i).position().top);
                    });
                    marker.setVisible(false);
                    place.marker = marker;
                    markers.push(marker);

                    activmap_places.append('<div class="activmap-place" id="activmap-place_'+i+'"><h3>'     +place.title+    '</h3><p>'+place.address+'</p><a href="'+place.url+'" target="_blank">'+place.urlTitle+'</a> </div>');
                    
                    
                    
                    
                    
                    
                    //Places indexed by tag name (checkbox name)
                    $.each(place.tags, function(j, tag){
                        if(ids[tag] === undefined) ids[tag] = [];
                        ids[tag].push(i);
                    }); 
                });
                
                //Radius change event
                $('input[name="activmap_radius"]').change(function(){
                	
                	
                	
                    s.radius = $(this).val();
                    $('input[name="marker_type[]"]').each(function(){
                        _update_places_tag($(this));
                    });
                    _update_map();
                });

                //Filter change event
                $('input[name="marker_type[]"]').change(function(){
                	
                	//alert("aaaa");
                	
                    _update_places_tag($(this));
                    _update_map();
                });

                //Reset click event
                $('#activmap-reset').click(function(){
                	
                	
                	
                    $('input[name="marker_type[]"]').prop('checked',false);
                    $('input[name="marker_type[]"]').each(function(){
                        _update_places_tag($(this));
                    });
                    _update_map();
                    return false;
                });
                
                //Place click event
                $('.activmap-place').click(function(){
                    var id = $(this).attr('id').replace('activmap-place_','');
                    google.maps.event.trigger(s.places[id].marker, 'click');
                });
                
                //Window resize event
                $(window).resize(function(){
                    _update_map();
                });
                
                $('input[name="marker_type[]"]:checked').each(function(){
                    _update_places_tag($(this));
                });
                _update_map();
            }
            
            _rad = function(a){
                return a * Math.PI / 180;
            }

            /* _getDistance() returns the distance between 2 points in meters
             * 
             */
            _get_distance = function(p1, p2){
                var R = 6378137;
                var dLat = _rad(p2.lat() - p1.lat());
                var dLong = _rad(p2.lng() - p1.lng());
                var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(_rad(p1.lat())) * Math.cos(_rad(p2.lat())) *
                    Math.sin(dLong / 2) * Math.sin(dLong / 2);
                var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                var d = R * c;
                return d;
            }
            
            /* _update_map() hides or shows the places panel. Shrinks, enlarges and refresh the map
             * 
             */
            _update_map = function(){
                map_w = $('#activmap-wrapper').width();
                if(num_places > 0){
                    //results found
                    if(old_results == 0){
                        //no previous result
                        //side panel displaying for 1st time => shows places
                        if(s.showPanel){
                            if(s.posPanel == 'left'){
                                activmap_places.stop(true,true).animate({
                                    left: 0
                                },600);
                            }
                            if(s.posPanel == 'right'){
                                activmap_places.stop(true,true).animate({
                                    right: 0
                                },600);
                            }
                            //side panel is visible on screen device only => shrinks map
                            if(activmap_places.is(':visible')){
                                activmap_canvas.animate({
                                    width: map_w-cont_w
                                },600, function(){
                                    //update of map's bounds
                                    map.fitBounds(bounds);
                                    if(s.cluster) markerCluster.repaint();
                                });
                            }else{
                                map.fitBounds(bounds);
                                if(s.cluster) markerCluster.repaint();
                            }
                        }else{
                            map.fitBounds(bounds);
                            if(s.cluster) markerCluster.repaint();
                        }
                    }else{
                        if(s.showPanel && activmap_places.is(':visible')) activmap_canvas.width(map_w-cont_w);
                        else activmap_canvas.width(map_w);
                        map.fitBounds(bounds);
                        if(s.cluster) markerCluster.repaint();
                    }
                }else{
                    //no result found
                    if(opendInfoWindow != null) opendInfoWindow.close();
                    if(s.showPanel){
                        if(activmap_places.is(':visible')){
                            //there were previous results
                            //side panel is visible on screen device only => hides places, enlarges map
                            activmap_canvas.animate({
                                width: map_w
                            },600);
                            if(s.posPanel == 'left'){
                                 activmap_places.animate({
                                    left: -cont_w
                                },600);
                            }
                            if(s.posPanel == 'right'){
                                 activmap_places.animate({
                                    right: -cont_w
                                },600);
                            }
                        }else
                            activmap_canvas.width(map_w);
                    }else
                        activmap_canvas.width(map_w);
                    
                    if(s.cluster) markerCluster.repaint();
                    map.setZoom(s.zoom);
                    map.setCenter(latlng);
                }
                var listener = google.maps.event.addListener(map, 'idle', function(){ 
                    if(map.getZoom() > 16) map.setZoom(16);
                    google.maps.event.removeListener(listener);
                });
                old_results = num_places;
            }
            
            /* _update_places_tag(input) updates the array of places and the map's bounds
             * 
             * @param input checkbox object
             * 
             */
            _update_places_tag = function(input){
                var val = input.val();
                //current tag has places
                if(ids[val] !== undefined){
                    //loop on each place for this tag
                    $.each(ids[val], function(i, id){
                        s.places[id].num_tags = 0;
                        if(input.prop('checked') === true){
                            //the tag is checked => set visible
                            if(s.radius == 0 || _get_distance(s.places[id].marker.position, latlng) <= s.radius*1000){
                                s.places[id].num_tags++;
                                s.places[id].marker.setVisible(true);
                                s.places[id].isVisible = true;
                                $('#activmap-place_'+id).show();
                            }else{
                                //perform only if a checkbox is clicked
                                if(s.places[id].num_tags > 0) s.places[id].num_tags--;
                                if(s.places[id].num_tags == 0){
                                    s.places[id].marker.setVisible(false);
                                    s.places[id].isVisible = false;
                                    $('#activmap-place_'+id).hide();
                                }
                            }
                        }else{
                            //perform only if a checkbox is clicked
                            if(s.places[id].num_tags > 0) s.places[id].num_tags--;
                            if(s.places[id].num_tags == 0){
                                s.places[id].marker.setVisible(false);
                                s.places[id].isVisible = false;
                                $('#activmap-place_'+id).hide();
                            }
                        }
                    });
                    //update of map's bounds
                    bounds = new google.maps.LatLngBounds();
                    var i = 0;
                    $.each(s.places, function(id, place){
                        if(place.isVisible == true){
                            bounds.extend(place.marker.getPosition());
                            i++;
                        }
                    });
                    num_places = i;
                    $('#activmap-results-num').html(i+' risultati');
                }
            }

            if(!Array.isArray(s.places)){
                $.ajax({
                    url: s.places,
                    dataType: 'json',
                    cache: false,
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                    },
                    success: function(data){
                        var obj = eval(data);
                        s.places = obj.places;
                        _init();
                    }
                });
            }else
                _init();
        }
    });
})(jQuery);
