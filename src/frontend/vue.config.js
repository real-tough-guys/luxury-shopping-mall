const path = require("path");

module.exports = {
    transpileDependencies: ["vuetify"],

    devServer: {
        proxy: {
            "/": {
                target: "http://localhost:8080",
                ws: true,
                changeOrigin: true
            }
        },
        port: 9000
    },
    outputDir: path.resolve("__dirname", "../../main/resources/static"),
};
