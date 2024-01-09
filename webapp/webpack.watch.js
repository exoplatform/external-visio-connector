const path = require('path');
const { merge } = require('webpack-merge');
const webpackProductionConfig = require('./webpack.prod.js');
module.exports = merge(webpackProductionConfig, {
  mode: 'development',
  output: {
    path: '/exo-server/webapps/external-visio/',
    filename: 'js/[name].bundle.js'
  }
});