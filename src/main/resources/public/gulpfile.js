var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');


gulp.task('compileClient', function() {
    return gulp
	    	.src('app/ng/**/*.js')
	    	.pipe(concat('client.js'))
	    	.pipe(uglify())		        
	        .pipe(gulp.dest('client'));
});

gulp.task('compileVendor', function() {
    return gulp
	    	.src([
	    		  'app/vendor/angular/angular.js',
	    		  'app/vendor/angular-ui-router/release/angular-ui-router.js',
	    		  'app/vendor/angular-cookies/angular-cookies.js',
	    		  'app/vendor/angular-ui-bootstrap/dist/ui-bootstrap-tpls.js',
	    		  'app/vendor/js-custom-select/js/customSelect.js'
	    		])
	        .pipe(concat('vendor.js'))
	        .pipe(uglify())
	        .pipe(gulp.dest('app/vendor'));
});