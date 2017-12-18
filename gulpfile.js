//gulp 依赖模块引入
var gulp = require('gulp'),
    browserSync = require('browser-sync').create(),
    autoprefixer = require('gulp-autoprefixer')


// 默认任务
gulp.task('default', ['browserSync']);


// 监听任务 创建一个broswerSync任务，我们需要告知它，根目录在哪里
gulp.task('browserSync', function () {
    // 建立浏览器自动刷新服务器
    browserSync.init({
        port:3000,
        server: {
            baseDir: "src",
            index: "/main/webapp/index.html"
        }
    });

    // 保存自动刷新浏览器
    gulp.watch('src/main/webapp/**', function () {
        browserSync.reload();
    });

});

/* 自动添加css兼容前缀任务*/
gulp.task('autoprefixer', function () {
    gulp.src('src/main/webapp/css/**')
        .pipe(autoprefixer({
            browsers: ['last 2 versions', 'Android >= 4.0'],//last 2 versions: 主流浏览器的最新两个版本   Android for Android WebView.
            cascade: true, //是否美化属性值 默认：true 像这样：
            //-webkit-transform: rotate(45deg);
            //        transform: rotate(45deg);
            remove: true //是否去掉不必要的前缀 默认：true
        }))
        .pipe(gulp.dest('src/main/webapp/css/'));
});




