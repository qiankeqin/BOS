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
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

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
			<div title="面板二">
				<!-- 展示ztree效果：使用标准json数据构造ztree -->
				<ul id="ztree1" class="ztree">
					<script type="text/javascript">
						$(function(){
							//页面加载完成后，执行这段代码——动态创建ztree
							var setting = {};
							//构造节点的数据
							var zNodes = [
							              //每个json对象表示一个节点数据
							              {"name":"节点1","children":[
							                                        {"name":"节点1_1"},
							                                        {"name":"节点2_2"}
							                                        ]},
							              {"name":"节点2"},
							              {"name":"节点3"}
							              ];
							//调用api，初始化ztree
							$.fn.zTree.init($("#ztree1"),setting,zNodes);
						});
					</script>
				</ul>
			</div>
			<div title="面板三">
				<!-- 展示ztree效果：使用标准json数据构造ztree -->
				<ul id="ztree2" class="ztree">
					<script type="text/javascript">
						$(function(){
							//页面加载完成后，执行这段代码——动态创建ztree
							var setting2 = {
									data:{
										simpleData:{
											enable:true //设置使用简单json数据构造ztree节点
										}
									}
									
							};
							//构造节点的数据
							var zNodes2 = [
											{"id":"1","pId":"2","name":"节点1"},
											{"id":"2","pId":"3","name":"节点2"},
											{"id":"3","pId":"0","name":"节点3"}
							              ];
							//调用api，初始化ztree
							$.fn.zTree.init($("#ztree2"),setting2,zNodes2);
						});
					</script>
				</ul>
			</div>
			<div title="面板四">
				<!-- 展示ztree效果：使用标准json数据构造ztree -->
				<ul id="ztree3" class="ztree">
					<script type="text/javascript">
						$(function(){
							//页面加载完成后，执行这段代码——动态创建ztree
							var setting3 = {
									data:{
										simpleData:{
											enable:true //设置使用简单json数据构造ztree节点
										}
									},
									callback:{
										//为ztree的节点绑定事件
										onClick:function(event,treeId,treeNode){
											if(treeNode.page!=undefined){
												var e = $("#mytabs").tabs("exists",treeNode.name);
												if(e){
													$("#mytabs").tabs("select",treeNode.name);
												}else{
													//动态添加选项卡
													$("#mytabs").tabs("add",{
														title:treeNode.name,
														closable:true,
														content:'<iframe frameborder="0" width="100%" height="100%" src="'+treeNode.page+'"></iframe>'
													});
												}
												
											}
										}
									}
									
							};
							//构造节点的数据
							//发送ajax请求，获取json数据
							var url="${pageContext.request.contextPath}/json/menu.json";
							$.post(url,{},function(data){
								//调用api，初始化ztree
								$.fn.zTree.init($("#ztree3"),setting3,data);
							},"json");
							
						});
					</script>
				</ul>
			</div>
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