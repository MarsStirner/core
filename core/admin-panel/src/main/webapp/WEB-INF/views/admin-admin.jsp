<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<div class="row">
    <div class="panel panel-info">
        <div class="panel-body">
            <span class="h2">Версия ядра: <span class="label label-default">${version}</span></span>
        </div>
    </div>
</div>
<div class="row">
    <div class="panel panel-info">
        <div class="panel-heading">
            База данных ЛПУ
        </div>
        <div class="panel-body">
            <table class="table">
                <tr>
                    <td class="col-md-1">Адрес сервера баз данных:</td>
                    <td class="col-md-3"><b>${s11r64Db.getUrl()}</b></td>
                </tr>

                <tr>
                    <td>Имя базы данных:</td>
                    <td><b>${s11r64Db.getName()}</b></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-heading">
            База данных настроек ядра
        </div>
        <div class="panel-body">
            <table class="table">
                <tr>
                    <td class="col-md-1">Адрес сервера баз данных:</td>
                    <td class="col-md-3"><b>${tmisCoreDb.getUrl()}</b></td>
                </tr>

                <tr>
                    <td>Имя базы данных:</td>
                    <td><b>${tmisCoreDb.getName()}</b></td>
                </tr>
            </table>
        </div>
    </div>
</div>