<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                Домен
            </div>
            <div class="panel-body">
                <table class="table">
                    <tr>
                        <td class="col-md-1">Версия ядра:</td>
                        <td class="col-md-3"><h4><strong><span class="label label-primary sp">${version}</span></strong></h4></td>
                    </tr>

                    <tr>
                        <td>Домашняя папка домена:</td>
                        <td><b>${domainPath}</b></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-4">
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
    </div>
    <div class="col-md-4">
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
</div>