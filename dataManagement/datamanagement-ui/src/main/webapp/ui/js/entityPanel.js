var loadedEntityName;
var selectedPropertyNode;

function setEntity(name) {
	if (name) {
        		$.get("readEntity.jsp", {
            data : name
        }, function(response) {
			var result = jQuery.parseJSON(response);

			$("#txtName").val(result.name);
			$("#txtDescription").val(result.description);

			$("#chkRead").prop('checked', result.readAccess);
			$("#chkWrite").prop('checked', result.writeAccess);

			$("#tree").dynatree("getRoot").removeChildren();
			$("#tree").dynatree("getRoot").addChild(result.children);

			loadedEntityName = result.name;
			$("#entityPanel").show();
		});
	} else {
		$("#txtName").val("");
		$("#txtDescription").val("");

		$("#chkRead").prop('checked', false);
		$("#chkWrite").prop('checked', false);

		$("#tree").dynatree("getRoot").removeChildren();

		$("#entityPanel").show();
	}
}

function setProperty(node) {
	selectedPropertyNode = node;
	$("#propertyName").val(node.data.title);
	$("#sctIsComposite").val(node.data.isFolder.toString());

	if (node.data.isFolder) {
		$("#atomicProp").hide();
		$("#entityProp").show();
		$("#chkMultiple").prop('checked', node.data.isMultiple);
	} else {
		$("#atomicProp").show();
		$("#entityProp").hide();
		$("#sctType").val(node.data.rglType);
	}

	$("#property").modal('show');
}

function storeEntity() {
	var dict = $("#tree").dynatree("getTree").toDict();

	var request = {
		"name" : $("#txtName").val(),
		"description" : $("#txtDescription").val(),
		"readAccess" : $("#chkRead").prop('checked'),
		"writeAccess" : $("#chkWrite").prop('checked'),
		"children" : dict.children
	};

	$.get("saveEntity.jsp", {
		data : JSON.stringify(request)
	}, function(response) {
		loadedEntityName = null;
		$("#entityPanel").hide();

		resetEntityList();

	});
}

$(function() {
	$("#atomicProp").hide();
	$("#entityProp").hide();
	$("#entityPanel").hide();

	// Initialize the tree inside the <div>element.
	// The tree structure is read from the contained <ul> tag.
	$("#tree").dynatree({
		title : "Entity Structure",
		clickFolderMode: 1,
		onDblClick : function(node) {
			setProperty(node);
		},
		onKeydown : function(node, event) {
			//Esc to remove selection
			if (event.which == 27 && node) {
				node.deactivate();
			}
		}
	});

	$('#sctIsComposite').change(function() {
		if ($("#sctIsComposite").val() == "true") {
			$("#atomicProp").hide();
			$("#entityProp").show();
		} else {
			$("#atomicProp").show();
			$("#entityProp").hide();
		}
	});

	$("#btnAddAtomic").click(function() {
		// Sample: add an hierarchic branch using an array
		var obj = [ {
			title : 'Propety',
			name : 'Property',
			isFolder : false,
			rglType : 'boolean'
		} ];

		var node = $("#tree").dynatree("getActiveNode");
		if (node) {
			if (node.data.isFolder)
				node.addChild(obj);
		} else
			$("#tree").dynatree("getRoot").addChild(obj);
	});

	$("#btnRemove").click(function() {
		var node = $("#tree").dynatree("getActiveNode");
		if (node)
			node.remove();
	});

	$("#btnSaveProperty")
			.click(
					function() {
						if (selectedPropertyNode) {
							selectedPropertyNode.data.title = $("#propertyName")
									.val();
							selectedPropertyNode.data.name = $("#propertyName")
									.val();
							selectedPropertyNode.data.isFolder = $(
									"#sctIsComposite").val() == "true";

							if (selectedPropertyNode.data.isFolder) {
								selectedPropertyNode.data.isMultiple = $(
										"#chkMultiple").prop('checked');
							} else {
								selectedPropertyNode.data.rglType = $(
										"#sctType").val();
							}

							selectedPropertyNode.render();
							selectedPropertyNode.deactivate();
							$("#property").modal('hide');
						}
					});

	$("#btnSave")
			.click(
					function() {

						if (loadedEntityName) {
							bootbox
									.confirm(
											"This operation will remove any existing data for this entity. Are you sure you want to continue?",
											function(result) {
												if (result)
													storeEntity();
											});
						} else {
							storeEntity();
						}
					});

	$("#btnCancel").click(function() {
		loadedEntityName = null;
		$("#entityPanel").hide();
		var node = $("#entityListTree").dynatree("getActiveNode");
		if (node) {
			node.deactivate();
		}
	});

	$("#property").modal({
		backdrop : 'static',
		show : false
	});

	$('#property').on('hidden', function() {
		selectedPropertyNode = null;
		var node = $("#tree").dynatree("getActiveNode");
		if (node) {
			node.deactivate();
		}
	});
});