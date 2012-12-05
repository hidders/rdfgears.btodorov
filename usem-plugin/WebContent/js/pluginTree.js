var selectedPlugins = [];
function loadTree(){
    $("#plugin-tree").dynatree({
      checkbox: true,
      selectMode: 3,
      initAjax: {url: "listRepo.jsp"},
      onSelect: function(select, node) {
        // Get a list of all selected nodes, and convert to a key array:
    	  selectedPlugins = $.map(node.tree.getSelectedNodes(), function(node){
			if (node.data.isFolder != true)
				return node.data.key;
			return null;
        });
      },
      onDblClick: function(node, event) {
        node.toggleSelect();
      },
      onKeydown: function(node, event) {
        if( event.which == 32 ) {
          node.toggleSelect();
          return false;
        }
      },
      // The following options are only required, if we have more than one tree on one page:
//        initId: "treeData",
      cookieId: "dynatree-Cb3",
      idPrefix: "dynatree-Cb3-"
    });
  

    $("#btnToggleSelect").click(function(){
      $("#tree2").dynatree("getRoot").visit(function(node){
        node.toggleSelect();
      });
      return false;
    });
    $("#btnDeselectAll").click(function(){
      $("#tree2").dynatree("getRoot").visit(function(node){
        node.select(false);
      });
      return false;
    });
    $("#btnSelectAll").click(function(){
      $("#tree2").dynatree("getRoot").visit(function(node){
        node.select(true);
      });
      return false;
    });
  }