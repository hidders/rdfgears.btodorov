$('head').append( $('<link rel="stylesheet" type="text/css" />').attr('href', '/datamanagement-ui/src/skin/ui.dynatree.css') );
$('head').append( $('<script  />').attr('src', '/datamanagement-ui/src/jquery.dynatree.js') );

function loadEntities() {
    $.get("/datamanagement-ui/ui/previewEntities.jsp", {}, function(response) {
        var result = jQuery.parseJSON(response);

        $("#previewTree").dynatree("getRoot").removeChildren();
        $("#previewTree").dynatree("getRoot").addChild(result);
    });
}

$(function() {

    // Initialize the tree inside the <div>element.
    // The tree structure is read from the contained <ul> tag.
    $("#previewTree").dynatree({
        title : "Entity Structure",
    });

    $("#btnRefreshEntities").click(function() {
        loadEntities();
    });

});