


<link rel="stylesheet" href="/scripts/CLNDR/example/styles/clndr.css">


<div class="cal1"></div>


<script>
var local = '${pageContext.request.locale.language}';
var inizioDay = '${dogSitter.dataInizioDisponib}';
var fineDay = '${dogSitter.dataFineDisponib}';
//call this from the developer console and you can control both instances
var calendars = {};

$(document).ready( function() {

  var thisMonth = moment().format('YYYY-MM');
  var eventArray = [
    { startDate: inizioDay, endDate: fineDay, title: 'Disponibilita' }
  ];


  calendars.clndr1 = $('.cal1').clndr({
    events: eventArray,

    clickEvents: {
      click: function(target) {
        console.log(target);
      },
      nextMonth: function() {
        console.log('next month.');
      },
      previousMonth: function() {
        console.log('previous month.');
      },
      onMonthChange: function() {
        console.log('month changed.');
      },
      nextYear: function() {
        console.log('next year.');
      },
      previousYear: function() {
        console.log('previous year.');
      },
      onYearChange: function() {
        console.log('year changed.');
      }
    },
    multiDayEvents: {
      startDate: 'startDate',
      endDate: 'endDate',
      singleDay: 'date'
    },
    showAdjacentMonths: true,
    adjacentDaysChangeMonth: false
  });


  // bind both clndrs to the left and right arrow keys
  $(document).keydown( function(e) {
    if(e.keyCode == 37) {
      // left arrow
      calendars.clndr1.back();
      calendars.clndr2.back();
    }
    if(e.keyCode == 39) {
      // right arrow
      calendars.clndr1.forward();
      calendars.clndr2.forward();
    }
  });

});
</script>


  <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
  <script src= "/scripts/CLNDR/example/moment-2.8.3.js"></script>
  <script src="/scripts/CLNDR/src/clndr.js"></script>


