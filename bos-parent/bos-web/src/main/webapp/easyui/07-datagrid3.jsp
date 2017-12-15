<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<!-- 发送ajax请求获取json数据创建datagrid-->
	<table id="mytable">
	</table>
	
	<!-- 使用easyUI提供的API创建datagrid -->
	<script type="text/javascript">
		$(function(){
			$("#mytable").datagrid({
				//定义标题行所有的列
				columns:[[
				          {title:'编号',field:'id',checkbox:true },
				          {title:'姓名',field:'name'  },
				          {title:'年龄',field:'age'  },
				          {title:'地址',field:'address'  },
				          ]],
				url:'${pageContext.request.contextPath}/json/datagrid_data.json',
				rownumbers:true,//行号
				singleSelect:true,//单选checkbox
				//按钮
				toolbar:[
				         {text:'添加',iconCls:'icon-add',handler:function(){
				        	 //为按钮绑定单击事件
				        	 alert("add");
				         }},
				         {text:'删除',iconCls:'icon-remove'},
				         {text:'查询',iconCls:'icon-search'},
				         {text:'修改',iconCls:'icon-edit'},
				         ],
				//显示分页条
				pagination:true
			})
		})
	</script>
	
	
</body>
</html>