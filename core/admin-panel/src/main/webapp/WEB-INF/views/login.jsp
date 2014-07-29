<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Авторизация</title>

    <link href="resources/css/select2.css" rel="stylesheet" type="text/css">

    <link href="resources/css/bootstrap-grid-and-layout.css" rel="stylesheet" type="text/css">
    <link href="resources/css/common.css" rel="stylesheet" type="text/css">
    <link href="resources/css/custom-theme/Aristo.css" rel="stylesheet" type="text/css">
    <link href="resources/css/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
    <link href="resources/css/main.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/layout.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/grid.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/uiLib.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/aside.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/popup.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/vidget.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/editform.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/treatment.css" rel="stylesheet" type="text/css">
    <link href="resources/css/modules/inspection.css" rel="stylesheet" type="text/css">

</head>
<body>
<div id="wrapper">
    <div id="main">
        <div class="ServiceLogin"><h2>ФГБУ «ФНКЦ ДГОИ им. Дмитрия Рогачева»<br>Минздрава России</h2>

            <h1>Медицинская информационная система <br> Администрирование</h1>

            <div class="LoginForm">
                <form:form method="post" commandName="userForm">
                    <ul>
                        <li>
                            <div id="auth-error" class="ToolTip" style="width: 100%; margin-left: -1.2em; margin-bottom: 1em; display: ${displayError};"><span
                                    class="Error">Введены недопустимые символы</span></div>
                        </li>
                        <li><label>Логин</label> <form:input path="username" type="text" name="login" placeholder="Логин"/></li>
                        <li><label>Пароль</label> <form:input path="password" type="password" name="password" placeholder="Пароль"/></li>
                    </ul>
                    <div class="SubmitLine">
                        <button class="Submit ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span
                                class="ui-button-text"><span>Войти</span></span></button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<%--<div align="center">
    <form:form action="register" method="post" commandName="userForm">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Spring MVC Form Demo - Registration</h2></td>
            </tr>
            <tr>
                <td>User Name:</td>
                <td><form:input path="username" /></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><form:password path="password" /></td>
            </tr>
            <tr>
                <td>E-mail:</td>
                <td><form:input path="email" /></td>
            </tr>
            <tr>
                <td>Birthday (mm/dd/yyyy):</td>
                <td><form:input path="birthDate" /></td>
            </tr>
            <tr>
                <td>Profession:</td>
                <td><form:select path="profession" items="${professionList}" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Register" /></td>
            </tr>
        </table>
    </form:form>
</div>--%>
</body>
</html>