<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<div class="row">
    <div class="col-xs-4">
        <div class="panel panel-info">
            <div class="panel-heading">
                ЛПУ
            </div>
            <div class="panel-body">
                <form:form method="POST" modelAttribute="commonSettings" role="form">
                    <div class="row">
                        <div class="form-group">
                            <div class="col-xs-4">
                                <form:label path="orgId" placeholder=".col-xs-1">ЛПУ id</form:label>
                                <form:input path="orgId" cssClass="form-control" placeholder=".col-xs-1"/>
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
</div>