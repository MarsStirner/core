<?php

namespace Webmis\Controllers\Dir;

use Silex\Application;
use Silex\ControllerProviderInterface;


class DirRouter implements ControllerProviderInterface
{
	public function connect(Application $app)
	{
		function getController($shortName)
		{
			list($shortClass, $shortMethod) = explode('/', $shortName, 2);
			return sprintf('Webmis\Controllers\Dir\%sController::%sAction', $shortClass, $shortMethod);
		}

		$dirRouter = $app['controllers_factory'];

		//справочник QuotaType
		$dirRouter->get('/quotaType', getController('QuotaType/list'));

		//справочник rbResult
		$dirRouter->get('/result', getController('rbResult/list'));

		//справочник RbMethodOfAdministrationController
		$dirRouter->get('/administration', getController('rbMethodOfAdministration/list'));

		//справочник rbTreatment
		$dirRouter->get('/treatment', getController('rbTreatment/list'));

		//справочник rbPacientModel
		$dirRouter->get('/pacient_model', getController('rbPacientModel/list'));

		//справочник флат директори
		$dirRouter->get('/fd', getController('fd/index'));

		$dirRouter->get('/fd/{code}', getController('fd/listByCode'));

        $dirRouter->get('/bed_profile', getController('bedProfile/list'));

        $dirRouter->get('/settings', getController('settings/list'));

		return $dirRouter;
	}
}
