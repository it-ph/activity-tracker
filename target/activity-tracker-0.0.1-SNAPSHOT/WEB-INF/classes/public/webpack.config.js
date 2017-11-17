var webpack = require('webpack');
	module.exports = {
		context: __dirname + '/app/ng',
		entry: {
		app: './app.js',
		vendor: ['angular']
	},
	output: {
		path: __dirname + '/app/js/client',
		filename: "[name].js"
	},	
	plugins: [new webpack.optimize.CommonsChunkPlugin("vendor.bundle")]
};