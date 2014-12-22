<?php

namespace Webmis\Services;

use Thrift\Transport\TSocket;
use Thrift\Transport\TBufferedTransport;
use Thrift\Protocol\TBinaryProtocol;

define('__SRC__', dirname(dirname(dirname(__FILE__))));
require_once __SRC__.'/ThriftPackages/ThriftPrescriptionService/Types.php';
require_once __SRC__.'/ThriftPackages/ThriftPrescriptionService/PrescriptionExchange.php';
use ThriftPrescriptionService\PrescriptionExchangeClient;



class PrescriptionService
{
    public function __construct($host, $port)
    {
        $socket = new TSocket($host, $port);
        $socket->setSendTimeout(100000);
        $socket->setRecvTimeout(100000);
        $transport = new TBufferedTransport($socket, 1024, 1024);
        $protocol = new TBinaryProtocol($transport);
        $this->client = new PrescriptionExchangeClient($protocol);
        $transport->open();
    }

    public function getClient(){
        return $this->client;
    }


}
