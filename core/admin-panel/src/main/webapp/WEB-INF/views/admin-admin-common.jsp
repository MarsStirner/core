<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<div class="col-xs-4">
    <div class="panel panel-info">
        <div class="panel-heading">
            ЛПУ
        </div>
        <div class="panel-body">
            <form:form method="POST" action="common/orgid" modelAttribute="commonSettings" role="form">
                <div class="row">
                    <div class="col-xs-8">
                        <div class="form-group">
                            <form:label path="orgId">ЛПУ id</form:label>
                            <div class="input-group">
                                <form:input path="orgId" cssClass="form-control"/>
                                    <span class="input-group-btn">
                                        <button type="submit" class="form-control btn btn-primary"><span class="glyphicon glyphicon-ok"/></button>
                                    </span>
                                    <span class="input-group-btn">
                                        <button type="reset" class="form-control btn btn-default"><span class="glyphicon glyphicon-remove"/></button>
                                    </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <p class=help-block>${commonSettings.orgName}</p>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>