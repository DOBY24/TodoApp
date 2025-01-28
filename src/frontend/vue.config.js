// vue.config.js
/**
 * @type {import('@vue/cli-service').ProjectOptions}
 */
module.exports = {
  devServer: {
    port: 3002,
    proxy:{
      '/api':{
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
      }
    }
  }
}