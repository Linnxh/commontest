<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500</title>
<script>
$(document).ready(function() {
	$("#showErrorMessageButton").click(function() {
		$("#errorMessageDiv").toggle();
	});
});
</script>
</head>
<body>
<div class="wrap">
    <p>500~~~~~~~~~</p>
<!--      <br/><a id="showErrorMessageButton"  href="javascript:void(0)">详细错误信息</a> -->
</div>
<div id="errorMessageDiv" style="display:none;">
	<pre></pre>
</div>
</body>
</html>




