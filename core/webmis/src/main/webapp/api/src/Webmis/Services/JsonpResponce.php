<?php

namespace Webmis\Services;

class JsonpResponce
{
    protected $callback;
    protected $app;


    public function __construct($app)
    {
        $this->app = $app;
        $callback = $app['request']->query->get('callback');
        $this->callback = $callback ? $callback : 'callback';

    }

    public function jsonp($data = null, $status = 200, $headers = array())
    {
        $json = $this->app->json($data, $status, $headers);
        return $json->setCallback($this->callback);

    }


}
