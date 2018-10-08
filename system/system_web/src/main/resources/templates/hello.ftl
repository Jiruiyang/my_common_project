<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <title>hello</title>
    <script type="text/javascript" src="${base.contextPath}/js/jquery-1.12.4.min.js"></script>

</head>
<body>
<button id="button1" type="button">Click Me!</button>
</body>
<script>
    $(function () {
        $("#button1").click(function () {
            $.ajax({
                type: "POST",
                url: "/system/user/findStudent",
                data: "",
                success: function (data) {
                    var exportPath = window.location.protocol + "//" + window.location.host + "/system";
                    window.location.href = exportPath + "/user/export";
                },
                fail: function (data) {
                    alert(2);
                }
            });
        });
    });
</script>
</html>