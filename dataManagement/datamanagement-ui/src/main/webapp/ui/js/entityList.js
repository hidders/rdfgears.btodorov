function resetEntityList() {
	$.get("listEntities.jsp", {}, function(response) {
		var result = jQuery.parseJSON(response);
		$("#entityListTree").dynatree("getRoot").removeChildren();
		$("#entityListTree").dynatree("getRoot").addChild(result);
	});
}

function deleteEntity(name){
	$.get("deleteEntity.jsp", {
		data : name
	}, function(response) {
		resetEntityList();
	});
}

$(function() {
	// Initialize the tree inside the <div>element.
	// The tree structure is read from the contained <ul> tag.
	$("#entityListTree").dynatree({
		title : "List of Entities",
		minExpandLevel : 2,
		onDblClick : function(node) {
			setEntity(node.data.title);
		},
	});

	$("#btnAddNew").click(function() {
		var node = $("#entityListTree").dynatree("getActiveNode");
		if (node) {
			node.deactivate();
		}
		setEntity();
	});

	$("#btnRemoveEntity")
			.click(
					function() {
						var node = $("#entityListTree").dynatree(
								"getActiveNode");
						if (node) {
							bootbox
									.confirm(
											"Are you sure you want to remove the selected entity?",
											function(result) {
												if (result) {
													deleteEntity(node.data.title);
												}
											});
						}
					});

	resetEntityList();
});