/*
        This file can be used to contain all your plugins, such as jQuery plugins and other 3rd party scripts.
		One approach is to put jQuery plugins inside of a (function($){ ... })(jQuery); 
		closure to make sure they're in the jQuery namespace safety blanket. 
		Read more about jQuery plugin authoring.
		By default the plugins.js file contains a small script to avoid console errors in browsers that lack a console. 
		The script will make sure that, if a console method isn't available, that method will have the value of empty function, thus, 
		preventing the browser from throwing an error.
 */

// Avoid `console` errors in browsers that lack a console.


(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeline', 'timelineEnd', 'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());

// Place any jQuery/helper plugins in here.
