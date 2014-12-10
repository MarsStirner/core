<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\FlatDirectoryQuery;
use Webmis\Models\FDRecordQuery;
use Webmis\Models\FDFieldValueQuery;

class fdController
{
    //список справочников
    public function indexAction(Request $request, Application $app)
    {
            $list = FlatDirectoryQuery::create()->find()->toArray();

            return $app['jsonp']->jsonp(array('data'=>$list));
    }

    //справочник
    public function listByCodeAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $code = (int) $route_params['code'];

        if(!$code){
            $error = array('message' => 'Нет кода справочника.');
            return $app['jsonp']->jsonp($error);
        }

        $records = FDRecordQuery::create()
            ->joinWith('FDFieldValueRelatedByFDRecordId')
            ->joinWith('FDFieldValueRelatedByFDRecordId.FDFieldRelatedById')
            ->filterByFlatDirectoryCode($code)
            ->orderByOrder()
            ->find();

        $directory = array();

        foreach ($records as $record) {
            $recordId = $record->getId();
            $directory[$recordId] = array('id' => $recordId);

            $values = $record->getFDFieldValuesRelatedByFDRecordId();

            foreach ($values as $value) {
                $field = $value->getFDFieldRelatedById();
                $fieldName = $field->getName();
                $fieldValue = $value->getValue();

                $directory[$recordId][$fieldName] = $fieldValue;
            }

        }

        $directory = array_values($directory);


        return $app['jsonp']->jsonp(array('data'=>$directory));
    }
}
