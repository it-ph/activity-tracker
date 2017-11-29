var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var minify = require('gulp-minify');

gulp.task('compileClient', function() {
    return gulp
	    	.src('app/ng/**/*.js')
	    	.pipe(concat('client.js'))
	    	.pipe(uglify())		        
	        .pipe(gulp.dest('app/client'));
});

gulp.task('compileVendor', function() {
    return gulp
	    	.src([
	    		  'app/vendor/angular/angular.js',
	    		  'app/vendor/angular-ui-router/release/angular-ui-router.js',
	    		  'app/vendor/angular-cookies/angular-cookies.js',
	    		  'app/vendor/angular-ui-bootstrap/dist/ui-bootstrap-tpls.js',
	    		  'app/vendor/angular-ui-bootstrap-datetimepicker/datetimepicker.js',
	    		  'app/vendor/js-custom-select/js/customSelect.js',
	    		  'app/vendor/moment/moment.js'
	    		])
	        .pipe(concat('vendor.js'))
	        .pipe(uglify())
	        .pipe(gulp.dest('app/vendor'));
});

gulp.task('compileCss',function(){
	 return gulp
 	.src([
 		  'app/vendor/bootstrap/dist/css/bootstrap.css',	
 		  'app/css/main.css',
 		  'app/vendor/metisMenu/dist/metisMenu.css',
 		  'app/css/sb-admin-2.css'
 		  
 		])
     .pipe(concat('vendor.css'))
     .pipe(minify())
     .pipe(gulp.dest('app/vendor'));
});