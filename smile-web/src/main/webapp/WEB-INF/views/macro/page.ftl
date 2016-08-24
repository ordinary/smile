<#macro page>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--360浏览器优先以webkit内核解析-->
    <title>H+ 后台主题UI框架 - 主页示例</title>
    <link rel="shortcut icon" href="favicon.ico"> <link href="${base}/resources/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="${base}/resources/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${base}/resources/css/animate.min.css" rel="stylesheet">
    <link href="${base}/resources/css/style.min.css?v=4.0.0" rel="stylesheet">
    <link href="${base}/resources/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${base}/resources/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${base}/resources/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
</head>
<body>
<script src="${base}/resources/js/jquery.min.js?v=2.1.4"></script>
<script src="${base}/resources/js/bootstrap.min.js?v=3.3.5"></script>
<script src="${base}/resources/js/plugins/layer/layer.js"></script>
<#--<script src="${base}/resources/js/content.min.js"></script>-->
<#--<script src="${base}/resources/js/welcome.min.js"></script>-->
<script src="${base}/resources/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${base}/resources/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${base}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${base}/resources/js/plugins/bootstrap-table/bootstrap-table-default.js"></script>
<script src="${base}/resources/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${base}/resources/js/common.js"></script>
	<#nested>
</body>
</html>
</#macro>