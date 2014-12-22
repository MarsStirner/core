<?php

use Silex\Application;


require_once __DIR__.'/../vendor/autoload.php';

$app = new Silex\Application();

require __DIR__.'/../src/app-config.php';



return $app;
