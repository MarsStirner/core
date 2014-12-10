<?php

namespace Webmis\Controllers\Test;

use Silex\Application;
use Silex\ControllerProviderInterface;



class TestRouter implements ControllerProviderInterface
{
	public function connect(Application $app)
	{
		function getController($shortName)
		{
			list($shortClass, $shortMethod) = explode('/', $shortName, 2);
			return sprintf('Webmis\Controllers\Test\%sController::%sAction', $shortClass, $shortMethod);
		}

		$dirRouter = $app['controllers_factory'];

		$dirRouter->get('/', getController('Test/index'));

		return $dirRouter;
	}
}
