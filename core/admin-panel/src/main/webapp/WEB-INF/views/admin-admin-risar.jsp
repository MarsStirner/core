<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<div class="row">
    <div class="col-xs-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                Интеграция с РИСАР
            </div>
            <div class="panel-body">
                <form:form method="POST" action="risar/url" modelAttribute="risarSettings" role="form">
                    <div class="row">
                        <div class="col-xs-8">
                            <div class="form-group">
                                <form:label path="url">Базовый URL:</form:label>
                                <div class="input-group">
                                    <form:input path="url" cssClass="form-control"/>
                                    <span class="input-group-btn">
                                        <button type="submit" class="form-control btn btn-default"><span class="glyphicon glyphicon-save"/></button>
                                    </span>
                                    <span class="input-group-btn">
                                        <button type="reset" class="form-control btn btn-default"><span class="glyphicon glyphicon-remove"/></button>
                                    </span>
                                </div>
                            </div>
                            <c:if test="${risarSettings.errorMsg != null && !risarSettings.errorMsg.isEmpty()}">
                                <div class="alert alert-danger">
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                        ${risarSettings.errorMsg}
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <p class=help-block>${commonSettings.orgName}</p>
                        </div>
                    </div>
                </form:form>
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#risarActionList" data-toggle="tab">Осмотры для передачи в РИСАР</a></li>
                    <li><a href="#addActionsToRisar" data-toggle="tab">Добавить осмотры</a></li>
                </ul>
                <div id='content' class="tab-content">
                    <div class="tab-pane active" id="risarActionList">
                        <form:form method="POST" action="risar/actions/remove" modelAttribute="risarSettings" role="form">
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Удалить отмеченные осмотры</button>
                                <button type="reset" class="btn btn-default">Отмена</button>
                            </div>
                            <table class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>id осмотра</th>
                                    <th>Наименование</th>
                                    <th>Не пердавать</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${risarSettings.risarActionList}" var="risarAction" varStatus="status">
                                    <tr>
                                        <td>${risarAction.id}</td>
                                        <td>${risarAction.name}</td>
                                        <td><form:checkbox path="risarActionList[${status.index}].remove"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form:form>
                    </div>
                    <div class="tab-pane" id="addActionsToRisar">
                        <div class="panel">
                            <c:choose>
                                <c:when test="${risarSettings.risarNewActionList.isEmpty()}">
                                    <b>Подходящие осмотры не найдены.</b>
                                    <p>Типы действий, соответствующие выгружаемым осмотрам, должны иметь свойства «Диагноз» и «Рекомендации» вида:
                                    <table class="table table-bordered table-condensed">
                                        <thead>
                                        <tr>
                                            <th>Название (может быть любым)</th>
                                            <th>Тип</th>
                                            <th>code</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tr>
                                        <tr>
                                            <td>Диагноз</td>
                                            <td>MKB</td>
                                            <td>diagnosis</td>
                                        </tr>
                                        <tr>
                                            <td>Рекомендации</td>
                                            <td>Text, String или Html</td>
                                            <td>recommendations</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </p>
                                </c:when>
                                <c:otherwise>pizzas.

                                        <form:form method="POST" action="risar/actions/add" modelAttribute="risarSettings" role="form">
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-primary">Добавить отмеченные осмотры</button>
                                                <button type="reset" class="btn btn-default">Отмена</button>
                                            </div>
                                            <table class="table table-bordered table-striped">
                                                <thead>
                                                <tr>
                                                    <th>Добавить</th>
                                                    <th>id осмотра</th>
                                                    <th>Наименование</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${risarSettings.risarNewActionList}" var="risarAction" varStatus="status">
                                                    <tr>
                                                        <td><form:checkbox path="risarNewActionList[${status.index}].add"/></td>
                                                        <td>${risarAction.id}</td>
                                                        <td>${risarAction.name}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </form:form>


                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>