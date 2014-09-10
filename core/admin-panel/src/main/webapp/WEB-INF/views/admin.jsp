<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="utf-8">
    <title>ТМИС: Администрирование. ${state.getTitle()}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ТМИС: Администрирование">
    <meta name="author" content="ВТР">
    <link href="resources/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="resources/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="resources/bootstrap/3.1.0/js/bootstrap.min.js"></script>
    .attr .lend{}
</head>

<body>
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">

        <ul class="nav navbar-left navbar-nav">
            <li>
                <a class="navbar-brand" href="/admin/info" title="Общая информация о системе">ФТМИС</a>
            </li>
            <li>
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Настройка инеграции с другими подсистемами">Интеграции<b
                        class="caret"></b></a>
                <ul class="dropdown-menu global_nav">
                    <li>
                        <a href="/admin/integration/pharmacy">1C Аптека</a>
                    </li>
                    <li>
                        <a href="/admin/integration/trfu">ТРФУ</a>
                    </li>
                    <li>
                        <a href="/admin/integration/risar">РИСАР</a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="/admin/settings" title="Все настройки (из табл. tmis_core.setting)">Настройки</a>
            </li>
        </ul>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="jumbotron">
            <h3>${state.getTitle()}</h3>
        </div>
    </div>
    <div class="row">
        <jsp:include page="admin${state.getJspPath()}.jsp" />
    </div>
</div>

</body>

</html>