 $(function() {
        var pluginfile = $( "#pluginfile" ),
            allFields = $( [] ).add( pluginfile ),
            tips = $( ".validateTips" );
 
        function updateTips( t ) {
            tips
                .text( t )
                .addClass( "ui-state-highlight" );
            setTimeout(function() {
                tips.removeClass( "ui-state-highlight", 1500 );
            }, 500 );
        }
 
        function checkLength( o, n ) {
            if ( o.val().length == 0) {
                o.addClass( "ui-state-error" );
                updateTips( n + " must be provided" );
                return false;
            } else {
                return true;
            }
        }
 
        $( "#upload-dialog" ).dialog({
            autoOpen: false,
            height: 280,
            width: 350,
            modal: true,
            buttons: {
                "Install": function() {
                    var bValid = true;
                    allFields.removeClass( "ui-state-error" );
 
                    bValid = bValid && checkLength( pluginfile, "Plug-in file");
 
                    if ( bValid ) {
                        $( "#uploadPluginFrom" ).submit();
                    }
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
    
    $(function() {
        var tree = $( "#plugin-tree" ),
            allFields = $( [] ).add( tree ),
            tips = $( ".validateTips" );
 
        function updateTips( t ) {
            tips
                .text( t )
                .addClass( "ui-state-highlight" );
            setTimeout(function() {
                tips.removeClass( "ui-state-highlight", 1500 );
            }, 500 );
        }
 
        function checkSelection( o) {
        	var checked = selectedPlugins.length;
        	if (checked == 0) {
        		o.addClass( "ui-state-error" );
                updateTips("At least one plugin must be selected" );
                return false;
            } else {
                return true;
            }
        }

 
        $( "#repo-dialog-form" ).dialog({
            autoOpen: false,
            height: 400,
            width: 380,
            modal: true,
            buttons: {
                "Install": function() {
                    var bValid = true;
                    allFields.removeClass( "ui-state-error" );
 
                    bValid = bValid && checkSelection( tree);
 
                    if ( bValid ) {
                    	$.get("installFromRepo.jsp", { data: JSON.stringify(selectedPlugins) },function(response){
                        	$("#info-dialog").html(response);
                        	$( "#info-dialog" ).dialog( "open" );
                		});
                    }
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
                tree.removeClass( "ui-state-error" );
            }
        });
    });