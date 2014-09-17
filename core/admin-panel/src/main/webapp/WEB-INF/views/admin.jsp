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
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <link href="${pageContext.request.contextPath}/resources/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="resources/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="resources/bootstrap/3.1.0/js/bootstrap.min.js"></script>
    <style type="text/css">
        .attr-title .lend {
        }

    </style>

</head>

<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/admin" title="Общая информация о системе">ФТМИС</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/common" title="Общие настройки ЛПУ">ЛПУ</a>
                </li>
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" title="Настройка инеграции с другими подсистемами">Интеграции<b
                            class="caret"></b></a>
                    <ul class="dropdown-menu global_nav">
                        <li>
                            <a href="${pageContext.request.contextPath}/admin">1C Аптека</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/admin">ТРФУ</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/admin">РИСАР</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/settings" title="Все настройки (из табл. tmis_core.setting)">Настройки</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-lg-8 ${state.isShowTitle() ? "aaa" : "hide"}">
            <h1 class="page-header">
                ${state.getTitle()}
            </h1>
        </div>
    </div>
    <div class="row">
        <jsp:include page="admin${state.getJspPath()}.jsp"/>
    </div>
</div>

</body>

</html>