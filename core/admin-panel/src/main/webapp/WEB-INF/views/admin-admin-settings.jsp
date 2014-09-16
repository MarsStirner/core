<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">
            Все настройки (таблица tmis_core.Setting)
        </div>
        <div class="panel-body">
            <form:form method="POST" modelAttribute="tmisSettings" role="form" cssClass="form-horizontal">
                <div class="form-group">
                    <button type="reset" class="btn btn-default  pull-right">Отмена</button>
                    <button type="submit" class="btn btn-primary pull-right">Сохранить</button>
                </div>
                <c:forEach items="${tmisSettings.settings}" var="setting" varStatus="status">
                    <div class="form-group">
                        <label for="input${status.index}" class="col-sm-4 control-label">${setting.path} :</label>

                        <div class=${setting.isBoolean() ? "col-sm-1" : "col-sm-8"}>
                            <c:set var="settings[status.index].path" value = "${setting.path}"/>
                            <c:choose>
                                <c:when test="${setting.isBoolean()}">
                                    <form:checkbox path="settings[${status.index}].enable" cssClass="form-control" cssStyle="width:40px; height:40px;" id="input${status.index}"/>
                                </c:when>
                                <c:otherwise>
                                    <form:input path="settings[${status.index}].value" cssClass="form-control" id="input${status.index}"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
                <div class="form-group">
                    <button type="reset" class="btn btn-default  pull-right">Отмена</button>
                    <button type="submit" class="btn btn-primary pull-right">Сохранить</button>
                </div>
            </form:form>
        </div>
    </div>
</div>