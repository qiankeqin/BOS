<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>01-layout</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>

</head>
<body class="easyui-layout">
	<div title="bos管理系统" style="height:100px" data-options="region:'north'">北部区域</div>
	<div title="菜单" style="width:200px" data-options="region:'west'">
		<!-- 制作accordion折叠面板
			fit:true----自适应（填充父容器）
		 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div title="面板一" data-options="iconCls:'icon-cut'">
				<a id="button1" class="easyui-linkbutton">添加一个选项卡</a>
				<script type="text/javascript">
					$("#button1").click(function(){
						var exist = $("#mytabs").tabs("exists","系统管理");
						
						if(exist){
							$("#mytabs").tabs("select","系统管理");
						}
						else{
							$("#mytabs").tabs("add",{
								title:"系统管理",
								iconCls:'icon-edit',
								closable:true,
								content:'<iframe frameborder="0" height="100%" width="100%" src="https://www.baidu.com"></iframe>'
							});							
						}
					});
				</script>
			</div>
			<div title="面板二"></div>
			<div title="面板三"></div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div title="面板一" data-options="iconCls:'icon-cut'">
			</div>
			<!-- 配置可以进行关闭 -->
			<div title="面板二" data-options="closable:true"></div>
			<div title="面板三"></div>
		</div>
	</div>
	<div style="width:100px" data-options="region:'east'">东部区域</div>
	<div style="height:50px" data-options="region:'south'">南部区域</div>

</body>
</html>