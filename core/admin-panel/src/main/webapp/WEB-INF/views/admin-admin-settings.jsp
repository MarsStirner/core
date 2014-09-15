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
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                    <button type="reset" class="btn btn-default">Отмена</button>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">Path</label>

                    <div class="col-sm-10">
                        <label class="col-sm-2 control-label">Value</label>
                    </div>
                </div>
                <c:forEach items="${tmisSettings.settings}" var="setting" varStatus="status">
                    <div class="form-group">
                        <label for="input${status.index}" class="col-sm-2 control-label">${setting.path}</label>

                        <div class="col-sm-10">
                            <form:input path="settings[${status.index}].value" cssClass="form-control" id="input${status.index}"/>
                        </div>
                    </div>
                </c:forEach>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                    <button type="reset" class="btn btn-default">Отмена</button>
                </div>
            </form:form>
        </div>
    </div>
</div>