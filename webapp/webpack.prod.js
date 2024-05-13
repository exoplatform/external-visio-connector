const path = require('path');
const ESLintPlugin = require('eslint-webpack-plugin');
const { VueLoaderPlugin } = require('vue-loader')

const config = {
  mode: 'production',
  context: path.resolve(__dirname, '.'),
  module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          'css-loader'
        ],
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: [
          'babel-loader',
        ]
      },
      {
        test: /\.vue$/,
        use: [
          'vue-loader'
        ]
      }
    ]
  },
  plugins: [
    new ESLintPlugin({
      files: [
        './src/main/webapp/vue-app/*.js',
        './src/main/webapp/vue-app/*.vue',
        './src/main/webapp/vue-app/**/*.js',
        './src/main/webapp/vue-app/**/*.vue',
      ],
    }),
    new VueLoaderPlugin()
  ],
  entry: {
    externalVisio: './src/main/webapp/vue-app/external-visio/main.js',
    externalVisioConnector: './src/main/webapp/vue-app/external-visio-connector/main.js',
    callButton: "./src/main/webapp/vue-app/CallButton/main.js",
  },
  output: {
    path: path.join(__dirname, 'target/external-visio/'),
    filename: 'js/[name].bundle.js',
    libraryTarget: 'amd'
  },
  externals: {
    vue: 'Vue',
    vuetify: 'Vuetify',
    jquery: '$',
  },
};

module.exports = config;
