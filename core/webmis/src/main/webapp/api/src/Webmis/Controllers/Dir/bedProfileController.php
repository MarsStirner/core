<?php
namespace Webmis\Controllers\Dir;

use Silex\Application;
use Symfony\Component\HttpFoundation\Request;
use Webmis\Models\RbHospitalBedProfileQuery;

class BedProfileController
{
    public function listAction(Request $request, Application $app)
    {

            $bedProfiles = RbHospitalBedProfileQuery::create()
            ->select(array('id', 'code','name'))
            ->find()
            ->toArray();

            return $app['jsonp']->jsonp(array('data'=>$bedProfiles));
    }
}
