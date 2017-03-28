<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/SQLRunner/static/css/w3css.css" />
<script src="/SQLRunner/static/js/jquery-3.1.1.js"></script>
<script src="/SQLRunner/static/js/jquery.number.js"></script>
<title>SQL Runner</title>
<style type="text/css">
	div[data-name='result']{ width: 37.5em; height: 20em; border: 1px solid black; overflow: scroll; overflow-x:auto;overflow-y:auto;}
	dl {position: relative;}
	dt {height: 30px;float: left; left:10px; text-align:center;  width: 100px;z-index: 9;position: relative;  border: 1px solid black;}
	dd {position: absolute; left:-30px; padding-top: 30px;/* background-color: #f3f3f3;width: 150px;height: 200px; */}
	dd.hidden {display: none;}
	.clicked{background-color: #4842f4;}
	.unclicked{background-color: #42f44b;}
	li{border: 1px solid black; color:white; width:112px; text-align: center;}
	ul{position: relative; left: 20px;}
	.resultTable{position:relative; top:10px; border:1px solid black; border-collapse:collapse;}
	.resultTd{margin:10px;padding:5px;border:1px solid black;border-collapse:collapse;}
	.resultColumnTitle{background-color: yellow;}
	

</style>
</head>
<body>
	<div class="w3-container">
	</div>

	<ul class="w3-navbar">
		<li><a href="javascript:void(0)"  class="clicked" onclick="opentab('tab1')" data-tab="tab" >TAB1</a></li>
		<li><a href="javascript:void(0)"  class="unclicked" onclick="opentab('tab2')" data-tab="tab" >TAB2</a></li>
		<li><a href="javascript:void(0)"  class="unclicked" onclick="opentab('tab3')" data-tab="tab" >TAB3</a></li>
		<li><a href="javascript:void(0)"  class="unclicked" onclick="opentab('tab4')" data-tab="tab" >TAB4</a></li>
		<li><a href="javascript:void(0)"  class="unclicked" onclick="opentab('tab5')" data-tab="tab" >TAB5</a></li>
	</ul>

	<div id="tab1" class="w3-container tab">
		<table>
			<tr>
				<td>
					<select name="key" data-group="group1">
					<c:forEach var="key" items="${keys}">
						<option value="${key}">${key}</option>
					</c:forEach>
					</select>
				</td>
				<td>
					최대 조회 건수 : <input type="text" name="limit" data-group="group1" value="100"/>
				</td>
				<td>
					총 건수 여부 : <input type="checkbox" name="isGetTotal"  data-group="group1" checked="checked"/>
				</td>
				<td>
					<button data-group="group1" >실행</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"><textArea name="query"  data-group="group1" cols="60" rows="5"></textArea></td>
			</tr>
			<tr>
				<td colspan="4"><div data-group="group1" data-name='result'></div></td>
			</tr>
		</table>
	</div>

	<div id="tab2" class="w3-container tab" style="display: none">
		<table>
			<tr>
				<td>
					<select name="key" data-group="group2">
					<c:forEach var="key" items="${keys}">
						<option value="${key}">${key}</option>
					</c:forEach>
					</select>
				</td>
				<td>
					최대 조회 건수 : <input type="text" name="limit" data-group="group2" value="100"/>
				</td>
				<td>
					총 건수 여부 : <input type="checkbox" name="isGetTotal"  data-group="group2" checked="checked"/>
				</td>
				<td>
					<button data-group="group2" >실행</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"><textArea name="query"  data-group="group2" cols="60" rows="5"></textArea></td>
			</tr>
			<tr>
				<td colspan="4"><div data-group="group2" data-name='result'></div></td>
			</tr>
		</table>
	</div>

	<div id="tab3" class="w3-container tab" style="display: none">
		<table>
			<tr>
				<td>
					<select name="key" data-group="group3">
					<c:forEach var="key" items="${keys}">
						<option value="${key}">${key}</option>
					</c:forEach>
					</select>
				</td>
				<td>
					최대 조회 건수 : <input type="text" name="limit" data-group="group3" value="100"/>
				</td>
				<td>
					총 건수 여부 : <input type="checkbox" name="isGetTotal"  data-group="group3" checked="checked"/>
				</td>
				<td>
					<button data-group="group3" >실행</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"><textArea name="query"  data-group="group3" cols="60" rows="5"></textArea></td>
			</tr>
			<tr>
				<td colspan="4"><div data-group="group3" data-name='result'></div></td>
			</tr>
		</table>
	</div>
	
	<div id="tab4" class="w3-container tab" style="display: none">
		<table>
			<tr>
				<td>
					<select name="key" data-group="group4">
					<c:forEach var="key" items="${keys}">
						<option value="${key}">${key}</option>
					</c:forEach>
					</select>
				</td>
				<td>
					최대 조회 건수 : <input type="text" name="limit" data-group="group4" value="100"/>
				</td>
				<td>
					총 건수 여부 : <input type="checkbox" name="isGetTotal"  data-group="group4" checked="checked"/>
				</td>
				<td>
					<button data-group="group4" >실행</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"><textArea name="query"  data-group="group4" cols="60" rows="5"></textArea></td>
			</tr>
			<tr>
				<td colspan="4"><div data-group="group4" data-name='result'></div></td>
			</tr>
		</table>
	</div>
	
	<div id="tab5" class="w3-container tab" style="display: none">
		<table>
			<tr>
				<td>
					<select name="key" data-group="group5">
					<c:forEach var="key" items="${keys}">
						<option value="${key}">${key}</option>
					</c:forEach>
					</select>
				</td>
				<td>
					최대 조회 건수 : <input type="text" name="limit" data-group="group5" value="100"/>
				</td>
				<td>
					총 건수 여부 : <input type="checkbox" name="isGetTotal"  data-group="group5" checked="checked"/>
				</td>
				<td>
					<button data-group="group5" data-name='result'>실행</button>
				</td>
			</tr>
			<tr>
				<td colspan="4"><textArea name="query"  data-group="group5" cols="60" rows="5"></textArea></td>
			</tr>
			<tr>
				<td colspan="4"><div data-group="group5" data-name='result'></div></td>
			</tr>
		</table>
	</div>

	<script>
		function opentab(tabName) {
			var i;
			var x = document.getElementsByClassName("tab");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			document.getElementById(tabName).style.display = "block";
		}
		
		//탭버튼 색변경
		$("a[data-tab='tab']").click(function(){			
			
			$("a[data-tab='tab']").each(function(i,item){
				$(item).removeClass("clicked").addClass("unclicked");
			});
			
			$(this).removeClass("unclicked").addClass("clicked");
		});
		
		$(function(){
			$("input[name='limit']").number(true,0);
			
			//결과 탭 보여주기 처리
			$(document).on('click', 'dt', function(){
				 
				 var $dataGroup=$(this).attr("data-group");
				 $("dt[data-group="+$dataGroup+"]").each(function(i,item){
					 $(item).removeClass("clicked").addClass("unclicked");
				 });
				 $(this).removeClass("unclicked").addClass("clicked");
				 
				 
				 $("dd[data-group="+$dataGroup+"]").addClass('hidden');
			     $(this).next().removeClass('hidden');
			});	
			
			$("button").click(function(){
				var $dataGroup=$(this).attr("data-group");
				console.log($dataGroup);
				console.log($dataGroup.substr($dataGroup.length-1, $dataGroup.length)); //dataGroup에서 숫자만 추출
				var $query=$("textArea[data-group="+$dataGroup+"]").val();
				if(!$query||($query.trim()=="")){
					alert("값을 입력해주세요 ");
					$("textArea[data-group="+$dataGroup+"]").focus();
					return;
				}
				
				$query=$query.replace(";", "").trim().toLowerCase();
				console.log($query);
				
				var $key=$("select[data-group="+$dataGroup+"]").val();					
				var $resultDiv=$("div[data-group="+$dataGroup+"]");
				$($resultDiv).empty();
					
				var $x={};
				$x.key=$("select[data-group="+$dataGroup+"]").val(); //db 연결값
				$x.query=$query;
				$x.isTotal=$("input[type='checkbox'][data-group="+$dataGroup+"]").is(":checked");
				$x.limitRow=$("input[name='limit'][data-group="+$dataGroup+"]").val();
				if(!$x.limitRow){
					alert("최대 조회 건수를 입력해 주세요");
					return;
				}
		
				$.ajax({
	               url: '/SQLRunner/SQLProcess.do',
	               data:JSON.stringify($x),//data로 JSON 객체 문자열 형식으로 바꿔서 전달
	               success: function (data) {
	            	   var $result=JSON.parse(data);
	            	   if($result.type=="select"){
	            		    $($resultDiv).text($result.message);
	            		   	var dl=$("<dl/>").appendTo($resultDiv);
	            			var dt=$("<dt/>",{"data-group":$dataGroup,"class":"clicked"}).appendTo(dl);
	            			$(dt).text("결과");
	            			var dd=$("<dd/>",{"data-group":$dataGroup}).appendTo(dl);
	            			var table=$("<table/>",{"class":"resultTable"}).appendTo(dd);//,{"class":"data"} 삭제
	            			var firstLine=true;
	            			$($result.data).each(function(){
	            				
	            				var $property={};
            					if(firstLine==true){
            						$property.class="resultColumnTitle";	
            						firstLine=false;
            					}	
	            				var tr=$("<tr/>",$property).appendTo(table);
	            				var cnt=0;
	            				$(this).each(function(i,item){
	            					var $property={}
	            					$property.class="resultTd";
	            					
	            					var td=$("<td/>",{"class":"resultTd"}).appendTo(tr);
	            					td.text(item);
	            				});
	            			});
	            			
	            			dt=$("<dt/>",{"data-group":$dataGroup,"class":"unclicked"}).appendTo(dl);
	            			$(dt).text("Meta Data");
	            			dd=$("<dd/>",{"class":"hidden","data-group":$dataGroup}).appendTo(dl);
	            			table=$("<table/>",{"class":"resultTable"}).appendTo(dd);
	            			
	            			firstLine=true;
	            			$($result.meta).each(function(){
	            				var $property={};
            					if(firstLine==true){
            						$property.class="resultColumnTitle";	
            						firstLine=false;
            					}	
	            				var tr=$("<tr/>",$property).appendTo(table);
	            				$(this).each(function(i,item){
	            					var td=$("<td/>",{"class":"resultTd"}).appendTo(tr);
	            					td.text(item);
	            				});
	            			});
	            			
	            	   }else if($result.type=="desc"){
	            		   	var dl=$("<dl/>").appendTo($resultDiv);
	            			var dt=$("<dt/>",{"data-group":$dataGroup,"class":"clicked"}).appendTo(dl);
	            			$(dt).text("COLUMNS");
	            			var dd=$("<dd/>",{"data-group":$dataGroup}).appendTo(dl);
	            			var table=$("<table/>",{"class":"resultTable"}).appendTo(dd);//,{"class":"data"} 삭제
	            			var firstLine=true;
	            			$($result.columns).each(function(){
	            				var $property={};
            					if(firstLine==true){
            						$property.class="resultColumnTitle";	
            						firstLine=false;
            					}	
	            				var tr=$("<tr/>",$property).appendTo(table);
	            				$(this).each(function(i,item){
	            					var td=$("<td/>",{"class":"resultTd"}).appendTo(tr);
	            					td.text(item);
	            				});
	            			});
	            			
	            			dt=$("<dt/>",{"data-group":$dataGroup,"class":"unclicked"}).appendTo(dl);
	            			$(dt).text("INDEXES");
	            			dd=$("<dd/>",{"class":"hidden","data-group":$dataGroup}).appendTo(dl);
	            			table=$("<table/>",{"class":"resultTable"}).appendTo(dd);
	            			
	            			firstLine=true;
	            			$($result.indexes).each(function(){
	            				var $property={};
            					if(firstLine==true){
            						$property.class="resultColumnTitle";	
            						firstLine=false;
            					}	
	            				var tr=$("<tr/>",$property).appendTo(table);
	            				$(this).each(function(i,item){
	            					var td=$("<td/>",{"class":"resultTd"}).appendTo(tr);
	            					td.text(item);
	            				});
	            			});
	            	   }else if($result.type=="etc"){
	            		   $($resultDiv).text($result.message);
	            	   }
	            	   
	               },
	               error:function(xhr){
	            	   $($resultDiv).text(xhr.responseText);
	               },
	               type:"POST",
	               async: false
	            });            
								
			});
		});
	</script>
</body>
</html>