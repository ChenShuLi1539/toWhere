module.exports = {
  configureWebpack: {
    devServer: {
      proxy: {
        // 名字可以自定义，这里我用的是api
        '/api': {
          target: 'http://localhost:8085', // 设置你调用的接口域名和端口号 别忘了加http
          changeOrigin: true, // 这里设置是否跨域
          pathRewrite: {
            /* 重写路径，当我们在浏览器中看到请求的地址为：http://localhost:8080/api/core/getData/userInfo 时
                          实际上访问的地址是：http://121.121.67.254:8185/core/getData/userInfo,因为重写了 /api
                         */
            '^/api': ''
          }
        }
      },
      host: '0.0.0.0',
      port: 8080
    }
  }
}
