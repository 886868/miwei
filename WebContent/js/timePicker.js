var timePicker = new function(){
	
};
$('.srcTime').bind('focus',function(){WdatePicker()});
timePicker.toYearPicker = function (){
	var myDate = new Date();
	$('#srcStartDate').unbind('focus');
	$('#srcStartDate').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy',maxDate:myDate.getFullYear()});});
	$('#srcEndDate').unbind('focus');
	$('#srcEndDate').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy',minDate:'#F{$dp.$D(\'srcStartDate\')}',maxDate:myDate.getFullYear()});});
};
timePicker.toMonthPicker = function (){
	var date = new Date();
	var year=date.getFullYear(); 
	var month=date.getMonth()+1;
	month =(month<10 ? "0"+month:month); 
	var mydate = (year.toString()+"-"+month.toString());
	$('#srcStartDate').unbind('focus');
	$('#srcStartDate').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',maxDate:mydate});});
	$('#srcEndDate').unbind('focus');
	$('#srcEndDate').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'srcStartDate\')}',maxDate:mydate});});
};
timePicker.toDayPicker = function(){var date = new Date();
	var year=date.getFullYear(); 
	var month=date.getMonth()+1;
	month =(month<10 ? "0"+month:month); 
	var day = date.getDate();
	day = (day<10 ? "0"+day:day); 
	var mydate = (year.toString()+"-"+month.toString()+"-"+day.toString());
	$('#srcStartDate').unbind('focus');
	$('#srcStartDate').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:mydate});});
	$('#srcEndDate').unbind('focus');
	$('#srcEndDate').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'srcStartDate\')}',maxDate:mydate});});
};