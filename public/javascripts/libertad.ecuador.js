(function( $ ){

  function confirmDelete(deleteRoute){
      bootbox.dialog("Are you sure you want to delete the Category?", [  {
          "label" : "Cancel",
          "class" : "primary",
          "callback": function() {
              console.log("user cancelled");
          }
      }, {
          "label" : "Delete!",
          "class" : "btn-danger",
          "callback": function() {
              $.ajax({
                url: deleteRoute,
                type: "DELETE"
              }).done(function(data, textStatus, jqXHR){
                  // similar behavior as an HTTP redirect
                  window.location.replace("/segmentCategory/deleteConfirmed/"+data);
                });
              
          }
      
      }]);
    console.log(deleteRoute);

  }

  var methods = {
    init : function( options ) { 
      $("body").on("click","#delete-category", function(event){
          confirmDelete($(event.target).attr("delete-route"));
      });
    },
    show : function( ) {
      // IS
    },
    hide : function( ) { 
      // GOOD
    },
    update : function( content ) { 
      // !!! 
    }
  };

  $.fn.libertad = function( method ) {
    
    // Method calling logic
    if ( methods[method] ) {
      return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    } else if ( typeof method === 'object' || ! method ) {
      return methods.init.apply( this, arguments );
    } else {
      $.error( 'Method ' +  method + ' does not exist on jQuery.libertad' );
    }    
  
  };

})( jQuery );
var libertad = $().libertad;