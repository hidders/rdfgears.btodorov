<!DOCTYPE html>
<%@page import="nl.tudelft.wis.usem.service.ServicesUIHandler"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <!-- Le styles -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 50px;
        padding-bottom: 40px;
      }
	  #mydiv{
		position:absolute;
		top:43px;
		bottom:0px;
		width:100%;
	  }
    </style>
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    
    <script>
		function navigate(page)
		{			
			if(myframe.src){
			  myframe.src = page; }
			else if(myframe.location){
			  myframe.location.href = page; }
		}
	</script>

  </head>

  <body>

      <div class="container-fluid">
		<div class="row-fluid">
			<div class="span2 well">
				<h4>U-Sem Services</h4>
				<hr>
				<ul class="nav list">
				<%
					out.write(new ServicesUIHandler().getServicesHTML());
				%>
				</ul>
			</div>
			<div class="span10">
					<div id="mydiv">
						<iframe id="myframe" 
							height="100%" 
							src=""
							frameborder="0" 
							scrolling="no"
							width="100%">
						</iframe>
					</div>
			</div>
		</div>
	  </div>

  </body>
</html>
