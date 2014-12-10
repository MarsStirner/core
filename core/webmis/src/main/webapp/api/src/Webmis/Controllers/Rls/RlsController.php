<?php
namespace Webmis\Controllers\Rls;

// use \Propel;
// use \PDO;
// use \BasePeer;
// use \Criteria;
use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
// use Webmis\Models\DrugComponentQuery;
use Webmis\Models\RlsBalanceOfGoodsQuery;
use Webmis\Models\rlsNomenQuery;

class RlsController
{

    public function listAction(Request $request, Application $app)
    {

        $page = (int) $request->get('page') ?: 1;
        $limit = (int) $request->get('limit') ?: 20;
        $name = $request->get('name');
        $material = $request->get('material');

        $data = array();

        $items = rlsNomenQuery::create()
        ->useRlsTradeNameQuery()
            ->_if($name)
                ->filterByLocalName($name.'%')
            ->_endIf()
            ->orderByLocalName()
        ->endUse()
        ->with('rlsTradeName')

        ->useRlsActMattersQuery()
            ->_if($material)
                ->filterByName($material.'%')
            ->_endIf()
        ->endUse()
        ->with('rlsActMatters')

        ->leftJoinWithrlsForm()
        ->leftJoinWithrlsFilling()
        ->leftJoinWithrlsPacking()
        ->leftJoinWithrbUnitRelatedByunitId()

        ->paginate($page, $limit);

        foreach ($items as $item) {
            $i = array(
                'id' => null,
                'tradeName' => '',
                'tradeLocalName' => '',
                'formName' => '',
                'packingName' => '',
                'actMattersName' => '',
                'fillingName' => '',
                'unitCode' => '',
                'unitName' => '',
                'dosageValue' => ''
                );

            $i['id'] = $item->getId();

            if($item->getRlsTradeName()){
                $i['tradeName'] = $item->getRlsTradeName()->getName();
                $i['tradeLocalName'] = $item->getRlsTradeName()->getLocalName();
            }

            if($item->getRlsForm()){
                $i['formName'] = $item->getRlsForm()->getName();
            }

            if ($item->getRlsPacking()) {
                $i['packingName'] = $item->getRlsPacking()->getName();
            }

            if($item->getRlsActMatters()){
                $i['actMattersName'] = $item->getRlsActMatters();
            }

            if($item->getRlsFilling()){
                $i['fillingName'] = $item->getRlsFilling();
            }

            if($item->getRbUnitRelatedByunitId()){
                $i['unitCode'] = $item->getRbUnitRelatedByunitId()->getCode();
                $i['unitName'] = $item->getRbUnitRelatedByunitId()->getName();
            }

            $i['dosageValue'] = $item->getDosageValue();


            $data[] = $i;
        }

        return $app['jsonp']->jsonp(array(
            'data' => $data,
            'request' => array(
                'page' => $page
                ,'limit' => $limit
                ,'count'=> $items->getNbResults()
                )
            ));
    }

    public function readAction(Request $request, Application $app)
    {
        return $app['jsonp']->jsonp(array('message' => 'тут ничего нет, иди домой' ));
    }

    public function balanceAction(Request $request, Application $app)
    {
        $route_params = $request->get('_route_params') ;
        $nomenIds = explode(',',$route_params['nomenId']);
        $data = array();
        $client = $app['prescriptionExchange']->getClient();

        $responce = $client->updateBalanceOfGoods($nomenIds);


        $balance = RlsBalanceOfGoodsQuery::create()
        ->leftJoinWithRbStorage('storage')
        ->filterByRlsNomenId($nomenIds)
        ->useRlsNomenQuery('nomen')
            ->leftJoinRlsTradeName('tradeName')
            ->leftJoinRlsActMatters('actMatters')
            ->leftJoinrbUnitRelatedByunitId('rbUnit')
            ->leftJoinRlsForm('rlsForm')
        ->endUse()
        ->with('nomen')
        ->with('rbUnit')
        ->with('tradeName')
        ->with('actMatters')
        ->with('rlsForm')

        ->leftJoinWithRbStorage()
        ->find();

        foreach ($balance as $item) {
            $rlsNomen = $item->getRlsNomen();
            $rbUnit = $rlsNomen->getRbUnitRelatedByunitId();
            if($rbUnit){
                $unitName = $rbUnit->getName();
                $unitId = $rbUnit->getId();
            }else{
                $unitName = ''; 
                $unitId = ''; 
            }

            $tradeLocalName = $rlsNomen->getRlsTradeName()->getLocalName();
            $tradeName = $rlsNomen->getRlsTradeName()->getName();
            $rlsActMatters = $rlsNomen->getRlsActMatters();
            $actMattersName = $rlsActMatters->getName(); 
            $actMattersLocalName = $rlsActMatters->getLocalName(); 
            $drugLifeTime = $rlsNomen->getDrugLifeTime();

            $storage = $item->getRbStorage();
            $storageName = $storage->getName();
            $storageOrgstructureId = $storage->getOrgstructureId();
            $form = $rlsNomen->getRlsForm();
            $formName = $form->getName();
            $dosageValue = $rlsNomen->getDosageValue();

            array_push($data, array_merge(
                $item->toArray(),
                array('drugLifeTime' => $drugLifeTime,
                'storageName' => $storageName,
                'storageOrgstructureId' => $storageOrgstructureId,
                'tradeLocalName' => $tradeLocalName,
                'tradeName' => $tradeName,
                'actMattersName' => $actMattersName,
                'actMattersLocalName' => $actMattersLocalName,
                'formName' => $formName,
                'dosageValue' => $dosageValue,
                'unitName' => $unitName,
                'unitId' => $unitId)
                ));
        }



        return $app['jsonp']->jsonp(array('data' => $data, 'trift'=>$responce ));
    }




}
