<?php

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\ParameterBag;

use Webmis\Controllers\Dir\DirRouter;
use Webmis\Controllers\Appeal\AppealRouter;
use Webmis\Controllers\Therapy\TherapyRouter;
use Webmis\Controllers\Patients\PatientsRouter;
use Webmis\Controllers\Action\ActionRouter;
use Webmis\Controllers\Event\EventRouter;
use Webmis\Controllers\Prescription\PrescriptionRouter;
use Webmis\Controllers\Rls\RlsRouter;
use Webmis\Controllers\Test\TestRouter;

$app->before(function (Request $request) {
    if (0 === strpos($request->headers->get('Content-Type'), 'application/json')) {
        $data = json_decode($request->getContent(), true);
        $request->request = new ParameterBag(is_array($data) ? $data : array());
    }
});


$app->mount('/api/v1/appeals', new AppealRouter());
$app->mount('/api/v1/dir', new DirRouter());
$app->mount('/api/v1/therapy', new TherapyRouter());
$app->mount('/api/v1/patients', new PatientsRouter());
$app->mount('/api/v1/actions', new ActionRouter());
$app->mount('/api/v1/events', new EventRouter());
$app->mount('/api/v1/prescriptions', new PrescriptionRouter());

$app->mount('/api/v1/rls', new RlsRouter());
$app->mount('/api/v1/test', new TestRouter());
