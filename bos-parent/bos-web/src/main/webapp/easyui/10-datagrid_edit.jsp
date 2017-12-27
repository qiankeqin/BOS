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
		var myIndex = "";
		$(function(){
			$("#mytable").datagrid({
				//定义标题行所有的列
				columns:[[
				          {title:'编号',field:'id',checkbox:true },
				          {title:'姓名',field:'name',width:120 ,editor:{
			        	  										type:'validatebox',//编辑框类型
			        	  										options:{}
				          									} 
				          },
				          {title:'年龄',field:'age',width:100  ,editor:{
				        	  type:"numberbox",
				        	  options:{}
				          }},
				          {title:'生日',field:'birthday',width:100,editor:{
				        	  type:"datebox",
				        	  options:{}
				          }  },
				          ]],
				url:'${pageContext.request.contextPath}/json/datagrid_data.json',
				rownumbers:true,//行号
				singleSelect:true,//单选checkbox
				//按钮
				toolbar:[
				         {text:'添加',iconCls:'icon-add',handler:function(){
				        	 //为按钮绑定单击事件
				        	 myIndex = 0;
				        	 $("#mytable").datagrid('insertRow',{
				        		 index:0,
				        		 row:{
				        			 //设置新增行是的默认值，如果没有默认值，这里可为空
				        			 /* name:'new name',
				        			 age:30,
				        			 birthday:'2017-12-26' */
				        		 }
				        	 });
				        	 $("#mytable").datagrid('beginEdit',0);
				         }},
				         {text:'删除',iconCls:'icon-remove',handler:function(){
				        	 //获取选中的行数据
				        	 var rows = $("#mytable").datagrid("getSelections");
				        	 if(rows.length==1){
				        		 var row = rows[0];
				        		 //获取指定行对象的索引
				        		 myIndex = $("#mytable").datagrid("getRowIndex",row);
				        	 }else{
				        		$.messager.alert("信息","请选中数据行操作");
				        		return;
				        	 }
				        	 $("#mytable").datagrid("deleteRow",myIndex);
				        	 //$.post
				         }},
				         {text:'查询',iconCls:'icon-search'},
				         {text:'修改',iconCls:'icon-edit',handler:function(){
				        	 //获取选中的行数据
				        	 var rows = $("#mytable").datagrid("getSelections");
				        	 if(rows.length==1){
				        		 var row = rows[0];
				        		 //获取指定行对象的索引
				        		 myIndex = $("#mytable").datagrid("getRowIndex",row);
				        	 }
				        	 $("#mytable").datagrid("beginEdit",myIndex);//指定编辑第0行
				         }},
				         {text:'保存',iconCls:'icon-save',handler:function(){
				        	 $("#mytable").datagrid("endEdit",myIndex);//结束编辑第0行
				         }}
				         ],
				//显示分页条
				pagination:true,
				pageList:[3,5,7,9],
				//数据表格提供的用于监听结束编辑事件的方法
				onAfterEdit:function(index,data,changes){
					console.info(data);
					alert("结束编辑了："+data.name);
				}
			})
		})
	</script>
	
	
</body>
</html>