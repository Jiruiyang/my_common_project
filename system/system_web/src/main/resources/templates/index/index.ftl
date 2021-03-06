<html>
<head>
    <meta charset="utf-8">
    <title>在线示例 - layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="stylesheet" href="${base.contextPath}/third/layui/css/layui.css" media="all">
</head>
<body>
<div class="layui-layout-admin">
    <div class="layui-header">
        <ul class="layui-nav layui-bg-cyan" lay-filter="">
            <li class="layui-nav-item"><a href="">最新活动</a></li>
            <li class="layui-nav-item layui-this"><a href="">产品</a></li>
            <li class="layui-nav-item"><a href="">大数据</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">解决方案</a>
                <dl class="layui-nav-child"> <!-- 二级菜单 -->
                    <dd><a href="">移动模块</a></dd>
                    <dd><a href="">后台模版</a></dd>
                    <dd><a href="">电商平台</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">社区</a></li>
        </ul>
    </div>
</div>
<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
</body>
</html>