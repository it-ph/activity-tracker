angular
	.module('employeeTracker')
	.controller('TaskHistoryController',['$rootScope','$cookies','$scope', '$state','$stomp','$location','TaskDataOp','Access','FileSaver','Blob',
		function($rootScope,$cookies,$scope, $state,$stomp,$location,TaskDataOp,Access,FileSaver,Blob){
		
		
		$scope.success_message = '';		
		$scope.itemList =[];
		$scope.filteredList =[];
		$scope.item ={};
		
		$scope.totalItems = null;
		$scope.currentPage = 1;
		$scope.maxSize = 5;
		$scope.numPerPage = 10;
		$scope.keyword ='';
		
		$scope.numPerPageCap = [10,25,50,100];
		
		$scope.pageCap = $scope.numPerPageCap[0];
		$scope.numPerPage = $scope.numPerPageCap[0];
		
		
		$scope.error ='';
		$scope.success_msg ='';
		
		$scope.dateOptions = {
			showWeeks: false,
			startingDay: 0
		};
		
		$scope.history ={
				start: new Date(),
				end: new Date()
		};
		$scope.report ={
				title: undefined,
				tasks: []
		};
		
	
		$scope.searchItem = function(){
			
			
			TaskDataOp
				.getTaskHistory($scope.history)
				.success(function(data, status, headers, config){
					
					console.log(data);
					
					$scope.itemList = [];
					//response.data;
					$scope.report = {
							title: 'test',
							tasks: []
					};
					
					data.forEach(function(obj){
						
						s = moment(obj.start);
						e = moment(obj.end);
						
						dur = moment.duration(obj.endDate - obj.startDate);
						
						$scope.itemList.push({
							
							employee: obj.employee.firstName +' ' +obj.employee.lastName +' '+ ((obj.employee.suffix ==null) ? '':(obj.employee.suffix)),
							task: obj.task.taskName,
							start: obj.startDate,//new Date(moment.utc(obj.startDate).format("MMM DD YYYY h:mm:ss a")),
							end:   obj.endDate,//new Date(moment.utc(obj.endDate).format("MMM DD YYYY h:mm:ss a")),
							duration: (obj.endDate == null)?'N/A':moment.utc(dur.asMilliseconds()).format('HH:mm:ss')
									
						});
						
						$scope.report.tasks.push({
							employeeNumber: obj.employee.employeeNumber,
							employee: obj.employee.firstName +' ' +obj.employee.lastName +' '+((obj.employee.suffix ==null) ? '': obj.employee.suffix),
							task: obj.task.taskName,
							start: obj.startDate,
							end: obj.endDate,
							duration: (obj.endDate == null)?'N/A':moment.utc(dur.asMilliseconds()).format('HH:mm:ss')		
						});
						
					});
					
					//console.log($scope.itemList);
					
					$scope.totalItems = $scope.itemList.length;
					var begin = (($scope.currentPage - 1) * $scope.numPerPage);
					var end = begin + $scope.numPerPage;
					$scope.filteredList = $scope.itemList.slice(begin, end);
					
					
					//$scope.report = { title:'test', tasks: data };
					
					
					
				})
				.error(function(data, status, headers, config){
					//console.log(error);
				});
			
		}
		
		$scope.canDownload = function(){
			return $scope.report.tasks.length > 0;
		}
		Access.showNav(true);
		
		
		$scope.changeNumPerPage = function(){
			$scope.numPerPage = $scope.pageCap;
			refresh();
		}
		
		$scope.setPage = function (pageNo) {
			$scope.currentPage = pageNo;
		};
	
		$scope.pageChanged = function() {
			
			console.log('page changed');
			
			 var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			 var end = begin + $scope.numPerPage;
			 $scope.filteredList = $scope.itemList.slice(begin, end);
	
		};

		$scope.b64toBlob = function(b64Data, contentType, sliceSize) {
			
		    contentType = contentType || '';
		    sliceSize = sliceSize || 512;
		    var byteCharacters = atob(b64Data);
		    var byteArrays = [];
		    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
		        var slice = byteCharacters.slice(offset, offset + sliceSize);
		        var byteNumbers = new Array(slice.length);
		        for (var i = 0; i < slice.length; i++) {
		            byteNumbers[i] = slice.charCodeAt(i);
		        }
		        var byteArray = new Uint8Array(byteNumbers);
		        byteArrays.push(byteArray);
		    }
		    var blob = new Blob(byteArrays, {type: contentType});
		    
		    return blob;
		}
		
		
		$scope.download = function(){
			
			console.log($scope.report);
			
			TaskDataOp
				.downloadReport($scope.report)
				.success(function(data, status, headers, config){
					//console.log(data);
					
					blob  = $scope.b64toBlob(data.contents, 'application/octet-stream'); 									
					FileSaver.saveAs(blob,'report.xlsx');
					
				})
				.error(function(data, status, headers, config){
					console.log(data);
				});
		}
	
		
		
		function refresh(){
			$scope.totalItems = $scope.itemList.length;
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
			$scope.filteredList = $scope.itemList.slice(begin, end);
		
		}
		
	}]); 